package src.map_reduce.barrier;

import src.map_reduce.constant.MapReduceConstants;

public class Barrier implements IBarrier {
    private int numThreads;
    private int numFinishThreads = 0;

    public Barrier(final int numBarriers) {
        this.numThreads = numBarriers;
    }

    @Override
    public synchronized void barrier() {
        this.numFinishThreads++;
        if (this.numFinishThreads < this.numThreads) {
            try {
                this.wait();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        } else {
            this.notifyAll();
            this.numFinishThreads = 0;
        }
    }

    @Override
    public String toString() {
        return MapReduceConstants.BARRIER;
    }
}
