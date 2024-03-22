package src.map_reduce.map;

import java.io.Serializable;
import java.util.List;

import src.map_reduce.type.IKeyValue;

public interface IMapper<K extends Serializable, V extends Serializable> {
    List<IKeyValue<K, V>> map(List<String> tokens);
}
