package src.applications.word_count;

import java.beans.PropertyChangeListener;

import src.map_reduce.map.MapperFactory;
import src.map_reduce.mvc.controller.Controller;
import src.map_reduce.mvc.controller.IController;
import src.map_reduce.mvc.model.sequential.IModel;
import src.map_reduce.mvc.model.sequential.SequentialModel;
import src.map_reduce.mvc.view.View;
import src.map_reduce.reduce.ReducerFactory;

/**
 * A sequential map reduce application
 */
public class SequentialWordCounter {

	/**
	 * The main program
	 * @param args command-line arguments
	 */
	public static void main(final String[] args) {
		// Set mapper
		MapperFactory.setMapper(WordCountMapper.INSTANCE);

		// Set reducer
		ReducerFactory.setReducer(WordCountReducer.INSTANCE);
		
		// Instantiate the model
		final IModel<String, Integer> model = new SequentialModel<String, Integer>();
		
		// Instantiate the view
		final PropertyChangeListener view = new View();
		
		// Make the view an observable of the model
		try {
			model.addPropertyChangeListener(view);
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		// Instantiate the controller
		final IController<String, Integer> controller = new Controller<String, Integer>(model);
		controller.processInput();
		
		System.exit(0);
	}
}
