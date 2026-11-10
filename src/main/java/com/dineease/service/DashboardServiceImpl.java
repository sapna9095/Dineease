package com.dineease.service;

import com.dineease.dao.DashboardDAO;
import com.dineease.model.DashboardStats;
import com.dineease.model.OrderRow;
import com.dineease.model.OrderStatusCount;
import com.dineease.model.SalesData;
import com.dineease.model.SalesPoint;

import java.util.List;

public class DashboardServiceImpl implements DashboardService {

    private final DashboardDAO dashboardDAO = new DashboardDAO();

    @Override
    public DashboardStats getDashboardStats() {
        return dashboardDAO.getDashboardStats();
    }

    @Override
    public SalesData getSalesData(String period) {
        return dashboardDAO.getSalesData(period);
    }

    @Override
    public List<SalesPoint> getSalesTrend() {
        return dashboardDAO.getSalesData("Last 7 Days").getPoints();
    }

    @Override
    public List<OrderStatusCount> getOrderStatuses() {
        return dashboardDAO.getOrderStatuses();
    }

    @Override
    public List<OrderRow> getRecentOrders() {
        return dashboardDAO.getRecentOrders();
    }
}

