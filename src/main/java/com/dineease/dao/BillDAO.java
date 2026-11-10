package com.dineease.dao;

import com.dineease.model.Bill;
import com.dineease.model.BillItem;
import com.dineease.util.DBConnection;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class BillDAO {

    // =========================
    // ADD BILL
    // =========================

    public int addBill(Bill bill) {

        String sql =
                "INSERT INTO bills " +
                        "(table_number, subtotal, tax, discount, total_amount, payment_method, status) " +
                        "VALUES (?, ?, ?, ?, ?, ?, ?)";

        try (
                Connection connection = DBConnection.getConnection();

                PreparedStatement statement =
                        connection.prepareStatement(
                                sql,
                                Statement.RETURN_GENERATED_KEYS
                        )
        ) {

            statement.setInt(
                    1,
                    bill.getTableNumber()
            );

            statement.setDouble(
                    2,
                    bill.getSubtotal()
            );

            statement.setDouble(
                    3,
                    bill.getTax()
            );

            statement.setDouble(
                    4,
                    bill.getDiscount()
            );

            statement.setDouble(
                    5,
                    bill.getTotalAmount()
            );

            statement.setString(
                    6,
                    bill.getPaymentMethod()
            );

            statement.setString(
                    7,
                    bill.getStatus()
            );

            int rows =
                    statement.executeUpdate();

            if (rows > 0) {

                try (
                        ResultSet generatedKeys =
                                statement.getGeneratedKeys()
                ) {

                    if (generatedKeys.next()) {

                        int billId =
                                generatedKeys.getInt(1);

                        bill.setBillId(billId);

                        return billId;
                    }
                }
            }

        } catch (SQLException e) {

            e.printStackTrace();
        }

        return -1;
    }


    // =========================
    // ADD BILL ITEM
    // =========================

    public boolean addBillItem(BillItem item) {

        String sql =
                "INSERT INTO bill_items " +
                        "(bill_id, item_id, quantity, price, total_price) " +
                        "VALUES (?, ?, ?, ?, ?)";

        try (
                Connection connection =
                        DBConnection.getConnection();

                PreparedStatement statement =
                        connection.prepareStatement(sql)
        ) {

            statement.setInt(
                    1,
                    item.getBillId()
            );

            statement.setInt(
                    2,
                    item.getMenuItemId()
            );

            statement.setInt(
                    3,
                    item.getQuantity()
            );

            statement.setDouble(
                    4,
                    item.getPrice()
            );

            statement.setDouble(
                    5,
                    item.getSubtotal()
            );

            return statement.executeUpdate() > 0;

        } catch (SQLException e) {

            e.printStackTrace();

            return false;
        }
    }


    // =========================
    // GET ALL BILLS
    // =========================

    public List<Bill> getAllBills() {

        List<Bill> bills =
                new ArrayList<>();

        String sql =
                "SELECT bill_id, table_number, bill_date, " +
                        "subtotal, tax, discount, total_amount, " +
                        "payment_method, status " +
                        "FROM bills " +
                        "ORDER BY bill_date DESC";

        try (
                Connection connection =
                        DBConnection.getConnection();

                PreparedStatement statement =
                        connection.prepareStatement(sql);

                ResultSet resultSet =
                        statement.executeQuery()
        ) {

            while (resultSet.next()) {

                Bill bill =
                        new Bill();

                bill.setBillId(
                        resultSet.getInt("bill_id")
                );

                bill.setTableNumber(
                        resultSet.getInt("table_number")
                );

                bill.setBillDate(
                        resultSet.getTimestamp("bill_date")
                );

                bill.setSubtotal(
                        resultSet.getDouble("subtotal")
                );

                bill.setTax(
                        resultSet.getDouble("tax")
                );

                bill.setDiscount(
                        resultSet.getDouble("discount")
                );

                bill.setTotalAmount(
                        resultSet.getDouble("total_amount")
                );

                bill.setPaymentMethod(
                        resultSet.getString("payment_method")
                );

                bill.setStatus(
                        resultSet.getString("status")
                );

                bills.add(bill);
            }

        } catch (SQLException e) {

            e.printStackTrace();
        }

        return bills;
    }


    // =========================
    // GET BILL ITEMS
    // =========================
// =========================
// GET BILL ITEMS
// =========================

    public List<BillItem> getBillItems(int billId) {

        List<BillItem> items =
                new ArrayList<>();

        String sql =
                "SELECT " +
                        "bi.bill_item_id, " +
                        "bi.bill_id, " +
                        "bi.item_id, " +
                        "mi.item_name, " +
                        "bi.quantity, " +
                        "bi.price, " +
                        "bi.total_price " +
                        "FROM bill_items bi " +
                        "JOIN menu_items mi " +
                        "ON bi.item_id = mi.item_id " +
                        "WHERE bi.bill_id = ?";

        try (
                Connection connection =
                        DBConnection.getConnection();

                PreparedStatement statement =
                        connection.prepareStatement(sql)
        ) {

            statement.setInt(
                    1,
                    billId
            );

            try (
                    ResultSet resultSet =
                            statement.executeQuery()
            ) {

                while (resultSet.next()) {

                    BillItem item =
                            new BillItem();

                    item.setBillItemId(
                            resultSet.getInt(
                                    "bill_item_id"
                            )
                    );

                    item.setBillId(
                            resultSet.getInt(
                                    "bill_id"
                            )
                    );

                    item.setMenuItemId(
                            resultSet.getInt(
                                    "item_id"
                            )
                    );

                    item.setItemName(
                            resultSet.getString(
                                    "item_name"
                            )
                    );

                    item.setQuantity(
                            resultSet.getInt(
                                    "quantity"
                            )
                    );

                    item.setPrice(
                            resultSet.getDouble(
                                    "price"
                            )
                    );

                    item.setSubtotal(
                            resultSet.getDouble(
                                    "total_price"
                            )
                    );

                    items.add(
                            item
                    );
                }
            }

        } catch (SQLException e) {

            e.printStackTrace();
        }

        return items;
    }


    // =========================
    // UPDATE BILL STATUS
    // =========================

    public boolean updateBillStatus(
            int billId,
            String status
    ) {

        String sql =
                "UPDATE bills " +
                        "SET status = ? " +
                        "WHERE bill_id = ?";

        try (
                Connection connection =
                        DBConnection.getConnection();

                PreparedStatement statement =
                        connection.prepareStatement(sql)
        ) {

            statement.setString(
                    1,
                    status
            );

            statement.setInt(
                    2,
                    billId
            );

            return statement.executeUpdate() > 0;

        } catch (SQLException e) {

            e.printStackTrace();

            return false;
        }
    }
    // =========================
// DELETE BILL
// =========================


    public boolean deleteBill(int billId) {

        String sql =
                "DELETE FROM bills " +
                        "WHERE bill_id=?";

        try (
                Connection con = DBConnection.getConnection();
                PreparedStatement ps =
                        con.prepareStatement(sql)
        ) {

            ps.setInt(
                    1,
                    billId
            );

            return ps.executeUpdate() > 0;

        } catch (SQLException e) {

            e.printStackTrace();
        }

        return false;
    }
}