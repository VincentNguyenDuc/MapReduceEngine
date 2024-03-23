package src.map_reduce.client;

import java.rmi.Remote;
import java.rmi.RemoteException;
import java.util.List;
import java.util.Map;

import src.map_reduce.type.IKeyValue;

public interface IRemoteClient<K, V> extends Remote {
    Map<K, V> reduce(final List<IKeyValue<K, V>> keyValues) throws RemoteException;
	void quit() throws RemoteException;
	void waitForExit() throws RemoteException;
}
