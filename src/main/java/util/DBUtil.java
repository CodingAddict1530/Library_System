package util;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import de.mkammerer.argon2.Argon2;
import de.mkammerer.argon2.Argon2Factory;

import java.security.SecureRandom;
import java.util.Base64;

/**
 * Contains utility methods for database related operations
 * Compatible with MySQL and PostregSQL
 */
public class DBUtil {

    // JDBC URL, Set last part (db name) to your database name
    private static final String JDBC_URL_POSTGRES = "jdbc:postgresql://localhost:5432/Library_System";
    private static final String JDBC_URL_MYSQL = "jdbc:mysql://localhost:3306/Library_System";

    // Set USER and PASSWORD to your user and password
    private static final String JDBC_USER = "login1";
    private static final String JDBC_PASSWORD = "librarysystem@2024";

    // JDBC variables for opening and managing connection
    private static Connection conn;
    private static PreparedStatement pstmt;

    // Pepper
    private static final String PEPPER = "09uygvbhjmd2ehow092uenfdmwlkma";

    // SecureRandom for generating salts
    private static final SecureRandom secureRandom = new SecureRandom();

    // Length of salt in bytes
    private static final int SALT_LENGTH = 16;

    // Argon2 instance
    private static final Argon2 argon2 = Argon2Factory.create();

    /**
     * Establishes a connection to the database based on the JDBC URL.
     *
     * @param jdbcUrl the JDBC URL of the database
     * @throws SQLException if a database access error occurs
     */
    public static void connect(String jdbcUrl) throws SQLException {

        // Check whether there is not already a connection
        if (conn == null || conn.isClosed()) {
            conn = DriverManager.getConnection(jdbcUrl, JDBC_USER, JDBC_PASSWORD);
        }
    }

    /**
     * Executes a query with optional parameters and returns a ResultSet. Use for SELECT
     *
     * @param sql the SQL query to execute
     * @param url the Url for the connection, should be one of the three predefined constants
     * @param params optional parameters for the query
     * @return the ResultSet object resulting from the query
     * @throws SQLException if a database access error occurs
     */
    public static ResultSet executeQuery(String sql, String url, Object... params) throws SQLException {
        connect(url);

        // Last 2 parameters make the ResultSet scrollable
        pstmt = conn.prepareStatement(sql, ResultSet.TYPE_SCROLL_INSENSITIVE,
                ResultSet.CONCUR_READ_ONLY);

        // Set parameters, if any
        for (int i = 0; i < params.length; i++) {
            pstmt.setObject(i + 1, params[i]);
        }

        return pstmt.executeQuery();
    }

    /**
     * Executes an update, insert, or delete SQL statement with optional parameters.
     *
     * @param sql the SQL statement to execute
     * @param url the Url for the connection, should be one of the three predefined constants
     * @param params optional parameters for the query
     * @return the number of rows affected
     * @throws SQLException if a database access error occurs
     */
    public static int executeUpdate(String sql, String url, Object... params) throws SQLException {
        connect(url);
        pstmt = conn.prepareStatement(sql);

        // Set parameters, if any
        for (int i = 0; i < params.length; i++) {
            pstmt.setObject(i + 1, params[i]);
        }

        return pstmt.executeUpdate();
    }

    /**
     * Closes the connection and statement.
     */
    public static void close() {
        try {
            if (pstmt != null) pstmt.close();
            if (conn != null) conn.close();
        } catch (SQLException ex) {
            System.out.println(ex.getMessage());
        }
    }

    /**
     * Hashes a password
     *
     * @param password the password to be hashed
     * @return the hashed password
     */
    public static String hashPassowrd(String password) {

        // Generate the salt
        String salt = generateSalt();

        // Add salt and pepper to the password
        String saltedPepperedPassword = salt + password + PEPPER;

        // Break down the password into an array of characters
        char[] passwordChars = saltedPepperedPassword.toCharArray();

        // Hash the password
        String hash = argon2.hash(4, 1024 * 1024, 8, passwordChars);

        return salt + hash;
    }

    /**
     * Takes a String and compares it to a hashed String
     *
     * @param password the entered password
     * @param storedHash the stored hashed password
     * @return true or false where the passwords match or not
     */
    public static boolean checkPassword(String password, String storedHash) {

        // Get the salt part of the stored hash
        String extractedSalt = storedHash.substring(0, Base64.getEncoder().encodeToString(new byte[SALT_LENGTH]).length());

        // Get the password part of the stored hash
        String extractedHash = storedHash.substring(Base64.getEncoder().encodeToString(new byte[SALT_LENGTH]).length());

        // Add salt and pepper to the entered password
        String saltedPepperedPassword = extractedSalt + password + PEPPER;

        // Break down the new password into an array of characters
        char[] passwordChars = saltedPepperedPassword.toCharArray();

        // Check whether the passwords match
        return argon2.verify(extractedHash, passwordChars);
    }

    /**
     * Generates a random salt
     *
     * @return the salt
     */
    public static String generateSalt() {

        // create a byte array storing the characters of the salt
        byte[] salt = new byte[SALT_LENGTH];

        // create the salt characters
        secureRandom.nextBytes(salt);
        return Base64.getEncoder().encodeToString(salt);
    }

    /**
     * Retrieves the URL for connecting to PostgreSQL database
     *
     * @return the PostgreSQL Database URL
     */
    public static String getPostreSQLURL() {

        return JDBC_URL_POSTGRES;
    }

    /**
     * Retrieves the URL for connecting to MySQL database
     *
     * @return the MySQL Database URL
     */
    public static String getMySQLURL() {

        return JDBC_URL_MYSQL;
    }

}
