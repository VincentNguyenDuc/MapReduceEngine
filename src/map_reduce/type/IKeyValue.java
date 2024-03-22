package src.map_reduce.type;

import java.io.Serializable;

/**
 * Key Value interface.
 * Extending generic type from Serializable to ensure the whole class is serializable
 * 
 * @author Vincent Nguyen
 */
public interface IKeyValue<K, V> extends Serializable {
	K getKey();
	V getValue();
	void setValue(V value);
}