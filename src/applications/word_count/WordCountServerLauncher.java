package src.applications.word_count;

import java.beans.PropertyChangeListener;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.rmi.server.UnicastRemoteObject;

import src.map_reduce.constant.MapReduceConstants;
import src.map_reduce.map.MapperFactory;
import src.map_reduce.mvc.controller.MultithreadController;
import src.map_reduce.mvc.controller.IController;
import src.map_reduce.mvc.model.distributed.IRemoteModel;
import src.map_reduce.mvc.model.distributed.RemoteModel;
import src.map_reduce.mvc.view.View;
import src.map_reduce.partitioner.PartitionerFactory;
import src.map_reduce.reduce.ReducerFactory;

public class WordCountServerLauncher {
	/**
	 * Launch the token counter server
	 */
	public static void main(final String[] args) {
		try {
			// Set mapper
			MapperFactory.setMapper(WordCountMapper.INSTANCE);

			// Set reducer
			ReducerFactory.setReducer(WordCountReducer.INSTANCE);

			// Set partitioner
			PartitionerFactory.setPartitioner(WordCountPartitioner.INSTANCE);
			
			// Start RMI Registry
			final Registry rmiRegistry = LocateRegistry.createRegistry(Registry.REGISTRY_PORT);
			
			// Instantiate the model and bind to registry
			final IRemoteModel<String, Integer> model = new RemoteModel<String, Integer>();
			UnicastRemoteObject.exportObject(model, 0);
			rmiRegistry.rebind(MapReduceConstants.MODEL, model);
			
			// Instantiate the view
			final PropertyChangeListener view = new View();
			
			// Make the view an observable of the model
			model.addPropertyChangeListener(view);
			
			// Instantiate the controller
			final IController<String, Integer> controller = new MultithreadController<String, Integer>(model);
			controller.processInput();
			
			System.exit(0);

		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
