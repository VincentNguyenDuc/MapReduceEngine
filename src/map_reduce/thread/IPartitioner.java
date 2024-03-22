package src.map_reduce.thread;

public interface IPartitioner<K, V> {
    int getPartition(K key, V value, int numberOfPartitions);
}