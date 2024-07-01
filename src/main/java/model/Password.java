package model;

import abstractModel.DatabaseAddable;

import static util.DBUtil.executeUpdate;
import static util.DBUtil.getPostreSQLURL;

/**
 * Represents a database Password Object (Hashed)
 */
public class Password implements DatabaseAddable {

    // Attributes of a Password.
    private final int user_id;
    private String password;

    /**
     * Constructor for creating a new Password.
     *
     * @param user_id the id of the user.
     * @param password the hashed password of the user.
     */
    public Password(int user_id, String password) {

        this.user_id = user_id;
        this.password = password;
    }

    /**
     * Retrieves the id of the user.
     *
     * @return the id of the user.
     */
    public int getUser_id() {

        return user_id;
    }

    /**
     * Retrieves the password of the user.
     *
     * @return the password.
     */
    public String getPassword() {

        return this.password;
    }

    /**
     * Updates the password of the user.
     *
     * @param password the new password.
     */
    public void setPassword(String password) {

        this.password = password;
    }

    /**
     * Adds the Password to the database.
     *
     * @return the number of rows affected or the error message if any occurs.
     */
    @Override
    public String addToDatabase() {

        // The SQL statement.
        String sql = "INSERT INTO public.passwords (user_id, password)" +
                " VALUES (?, ?)";

        try {

            // Use executeUpdate() method to save the password in the database.

            return String.valueOf(executeUpdate(sql, getPostreSQLURL(), this.user_id, this.password));

        } catch (Exception ex) {
            return ex.getMessage();
        }
    }

    /**
     * Save the changes made to the password in the database.
     *
     * @return the number of rows affected or the error message if any occurs.
     */
    @Override
    public String saveChanges() {

        // The SQL statement.
        String sql = "UPDATE public.passwords SET " +
                "password = ? " +
                "WHERE user_id = ?";
        try {

            // Use executeUpdate() method to save the changes in the database.
            return String.valueOf(executeUpdate(sql, getPostreSQLURL(), this.password, this.user_id));

        } catch (Exception ex) {
            return ex.getMessage();
        }
    }

    /**
     * Delete the Password from the database.
     *
     * @return the number of rows affected or the error message if any occurs.
     */
    @Override
    public String delete() {

        // The SQL statement.
        String sql = "DELETE FROM public.passwords WHERE user_id = ?";

        try {

            // Use executeUpdate() method from DBUtil to delete the password from the database.
            return String.valueOf(executeUpdate(sql, getPostreSQLURL(), this.user_id));

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
        return "Password{" +
                "user_id=" + user_id +
                ", password = '" + password + '\'' +
                '}';
    }

}
