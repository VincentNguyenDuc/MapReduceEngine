package src.applications.word_count;

import java.beans.PropertyChangeListener;

import src.map_reduce.map.MapperFactory;
import src.map_reduce.mvc.controller.Controller;
import src.map_reduce.mvc.controller.IController;
import src.map_reduce.mvc.model.sequential.IModel;
import src.map_reduce.mvc.model.sequential.SequentialModel;
import src.map_reduce.mvc.view.View;
import src.map_reduce.reduce.ReducerFactory;

public class SequentialWordCount {
	public static void main(final String[] args) {
		try {
			// Set mapper
			MapperFactory.setMapper(WordCountMapper.getWordCountingMapper());

			// Set reducer
			ReducerFactory.setReducer(WordCountReducer.getWordCountingReducer());
			
			// Instantiate the model
			final IModel<String, Integer> model = new SequentialModel<String, Integer>();
			
			// Instantiate the view
			final PropertyChangeListener view = new View();
			
			// Make the view an observable of the model
			model.addPropertyChangeListener(view);
			
			// Instantiate the controller
			final IController<String, Integer> controller = new Controller<String, Integer>(model);
			controller.processInput();
			
			System.exit(0);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
