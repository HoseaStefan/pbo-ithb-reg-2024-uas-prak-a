package controller;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import javax.swing.JOptionPane;
import javax.swing.table.DefaultTableModel;

import model.CurrentUser;
import model.Customer;

public class Controller {

    static DatabaseHandler conn = new DatabaseHandler();

    public static void addShipmentDetails(int transactionId, String status, String currentPosition, String evidence,
            String updatedBy) {
        try {
            conn.connect();

            String query = "INSERT INTO shipment_details (transaction_id, status, current_position, evidence, updated_by, date) "
                    + "VALUES (?, ?, ?, ?, ?, ?)";

            PreparedStatement stmt = conn.con.prepareStatement(query);

            stmt.setInt(1, transactionId);
            stmt.setString(2, status);
            stmt.setString(3, currentPosition);
            stmt.setString(4, evidence);
            stmt.setString(5, updatedBy);
            stmt.setString(6, java.time.LocalDateTime.now().toString());

            int rowsAffected = stmt.executeUpdate();

            if (rowsAffected > 0) {
                JOptionPane.showMessageDialog(null, "Shipment details successfully saved!");
            } else {
                JOptionPane.showMessageDialog(null, "Failed to save shipment details!");
            }
        } catch (SQLException e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(null, "Database error: " + e.getMessage());
        }
    }

    public static void loadDetailHistoryData(DefaultTableModel tableModel, String transactionId) {

        try {
            conn.connect();

            String query = "SELECT status, evidence, date, updated_by FROM shipment_details WHERE transaction_id = ? ORDER BY date DESC";

            PreparedStatement stmt = conn.con.prepareStatement(query);
            stmt.setString(1, transactionId);

            try (ResultSet rs = stmt.executeQuery()) {
                tableModel.setRowCount(0);

                while (rs.next()) {
                    String status = rs.getString("status");
                    String evidence = rs.getString("evidence");
                    String date = rs.getString("date");
                    String updatedBy = rs.getString("updated_by");

                    tableModel.addRow(new Object[] { status, evidence, date, updatedBy });
                }
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public static void loadTransactionData(DefaultTableModel tableModel, String customerId) {
        try {
            conn.connect();
            String query = """
                        SELECT
                            t.transaction_id,
                            t.package_type,
                            t.package_weight,
                            t.total_cost,
                            t.created_at,
                            MAX(sd.updated_at) AS updated_at
                        FROM
                            transaction t
                        LEFT JOIN
                            shipment_details sd ON t.transaction_id = sd.transaction_id
                        WHERE
                            t.customer_id = ?
                        GROUP BY
                            t.transaction_id, t.package_type, t.package_weight, t.total_cost, t.created_at
                        ORDER BY
                            t.created_at DESC
                    """;

            PreparedStatement stmt = conn.con.prepareStatement(query);
            stmt.setString(1, customerId);

            ResultSet rs = stmt.executeQuery();

            while (rs.next()) {
                String transactionId = rs.getString("transaction_id");
                String packageType = rs.getString("package_type");
                double packageWeight = rs.getDouble("package_weight");
                int totalCost = rs.getInt("total_cost");
                String createdAt = rs.getString("created_at");
                String updatedAt = rs.getString("updated_at");

                tableModel.addRow(new Object[] {
                        transactionId,
                        packageType,
                        packageWeight,
                        totalCost,
                        createdAt,
                        updatedAt != null ? updatedAt : "N/A",
                        "View Details"
                });
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public static void loadTransactionDataKurir(DefaultTableModel tableModel) {
        try {
            conn.connect();
            String query = """
                        SELECT
                            t.transaction_id,
                            t.created_at,
                            MAX(sd.updated_at) AS updated_at
                        FROM
                            transaction t
                        LEFT JOIN
                            shipment_details sd ON t.transaction_id = sd.transaction_id
                        GROUP BY
                            t.transaction_id, t.created_at
                        ORDER BY
                            t.created_at DESC
                    """;

            PreparedStatement stmt = conn.con.prepareStatement(query);
            ResultSet rs = stmt.executeQuery();

            while (rs.next()) {
                String transactionId = rs.getString("transaction_id");
                String createdAt = rs.getString("created_at");
                String updatedAt = rs.getString("updated_at");

                tableModel.addRow(new Object[] {
                        transactionId,
                        createdAt,
                        updatedAt != null ? updatedAt : "N/A",
                        "View Details"
                });
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public static int createTransaksi(Customer currentCustomer, String name, String address, String phone,
            String weightText, String packageType) {
        try {
            double weight = Double.parseDouble(weightText);
            if (weight < 1) {
                weight = 1;
            } else {
                double desimal = weight - Math.floor(weight);
                if (desimal > 0.4) {
                    weight = (int) Math.ceil(weight);
                } else {
                    weight = (int) Math.floor(weight);
                }
            }

            int costPerKilo;
            switch (packageType) {
                case "Fast":
                    costPerKilo = 10000;
                    break;
                case "Super Fast":
                    costPerKilo = 20000;
                    break;
                default:
                    costPerKilo = 5000;
            }

            int totalCost = (int) (costPerKilo * weight);
            weight = Double.parseDouble(weightText);

            conn.connect();
            String query = "INSERT INTO transaction (customer_id, package_type, package_weight, total_cost, created_at, receipt_name, receipt_address, receipt_phone) VALUES (?, ?, ?, ?, ?, ?, ?, ?)";
            PreparedStatement stmt = conn.con.prepareStatement(query, PreparedStatement.RETURN_GENERATED_KEYS);

            stmt.setInt(1, currentCustomer.getCustomer_id());
            stmt.setString(2, packageType);
            stmt.setDouble(3, weight);
            stmt.setInt(4, totalCost);
            stmt.setString(5, java.time.LocalDateTime.now().toString());
            stmt.setString(6, name);
            stmt.setString(7, address);
            stmt.setString(8, phone);

            int rowsInserted = stmt.executeUpdate();
            if (rowsInserted > 0) {
                System.out.println("Transaction inserted successfully!");

                ResultSet generatedKeys = stmt.getGeneratedKeys();
                if (generatedKeys.next()) {
                    int transactionId = generatedKeys.getInt(1);

                    String query2 = "INSERT INTO shipment_details (transaction_id, status, date, updated_by) VALUES (?, ?, ?, ?)";
                    PreparedStatement stmt2 = conn.con.prepareStatement(query2);
                    stmt2.setInt(1, transactionId);
                    stmt2.setString(2, "pending");
                    stmt2.setString(3, java.time.LocalDateTime.now().toString());
                    stmt2.setString(3, "Kurir");

                    int rowsInserted2 = stmt2.executeUpdate();
                    if (rowsInserted2 > 0) {
                        System.out.println("Shipment details inserted successfully!");
                    } else {
                        System.out.println("Failed to insert shipment details.");
                    }
                }
            } else {
                System.out.println("Failed to insert transaction.");
            }

            return totalCost;
        } catch (SQLException e) {
            e.printStackTrace();
            return -1;
        } catch (NumberFormatException ex) {
            System.out.println("Invalid weight format!");
            return -1;
        }
    }

    public static Customer verifyUser(String phone, String password) {
        try {
            conn.connect();
            String query = "SELECT * FROM customer WHERE phone = ? AND password = ?";
            PreparedStatement stmt = conn.con.prepareStatement(query);
            stmt.setString(1, phone);
            stmt.setString(2, password);

            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                Customer loggedInUser = new Customer(rs.getInt("customer_id"), rs.getString("password"),
                        rs.getString("name"),
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
            String query = "SELECT * FROM customer WHERE phone = ? OR address = ?";
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
