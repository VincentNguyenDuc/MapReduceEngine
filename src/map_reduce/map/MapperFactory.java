package src.map_reduce.map;

@SuppressWarnings("rawtypes")
public class MapperFactory {
    private static IMapper mapperInstance = null;

	public static IMapper getMapper() {
		return mapperInstance;
	}

	public static void setMapper(final IMapper newMapper) {
		mapperInstance = newMapper;
	}
}
