package src.map_reduce.mvc.model.sequential;

import java.beans.PropertyChangeListener;
import java.beans.PropertyChangeSupport;
import java.util.Map;

public interface IModel<K, V> {

    void setInputString(final String newInput);
    
    String getInputString();

    Map<K, V> getResult();

    void setResult(final Map<K, V> aResult);

    PropertyChangeSupport getPropertyChangeSupport();

    void addPropertyChangeListener(final PropertyChangeListener aListener);

    void removePropertyChangeListener(final PropertyChangeListener aListener);
}
