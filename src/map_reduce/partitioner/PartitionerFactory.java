package src.map_reduce.partitioner;

@SuppressWarnings("rawtypes")
public class PartitionerFactory {
    private static IPartitioner partitioner;

    public static IPartitioner getPartitioner() {
        return partitioner;
    }

    public static void setPartitioner(final IPartitioner newPartitioner) {
        partitioner = newPartitioner;
    }
}
