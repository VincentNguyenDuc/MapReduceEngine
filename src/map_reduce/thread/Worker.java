package src.map_reduce.thread;

import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.BlockingQueue;

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
	 * The thread id associated with this worker
	 */
    private int threadId;

	/**
	 * The concurrent model
	 */
	private IConcurrentModel<K, V> model;

	/**
	 * A local key-value list at the current thread
	 */
	private List<IKeyValue<K, V>> slaveKeyValueList = new LinkedList<IKeyValue<K, V>>();

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
	 * Implementation of the run() method of the Runnable interface. The method performs:
	 * - Retrieve key-value pairs from the bounded buffer until hitting a stop pair
	 * - Perform partial reduction within the current worker
	 * - Re-partition reduced key-value pairs across all threads
	 * - Perform final reduction and write back to the model
	 */
	@SuppressWarnings("unchecked")
    @Override
	public void run() {
		while (true) {
			
			final BlockingQueue<IKeyValue<K, V>> boundedBuffer = this.model.getKeyValueQueue();
			
			try {
				final IKeyValue<K, V> item = boundedBuffer.take();
				
				if (item.getKey() != null && item.getValue() != null) {
					this.slaveKeyValueList.add(item);
				} else {
					// Partial Reduction
					final Map<K, V> partialReducedMap = ReducerFactory.getReducer().reduce(slaveKeyValueList);
					this.slaveKeyValueList.clear();
					
					// Splitting/partitioning of partially reduced map					
					partialReducedMap.forEach((key, value) -> {
						try {
							final int reductionQueueId = PartitionerFactory.getPartitioner().getPartition(key, value, this.model.getNumThreads());
							final LinkedList<IKeyValue<K, V>> reductionQueue = this.model.getReductionQueueList()
									.get(reductionQueueId);
							synchronized (reductionQueue) {
								reductionQueue.add(new KeyValue<K, V>(key, value));
							}
						} catch (Exception e) {
							e.printStackTrace();
						}
					});
					
					// Wait at barrier for all other slaves
					this.model.getBarrier().barrier();
					
					// Full Reduction
					final LinkedList<IKeyValue<K, V>> reductionQueue = this.model.getReductionQueueList().get(threadId);
					final Map<K, V> subReducedMap = ReducerFactory.getReducer().reduce(reductionQueue);
					reductionQueue.clear();
					subReducedMap.forEach((key, value) -> {
						reductionQueue.add(new KeyValue<K, V>(key, value));
					});
					
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
