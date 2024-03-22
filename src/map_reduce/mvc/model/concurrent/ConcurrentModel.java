package src.map_reduce.mvc.model.concurrent;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.BlockingQueue;

import src.map_reduce.barrier.Barrier;
import src.map_reduce.barrier.IBarrier;
import src.map_reduce.constant.MapReduceConstants;
import src.map_reduce.join.IJoiner;
import src.map_reduce.join.Joiner;
import src.map_reduce.map.MapperFactory;
import src.map_reduce.mvc.model.sequential.BaseModel;
import src.map_reduce.thread.IWorker;
import src.map_reduce.thread.Worker;
import src.map_reduce.type.IKeyValue;
import src.map_reduce.type.KeyValue;

/**
 * A multhreading Model in MVC architecture for Map Reduce Engine
 */
public class ConcurrentModel<K, V> extends BaseModel<K, V> implements IConcurrentModel<K,V> {
	private int numThreads;
	private List<Thread> threads;
	private List<IWorker<K, V>> workers;
	private BlockingQueue<IKeyValue<K, V>> keyValueQueue = new ArrayBlockingQueue<IKeyValue<K, V>>(MapReduceConstants.BUFFER_SIZE);
	private List<LinkedList<IKeyValue<K, V>>> reductionQueueList = new ArrayList<LinkedList<IKeyValue<K, V>>>();
	private IJoiner joiner;
	private IBarrier barrier;

    /**
     * Get the number of worker threads
     */
    @Override
    public int getNumThreads() {
        return this.numThreads;
    }

    /**
     * Get a List of Threads
     */
    @Override
    public List<Thread> getThreads() {
        return this.threads;
    }

    /**
     * Return the joiner in Fork-Joiner
     */
    @Override
    public IJoiner getJoiner() {
        return this.joiner;
    }

    /**
     * Return the synchronisation barrier
     */
    @Override
    public IBarrier getBarrier() {
        return this.barrier;
    }

    /**
     * Return the bounded buffeer
     */
    @Override
    public BlockingQueue<IKeyValue<K, V>> getKeyValueQueue() {
        return this.keyValueQueue;
    }

    /**
     * Return the partial reduction
     */
    @Override
    public List<LinkedList<IKeyValue<K, V>>> getReductionQueueList() {
        return this.reductionQueueList;
    }

    /**
     * Initialize threads-related stuff
     */
    @Override
    public void setNumThreads(int newNumThreads) {
        final int oldNum = this.numThreads;
        this.numThreads = newNumThreads;
        try {
            this.getPropertyChangeSupport().firePropertyChange(
                    "NumThreads",
                    Integer.valueOf(oldNum),
                    Integer.valueOf(newNumThreads));

            this.forkThreads(newNumThreads);
        } catch (Exception e) {
            e.printStackTrace();
        }
        this.joiner = new Joiner(newNumThreads);
        this.barrier = new Barrier(newNumThreads);
    }

    /**
     * Interrupt forked threads
     */
    @Override
    public void terminate() {
        this.threads.forEach((thread) -> {
            thread.interrupt();
        });
    }

    /**
     * Process the string, this is basically the core of the whole engine
     */
    @Override
    public void processInputString() {
        // Initialize next round of token processing
        this.nextRound();

        // Possible unblocking of slave threads
        this.unblockThreads();

        // Produce Bounded Buffer
        this.produceBoundedBuffer();

        // End of Input
        this.enqueueEndOfInput();

        // Join
        this.joiner.join();

        // Merge partitioned results serially from reduction queues
        this.reduceFinalResult();
    }

    /**
	 * Initialization of next round of token processing:
	 * It initializes all data structures associated with processing of the tokens input in this round.
	 * This includes clearing the final Result, clearing the reduction queues (which should be empty).
	 */
	private synchronized void nextRound() {
		try {
            this.getResult().clear();
        } catch (Exception e) {
            e.printStackTrace();
        }
		this.reductionQueueList.forEach((queue) -> queue.clear());
	}
	
	/**
	 * Possible unblocking of slave threads:
	 * It invokes the slave notifying method of the Runnable of each thread.
	 * If the thread is waiting on this Runnable, then it will unblock.
	 */
	private synchronized void unblockThreads() {
		for (int i = 0; i < this.numThreads; i++) {
			final IWorker<K, V> worker = this.workers.get(i);
			synchronized (worker) {
				worker.workerNotify();
			}	
		}
	}
	
	/**
	 * Problem split:
	 * It uses the String split() function create tokens from the string.
	 * It converts each token to an instance of the Key-Value object using the map() method.
	 * 
	 * Bounded buffer production:
	 * It adds this object to the bounded buffer using the blocking put() operation,
	 * which will be consumed by the slaves.
	 * This means the master and slave threads may interleave their execution.
	 * 
	 */
	@SuppressWarnings("unchecked")
    private void produceBoundedBuffer() {
		
        try {
            final List<String> tokenStrings = Arrays.asList(this.getInputString().split(" "));
            final List<IKeyValue<K, V>> tokens = MapperFactory.getMapper().map(tokenStrings);
            // Add tokens to Bounded Buffer
            for (IKeyValue<K, V> token : tokens) {
                this.keyValueQueue.put((IKeyValue<K, V>) token);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

	}
	
	/**
	 * End of input enqueue:
	 * After all items have been enqueued, it indicates the end of input to each slave
	 * by adding a Key-Value object with the components (Null, Null) for each slave.
	 */
	private void enqueueEndOfInput() {
		for (int i = 0; i < this.numThreads; i++) {
			try {
				final IKeyValue<K, V> nullKeyValue = new KeyValue<K, V>(null, null);
				this.keyValueQueue.put(nullKeyValue);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
	}
	
	/**
	 * Merge partitioned results serially:
	 * 
	 * Serially combine into the Result property the slaves’ final reductions of their partitions in the
	 * reduction queue list.
	 * 
	 * Announce a change to the Result property to its observers. The announced old value should be null
	 * and the new property should be the Result Map.
	 */
	private void reduceFinalResult() {
		
		this.reductionQueueList.forEach((queue) -> {
			queue.forEach(item -> {
				try {
                    this.getResult().put(item.getKey(), item.getValue());
                } catch (Exception e) {
                    e.printStackTrace();
                }
			}); 
		}); 
		
		// Announce change to the result property
		try {
            this.getPropertyChangeSupport().firePropertyChange("Result", null, this.getResult());
        } catch (Exception e) {
            e.printStackTrace();
        }
	}

    /**
     * Create multiple threads from the main thread
     * @param threadsCount the number of threads to be forked
     */
    private void forkThreads(final Integer threadsCount) {
        this.threads = new ArrayList<Thread>();
        this.workers = new ArrayList<IWorker<K, V>>();
        for (int i = 0; i < threadsCount; i++) {
            final IWorker<K, V> aWorker = new Worker<K, V>(i, this);
            this.workers.add(i, aWorker);

            final Thread aThread = new Thread(aWorker);
            aThread.setName(aWorker.toString());
            this.threads.add(i, aThread);

            this.reductionQueueList.add(i, new LinkedList<IKeyValue<K, V>>());

            aThread.start();
        }

        try {
            this.getPropertyChangeSupport().firePropertyChange(
                    "Threads",
                    null,
                    this.threads);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}
