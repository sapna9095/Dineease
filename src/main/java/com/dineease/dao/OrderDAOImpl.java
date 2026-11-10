package com.dineease.dao;

import com.dineease.model.Order;
import com.dineease.model.OrderItem;
import com.dineease.util.DBConnection;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class OrderDAOImpl implements OrderDAO {

    // =========================
    // GET ALL ORDERS
    // =========================

    @Override
    public List<Order> getAllOrders() {

        List<Order> orders = new ArrayList<>();

        String sql =
                "SELECT order_id, table_id, order_date, " +
                        "order_status, subtotal, tax, total_amount " +
                        "FROM orders " +
                        "ORDER BY order_id DESC";

        try (
                Connection con = DBConnection.getConnection();
                PreparedStatement ps = con.prepareStatement(sql);
                ResultSet rs = ps.executeQuery()
        ) {

            while (rs.next()) {

                Order order = new Order();

                order.setOrderId(
                        rs.getInt("order_id")
                );

                order.setTableId(
                        rs.getInt("table_id")
                );

                order.setOrderDate(
                        rs.getTimestamp("order_date")
                );

                order.setOrderStatus(
                        rs.getString("order_status")
                );

                order.setSubtotal(
                        rs.getDouble("subtotal")
                );

                order.setTax(
                        rs.getDouble("tax")
                );

                order.setTotalAmount(
                        rs.getDouble("total_amount")
                );

                orders.add(order);
            }

        }
        catch (SQLException e) {

            e.printStackTrace();
        }

        return orders;
    }


    // =========================
    // GET ORDER BY ID
    // =========================

    @Override
    public Order getOrderById(
            int orderId
    ) {

        String sql =
                "SELECT order_id, table_id, order_date, " +
                        "order_status, subtotal, tax, total_amount " +
                        "FROM orders " +
                        "WHERE order_id=?";

        try (
                Connection con = DBConnection.getConnection();
                PreparedStatement ps = con.prepareStatement(sql)
        ) {

            ps.setInt(
                    1,
                    orderId
            );

            try (
                    ResultSet rs = ps.executeQuery()
            ) {

                if (rs.next()) {

                    Order order =
                            new Order();

                    order.setOrderId(
                            rs.getInt("order_id")
                    );

                    order.setTableId(
                            rs.getInt("table_id")
                    );

                    order.setOrderDate(
                            rs.getTimestamp("order_date")
                    );

                    order.setOrderStatus(
                            rs.getString("order_status")
                    );

                    order.setSubtotal(
                            rs.getDouble("subtotal")
                    );

                    order.setTax(
                            rs.getDouble("tax")
                    );

                    order.setTotalAmount(
                            rs.getDouble("total_amount")
                    );

                    return order;
                }
            }

        }
        catch (SQLException e) {

            e.printStackTrace();
        }

        return null;
    }


    // =========================
    // ADD ORDER
    // =========================

    @Override
    public int addOrder(
            Order order
    ) {

        String sql =
                "INSERT INTO orders " +
                        "(table_id, order_status, subtotal, tax, total_amount) " +
                        "VALUES (?, ?, ?, ?, ?)";

        try (
                Connection con = DBConnection.getConnection();
                PreparedStatement ps =
                        con.prepareStatement(
                                sql,
                                Statement.RETURN_GENERATED_KEYS
                        )
        ) {

            ps.setInt(
                    1,
                    order.getTableId()
            );

            ps.setString(
                    2,
                    order.getOrderStatus()
            );

            ps.setDouble(
                    3,
                    order.getSubtotal()
            );

            ps.setDouble(
                    4,
                    order.getTax()
            );

            ps.setDouble(
                    5,
                    order.getTotalAmount()
            );

            int rows =
                    ps.executeUpdate();

            if (rows == 0) {

                return -1;
            }

            try (
                    ResultSet rs =
                            ps.getGeneratedKeys()
            ) {

                if (rs.next()) {

                    int generatedId =
                            rs.getInt(1);

                    order.setOrderId(
                            generatedId
                    );

                    return generatedId;
                }
            }

        }
        catch (SQLException e) {

            e.printStackTrace();
        }

        return -1;
    }


    // =========================
    // UPDATE ORDER
    // =========================

    @Override
    public boolean updateOrder(
            Order order
    ) {

        String sql =
                "UPDATE orders SET " +
                        "table_id=?, " +
                        "order_status=?, " +
                        "subtotal=?, " +
                        "tax=?, " +
                        "total_amount=? " +
                        "WHERE order_id=?";

        try (
                Connection con = DBConnection.getConnection();
                PreparedStatement ps =
                        con.prepareStatement(sql)
        ) {

            ps.setInt(
                    1,
                    order.getTableId()
            );

            ps.setString(
                    2,
                    order.getOrderStatus()
            );

            ps.setDouble(
                    3,
                    order.getSubtotal()
            );

            ps.setDouble(
                    4,
                    order.getTax()
            );

            ps.setDouble(
                    5,
                    order.getTotalAmount()
            );

            ps.setInt(
                    6,
                    order.getOrderId()
            );

            return ps.executeUpdate() > 0;

        }
        catch (SQLException e) {

            e.printStackTrace();
        }

        return false;
    }


    // =========================
    // DELETE ORDER
    // =========================

    @Override
    public boolean deleteOrder(
            int orderId
    ) {

        String sql =
                "DELETE FROM orders " +
                        "WHERE order_id=?";

        try (
                Connection con = DBConnection.getConnection();
                PreparedStatement ps =
                        con.prepareStatement(sql)
        ) {

            ps.setInt(
                    1,
                    orderId
            );

            return ps.executeUpdate() > 0;

        }
        catch (SQLException e) {

            e.printStackTrace();
        }

        return false;
    }


    // =========================
    // UPDATE ORDER STATUS
    // =========================

    @Override
    public boolean updateOrderStatus(
            int orderId,
            String status
    ) {

        String sql =
                "UPDATE orders SET " +
                        "order_status=? " +
                        "WHERE order_id=?";

        try (
                Connection con = DBConnection.getConnection();
                PreparedStatement ps =
                        con.prepareStatement(sql)
        ) {

            ps.setString(
                    1,
                    status
            );

            ps.setInt(
                    2,
                    orderId
            );

            return ps.executeUpdate() > 0;

        }
        catch (SQLException e) {

            e.printStackTrace();
        }

        return false;
    }


    // =========================
    // GET ORDER ITEMS
    // =========================

    @Override
    public List<OrderItem> getOrderItems(int orderId) {

        List<OrderItem> items = new ArrayList<>();

        String sql =
                "SELECT oi.order_item_id, " +
                        "oi.order_id, " +
                        "oi.item_id, " +
                        "mi.item_name, " +
                        "oi.quantity, " +
                        "oi.price, " +
                        "oi.total_price " +
                        "FROM order_items oi " +
                        "JOIN menu_items mi " +
                        "ON oi.item_id = mi.item_id " +
                        "WHERE oi.order_id=? " +
                        "ORDER BY oi.order_item_id";

        try (
                Connection con = DBConnection.getConnection();
                PreparedStatement ps = con.prepareStatement(sql)
        ) {

            ps.setInt(1, orderId);

            try (ResultSet rs = ps.executeQuery()) {

                while (rs.next()) {

                    OrderItem item = new OrderItem();

                    item.setOrderItemId(
                            rs.getInt("order_item_id")
                    );

                    item.setOrderId(
                            rs.getInt("order_id")
                    );

                    item.setItemId(
                            rs.getInt("item_id")
                    );

                    item.setItemName(
                            rs.getString("item_name")
                    );

                    item.setQuantity(
                            rs.getInt("quantity")
                    );

                    item.setPrice(
                            rs.getDouble("price")
                    );

                    item.setTotalPrice(
                            rs.getDouble("total_price")
                    );

                    items.add(item);
                }
            }

        } catch (SQLException e) {

            e.printStackTrace();
        }

        return items;
    }


    // =========================
    // ADD ORDER ITEM
    // =========================

    @Override
    public boolean addOrderItem(
            OrderItem orderItem
    ) {

        String sql =
                "INSERT INTO order_items " +
                        "(order_id, item_id, quantity, price, total_price) " +
                        "VALUES (?, ?, ?, ?, ?)";

        try (
                Connection con = DBConnection.getConnection();
                PreparedStatement ps =
                        con.prepareStatement(sql)
        ) {

            ps.setInt(
                    1,
                    orderItem.getOrderId()
            );

            ps.setInt(
                    2,
                    orderItem.getItemId()
            );

            ps.setInt(
                    3,
                    orderItem.getQuantity()
            );

            ps.setDouble(
                    4,
                    orderItem.getPrice()
            );

            ps.setDouble(
                    5,
                    orderItem.getTotalPrice()
            );

            return ps.executeUpdate() > 0;

        }
        catch (SQLException e) {

            e.printStackTrace();
        }

        return false;
    }


    // =========================
    // UPDATE ORDER ITEM
    // =========================

    @Override
    public boolean updateOrderItem(
            OrderItem orderItem
    ) {

        String sql =
                "UPDATE order_items SET " +
                        "item_id=?, " +
                        "quantity=?, " +
                        "price=?, " +
                        "total_price=? " +
                        "WHERE order_item_id=?";

        try (
                Connection con = DBConnection.getConnection();
                PreparedStatement ps =
                        con.prepareStatement(sql)
        ) {

            ps.setInt(
                    1,
                    orderItem.getItemId()
            );

            ps.setInt(
                    2,
                    orderItem.getQuantity()
            );

            ps.setDouble(
                    3,
                    orderItem.getPrice()
            );

            ps.setDouble(
                    4,
                    orderItem.getTotalPrice()
            );

            ps.setInt(
                    5,
                    orderItem.getOrderItemId()
            );

            return ps.executeUpdate() > 0;

        }
        catch (SQLException e) {

            e.printStackTrace();
        }

        return false;
    }


    // =========================
    // DELETE ORDER ITEM
    // =========================

    @Override
    public boolean deleteOrderItem(
            int orderItemId
    ) {

        String sql =
                "DELETE FROM order_items " +
                        "WHERE order_item_id=?";

        try (
                Connection con = DBConnection.getConnection();
                PreparedStatement ps =
                        con.prepareStatement(sql)
        ) {

            ps.setInt(
                    1,
                    orderItemId
            );

            return ps.executeUpdate() > 0;

        }
        catch (SQLException e) {

            e.printStackTrace();
        }

        return false;
    }


    // =========================
    // DELETE ALL ORDER ITEMS
    // =========================

    @Override
    public boolean deleteAllOrderItems(
            int orderId
    ) {

        String sql =
                "DELETE FROM order_items " +
                        "WHERE order_id=?";

        try (
                Connection con = DBConnection.getConnection();
                PreparedStatement ps =
                        con.prepareStatement(sql)
        ) {

            ps.setInt(
                    1,
                    orderId
            );

            return ps.executeUpdate() >= 0;

        }
        catch (SQLException e) {

            e.printStackTrace();
        }

        return false;
    }
}