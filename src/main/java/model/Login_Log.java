package model;

import abstractModel.DatabaseAddable;
import util.Clock;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.OffsetDateTime;

import static util.DBUtil.executeUpdate;
import static util.DBUtil.getPostreSQLURL;
import static util.DBUtil.executeQuery;

/**
 * Represents a database Login Log Object
 */
public class Login_Log implements DatabaseAddable {

    // Attributes of a Login_Log.
    private int id;
    private int user_id;
    private OffsetDateTime time_logged_in;
    private OffsetDateTime time_logged_out;

    /**
     * Constructor for creating a new Login Log from an existing login log in the database.
     *
     * @param id the id of the login log.
     * @param user_id the id of the user who logged in.
     * @param time_logged_in the date and time the user logged in.
     * @param time_logged_out the date and time the user logged out.
     */
    public Login_Log(int id, int user_id, OffsetDateTime time_logged_in, OffsetDateTime time_logged_out) {

        this.id = id;
        this.user_id = user_id;
        this.time_logged_in = time_logged_in;
        this.time_logged_out = time_logged_out;
    }

    /**
     * Constructor for creating a brand-new Login Log.
     * Sets id to -1 indicating the Login Log does not exist in the database.
     * Sets time logged in to null since the user has not logged in yet.
     * Sets time logged out to null since the user has not logged out yet.
     *
     * @param user_id the user logging in.
     */
    public Login_Log(int user_id) {

        this(-1, user_id, null, null);
    }

    /**
     * Retrieves the id of the Login Log.
     *
     * @return the id.
     */
    public int getId() {

        return this.id;
    }

    /**
     * Retrieves the id of the user logging in.
     *
     * @return the id of the user.
     */
    public int getUser_id() {

        return user_id;
    }

    /**
     * Updates the id of the user logging in.
     *
     * @param user_id the new id of the user.
     */
    public void setUser_id(int user_id) {

        this.user_id = user_id;
    }

    /**
     * Retrieves the date and time the user logged in.
     *
     * @return the date and time.
     */
    public OffsetDateTime getTime_logged_in() {

        return this.time_logged_in;
    }

    /**
     * Retrieves the date and time the use logged out.
     *
     * @return the date and time.
     */
    public OffsetDateTime getTime_logged_out() {

        return this.time_logged_out;
    }

    /**
     * Saves the current time in the database as the time the user logged out.
     *
     * @return the number of rows affected or the error message if any occurs.
     */
    public String logOut() {

        // The SQL statement
        String sql = "UPDATE public.login_logs SET " +
                "time_logged_out = ? " +
                "WHERE user_id = ?";
        try {

            // Set time logged out to the current time
            this.time_logged_out = OffsetDateTime.now();

            // Use executeUpdate() method to save the changes in the database
            return String.valueOf(executeUpdate(sql, getPostreSQLURL(), this.time_logged_out,
                    this.user_id));

        } catch (Exception ex) {
            return ex.getMessage();
        }
    }

    /**
     * Adds the Login_Log to the database.
     *
     * @return the number of rows affected or the error message if any occurs.
     */
    @Override
    public String addToDatabase() {

        // The SQL statement.
        String sql = "INSERT INTO public.login_logs (user_id)" +
                " VALUES (?)";

        try {

            // Use executeUpdate() method to save the login log in the database.
            String returnValue = String.valueOf(executeUpdate(sql, getPostreSQLURL(), this.user_id));

            // Redefine an SQL statement to get the id and time logged in for the user.
            sql = "SELECT login_id, time_logged_in FROM public.login_logs ORDER BY login_id DESC LIMIT 1";

            try {

                // Store the results of the query.
                ResultSet result = executeQuery(sql, getPostreSQLURL());

                // Move cursor to the object.
                result.next();

                // Set user id and date added.
                this.id = result.getInt("login_id");
                this.time_logged_in = result.getObject("time_logged_in", OffsetDateTime.class);

            } catch (SQLException ex) {
                return ex.getMessage();
            }

            return returnValue;

        } catch (Exception ex) {
            return ex.getMessage();
        }
    }

    /**
     * Nothing changeable for a login log.
     *
     * @return an error message.
     */
    @Override
    public String saveChanges() {

        return "CAN'T BE EDITED";
    }

    /**
     * Delete the login log from the database.
     *
     * @return the number of rows affected or the error message if any occurs.
     */
    @Override
    public String delete() {

        // The SQL statement.
        String sql = "DELETE FROM public.login_logs WHERE login_id = ?";

        try {

            // Use executeUpdate() method from DBUtil to delete the login log from the database.
            return String.valueOf(executeUpdate(sql, getPostreSQLURL(), this.id));

        } catch (Exception ex) {
            return ex.getMessage();
        }
    }

    /**
     * Returns a string representation of the Login Log.
     *
     * @return a string representation of the Login Log.
     */
    @Override
    public String toString() {

        // Use CUSTOM_FORMATTER To convert the date into a readable format.
        return "Login Log{" +
                "id=" + id +
                ", user_id = '" + user_id + '\'' +
                ", time_logged_in = " + Clock.CUSTOM_FORMATTER.format(time_logged_in) +
                ", time_logged_out = " + ((time_logged_out != null) ?
                Clock.CUSTOM_FORMATTER.format(time_logged_out) : "NULL") +
                '}';
    }

}
