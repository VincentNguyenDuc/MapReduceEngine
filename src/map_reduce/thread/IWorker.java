package src.map_reduce.thread;

public interface IWorker<K, V> extends Runnable {
    public void workerNotify();
}