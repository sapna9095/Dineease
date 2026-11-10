package com.dineease.dao;

import com.dineease.model.Order;
import com.dineease.model.OrderItem;

import java.util.List;

public interface OrderDAO {

    // =========================
    // GET ALL ORDERS
    // =========================

    List<Order> getAllOrders();


    // =========================
    // GET ORDER BY ID
    // =========================

    Order getOrderById(
            int orderId
    );


    // =========================
    // ADD ORDER
    // =========================

    int addOrder(
            Order order
    );


    // =========================
    // UPDATE ORDER
    // =========================

    boolean updateOrder(
            Order order
    );


    // =========================
    // DELETE ORDER
    // =========================

    boolean deleteOrder(
            int orderId
    );


    // =========================
    // UPDATE ORDER STATUS
    // =========================

    boolean updateOrderStatus(
            int orderId,
            String status
    );


    // =========================
    // GET ORDER ITEMS
    // =========================

    List<OrderItem> getOrderItems(
            int orderId
    );


    // =========================
    // ADD ORDER ITEM
    // =========================

    boolean addOrderItem(
            OrderItem orderItem
    );


    // =========================
    // UPDATE ORDER ITEM
    // =========================

    boolean updateOrderItem(
            OrderItem orderItem
    );


    // =========================
    // DELETE ORDER ITEM
    // =========================

    boolean deleteOrderItem(
            int orderItemId
    );


    // =========================
    // DELETE ALL ORDER ITEMS
    // =========================

    boolean deleteAllOrderItems(
            int orderId
    );
}