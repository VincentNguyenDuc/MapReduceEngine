package src.map_reduce.mvc.model.distributed;

import java.rmi.Remote;
import java.rmi.RemoteException;

import src.map_reduce.client.IRemoteClient;
import src.map_reduce.mvc.model.concurrent.IConcurrentModel;

public interface IRemoteModel<K, V> extends IConcurrentModel<K, V>, Remote {
    void register(final IRemoteClient<K, V> aClient) throws RemoteException;
}
