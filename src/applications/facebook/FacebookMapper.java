package src.applications.facebook;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import src.map_reduce.constant.MapReduceConstants;
import src.map_reduce.map.IMapper;
import src.map_reduce.type.IKeyValue;
import src.map_reduce.type.KeyValue;

public enum FacebookMapper implements IMapper<String, List<String>> {
	INSTANCE;

	@Override
	public List<IKeyValue<String, List<String>>> map(final List<String> tokens) {
		Collections.sort(tokens);
		
		final List<IKeyValue<String, List<String>>> outputList = new ArrayList<IKeyValue<String, List<String>>>();
		for (String token : tokens) {
			final List<String> users = Arrays.asList(token.split(","));
			final String currentUser = users.get(0);
			
			// A List return from subList method is an instance of RandomAccessSubList (not serializable)
			// Therefore, we need to wrap it around a normal ArrayList
			final List<String> friends = new ArrayList<String>(users.subList(1, users.size()));
			
			friends.forEach((String friend) -> {
				final String[] pair = { currentUser, friend };
				Arrays.sort(pair);
				final String key = pair[0] + "_" + pair[1];
				outputList.add(new KeyValue<String, List<String>>(key, friends));
			});
		}
		return outputList;
	}

	@Override
	public String toString() {
		return MapReduceConstants.MAPPER;
	}
}
