package com.dineease.service;

import com.dineease.model.DashboardStats;
import com.dineease.model.OrderRow;
import com.dineease.model.OrderStatusCount;
import com.dineease.model.SalesData;
import com.dineease.model.SalesPoint;

import java.awt.Color;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class MockDashboardService implements DashboardService {

    @Override
    public DashboardStats getDashboardStats() {
        return DashboardStats.empty();
    }

    @Override
    public SalesData getSalesData(String period) {
        return new SalesData(period, 0.0, 0.0, new ArrayList<>());
    }

    @Override
    public List<SalesPoint> getSalesTrend() {
        return new ArrayList<>();
    }

    @Override
    public List<OrderStatusCount> getOrderStatuses() {
        return List.of(
                new OrderStatusCount("Pending", 0, new Color(237, 170, 58)),
                new OrderStatusCount("Preparing", 0, new Color(76, 141, 246)),
                new OrderStatusCount("Ready", 0, new Color(46, 158, 91)),
                new OrderStatusCount("Served", 0, new Color(124, 77, 190)),
                new OrderStatusCount("Cancelled", 0, new Color(224, 79, 74))
        );
    }

    @Override
    public List<OrderRow> getRecentOrders() {
        return new ArrayList<>();
    }
}
