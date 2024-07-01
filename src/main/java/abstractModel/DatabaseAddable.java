package abstractModel;

/**
 * Represents an Object that appears as a table in the database.
 */
public interface DatabaseAddable {

    /**
     * Method signature for adding a Person to the database.
     * Implemented by child classes.
     *
     * @return the number of rows affected or the error message if any occurs.
     */
    public abstract String addToDatabase();

    /**
     * Method signature for editing a Person to the database.
     * Implemented by child classes.
     *
     * @return the number of rows affected or the error message if any occurs.
     */
    public abstract String saveChanges();

    /**
     * Method signature for deleting a Person to the database.
     * Implemented by child classes.
     *
     * @return the number of rows affected or the error message if any occurs.
     */
    public abstract String delete();

}
