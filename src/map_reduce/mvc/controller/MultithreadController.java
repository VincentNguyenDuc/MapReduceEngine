package src.map_reduce.mvc.controller;

import java.util.Scanner;

import src.map_reduce.mvc.model.concurrent.IConcurrentModel;

public class MultithreadController<K, V> extends Controller<K, V> {

    private IConcurrentModel<K, V> concurrentModel;

    public MultithreadController(IConcurrentModel<K, V> aModel) {
        super(aModel);
        this.concurrentModel = aModel;
    }

    @Override
    public void setup(final Scanner scanner) {
        System.out.println("Please enter a number of threads:");
        // Prompt for number of threads
        final int numThreads = scanner.nextInt();
        scanner.nextLine();
        try {
            this.concurrentModel.setNumThreads(numThreads);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
