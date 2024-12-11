package edu.branwen;

import java.sql.*;

public class LoginOperations {
    static String DatabaseLocation = "jdbc:sqlite:src/db/SMCLite.db";

    /**
     * Check if username and password exists in the SQLite database.
     * @param username username for logging in.
     * @param password password for user.
     * @return true if username and password exist in the database.
     */
    public static boolean CheckUser(String username, String password) {
        boolean output = false;
        Connection loginConnection = null;
        PreparedStatement loginStatement = null;
        ResultSet loginResult = null;

        try {
            Class.forName("org.sqlite.JDBC");

            loginConnection = DriverManager.getConnection(DatabaseLocation);

            String query = "SELECT * FROM LoginData WHERE [Username] = ? AND [Password] = ?";

            loginStatement = loginConnection.prepareStatement(query);
            loginStatement.setString(1, username);
            loginStatement.setString(2, password);

            loginResult = loginStatement.executeQuery();

            output = loginResult.next();
        } catch (SQLException | ClassNotFoundException err) {
            System.out.println(err.getMessage());
        } finally {
            try {
                if (loginResult != null) {
                    loginResult.close();
                }
                if (loginStatement != null) {
                    loginStatement.close();
                }
                if (loginConnection != null) {
                    loginConnection.close();
                }
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }

        return output;
    }
}