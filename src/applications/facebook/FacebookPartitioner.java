package src.applications.facebook;

import java.util.List;

import src.map_reduce.partitioner.IPartitioner;

public enum FacebookPartitioner implements IPartitioner<String, List<String>> {
    INSTANCE;

    @Override
    public int getPartition(String key, List<String> value, int numberOfPartitions) {
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
