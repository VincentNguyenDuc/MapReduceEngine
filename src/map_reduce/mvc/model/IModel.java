package src.map_reduce.mvc.model;

import java.beans.PropertyChangeListener;
import java.rmi.Remote;
import java.rmi.RemoteException;
import java.util.List;
import java.util.Map;

public interface IModel<K, V> extends Remote {
    String getInputString() throws RemoteException;

    Map<K, V> getResult() throws RemoteException;

    void setInputString(final String newString) throws RemoteException;

    void addPropertyChangeListener(final PropertyChangeListener tokensCounterView) throws RemoteException;

    void removePropertyChangeListener(final PropertyChangeListener aListener) throws RemoteException;

    int getNumThreads() throws RemoteException;

    List<Thread> getThreads() throws RemoteException;

    void setNumThreads(final int newNumThreads) throws RemoteException;

    void terminate() throws RemoteException;
}