package src.map_reduce.mvc.model.concurrent;

import java.util.LinkedList;
import java.util.List;
import java.util.concurrent.BlockingQueue;

import src.map_reduce.barrier.IBarrier;
import src.map_reduce.join.IJoiner;
import src.map_reduce.mvc.model.sequential.IModel;
import src.map_reduce.type.IKeyValue;

public interface IConcurrentModel<K, V> extends IModel<K, V> {

    int getNumThreads();

    List<Thread> getThreads();

    IJoiner getJoiner();

    IBarrier getBarrier();

    void setNumThreads(final int newNumThreads);

    List<LinkedList<IKeyValue<K, V>>> getReductionQueueList();

    BlockingQueue<IKeyValue<K, V>> getKeyValueQueue();

    void terminate();
}