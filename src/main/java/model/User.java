package model;

import abstractModel.Person;
import util.Clock;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.OffsetDateTime;

import static util.DBUtil.executeUpdate;
import static util.DBUtil.getPostreSQLURL;
import static util.DBUtil.executeQuery;

/**
 * Represents a database User Object.
 */
public class User extends Person {

    // The additional attributes of a User.
    private String email;
    private boolean booking_record;

    /**
     * Constructor for creating a new User from an existing user in the database.
     *
     * @param id the id of the User.
     * @param first_name the first name of the User.
     * @param last_name the last name of the User.
     * @param email the email of the User.
     * @param booking_record the booking record of the User, true means good, false means not good.
     * @param date_added the date and time the User was added into the database.
     */
    public User(int id, String first_name, String last_name, String email, boolean booking_record,
                OffsetDateTime date_added) {

        // Use super class constructor to create the id, first name, last name and date added attributes.
        super(id, first_name, last_name, date_added);

        // Create the remaining attributes.
        this.email = email;
        this.booking_record = booking_record;
        this.date_added = date_added;
    }

    /**
     * Constructor for creating a brand-new User.
     * Sets id to -1 indicating the User does not exist in the database.
     * Sets booking record to true by default.
     * Sets date added to null since the object has not been added yet.
     *
     * @param first_name the first name of the User.
     * @param last_name the last name of the User.
     * @param email the email of the User.
     */
    public User(String first_name, String last_name, String email) {

        this(-1, first_name, last_name, email, true, null);
    }

    /**
     * Retrieves the email of the User.
     *
     * @return the email.
     */
    public String getEmail() {

        return this.email;
    }

    /**
     * Updates the email of the User.
     *
     * @param email the new email.
     */
    public void setEmail(String email) {

        this.email = email;
    }

    /**
     * Retrieves the booking record of the User.
     *
     * @return the booking record.
     */
    public boolean getBooking_record() {

        return this.booking_record;
    }

    /**
     * Updates the booking record of the User.
     *
     * @param booking_record the new booking record.
     */
    public void setBooking_record(boolean booking_record) {

        this.booking_record = booking_record;
    }

    /**
     * Adds the User to the database.
     *
     * @return the number of rows affected or the error message if any occurs.
     */
    @Override
    public String addToDatabase() {

        // The SQL statement
        String sql = "INSERT INTO public.users (first_name, last_name, email)" +
                " VALUES (?, ?, ?)";

        try {

            // Use executeUpdate() method to save the user in the database
            String returnValue = String.valueOf(executeUpdate(sql, getPostreSQLURL(), this.first_name,
                    this.last_name, this.email));

            // Redefine an SQL statement to get the id and date added for the object
            sql = "SELECT user_id, date_added FROM public.users ORDER BY user_id DESC LIMIT 1";

            try {

                // Store the results of the query
                ResultSet result = executeQuery(sql, getPostreSQLURL());

                // Move cursor to the object
                result.next();

                // Set user id and date added
                this.id = result.getInt("user_id");
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
     * Save the changes made to the user in the database.
     *
     * @return the number of rows affected or the error message if any occurs.
     */
    @Override
    public String saveChanges() {

        // The SQL statement.
        String sql = "UPDATE public.users SET " +
                "first_name = ?, " +
                "last_name = ?, " +
                "booking_record = ?, " +
                "email = ? " +
                "WHERE user_id = ?";
        try {

            // Use executeUpdate() method to save the changes in the database.
            return String.valueOf(executeUpdate(sql, getPostreSQLURL(), this.first_name,
                    this.last_name, this.booking_record, this.email, this.id));

        } catch (Exception ex) {
            return ex.getMessage();
        }
    }

    /**
     * Delete the user from the database.
     *
     * @return the number of rows affected or the error message if any occurs.
     */
    @Override
    public String delete() {

        // The SQL statement.
        String sql = "DELETE FROM public.users WHERE user_id = ?";

        try {

            // Use executeUpdate() method from DBUtil to delete the user from the database.
            return String.valueOf(executeUpdate(sql, getPostreSQLURL(), this.id));

        } catch (Exception ex) {
            return ex.getMessage();
        }
    }

    /**
     * Returns a string representation of the user.
     *
     * @return a string representation of the user.
     */
    @Override
    public String toString() {

        // Use CUSTOM_FORMATTER To convert the date into a readable format.
        return "User{" +
                "id=" + id +
                ", first_name = '" + first_name + '\'' +
                ", last_name = '" + last_name + '\'' +
                ", email = '" + email + '\'' +
                ", date_added = " + Clock.CUSTOM_FORMATTER.format(date_added) +
                ", booking_record = " + ((booking_record) ? "Clean" : "Not Clean") +
                '}';
    }

}
