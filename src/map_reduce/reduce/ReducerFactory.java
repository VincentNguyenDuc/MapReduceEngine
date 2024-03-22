package src.map_reduce.reduce;

@SuppressWarnings("rawtypes")
public class ReducerFactory {
    private static IReducer reducerInstance = null;

    public static IReducer getReducer() {
        return reducerInstance;
    }

    public static void setReducer(final IReducer newReducer) {
        reducerInstance = newReducer;
    }
}