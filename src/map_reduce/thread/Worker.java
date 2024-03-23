package src.map_reduce.thread;

import java.io.Serializable;
import java.rmi.RemoteException;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.BlockingQueue;

import src.map_reduce.client.IRemoteClient;
import src.map_reduce.constant.MapReduceConstants;
import src.map_reduce.mvc.model.concurrent.IConcurrentModel;
import src.map_reduce.partitioner.PartitionerFactory;
import src.map_reduce.reduce.ReducerFactory;
import src.map_reduce.type.IKeyValue;
import src.map_reduce.type.KeyValue;

/**
 * An implementation of IWorker
 * @see src.map_reduce.thread.IWorker
 */
public class Worker<K, V> implements IWorker<K, V> {

	/**
	 * A remote client that may perform the reduction step
	 */
	private IRemoteClient<K, V> client;

	/**
	 * The thread id associated with this worker
	 */
    private int threadId;

	/**
	 * The concurrent model
	 */
	protected IConcurrentModel<K, V> model;

	/**
	 * Store the partial reduced key-value
	 */
	protected Map<K, V> partialReducedMap;

	/**
	 * A local key-value list at the current thread
	 */
	protected List<IKeyValue<K, V>> slaveKeyValueList = new LinkedList<IKeyValue<K, V>>();

	/**
	 * Constructor
	 * @param threadIndex identify the current thread
	 * @param aModel a concurrent model
	 */
	public Worker(final int threadIndex, final IConcurrentModel<K, V> aModel) {
		this.threadId = threadIndex;
		this.model = aModel;
	}

	/**
	 * Match this thread with a remote client
	 */
	@Override
	public void setClient(final IRemoteClient<K, V> aClient) {
		this.client = aClient;
	}

	/**
	 * The run method of the Runnable interface
	 * 
	 * @see Runnable
	 */
	@Override
	public void run() {
		while (true) {
			try {
				final BlockingQueue<IKeyValue<K, V>> boundedBuffer;
				boundedBuffer = this.model.getKeyValueQueue();
				final IKeyValue<K, V> item = boundedBuffer.take();
				
				if (item.getKey() != null && item.getValue() != null) {
					this.slaveKeyValueList.add(item);
				} else {
					// Partial Reduction
					this.partialReduction();
					
					// Splitting/partitioning of partially reduced map					
					this.partitionPartialReduction();
					
					// Wait at barrier for all other slaves
					this.model.getBarrier().barrier();
					
					// Full Reduction
					this.fullReduction();
					
					// Current thread finished reduction, the last thread to finish will notify the main thread
					this.model.getJoiner().finished();
					
					// Synchronized wait
					synchronized (this) {
						this.wait();
					}
				}
			} catch (InterruptedException e) {
				e.printStackTrace();
				break;
			}
		}
	}
	
	/**
	 * Perform the final reduction
	 */
	private void fullReduction() {
		final LinkedList<IKeyValue<K, V>> reductionQueue = this.model.getReductionQueueList().get(threadId);
		final Map<K, V> subReducedMap = this.performReduction(reductionQueue);
		reductionQueue.clear();
		subReducedMap.forEach((key, value) -> {
			reductionQueue.add(new KeyValue<K, V>(key, value));
		});
	}
	
	/**
	 * Perform the partial reduction
	 */
	private void partialReduction() {
		this.partialReducedMap = this.performReduction(this.slaveKeyValueList);
		this.slaveKeyValueList.clear();
	}
	
	/**
	 * Partition key-value pair to threads
	 */
	@SuppressWarnings("unchecked")
	private void partitionPartialReduction() {
		this.partialReducedMap.forEach((key, value) -> {
            final int reductionQueueId = PartitionerFactory.getPartitioner().getPartition(key, value, this.model.getNumThreads());
            final LinkedList<IKeyValue<K, V>> reductionQueue = this.model.getReductionQueueList().get(reductionQueueId);
            synchronized (reductionQueue) {
                reductionQueue.add(new KeyValue<K, V>(key, value));
            }
		});
	}

	/**
	 * Define the way that key-value pairs being reduced
	 * @param aKeyValueList a list of key-value pairs
	 * @return a map
	 */
	@SuppressWarnings("unchecked")
	private Map<K, V> performReduction(final List<IKeyValue<K, V>> aKeyValueList) {
		Map<K, V> result = null;
		if (this.client == null) {
			result = ReducerFactory.getReducer().reduce(aKeyValueList);
		} else {
			try {
				result = client.reduce(aKeyValueList);
			} catch (RemoteException e) {
				e.printStackTrace();
			}
		}
		return result;
	}

	/**
	 * Notify thread waiting on this object
	 */
    @Override
    public void workerNotify() {
        this.notify();
    }

	/**
	 * The output string have the following format: Worker{threadId}
	 */
    @Override
    public String toString() {
        return MapReduceConstants.WORKER.concat(String.valueOf(threadId));
    }

}
