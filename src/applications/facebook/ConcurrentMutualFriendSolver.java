package src.applications.facebook;

import java.beans.PropertyChangeListener;
import java.util.List;

import src.map_reduce.map.MapperFactory;
import src.map_reduce.mvc.controller.MultithreadController;
import src.map_reduce.mvc.controller.IController;
import src.map_reduce.mvc.model.concurrent.ConcurrentModel;
import src.map_reduce.mvc.model.concurrent.IConcurrentModel;
import src.map_reduce.mvc.view.View;
import src.map_reduce.partitioner.PartitionerFactory;
import src.map_reduce.reduce.ReducerFactory;

public class ConcurrentMutualFriendSolver {
	public static void main(final String[] args) {

		// Switch to Facebook Mapper
		MapperFactory.setMapper(FacebookMapper.INSTANCE);

		// Switch to Facebook Reducer
		ReducerFactory.setReducer(FacebookReducer.INSTANCE);

		// Set partitioner
		PartitionerFactory.setPartitioner(FacebookPartitioner.INSTANCE);
		
		// Instantiate the model
		final IConcurrentModel<String, List<String>> model = new ConcurrentModel<String, List<String>>();
		
		// Instantiate the view
		final PropertyChangeListener view = new View();
		
		// Make the view an observable of the model
		try {
			model.addPropertyChangeListener(view);
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		// Instantiate the controller
		final IController<String, List<String>> controller = new MultithreadController<String, List<String>>(model);
		controller.processInput();
		
		System.exit(0);
	}
}
