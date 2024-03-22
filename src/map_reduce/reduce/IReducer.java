package src.map_reduce.reduce;

import java.io.Serializable;
import java.util.List;
import java.util.Map;

import src.map_reduce.type.IKeyValue;

public interface IReducer<K extends Serializable, V extends Serializable> {
    Map<K, V> reduce(List<IKeyValue<K, V>> keyValuePairs);
}
