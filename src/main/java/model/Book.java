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
 * Represents a database Book Object.
 */
public class Book implements DatabaseAddable {

    // Attributes of a book.
    private int id;
    private String title;
    private String description;
    private int number_of_pages;
    private OffsetDateTime date_added;
    private String genre;
    private int author_id;

    /**
     * Constructor for creating a new Book from an existing book in the database.
     *
     * @param id the id of the book.
     * @param title the title of the book.
     * @param description the description of the book.
     * @param number_of_pages the number of pages of the book.
     * @param date_added the date the book was added into the database.
     * @param genre the genre of the book.
     * @param author_id the id of the author of the book.
     */
    public Book(int id, String title, String description, int number_of_pages, OffsetDateTime date_added,
                String genre, int author_id) {

        this.id = id;
        this.title = title;
        this.description = description;
        this.number_of_pages = number_of_pages;
        this.date_added = date_added;
        this.genre = genre;
        this.author_id = author_id;
    }

    /**
     * Constructor for creating a brand-new Book.
     * Sets id to -1 indicating the Book does not exist in the database.
     * Sets date added to null since the Book has not been added yet.
     *
     * @param title the title of the book.
     * @param description the description of the book.
     * @param number_of_pages the number of pages of the book.
     * @param genre the genre of the book.
     * @param author_id the id of the author of the book.
     */
    public Book(String title, String description, int number_of_pages, String genre, int author_id) {

        this(-1, title, description, number_of_pages, null, genre, author_id);
    }

    /**
     * Retrieves the id of the Book.
     *
     * @return the id.
     */
    public int getId() {

        return this.id;
    }

    /**
     * Retrieves the title of the Book.
     *
     * @return the title.
     */
    public String getTitle() {

        return this.title;
    }

    /**
     * Updates the title of the Book.
     *
     * @param title the new title.
     */
    public void setTitle(String title) {

        this.title = title;
    }

    /**
     * Retrieves the description of the Book.
     *
     * @return the description.
     */
    public String getDescription() {

        return this.description;
    }

    /**
     * Updates the description of the book.
     *
     * @param description the new description.
     */
    public void setDescription(String description) {

        this.description = description;
    }

    /**
     * Retrieves the number of pages of the Book.
     *
     * @return the number of pages.
     */
    public int getNumber_of_pages() {

        return this.number_of_pages;
    }

    /**
     * Updates the number of pages of a Book.
     *
     * @param number_of_pages the new number of pages.
     */
    public void setNumber_of_pages(int number_of_pages) {

        this.number_of_pages = number_of_pages;
    }

    /**
     * Retrieves the date and time the Book was added into the database.
     *
     * @return the date and time.
     */
    public OffsetDateTime getDate_added() {

        return this.date_added;
    }

    /**
     * Retrieves the genre of the Book.
     *
     * @return the genre.
     */
    public String getGenre() {

        return this.genre;
    }

    /**
     * Updates the genre of the Book.
     *
     * @param genre the new genre.
     */
    public void setGenre(String genre) {

        this.genre = genre;
    }

    /**
     * Retrieves the id of the author of the Book.
     *
     * @return the id of the author.
     */
    public int getAuthor_id() {

        return this.author_id;
    }

    /**
     * Updates the id of the author of the Book.
     *
     * @param author_id the new id of the author.
     */
    public void setAuthor_id(int author_id) {

        this.author_id = author_id;
    }

    /**
     * Adds the Book to the database.
     *
     * @return the number of rows affected or the error message if any occurs.
     */
    @Override
    public String addToDatabase() {

        // The SQL statement.
        String sql = "INSERT INTO public.books (title, description, number_of_pages, genre, author_id)" +
                " VALUES (?, ?, ?, ?, ?)";

        try {

            // Use executeUpdate() method to save the book in the database.
            String returnValue = String.valueOf(executeUpdate(sql, getPostreSQLURL(), this.title,
                    this.description, this.number_of_pages, this.genre, this.author_id));

            // Redefine an SQL statement to get the id and date added for the object.
            sql = "SELECT book_id, date_added FROM public.books ORDER BY book_id DESC LIMIT 1";

            try {

                // Store the results of the query.
                ResultSet result = executeQuery(sql, getPostreSQLURL());

                // Move cursor to the object.
                result.next();

                // Set user id and date added.
                this.id = result.getInt("book_id");
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
     * Save the changes made to the book in the database.
     *
     * @return the number of rows affected or the error message if any occurs.
     */
    @Override
    public String saveChanges() {

        // The SQL statement.
        String sql = "UPDATE public.books SET " +
                "title = ?, " +
                "description = ?, " +
                "number_of_pages = ?, " +
                "genre = ?, " +
                "author_id = ? " +
                "WHERE book_id = ?";
        try {

            // Use executeUpdate() method to save the changes in the database.
            return String.valueOf(executeUpdate(sql, getPostreSQLURL(), this.title,
                    this.description, this.number_of_pages, this.genre, this.author_id, this.id));

        } catch (Exception ex) {
            return ex.getMessage();
        }
    }

    /**
     * Delete the book from the database.
     *
     * @return the number of rows affected or the error message if any occurs.
     */
    @Override
    public String delete() {

        // The SQL statement.
        String sql = "DELETE FROM public.books WHERE book_id = ?";

        try {

            // Use executeUpdate() method from DBUtil to delete the book from the database.
            return String.valueOf(executeUpdate(sql, getPostreSQLURL(), this.id));

        } catch (Exception ex) {
            return ex.getMessage();
        }
    }

    /**
     * Returns a string representation of the book.
     *
     * @return a string representation of the book.
     */
    @Override
    public String toString() {

        // Use CUSTOM_FORMATTER To convert the date into a readable format.
        return "Book{" +
                "id=" + id +
                ", title = '" + title + '\'' +
                ", description = '" + description + '\'' +
                ", number_of_pages = '" + number_of_pages + '\'' +
                ", date_added = " + Clock.CUSTOM_FORMATTER.format(date_added) +
                ", genre = '" + genre + '\'' +
                ", author_id = '" + author_id + '\'' +
                '}';
    }

}
