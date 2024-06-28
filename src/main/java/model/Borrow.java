package model;

import abstract_model.DatabaseAddable;
import util.Clock;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.OffsetDateTime;

import static util.DBUtil.executeUpdate;
import static util.DBUtil.getPostreSQLURL;
import static util.DBUtil.executeQuery;

/**
 * Represents a database Borrow Object
 */
public class Borrow implements DatabaseAddable {

    // Attributes of a Borrow Object
    private int id;
    private int book_id;
    private int user_id;
    private OffsetDateTime borrowing_date;
    private OffsetDateTime expected_return_date;
    private OffsetDateTime actual_return_date;

    /**
     * Constructor for creating a new Borrow from an existing borrow in the database
     *
     * @param id the id of the borrow
     * @param book_id the id of the book being borrowed
     * @param user_id the id of the user borrowing the book
     * @param borrowing_date the date and time the book was borrowed
     * @param expected_return_date the date the book is expected to be returned
     * @param actual_return_date the date and time the book is actually returned
     */
    public Borrow(int id, int book_id, int user_id, OffsetDateTime borrowing_date,
                  OffsetDateTime expected_return_date, OffsetDateTime actual_return_date) {

        this.id = id;
        this.book_id = book_id;
        this.user_id = user_id;
        this.borrowing_date = borrowing_date;
        this.expected_return_date = expected_return_date;
        this.actual_return_date = actual_return_date;
    }

    /**
     * Constructor for creating a brand-new Borrow
     * Sets id to -1 indicating the Borrow does not exist in the database
     * Sets borrowing date to null since the object has not been borrowed yet
     * Sets actual return date to null since the Borrow has not been returned yet
     *
     * @param book_id the id of the book being borrowed
     * @param user_id the id of the user borrowing the book
     * @param expected_return_date the date the boot is expected to be returned
     */
    public Borrow(int book_id, int user_id, OffsetDateTime expected_return_date) {

        this(-1, book_id, user_id, null, expected_return_date, null);
    }

    /**
     * Retrieves the id of the Borrow
     *
     * @return the id
     */
    public int getId() {
        return this.id;
    }

    /**
     * Retrieves the id of the book being borrowed
     *
     * @return the id of the book being borrowed
     */
    public int getBook_id() {
        return this.book_id;
    }

    /**
     * Updates the id of the book being borrowed
     *
     * @param book_id the new id of the book
     */
    public void setBook_id(int book_id) {
        this.book_id = book_id;
    }

    /**
     * Retrieves the id of the user borrowing the book
     *
     * @return the id of the user
     */
    public int getUser_id() {
        return this.user_id;
    }

    /**
     * Updates the id of the user borrowing the book
     *
     * @param user_id the new id of the user
     */
    public void setUser_id(int user_id) {
        this.user_id = user_id;
    }

    /**
     * Retrieves the date and time the book was borrowed
     *
     * @return the borrowing date and time
     */
    public OffsetDateTime getBorrowing_date() {
        return this.borrowing_date;
    }

    /**
     * Retrieves the date the book is expected to be returned
     *
     * @return the expected return date
     */
    public OffsetDateTime getExpected_return_date() {
        return this.expected_return_date;
    }

    /**
     * Updates the date and time the book is expected to be returned
     *
     * @param expected_return_date the new expected return date
     */
    public void setExpected_return_date(OffsetDateTime expected_return_date) {
        this.expected_return_date = expected_return_date;
    }

    /**
     * Retrieves the actual date and time the book was returned
     *
     * @return the actual return date and time
     */
    public OffsetDateTime getActual_return_date() {
        return this.actual_return_date;
    }

    /**
     * Adds the Borrow to the database
     *
     * @return the number of rows affected or the error message if any occurs
     */
    @Override
    public String addToDatabase() {

        // The SQL statement
        String sql = "INSERT INTO public.borrows (book_id, user_id, expected_return_date)" +
                " VALUES (?, ?, ?)";

        try {

            // Use executeUpdate() method to save the borrow in the database
            String returnValue = String.valueOf(executeUpdate(sql, getPostreSQLURL(), this.book_id,
                    this.user_id, this.expected_return_date));

            // Redefine an SQL statement to get the id and date added for the object
            sql = "SELECT borrowing_id, borrowing_date FROM public.borrows ORDER BY borrowing_id DESC LIMIT 1";

            try {

                // Store the results of the query
                ResultSet result = executeQuery(sql, getPostreSQLURL());

                // Move cursor to the object
                result.next();

                // Set user id and date added
                this.id = result.getInt("borrowing_id");
                this.borrowing_date = result.getObject("borrowing_date", OffsetDateTime.class);

            } catch (SQLException ex) {
                return ex.getMessage();
            }

            return returnValue;

        } catch (Exception ex) {
            return ex.getMessage();
        }
    }

    /**
     * Save the changes made to the borrow in the database
     *
     * @return the number of rows affected or the error message if any occurs
     */
    @Override
    public String saveChanges() {

        // The SQL statement
        String sql = "UPDATE public.borrows SET " +
                "book_id = ?, " +
                "user_id = ?, " +
                "expected_return_date = ? " +
                "WHERE borrowing_id = ?";
        try {

            // Use executeUpdate() method to save the changes in the database
            return String.valueOf(executeUpdate(sql, getPostreSQLURL(), this.book_id,
                    this.user_id, this.expected_return_date, this.id));

        } catch (Exception ex) {
            return ex.getMessage();
        }
    }

    /**
     * Delete the Borrow from the database
     *
     * @return the number of rows affected or the error message if any occurs
     */
    @Override
    public String delete() {

        // The SQL statement
        String sql = "DELETE FROM public.borrows WHERE borrowing_id = ?";

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
        return "Borrow{" +
                "id=" + id +
                ", book_id = '" + book_id + '\'' +
                ", user_id = '" + user_id + '\'' +
                ", borrowing_date = " + Clock.CUSTOM_FORMATTER.format(borrowing_date) +
                ", expected_return_date = " + Clock.CUSTOM_FORMATTER.format(expected_return_date) +
                ", actual_return_date = " + Clock.CUSTOM_FORMATTER.format(actual_return_date) +
                '}';
    }

    /**
     * Sets the actual return date to be the current date and time
     *
     * @return the number of rows affected or the error message if any occurs
     */
    public String returnBook() {

        // The SQL statement
        String sql = "UPDATE public.borrows SET actual_return_date = ? WHERE borrowing_id = ?";

        try {

            // Set actual return date to the current time
            this.actual_return_date = OffsetDateTime.now();

            // Use executeUpdate() method to save the changes in the database
            return String.valueOf(executeUpdate(sql, getPostreSQLURL(), this.actual_return_date,
                    this.id));

        } catch (Exception ex) {
            return ex.getMessage();
        }
    }

}
