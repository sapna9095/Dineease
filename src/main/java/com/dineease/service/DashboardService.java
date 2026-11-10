package com.dineease.service;

import com.dineease.model.DashboardStats;
import com.dineease.model.OrderRow;
import com.dineease.model.OrderStatusCount;
import com.dineease.model.SalesData;
import com.dineease.model.SalesPoint;

import java.util.List;

public interface DashboardService {
    DashboardStats getDashboardStats();
    SalesData getSalesData(String period);
    List<SalesPoint> getSalesTrend();
    List<OrderStatusCount> getOrderStatuses();
    List<OrderRow> getRecentOrders();
}
