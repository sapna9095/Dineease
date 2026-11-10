package com.dineease.ui;

import com.dineease.controller.MenuController;
import com.dineease.model.MenuItem;
import com.dineease.util.UITheme;

import javax.swing.*;
import java.awt.*;

public class BillingManagementPanel extends JPanel {

    private JButton createBillButton;
    private JButton viewBillsButton;

    public BillingManagementPanel() {

        setLayout(
                new BorderLayout(
                        20,
                        20
                )
        );

        setBackground(
                UITheme.BACKGROUND
        );

        setBorder(
                BorderFactory.createEmptyBorder(
                        30,
                        30,
                        30,
                        30
                )
        );


        createHeader();

        createCards();
    }


    // =========================
    // HEADER
    // =========================

    private void createHeader() {

        JPanel header =
                new JPanel(
                        new BorderLayout()
                );

        header.setBackground(
                UITheme.BACKGROUND
        );


        JLabel title =
                new JLabel(
                        "Billing Management"
                );

        title.setFont(
                new Font(
                        "Arial",
                        Font.BOLD,
                        28
                )
        );

        title.setForeground(
                UITheme.TEXT
        );


        JLabel subtitle =
                new JLabel(
                        "Create and manage restaurant bills"
                );

        subtitle.setForeground(
                Color.GRAY
        );

        subtitle.setFont(
                new Font(
                        "Arial",
                        Font.PLAIN,
                        13
                )
        );


        JPanel textPanel =
                new JPanel(
                        new GridLayout(
                                2,
                                1
                        )
                );

        textPanel.setBackground(
                UITheme.BACKGROUND
        );

        textPanel.add(title);

        textPanel.add(subtitle);


        header.add(
                textPanel,
                BorderLayout.WEST
        );


        add(
                header,
                BorderLayout.NORTH
        );
    }


    // =========================
    // CARDS
    // =========================

    private void createCards() {

        JPanel cards =
                new JPanel(
                        new GridLayout(
                                1,
                                2,
                                25,
                                25
                        )
                );

        cards.setBackground(
                UITheme.BACKGROUND
        );


        createBillButton =
                new JButton(
                        "Create New Bill"
                );

        viewBillsButton =
                new JButton(
                        "View Bills"
                );


        UITheme.stylePrimaryButton(
                createBillButton
        );

        UITheme.styleSecondaryButton(
                viewBillsButton
        );


        cards.add(
                createCard(
                        "Create Bill",
                        "Create a new restaurant bill.",
                        createBillButton
                )
        );


        cards.add(
                createCard(
                        "Bill History",
                        "View previously generated bills.",
                        viewBillsButton
                )
        );


        add(
                cards,
                BorderLayout.CENTER
        );


        createBillButton.addActionListener(
                e -> openCreateBill()
        );

        viewBillsButton.addActionListener(
                e -> openViewBills()
        );
    }


    // =========================
    // CARD
    // =========================

    private JPanel createCard(
            String title,
            String description,
            JButton button
    ) {

        JPanel card =
                new JPanel();

        card.setLayout(
                new BoxLayout(
                        card,
                        BoxLayout.Y_AXIS
                )
        );

        card.setBackground(
                UITheme.CARD
        );

        card.setBorder(
                BorderFactory.createCompoundBorder(
                        BorderFactory.createLineBorder(
                                UITheme.BORDER
                        ),
                        BorderFactory.createEmptyBorder(
                                30,
                                30,
                                30,
                                30
                        )
                )
        );


        JLabel titleLabel =
                new JLabel(
                        title
                );

        titleLabel.setAlignmentX(
                Component.CENTER_ALIGNMENT
        );

        titleLabel.setFont(
                new Font(
                        "Arial",
                        Font.BOLD,
                        20
                )
        );


        JLabel descriptionLabel =
                new JLabel(
                        description
                );

        descriptionLabel.setAlignmentX(
                Component.CENTER_ALIGNMENT
        );

        descriptionLabel.setForeground(
                Color.GRAY
        );


        button.setAlignmentX(
                Component.CENTER_ALIGNMENT
        );


        card.add(titleLabel);

        card.add(
                Box.createVerticalStrut(15)
        );

        card.add(descriptionLabel);

        card.add(
                Box.createVerticalStrut(25)
        );

        card.add(button);


        return card;
    }


    // =========================
    // OPEN CREATE BILL
    // =========================

    private void openCreateBill() {

        JFrame frame =
                new JFrame(
                        "Create Bill"
                );

        frame.setDefaultCloseOperation(
                JFrame.DISPOSE_ON_CLOSE
        );

        frame.setSize(
                1000,
                700
        );

        frame.setLocationRelativeTo(
                this
        );

        frame.add(
                new CreateBillPanel()
        );

        frame.setVisible(true);
    }


    // =========================
    // OPEN VIEW BILLS
    // =========================

    private void openViewBills() {

        JFrame frame =
                new JFrame(
                        "View Bills"
                );

        frame.setDefaultCloseOperation(
                JFrame.DISPOSE_ON_CLOSE
        );

        frame.setSize(
                1000,
                650
        );

        frame.setLocationRelativeTo(
                this
        );

        frame.add(
                new ViewBillsPanel()
        );

        frame.setVisible(true);
    }
}
