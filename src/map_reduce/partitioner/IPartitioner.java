package src.map_reduce.partitioner;

/**
 * A partitioner interface.
 * This interface return the partition of a key-value pair.
 * @see src.map_reduce.type.IKeyValue
 */
public interface IPartitioner<K, V> {
    int getPartition(K key, V value, int numberOfPartitions);
}