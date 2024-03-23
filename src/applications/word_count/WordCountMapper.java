package src.applications.word_count;

import java.util.ArrayList;
import java.util.List;

import src.map_reduce.constant.MapReduceConstants;
import src.map_reduce.map.IMapper;
import src.map_reduce.type.IKeyValue;
import src.map_reduce.type.KeyValue;

/**
 * A simple implementation of the mapper interface, with String key and Integer value
 * 
 * @see src.map_reduce.map.IMapper
 * 
 * This class follows the singleton pattern design.
 */
public enum WordCountMapper implements IMapper<String, Integer> {
	/**
	 * The singleton
	 */
	INSTANCE;

	/**
	 * Perform mapping from input tokens list to key-value pair:
	 * - Map each token to a key-value pair of (token, 1)
	 */
	@Override
	public List<IKeyValue<String, Integer>> map(final List<String> tokens) {
		final List<IKeyValue<String, Integer>> outputList = new ArrayList<IKeyValue<String, Integer>>();
		for (String token : tokens) {
			outputList.add(new KeyValue<String, Integer>(token, 1));
		}
		return outputList;
	}

	/**
	 * Output the name of the mapper as "Mapper"
	 */
	@Override
	public String toString() {
		return MapReduceConstants.MAPPER;
	}
}
