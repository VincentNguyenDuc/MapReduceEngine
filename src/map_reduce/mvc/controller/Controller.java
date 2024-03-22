package src.map_reduce.mvc.controller;

import java.util.Scanner;

import src.map_reduce.config.MapReduceConstants;
import src.map_reduce.mvc.model.sequential.IModel;

public class Controller<K, V> implements IController<K, V> {

    private IModel<K, V> model;

	public Controller(final IModel<K, V> aModel) {
		this.model = aModel;
	}

	/**
	 * Processing input from the user
	 */
	@Override
	public void processInput() {
		final Scanner scanner = new Scanner(System.in);

		// rest of processInput - loop
		while (true) {
			System.out.println("Please enter " + MapReduceConstants.QUIT + " or a line of tokens to be processed separated by spaces");
			final String userInput = scanner.nextLine().toLowerCase();
			if ("quit".equals(userInput)) {
				break;
			}
			try {
                this.model.setInputString(userInput);
            } catch (Exception e) {
                e.printStackTrace();
            }
		}
		scanner.close();

	}
}
