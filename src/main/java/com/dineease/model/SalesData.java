package com.dineease.model;

import java.util.List;

public class SalesData {
    private final String range;
    private final double totalSales;
    private final double percentChange;
    private final List<SalesPoint> points;

    public SalesData(String range, double totalSales, double percentChange, List<SalesPoint> points) {
        this.range = range;
        this.totalSales = totalSales;
        this.percentChange = percentChange;
        this.points = points;
    }

    public String getRange() {
        return range;
    }

    public double getTotalSales() {
        return totalSales;
    }

    public double getPercentChange() {
        return percentChange;
    }

    public List<SalesPoint> getPoints() {
        return points;
    }
}

