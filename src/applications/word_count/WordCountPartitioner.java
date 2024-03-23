package src.applications.word_count;

import src.map_reduce.partitioner.IPartitioner;

/**
 * A simple implementation of the partitioner interface, with String key and Integer value
 * @see src.map_reduce.partitioner.IPartitioner
 * 
 * This class follows the singleton pattern design.
 */
public enum WordCountPartitioner implements IPartitioner<String, Integer> {
    /**
     * The singleton
     */
    INSTANCE;

    /**
     * Calculate the parition number of a key-value pair based on its key
     */
    @Override
    public int getPartition(final String key, final Integer value, final int numberOfPartitions) {
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
