package src.applications.word_count;

import java.beans.PropertyChangeListener;

import src.map_reduce.map.MapperFactory;
import src.map_reduce.mvc.controller.MultithreadController;
import src.map_reduce.mvc.controller.IController;
import src.map_reduce.mvc.model.concurrent.ConcurrentModel;
import src.map_reduce.mvc.model.concurrent.IConcurrentModel;
import src.map_reduce.mvc.view.View;
import src.map_reduce.partitioner.PartitionerFactory;
import src.map_reduce.reduce.ReducerFactory;

/**
 * A concurrent word counting application
 */
public class ConcurrentWordCounter {

	/**
	 * The main program
	 * @param args Command-line arguments
	 */
	public static void main(final String[] args) {
		// Set mapper
		MapperFactory.setMapper(WordCountMapper.INSTANCE);

		// Set reducer
		ReducerFactory.setReducer(WordCountReducer.INSTANCE);

		// Set partitioner
		PartitionerFactory.setPartitioner(WordCountPartitioner.INSTANCE);
		
		// Instantiate the model
		final IConcurrentModel<String, Integer> model = new ConcurrentModel<String, Integer>();
		
		// Instantiate the view
		final PropertyChangeListener view = new View();
		
		// Make the view an observable of the model
		try {
			model.addPropertyChangeListener(view);
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		// Instantiate the controller
		final IController<String, Integer> controller = new MultithreadController<String, Integer>(model);
		controller.processInput();
		
		System.exit(0);
	}
}
