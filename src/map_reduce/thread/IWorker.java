package src.map_reduce.thread;

import java.io.Serializable;

import src.map_reduce.client.IRemoteClient;

public interface IWorker<K extends Serializable, V extends Serializable> extends Runnable {
    public void workerNotify();
    public void setClient(IRemoteClient<K, V> aClient);
}