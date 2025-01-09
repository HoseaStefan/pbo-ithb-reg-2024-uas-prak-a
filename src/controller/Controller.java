package controller;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import model.CurrentUser;
import model.Customer;

public class Controller {

    static DatabaseHandler conn = new DatabaseHandler();

    public static Customer verifyUser(String phone, String password) {
        try {
            conn.connect();
            String query = "SELECT * FROM customer WHERE phone = ? AND password = ?";
            PreparedStatement stmt = conn.con.prepareStatement(query);
            stmt.setString(1, phone);
            stmt.setString(2, password);

            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                Customer loggedInUser = new Customer(rs.getInt("customer_id"), rs.getString("password"), rs.getString("name"),
                        rs.getString("address"), rs.getString("phone"));

                if (loggedInUser != null) {
                    CurrentUser.getInstance().setUser(loggedInUser);
                }

                return loggedInUser;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    public static boolean registerUser(String phone, String address, String name, String password) {
        try {
            conn.connect();
            String query = "SELECT * FROM customer WHERE phone = ? AND address = ?";
            PreparedStatement stmt = conn.con.prepareStatement(query);
            stmt.setString(1, phone);
            stmt.setString(2, address);

            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                return false;
            }

            String insertQuery = "INSERT INTO customer (customer_id, name, address, phone, password) VALUES (?, ?, ?, ?, ?)";
            PreparedStatement insertStmt = conn.con.prepareStatement(insertQuery);
            int userUniqueId = generateUniqueId();
            insertStmt.setInt(1, userUniqueId);
            insertStmt.setString(2, name);
            insertStmt.setString(3, address);
            insertStmt.setString(4, phone);
            insertStmt.setString(5, password);

            int rowsInserted = insertStmt.executeUpdate();
            return rowsInserted > 0;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    public static int generateUniqueId() {
        String uniqueId = (System.currentTimeMillis() % 1000000) + "";
        return Integer.parseInt(uniqueId);
    }
}
