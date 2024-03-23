package src.applications.word_count;

import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.rmi.server.UnicastRemoteObject;

import src.map_reduce.client.IRemoteClient;
import src.map_reduce.client.RemoteClient;
import src.map_reduce.constant.MapReduceConstants;
import src.map_reduce.mvc.model.distributed.IRemoteModel;
import src.map_reduce.reduce.ReducerFactory;

public class WordCountRemoteClientLauncher {
	/**
	 * Launch a client
	 */
	public static void main(final String[] args) {
		try {
			// Set reducer
			ReducerFactory.setReducer(WordCountReducer.INSTANCE);

			// Locate and look up the Remote Model from RMI Registry
			final Registry rmiRegistry = LocateRegistry.getRegistry(Registry.REGISTRY_PORT);
			
			@SuppressWarnings("unchecked")
			final IRemoteModel<String, Integer> model = (IRemoteModel<String, Integer>) rmiRegistry.lookup(MapReduceConstants.MODEL);
			
			// Create and register a client object with the Remote Model
			final IRemoteClient<String, Integer> aClient = new RemoteClient<String, Integer>();
			UnicastRemoteObject.exportObject(aClient, 0);
			model.register(aClient);
			
			// Make the main thread of this client wait on the client
			// The main thread will exit when the server makes the client call notify()
			aClient.waitForExit();
			System.exit(0);
			
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
