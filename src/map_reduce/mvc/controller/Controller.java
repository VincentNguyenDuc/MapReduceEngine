package src.map_reduce.mvc.controller;

import java.rmi.RemoteException;
import java.util.Scanner;

import src.map_reduce.config.Config;
import src.map_reduce.mvc.model.IModel;

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
        try {
            final Scanner scanner = new Scanner(System.in);
            // Prompt for number of threads
            final int numThreads = scanner.nextInt();
            this.model.setNumThreads(numThreads);
            scanner.nextLine();

            while (true) {
                // Process tokens
                System.out.println("Please enter the number of threads: ");
                final String userInput = scanner.nextLine().toLowerCase();
                if ("quit".equals(userInput)) {
                    this.model.terminate();
                    System.out.println("Quit");
                    break;
                }
                this.model.setInputString(userInput);
            }
            scanner.close();
        } catch (RemoteException e) {
            e.printStackTrace();
        }
    }

    @Override
    public String toString() {
        return Config.CONTROLLER;
    }
}