package src.map_reduce.reduce;

import java.util.List;
import java.util.Map;

import src.map_reduce.type.IKeyValue;

/**
 * A generic reducer interface
 */
public interface IReducer<K, V> {
    /**
     * Perform reduction on a list of key-value pairs and return a Java Map
     * @see src.map_reduce.type.IKeyValue
     * @param keyValuePairs a list of key-value pairs
     * @return a map
     */
    Map<K, V> reduce(List<IKeyValue<K, V>> keyValuePairs);
}
