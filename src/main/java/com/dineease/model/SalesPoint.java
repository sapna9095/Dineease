package com.dineease.model;

import java.time.LocalDate;

public class SalesPoint {
    private final LocalDate date;
    private final String label;
    private final double amount;

    public SalesPoint(LocalDate date, String label, double amount) {
        this.date = date;
        this.label = label;
        this.amount = amount;
    }

    public LocalDate getDate() {
        return date;
    }

    public String getLabel() {
        return label;
    }

    public double getAmount() {
        return amount;
    }
}
