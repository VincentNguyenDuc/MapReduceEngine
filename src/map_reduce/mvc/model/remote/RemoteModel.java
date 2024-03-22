package src.map_reduce.mvc.model.remote;

import java.io.Serializable;
import java.rmi.RemoteException;
import java.util.List;

import src.map_reduce.client.IRemoteClient;
import src.map_reduce.mvc.model.sequential.BaseModel;

public class RemoteModel<K extends Serializable, V extends Serializable> extends BaseModel<K, V> implements IRemoteModel<K, V> {

    @Override
    public int getNumThreads() throws RemoteException {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'getNumThreads'");
    }

    @Override
    public List<Thread> getThreads() throws RemoteException {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'getThreads'");
    }

    @Override
    public void setNumThreads(int newNumThreads) throws RemoteException {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'setNumThreads'");
    }

    @Override
    public void terminate() throws RemoteException {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'terminate'");
    }

    @Override
    public void processInputString() throws Exception {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'processInputString'");
    }

    @Override
    public void register(IRemoteClient<K, V> aClient) throws RemoteException {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'register'");
    }
    
}
