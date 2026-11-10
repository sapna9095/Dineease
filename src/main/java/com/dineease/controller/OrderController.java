package com.dineease.controller;

import com.dineease.dao.OrderDAO;
import com.dineease.dao.OrderDAOImpl;
import com.dineease.model.Order;
import com.dineease.model.OrderItem;

import java.util.List;

public class OrderController {

    private OrderDAO orderDAO;


    // =========================
    // CONSTRUCTOR
    // =========================

    public OrderController() {

        orderDAO =
                new OrderDAOImpl();
    }


    // =========================
    // GET ALL ORDERS
    // =========================

    public List<Order> getAllOrders() {

        return orderDAO.getAllOrders();
    }


    // =========================
    // GET ORDER BY ID
    // =========================

    public Order getOrderById(
            int orderId
    ) {

        return orderDAO.getOrderById(
                orderId
        );
    }


    // =========================
    // ADD ORDER
    // =========================

    public int addOrder(
            Order order
    ) {

        return orderDAO.addOrder(
                order
        );
    }


    // =========================
    // UPDATE ORDER
    // =========================

    public boolean updateOrder(
            Order order
    ) {

        return orderDAO.updateOrder(
                order
        );
    }


    // =========================
    // DELETE ORDER
    // =========================

    public boolean deleteOrder(
            int orderId
    ) {

        return orderDAO.deleteOrder(
                orderId
        );
    }


    // =========================
    // UPDATE ORDER STATUS
    // =========================

    public boolean updateOrderStatus(
            int orderId,
            String status
    ) {

        return orderDAO.updateOrderStatus(
                orderId,
                status
        );
    }


    // =========================
    // GET ORDER ITEMS
    // =========================

    public List<OrderItem> getOrderItems(
            int orderId
    ) {

        return orderDAO.getOrderItems(
                orderId
        );
    }


    // =========================
    // ADD ORDER ITEM
    // =========================

    public boolean addOrderItem(
            OrderItem orderItem
    ) {

        return orderDAO.addOrderItem(
                orderItem
        );
    }


    // =========================
    // UPDATE ORDER ITEM
    // =========================

    public boolean updateOrderItem(
            OrderItem orderItem
    ) {

        return orderDAO.updateOrderItem(
                orderItem
        );
    }


    // =========================
    // DELETE ORDER ITEM
    // =========================

    public boolean deleteOrderItem(
            int orderItemId
    ) {

        return orderDAO.deleteOrderItem(
                orderItemId
        );
    }


    // =========================
    // DELETE ALL ORDER ITEMS
    // =========================

    public boolean deleteAllOrderItems(
            int orderId
    ) {

        return orderDAO.deleteAllOrderItems(
                orderId
        );
    }
}