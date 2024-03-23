package src.map_reduce.mvc.model.distributed;

import java.rmi.RemoteException;
import java.util.ArrayList;
import java.util.List;
import java.util.Stack;

import src.map_reduce.client.IRemoteClient;
import src.map_reduce.mvc.model.concurrent.ConcurrentModel;
import src.map_reduce.thread.IWorker;

public class RemoteModel<K, V> extends ConcurrentModel<K, V> implements IRemoteModel<K, V> {

	private List<IRemoteClient<K, V>> clients = new ArrayList<IRemoteClient<K, V>>();
	private Stack<IRemoteClient<K, V>> availableClients = new Stack<IRemoteClient<K, V>>();
	private Stack<IWorker<K, V>> availableWorkers = new Stack<IWorker<K, V>>();
	
	@Override
	public void register(final IRemoteClient<K, V> aClient) {
		this.availableClients.push(aClient);
		this.clients.add(aClient);
		this.assignClients();
	}
	
	private void assignClients() {
		while (!this.availableClients.empty() && !this.availableWorkers.empty()) {
			final IRemoteClient<K, V> aClient = this.availableClients.pop();
			final IWorker<K, V> aWorker = this.availableWorkers.pop();
			aWorker.setClient(aClient);
		}
	}
	
    @Override
	public void setNumThreads(final int threadsCount) {
		super.setNumThreads(threadsCount);
		this.availableWorkers.addAll(super.workers);
		this.assignClients();
	}
	
	@Override
	public void terminate() {
		super.terminate();
		this.clients.forEach((client) -> {
			try {
				client.quit();
			} catch (RemoteException e) {
				e.printStackTrace();
			}
		});
	}

}
