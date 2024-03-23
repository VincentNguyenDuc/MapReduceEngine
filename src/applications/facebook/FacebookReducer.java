package src.applications.facebook;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

import src.map_reduce.constant.MapReduceConstants;
import src.map_reduce.reduce.IReducer;
import src.map_reduce.type.IKeyValue;

public enum FacebookReducer implements IReducer<String, List<String>> {

    INSTANCE;

	@Override
	public Map<String, List<String>> reduce(final List<IKeyValue<String, List<String>>> keyValuePairs) {
		final Map<String, List<String>> result = new HashMap<String, List<String>>();
		final Iterator<IKeyValue<String, List<String>>> iterator = keyValuePairs.iterator();
		while (iterator.hasNext()) {
			final IKeyValue<String, List<String>> pair = iterator.next();
			final String key = pair.getKey();
			final List<String> newValue = pair.getValue();
			final List<String> currentValue = result.get(key);
	
			if (currentValue == null) {
				result.put(key, newValue);
			} else {
				// Calculate the intersection
				final Set<String> newSet = new HashSet<String>(newValue);
				final Set<String> currentSet = new HashSet<String>(currentValue);
				currentSet.retainAll(newSet);
				
				// Put back to a list
				final List<String> reduceList = new ArrayList<String>();
				reduceList.addAll(currentSet);
				result.put(key, reduceList);
			}
		}
		return result;
	}

	@Override
	public String toString() {
		return MapReduceConstants.REDUCER;
	}
}
