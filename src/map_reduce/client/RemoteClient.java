package src.map_reduce.client;

import java.rmi.RemoteException;
import java.util.List;
import java.util.Map;

import src.map_reduce.constant.MapReduceConstants;
import src.map_reduce.reduce.ReducerFactory;
import src.map_reduce.type.IKeyValue;

public class RemoteClient<K, V> implements IRemoteClient<K, V> {

    /**
	 *  Can be called remotely by the server runnable to which this remote client has been assigned.
	 *  Both the server and client share the same reducer and reducer factory.
	 */
	@SuppressWarnings("unchecked")
	@Override
	public Map<K, V> reduce(final List<IKeyValue<K, V>> keyValues) throws RemoteException {
		final Map<K, V> result = ReducerFactory.getReducer().reduce(keyValues);
		return result;
	}

    /**
     * Terminate the client
     */
    @Override
    public synchronized void quit() throws RemoteException {
        this.notify();
    }

    @Override
    public synchronized void waitForExit() throws RemoteException {
        try {
            this.wait();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    @Override
    public String toString() {
        return MapReduceConstants.CLIENT;
    }

}
