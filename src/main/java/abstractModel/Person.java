package abstractModel;

import java.time.OffsetDateTime;

/**
 * Represents a Person.
 */
public abstract class Person implements DatabaseAddable {

    // The attributes of a Person.
    protected int id;
    protected String first_name;
    protected String last_name;
    protected OffsetDateTime date_added;

    /**
     * Creates a Person Object (Creates the common attributes for child classes).
     *
     * @param id the id of the Person.
     * @param first_name the first name of the Person.
     * @param last_name the last name of the Person.
     * @param date_added the date th Person was added to the database.
     */
    public Person(int id, String first_name, String last_name, OffsetDateTime date_added) {

        this.id = id;
        this.first_name = first_name;
        this.last_name = last_name;
        this.date_added = date_added;
    }

    /**
     * Retrieves the id of the Person.
     *
     * @return the id.
     */
    public int getId() {

        return this.id;
    }

    /**
     * Retrieves the first name of the Person.
     *
     * @return the first name.
     */
    public String getFirst_name() {

        return this.first_name;
    }

    /**
     * Updates the first name of the Person.
     *
     * @param first_name the new first name.
     */
    public void setFirst_name(String first_name) {

        this.first_name = first_name;
    }

    /**
     * Retrieves the last name of the Person.
     *
     * @return the last name.
     */
    public String getLast_name() {

        return this.last_name;
    }

    /**
     * Updates the last name of the Person.
     *
     * @param last_name the new last name.
     */
    public void setLast_name(String last_name) {

        this.last_name = last_name;
    }

    /**
     * Retrieves the date the User was added to the database.
     *
     * @return the date the User was added.
     */
    public OffsetDateTime getDate_added() {

        return this.date_added;
    }

    /**
     * Method signature for adding a Person to the database.
     * Implemented by child classes.
     *
     * @return the number of rows affected or the error message if any occurs.
     */
    @Override
    public abstract String addToDatabase();

    /**
     * Method signature for editing a Person to the database.
     * Implemented by child classes.
     *
     * @return the number of rows affected or the error message if any occurs.
     */
    @Override
    public abstract String saveChanges();

    /**
     * Method signature for deleting a Person to the database.
     * Implemented by child classes.
     *
     * @return the number of rows affected or the error message if any occurs.
     */
    @Override
    public abstract String delete();

}
