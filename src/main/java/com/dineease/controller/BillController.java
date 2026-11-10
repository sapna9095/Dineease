package com.dineease.controller;
import com.dineease.model.Bill;

import java.util.List;

import com.dineease.dao.BillDAO;

import com.dineease.model.BillItem;



public class BillController {

    private final BillDAO billDAO;



    // =========================
    // CONSTRUCTOR
    // =========================

    public BillController() {

        billDAO = new BillDAO();

    }


    // =========================
    // ADD BILL
    // =========================

    public int addBill(Bill bill) {

        if (bill == null) {

            return -1;

        }


        if (bill.getTableNumber() < 0) {

            return -1;

        }


        if (bill.getSubtotal() < 0) {

            return -1;

        }


        if (bill.getTax() < 0) {

            return -1;

        }


        if (bill.getDiscount() < 0) {

            return -1;

        }


        if (bill.getTotalAmount() < 0) {

            return -1;

        }


        return billDAO.addBill(bill);
    }

    // =========================



    // =========================
    // ADD BILL ITEM
    // =========================

    public boolean addBillItem(BillItem item) {

        if (item == null) {

            return false;

        }


        if (item.getBillId() <= 0) {

            return false;

        }


        if (item.getMenuItemId() <= 0) {

            return false;

        }


        if (item.getQuantity() <= 0) {

            return false;

        }


        if (item.getPrice() < 0) {

            return false;

        }


        if (item.getSubtotal() < 0) {

            return false;

        }


        return billDAO.addBillItem(item);
    }


    // =========================
    // GET ALL BILLS
    // =========================

    public List<Bill> getAllBills() {

        return billDAO.getAllBills();

    }


    // =========================
    // GET BILL ITEMS
    // =========================

    public List<BillItem> getBillItems(
            int billId
    ) {

        if (billId <= 0) {

            return List.of();

        }


        return billDAO.getBillItems(
                billId
        );
    }


    // =========================
    // UPDATE BILL STATUS
    // =========================

    public boolean updateBillStatus(
            int billId,
            String status
    ) {

        if (billId <= 0) {

            return false;

        }


        if (
                status == null
                        ||
                        status.trim().isEmpty()
        ) {

            return false;

        }


        return billDAO.updateBillStatus(
                billId,
                status
        );
    }
        // =========================
// DELETE BILL
// =========================

        public boolean deleteBill(int billId) {

            if (billId <= 0) {
                return false;
            }

            return billDAO.deleteBill(billId);
        }


    }



