package src.map_reduce.mvc.model.sequential;

import java.beans.PropertyChangeListener;
import java.beans.PropertyChangeSupport;
import java.util.HashMap;
import java.util.Map;

import src.map_reduce.config.MapReduceConstants;

public abstract class BaseModel<K, V> implements IModel<K, V> {
    protected String inputString = "";
	protected Map<K, V> result = new HashMap<K, V>();
	protected PropertyChangeSupport propertyChangeSupport = new PropertyChangeSupport(this);

    protected abstract void processInputString() throws Exception;

    protected abstract void terminate() throws Exception;

    public void setInputString(final String newInput) throws Exception {
        this.inputString = newInput;
        this.propertyChangeSupport.firePropertyChange("InputString", null, newInput);
        this.processInputString();
    }
    
    public String getInputString() throws Exception {
        return this.inputString;
    }

    public Map<K, V> getResult() throws Exception {
        return this.result;
    }

    public void addPropertyChangeListener(final PropertyChangeListener aListener) throws Exception {
        this.propertyChangeSupport.addPropertyChangeListener(aListener);
    }

    public void removePropertyChangeListener(final PropertyChangeListener aListener) throws Exception {
        this.propertyChangeSupport.removePropertyChangeListener(aListener);
    }

    @Override
    public String toString() {
        return MapReduceConstants.MODEL;
    }
}
