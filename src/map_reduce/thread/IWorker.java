package src.map_reduce.thread;

/**
 * A wrapper of the Runnable interface
 * @see Runnable
 */
public interface IWorker<K, V> extends Runnable {
    /**
     * Notify the thread currently at the front of this object's wait queue
     */
    public void workerNotify();
}