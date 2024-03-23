package src.map_reduce.mvc.view;

import java.beans.PropertyChangeEvent;

import src.map_reduce.constant.MapReduceConstants;

public class View implements IView {

    /**
     * Output a property change event to the console
     * 
     * @param anEvent a property change event
     */
    @Override
    public void propertyChange(final PropertyChangeEvent anEvent) {
        System.out.println(anEvent);
    }

    /**
     * Override the string output of the object
     */
    @Override
    public String toString() {
        return MapReduceConstants.VIEW;
    }
}
