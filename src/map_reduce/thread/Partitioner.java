package src.map_reduce.thread;

public class Partitioner<K, V> implements IPartitioner<String, V> {
    private static final IPartitioner<String, Integer> PARTITIONER_INSTANCE = new Partitioner<String, Integer>();

    private Partitioner() {
    }

    public static IPartitioner<String, Integer> getInstance() {
        return PARTITIONER_INSTANCE;
    }

    @Override
    public int getPartition(final String key, final V value, final int numberOfPartitions) {
        final char firstCharacter = key.charAt(0);
        final int partitionNum;
        if (!Character.isLetter(firstCharacter)) {
            partitionNum = 0;
        } else {
            final double maxPartitionSize = Math.ceil(('z' - 'a' + 1) / (double) numberOfPartitions);
            partitionNum = (int) Math.floor((Character.toLowerCase(firstCharacter) - 'a' + 1) / maxPartitionSize);
        }
        return partitionNum;
    }
}
