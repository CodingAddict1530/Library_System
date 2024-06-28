package model;

import abstract_model.Person;
import util.Clock;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.OffsetDateTime;

import static util.DBUtil.executeUpdate;
import static util.DBUtil.getPostreSQLURL;
import static util.DBUtil.executeQuery;

/**
 * Represents a database Author Object
 */
public class Author extends Person {

    /**
     * Constructor for creating a new Author from an existing Author in the database
     *
     * @param id the id of the Author
     * @param first_name the first name of the Author
     * @param last_name the last name of the Author
     * @param date_added the date and time the Author was added into the database
     */
    public Author(int id, String first_name, String last_name, OffsetDateTime date_added) {

        // Use super class constructor to create the attributes
        super(id, first_name, last_name, date_added);
    }

    /**
     * Constructor for creating a brand-new Author
     * Sets id to -1 indicating the Author does not exist in the database
     * Sets date added to null since the Author has not been added yet
     *
     * @param first_name the first name of the Author
     * @param last_name the last name of the Author
     */
    public Author(String first_name, String last_name) {

        this(-1, first_name, last_name, null);
    }

    /**
     * Adds the Author to the database
     *
     * @return the number of rows affected or the error message if any occurs
     */
    @Override
    public String addToDatabase() {

        // The SQL statement
        String sql = "INSERT INTO public.authors (first_name, last_name)" +
                " VALUES (?, ?)";

        try {

            // Use executeUpdate() method to save the author in the database
            String returnValue = String.valueOf(executeUpdate(sql, getPostreSQLURL(), this.first_name,
                    this.last_name));

            // Redefine an SQL statement to get the id and date added for the object
            sql = "SELECT author_id, date_added FROM public.authors ORDER BY author_id DESC LIMIT 1";

            try {

                // Store the results of the query
                ResultSet result = executeQuery(sql, getPostreSQLURL());

                // Move cursor to the object
                result.next();

                // Set user id and date added
                this.id = result.getInt("author_id");
                this.date_added = result.getObject("date_added", OffsetDateTime.class);

            } catch (SQLException ex) {
                return ex.getMessage();
            }

            return returnValue;

        } catch (Exception ex) {
            return ex.getMessage();
        }
    }

    /**
     * Save the changes made to the author in the database
     *
     * @return the number of rows affected or the error message if any occurs
     */
    @Override
    public String saveChanges() {

        // The SQL statement
        String sql = "UPDATE public.authors SET " +
                "first_name = ?, " +
                "last_name = ? " +
                "WHERE author_id = ?";
        try {

            // Use executeUpdate() method to save the changes in the database
            return String.valueOf(executeUpdate(sql, getPostreSQLURL(), this.first_name,
                    this.last_name, this.id));

        } catch (Exception ex) {
            return ex.getMessage();
        }
    }

    /**
     * Delete the author from the database
     *
     * @return the number of rows affected or the error message if any occurs
     */
    @Override
    public String delete() {

        // The SQL statement
        String sql = "DELETE FROM public.authors WHERE author_id = ?";

        try {

            // Use executeUpdate() method from DBUtil to delete the author from the database
            return String.valueOf(executeUpdate(sql, getPostreSQLURL(), this.id));

        } catch (Exception ex) {
            return ex.getMessage();
        }
    }

    /**
     * Returns a string representation of the author
     *
     * @return a string representation of the author
     */
    @Override
    public String toString() {

        // Use CUSTOM_FORMATTER To convert the date into a readable format
        return "Author{" +
                "id=" + id +
                ", first_name = '" + first_name + '\'' +
                ", last_name = '" + last_name + '\'' +
                ", date_added = " + Clock.CUSTOM_FORMATTER.format(date_added) +
                '}';
    }

}
