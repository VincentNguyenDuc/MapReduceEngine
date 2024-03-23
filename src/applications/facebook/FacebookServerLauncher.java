package src.applications.facebook;

import java.beans.PropertyChangeListener;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.rmi.server.UnicastRemoteObject;
import java.util.List;

import src.map_reduce.constant.MapReduceConstants;
import src.map_reduce.map.MapperFactory;
import src.map_reduce.mvc.controller.IController;
import src.map_reduce.mvc.controller.MultithreadController;
import src.map_reduce.mvc.model.distributed.IRemoteModel;
import src.map_reduce.mvc.model.distributed.RemoteModel;
import src.map_reduce.mvc.view.View;
import src.map_reduce.partitioner.PartitionerFactory;
import src.map_reduce.reduce.ReducerFactory;

public class FacebookServerLauncher {
	/**
	 * Launch the Facebook Map Reduce
	 */
	public static void main(final String[] args) {
		try {
			// Switch to Facebook Mapper
			MapperFactory.setMapper(FacebookMapper.INSTANCE);
			
			// Switch to Facebook Reducer
			ReducerFactory.setReducer(FacebookReducer.INSTANCE);

            // Switch to Facebook Partitioner
            PartitionerFactory.setPartitioner(FacebookPartitioner.INSTANCE);
			
			// Start RMI Registry
			final Registry rmiRegistry = LocateRegistry.createRegistry(Registry.REGISTRY_PORT);
			
			// Instantiate the model
			final IRemoteModel<String, List<String>> model = new RemoteModel<String, List<String>>();
			UnicastRemoteObject.exportObject(model, 0);
			rmiRegistry.rebind(MapReduceConstants.MODEL, model);
			
			// Instantiate the view
			final PropertyChangeListener view = new View();
			
			// Make the view an observable of the model
			model.addPropertyChangeListener(view);

			// Instantiate the controller
			final IController<String, List<String>> controller = new MultithreadController<String, List<String>>(model);
			controller.processInput();

			System.exit(0);
			
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
