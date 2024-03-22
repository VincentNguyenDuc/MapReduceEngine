package src.map_reduce.mvc.model.concurrent;

import java.util.List;

import src.map_reduce.mvc.model.sequential.BaseModel;

public class ConcurrentModel<K, V> extends BaseModel<K, V> implements IConcurrentModel<K,V> {

    @Override
    public int getNumThreads() {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'getNumThreads'");
    }

    @Override
    public List<Thread> getThreads() {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'getThreads'");
    }

    @Override
    public void setNumThreads(int newNumThreads) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'setNumThreads'");
    }

    @Override
    public void terminate() {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'terminate'");
    }

    @Override
    public void processInputString() {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'processInputString'");
    }

}
