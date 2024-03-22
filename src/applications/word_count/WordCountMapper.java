package src.applications.word_count;

import java.util.ArrayList;
import java.util.List;

import src.map_reduce.config.MapReduceConstants;
import src.map_reduce.map.IMapper;
import src.map_reduce.type.IKeyValue;
import src.map_reduce.type.KeyValue;

public class WordCountMapper implements IMapper<String, Integer> {
	private static final IMapper<String, Integer> WORD_COUNTING_MAPPER = new WordCountMapper();

	private WordCountMapper() {}

	public static IMapper<String, Integer> getWordCountingMapper() {
		return WORD_COUNTING_MAPPER;
	}

	@Override
	public List<IKeyValue<String, Integer>> map(final List<String> tokens) {
		final List<IKeyValue<String, Integer>> outputList = new ArrayList<IKeyValue<String, Integer>>();
		for (String token : tokens) {
			outputList.add(new KeyValue<String, Integer>(token, 1));
		}
		return outputList;
	}

	@Override
	public String toString() {
		return MapReduceConstants.MAPPER;
	}
}
