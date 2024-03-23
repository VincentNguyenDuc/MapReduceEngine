package src.applications.facebook;

import java.beans.PropertyChangeListener;
import java.util.List;

import src.map_reduce.map.MapperFactory;
import src.map_reduce.mvc.controller.Controller;
import src.map_reduce.mvc.controller.IController;
import src.map_reduce.mvc.model.sequential.IModel;
import src.map_reduce.mvc.model.sequential.SequentialModel;
import src.map_reduce.mvc.view.View;
import src.map_reduce.reduce.ReducerFactory;

public class SequentialMutualFriendSolver {
	public static void main(final String[] args) {
		// Switch to Facebook Mapper
		MapperFactory.setMapper(FacebookMapper.INSTANCE);
		
		// Switch to Facebook Reducer
		ReducerFactory.setReducer(FacebookReducer.INSTANCE);
		
		// Instantiate the model
		final IModel<String, List<String>> model = new SequentialModel<String, List<String>>();
		
		// Instantiate the view
		final PropertyChangeListener view = new View();
		
		// Make the view an observable of the model
		model.addPropertyChangeListener(view);

		// Instantiate the controller
		final IController<String, List<String>> controller = new Controller<String, List<String>>(model);
		controller.processInput();
	}
}
