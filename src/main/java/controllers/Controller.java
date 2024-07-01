package controllers;

/**
 * Represents a base Controller
 */
public class Controller {

    // stores an array of objects. Used to pass data between controllers.
    protected Object[] data;

    /**
     * Adds the data passed to this.data.
     * Calls dataChanged() to make use of the data.
     *
     * @param objects the Objects passed to the controller.
     */
    public void setData(Object... objects) {

        // Check whether objects has data.
        if (objects != null && objects.length != 0) {
            this.data = objects;
            dataChanged();
        }
    }

    /**
     * Method signature to be implemented in Child classes, not required though.
     */
    protected void dataChanged() {

    }

}
