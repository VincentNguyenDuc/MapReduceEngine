package src.applications.word_count;

import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import src.map_reduce.constant.MapReduceConstants;
import src.map_reduce.reduce.IReducer;
import src.map_reduce.type.IKeyValue;

/**
 * A simple implementation of the reducer interface, with String key and Integer
 * value
 * 
 * @see src.map_reduce.reducer.IReducer
 * 
 * This class follows the singleton pattern design.
 */
public enum WordCountReducer implements IReducer<String, Integer> {
	/**
	 * The singleton
	 */
	INSTANCE;

	/**
	 * Perform reduction on a list of key-value pair.
	 * Return as a map
	 */
	@Override
	public Map<String, Integer> reduce(final List<IKeyValue<String, Integer>> keyValuePairs) {
		final Map<String, Integer> result = new HashMap<>();
		final Iterator<IKeyValue<String, Integer>> iterator = keyValuePairs.iterator();
		while (iterator.hasNext()) {
			final IKeyValue<String, Integer> pair = iterator.next();
			final String key = pair.getKey();
			final Integer value = pair.getValue();
			result.put(key, result.getOrDefault(key, 0) + value);
		}
		return result;
	}

	/**
	 * Output the reducer string as "Reducer"
	 */
	@Override
	public String toString() {
		return MapReduceConstants.REDUCER;
	}
}
