package src.map_reduce.reduce;

/**
 * A factory for reducer.
 * User will need to set the specific reducer used for a program within the main method
 */
@SuppressWarnings("rawtypes")
public class ReducerFactory {
    /**
     * A singlton instance of reducer
     */
    private static IReducer reducerInstance;

    /**
     * Return the reducer's singleton
     * @return a reducer
     */
    public static IReducer getReducer() {
        return reducerInstance;
    }

    /**
     * Change the reducer's singleton
     * @param newReducer a new reducer
     */
    public static void setReducer(final IReducer newReducer) {
        reducerInstance = newReducer;
    }
}