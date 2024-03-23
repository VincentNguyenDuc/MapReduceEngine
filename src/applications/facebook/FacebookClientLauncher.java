package src.applications.facebook;

import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.rmi.server.UnicastRemoteObject;
import java.util.List;

import src.map_reduce.client.IRemoteClient;
import src.map_reduce.client.RemoteClient;
import src.map_reduce.constant.MapReduceConstants;
import src.map_reduce.mvc.model.distributed.IRemoteModel;
import src.map_reduce.reduce.ReducerFactory;

public class FacebookClientLauncher {
	/**
	 * Launch a client
	 */
	public static void main(final String[] args) {
		try {
			// Switch to Facebook Reducer
			ReducerFactory.setReducer(FacebookReducer.INSTANCE);
			
			// Locate and look up the Remote Model from RMI Registry
			final Registry rmiRegistry = LocateRegistry.getRegistry(Registry.REGISTRY_PORT);
			@SuppressWarnings("unchecked")
			final IRemoteModel<String, List<String>> model = (IRemoteModel<String, List<String>>) rmiRegistry.lookup(MapReduceConstants.MODEL);
			
			// Create and register a client object with the Remote Model
			final IRemoteClient<String, List<String>> aClient = new RemoteClient<String, List<String>>();
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
