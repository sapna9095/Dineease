package com.dineease.dao;

import com.dineease.db.DBConnection;
import com.dineease.model.DeliveryOrder;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class DeliveryDAO {

    public static synchronized void ensureDeliveryTable() {
        try (Connection conn = DBConnection.getConnection();
             Statement stmt = conn.createStatement()) {
            stmt.executeUpdate("""
                CREATE TABLE IF NOT EXISTS delivery_orders (
                    id INT AUTO_INCREMENT PRIMARY KEY,
                    customer_name VARCHAR(100) NOT NULL,
                    customer_phone VARCHAR(20) NOT NULL,
                    area VARCHAR(50) NOT NULL,
                    full_address VARCHAR(255) NOT NULL,
                    items_summary TEXT NOT NULL,
                    total_amount DECIMAL(10,2) NOT NULL DEFAULT 0.00,
                    status ENUM('Order Placed', 'Preparing', 'Out for Delivery', 'Delivered', 'Cancelled') NOT NULL DEFAULT 'Order Placed',
                    rider_lat DECIMAL(10,6) DEFAULT 26.7818,
                    rider_lng DECIMAL(10,6) DEFAULT 75.8234,
                    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
                    INDEX idx_delivery_area (area),
                    INDEX idx_delivery_status (status)
                ) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4
            """);

            // Seed initial real orders if table is completely empty
            try (ResultSet rs = stmt.executeQuery("SELECT COUNT(*) FROM delivery_orders")) {
                if (rs.next() && rs.getInt(1) == 0) {
                    stmt.executeUpdate("""
                        INSERT INTO delivery_orders (customer_name, customer_phone, area, full_address, items_summary, total_amount, status, rider_lat, rider_lng)
                        VALUES 
                        ('Rohit Sharma', '9829012345', 'Mansarovar', 'Flat 402, Shipra Path, Mansarovar', '1x Paneer Butter Masala, 3x Butter Naan', 480.00, 'Out for Delivery', 26.8200, 75.7900),
                        ('Ananya Gupta', '9414056789', 'Sitapura', 'Plot 12, RIICO Institutional Area, Sitapura', '2x Dal Makhani, 1x Jeera Rice', 390.00, 'Preparing', 26.7818, 75.8234),
                        ('Vikramaditya Rathore', '9828034567', 'Malviya Nagar', 'B-14, Near Gaurav Tower, Malviya Nagar', '1x Veg Biryani, 1x Raita, 2x Gulab Jamun', 520.00, 'Out for Delivery', 26.8300, 75.8150),
                        ('Pooja Choudhary', '9928178901', 'Vaishali Nagar', '304, Queens Road, Vaishali Nagar', '1x Royal Thali, 1x Sweet Lassi', 650.00, 'Order Placed', 26.7818, 75.8234),
                        ('Deepak Khandelwal', '9785023456', 'Raja Park', 'Shop 7, Gali No. 4, Raja Park', '2x Chole Bhature, 2x Masala Chai', 310.00, 'Delivered', 26.8970, 75.8270)
                    """);
                }
            }
        } catch (Exception e) {
            System.err.println("Ensure delivery table warning: " + e.getMessage());
        }
    }

    public List<DeliveryOrder> getAllDeliveryOrders() {
        ensureDeliveryTable();
        List<DeliveryOrder> list = new ArrayList<>();
        String sql = "SELECT id, customer_name, customer_phone, area, full_address, items_summary, total_amount, status, rider_lat, rider_lng, created_at " +
                "FROM delivery_orders ORDER BY id DESC";
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {
            while (rs.next()) {
                list.add(new DeliveryOrder(
                        rs.getInt("id"),
                        rs.getString("customer_name"),
                        rs.getString("customer_phone"),
                        rs.getString("area"),
                        rs.getString("full_address"),
                        rs.getString("items_summary"),
                        rs.getDouble("total_amount"),
                        rs.getString("status"),
                        rs.getDouble("rider_lat"),
                        rs.getDouble("rider_lng"),
                        rs.getTimestamp("created_at")
                ));
            }
        } catch (Exception e) {
            System.err.println("Error fetching delivery orders: " + e.getMessage());
        }
        return list;
    }

    public boolean createDeliveryOrder(DeliveryOrder order) {
        ensureDeliveryTable();
        String sql = "INSERT INTO delivery_orders (customer_name, customer_phone, area, full_address, items_summary, total_amount, status, rider_lat, rider_lng) " +
                "VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?)";
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
            ps.setString(1, order.getCustomerName());
            ps.setString(2, order.getCustomerPhone());
            ps.setString(3, order.getArea());
            ps.setString(4, order.getFullAddress());
            ps.setString(5, order.getItemsSummary());
            ps.setDouble(6, order.getTotalAmount());
            ps.setString(7, order.getStatus() != null ? order.getStatus() : "Order Placed");
            ps.setDouble(8, order.getRiderLat());
            ps.setDouble(9, order.getRiderLng());

            int affected = ps.executeUpdate();
            if (affected > 0) {
                try (ResultSet generatedKeys = ps.getGeneratedKeys()) {
                    if (generatedKeys.next()) {
                        order.setId(generatedKeys.getInt(1));
                    }
                }
                return true;
            }
        } catch (Exception e) {
            System.err.println("Error creating delivery order: " + e.getMessage());
        }
        return false;
    }

    public boolean updateStatus(int orderId, String newStatus) {
        ensureDeliveryTable();
        String sql = "UPDATE delivery_orders SET status = ? WHERE id = ?";
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setString(1, newStatus);
            ps.setInt(2, orderId);
            return ps.executeUpdate() > 0;
        } catch (Exception e) {
            System.err.println("Error updating delivery status: " + e.getMessage());
            return false;
        }
    }
}

