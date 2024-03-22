package src.applications.word_count;

import java.beans.PropertyChangeListener;

import src.map_reduce.map.MapperFactory;
import src.map_reduce.mvc.controller.ConcurrentController;
import src.map_reduce.mvc.controller.IController;
import src.map_reduce.mvc.model.concurrent.ConcurrentModel;
import src.map_reduce.mvc.model.concurrent.IConcurrentModel;
import src.map_reduce.mvc.view.View;
import src.map_reduce.reduce.ReducerFactory;

public class ConcurrentWordCount {
	public static void main(final String[] args) {
		try {
			// Set mapper
			MapperFactory.setMapper(WordCountMapper.INSTANCE);

			// Set reducer
			ReducerFactory.setReducer(WordCountReducer.INSTANCE);
			
			// Instantiate the model
			final IConcurrentModel<String, Integer> model = new ConcurrentModel<String, Integer>();
			
			// Instantiate the view
			final PropertyChangeListener view = new View();
			
			// Make the view an observable of the model
			model.addPropertyChangeListener(view);
			
			// Instantiate the controller
			final IController<String, Integer> controller = new ConcurrentController<String, Integer>(model);
			controller.processInput();
			
			System.exit(0);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
