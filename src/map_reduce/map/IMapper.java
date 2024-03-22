package src.map_reduce.map;

import java.util.List;

import src.map_reduce.type.IKeyValue;

public interface IMapper<K, V> {
    List<IKeyValue<K, V>> map(List<String> tokens);
}
