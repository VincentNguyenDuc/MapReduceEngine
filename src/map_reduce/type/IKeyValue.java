package src.map_reduce.type;

import java.io.Serializable;

/**
 * Key Value interface.
 * This interface is implemented using generic typing for better flexibility
 * 
 * @author Vincent Nguyen
 */
public interface IKeyValue<K, V> extends Serializable {
	/**
	 * Return the key of the pair
	 * @return a key with type K
	 */
	K getKey();

	/**
	 * Return the value of the pair
	 * @return a value with type V
	 */
	V getValue();

	/**
	 * Change the value of the pair
	 * @param value a new value
	 */
	void setValue(V value);
}