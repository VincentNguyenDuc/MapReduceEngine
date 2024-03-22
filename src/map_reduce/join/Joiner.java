package src.map_reduce.join;

import src.map_reduce.constant.MapReduceConstants;

public class Joiner implements IJoiner {

    private int numThreads;
    private int numFinishedThreads = 0;

    public Joiner(final int numForkThreads) {
        this.numThreads = numForkThreads;
    }

    @Override
    public synchronized void finished() {
        this.numFinishedThreads++;
        if (this.numFinishedThreads == this.numThreads) {
            this.notify();
        }
    }

    @Override
    public synchronized void join() {
        while (this.numFinishedThreads < numThreads) {
            try {
                this.wait();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
        this.numFinishedThreads = 0;

    }

    @Override
    public String toString() {
        return MapReduceConstants.JOINER;
    }
}
