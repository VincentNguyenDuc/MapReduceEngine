package src.map_reduce.mvc.model.distributed;

import java.beans.PropertyChangeListener;
import java.beans.PropertyChangeSupport;
import java.rmi.Remote;
import java.rmi.RemoteException;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.BlockingQueue;

import src.map_reduce.barrier.IBarrier;
import src.map_reduce.client.IRemoteClient;
import src.map_reduce.join.IJoiner;
import src.map_reduce.mvc.model.concurrent.IConcurrentModel;
import src.map_reduce.type.IKeyValue;

public interface IRemoteModel<K, V> extends IConcurrentModel<K, V>, Remote {
    void register(final IRemoteClient<K, V> aClient) throws RemoteException;

    int getNumThreads() throws RemoteException;

    List<Thread> getThreads() throws RemoteException;

    IJoiner getJoiner() throws RemoteException;

    IBarrier getBarrier() throws RemoteException;

    void setNumThreads(final int newNumThreads) throws RemoteException;

    List<LinkedList<IKeyValue<K, V>>> getReductionQueueList() throws RemoteException;

    BlockingQueue<IKeyValue<K, V>> getKeyValueQueue() throws RemoteException;

    void terminate() throws RemoteException;

    void setInputString(final String newInput) throws RemoteException;
    
    String getInputString() throws RemoteException;

    Map<K, V> getResult() throws RemoteException;

    void setResult(final Map<K, V> aResult) throws RemoteException;

    PropertyChangeSupport getPropertyChangeSupport() throws RemoteException;

    void addPropertyChangeListener(final PropertyChangeListener aListener) throws RemoteException;

    void removePropertyChangeListener(final PropertyChangeListener aListener) throws RemoteException;
}
