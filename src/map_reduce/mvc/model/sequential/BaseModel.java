package src.map_reduce.mvc.model.sequential;

import java.beans.PropertyChangeListener;
import java.beans.PropertyChangeSupport;
import java.util.HashMap;
import java.util.Map;

import src.map_reduce.constant.MapReduceConstants;

public abstract class BaseModel<K, V> implements IModel<K, V> {
    private String inputString = "";
	private Map<K, V> result = new HashMap<K, V>();
	private PropertyChangeSupport propertyChangeSupport = new PropertyChangeSupport(this);

    protected abstract void processInputString();

    public abstract void terminate();

    @Override
    public void setInputString(final String newInput) {
        this.inputString = newInput;
        this.propertyChangeSupport.firePropertyChange("InputString", null, newInput);
        this.processInputString();
    }
    
    @Override
    public String getInputString() {
        return this.inputString;
    }

    @Override
    public Map<K, V> getResult() {
        return this.result;
    }

    @Override
    public void setResult(final Map<K, V> aResult) {
        this.result = aResult;
    }

    @Override
    public PropertyChangeSupport getPropertyChangeSupport() {
        return this.propertyChangeSupport;
    }

    public void addPropertyChangeListener(final PropertyChangeListener aListener) {
        this.propertyChangeSupport.addPropertyChangeListener(aListener);
    }

    public void removePropertyChangeListener(final PropertyChangeListener aListener) {
        this.propertyChangeSupport.removePropertyChangeListener(aListener);
    }

    @Override
    public String toString() {
        return MapReduceConstants.MODEL;
    }
}
