package src.map_reduce.thread;

import src.map_reduce.constant.MapReduceConstants;

@SuppressWarnings("rawtypes")
public class PartitionerFactory {
    private static IPartitioner partitioner = Partitioner.getInstance();

    public static IPartitioner getPartitioner() {
        return partitioner;
    }

    public static void setPartitioner(final IPartitioner newPartitioner) {
        partitioner = newPartitioner;
    }

    @Override
    public String toString() {
        return MapReduceConstants.PARTITIONER;
    }
}
