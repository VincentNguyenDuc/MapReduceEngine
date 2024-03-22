package src.applications.word_count;

import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import src.map_reduce.constant.MapReduceConstants;
import src.map_reduce.reduce.IReducer;
import src.map_reduce.type.IKeyValue;

public class WordCountReducer implements IReducer<String, Integer> {
	private static final IReducer<String, Integer> REDUCER_INSTANCE = new WordCountReducer();;

	private WordCountReducer() {}

	public static IReducer<String, Integer> getWordCountingReducer() {
		return REDUCER_INSTANCE;
	}

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

	@Override
	public String toString() {
		return MapReduceConstants.REDUCER;
	}
}
