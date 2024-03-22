package src.map_reduce.mvc.model.concurrent;

import java.util.List;

import src.map_reduce.mvc.model.sequential.IModel;

public interface IConcurrentModel<K, V> extends IModel<K, V> {

    int getNumThreads() throws Exception;

    List<Thread> getThreads() throws Exception;

    void setNumThreads(final int newNumThreads) throws Exception;

    void terminate() throws Exception;
}