package src.map_reduce.type;

/**
 * A generic serializable key-value pair.
 * The key and value of an object must also be serializable.
 * 
 * @author Vincent
 */
public class KeyValue<K, V> implements IKeyValue<K, V> {

	/**
	 * The key of the object
	 */
	private K key;

	/**
	 * The value of the object
	 */
	private V value;

	/**
	 * Constructor
	 * @param aKey a serializable object used as the key
	 * @param aValue a serializable object used as the value
	 */
	public KeyValue(final K aKey, final V aValue) {
		this.key = aKey;
		this.value = aValue;
	}

	/**
	 * Retrieve the key of the object
	 */
	@Override
	public K getKey() {
		return this.key;
	}

	/**
	 * Retrieve the value of the object
	 */
	@Override
	public V getValue() {
		return this.value;
	}

	/**
	 * Setting the value of the object
	 */
	@Override
	public void setValue(final V newValue) {
		this.value = newValue;
	}

	/**
	 * The string of the object is (key,value)
	 */
	@Override
	public String toString() {
		final String keyString = String.valueOf(key);
		final String valueString = String.valueOf(value);
		return "(" + keyString + "," + valueString + ")";
	}
}