package src.map_reduce.mvc.model.sequential;

import java.util.Arrays;
import java.util.List;

import src.map_reduce.map.MapperFactory;
import src.map_reduce.reduce.ReducerFactory;

public class SequentialModel<K, V> extends BaseModel<K, V> {

    @SuppressWarnings("unchecked")
    @Override
    protected void processInputString() {
        final List<String> tokenStrings = Arrays.asList(super.getInputString().split(" "));
        super.setResult(ReducerFactory.getReducer().reduce(MapperFactory.getMapper().map(tokenStrings)));
        this.getPropertyChangeSupport().firePropertyChange("Result", null, this.getResult());
    }

    @Override
    public void terminate() {
        System.exit(0);
    }
}
