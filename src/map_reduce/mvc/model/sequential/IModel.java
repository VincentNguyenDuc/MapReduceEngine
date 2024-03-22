package src.map_reduce.mvc.model.sequential;

import java.beans.PropertyChangeListener;
import java.util.Map;

public interface IModel<K, V> {

    void setInputString(final String newInput) throws Exception;
    
    String getInputString() throws Exception;

    Map<K, V> getResult() throws Exception;

    void addPropertyChangeListener(final PropertyChangeListener aListener) throws Exception;

    void removePropertyChangeListener(final PropertyChangeListener aListener) throws Exception;
}
