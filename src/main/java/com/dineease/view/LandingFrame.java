package com.dineease.view;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;

public class LandingFrame extends JFrame {

    private static final Color DARK_BROWN = new Color(42, 26, 18);
    private static final Color DARK_BROWN_ALT = new Color(61, 43, 31);
    private static final Color GOLD = new Color(197, 139, 43);
    private static final Color GOLD_HOVER = new Color(214, 154, 53);
    private static final Color GOLD_SOFT = new Color(212, 160, 58);
    private static final Color CREAM = new Color(250, 243, 232);
    private static final Color INPUT_BG = new Color(255, 253, 248);
    private static final Color INPUT_BORDER = new Color(227, 216, 200);
    private static final Color TEXT_DARK = new Color(43, 27, 18);
    private static final Color TEXT_MUTED = new Color(107, 91, 78);
    private static final Color WHITE = new Color(255, 255, 255);

    public LandingFrame() {
        setTitle("DineEase AI - Landing");
        setSize(1536, 1024);
        setMinimumSize(new Dimension(1200, 760));
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setResizable(true);
        setContentPane(createRootPanel());
        setVisible(true);
    }

    private JPanel createRootPanel() {
        JPanel root = new JPanel(new BorderLayout());
        root.setBackground(new Color(233, 227, 218));

        root.add(createHeader(), BorderLayout.NORTH);

        JPanel middle = new JPanel(new BorderLayout());
        middle.setBackground(new Color(233, 227, 218));
        middle.add(createHeroPanel(), BorderLayout.CENTER);
        middle.add(createFeatureStrip(), BorderLayout.SOUTH);
        root.add(middle, BorderLayout.CENTER);

        root.add(createFooter(), BorderLayout.SOUTH);
        return root;
    }

    private JPanel createHeader() {
        JPanel header = new JPanel(new BorderLayout());
        header.setBackground(DARK_BROWN_ALT);
        header.setBorder(new EmptyBorder(12, 26, 12, 28));

        JPanel left = new JPanel(new FlowLayout(FlowLayout.LEFT, 10, 0));
        left.setOpaque(false);
        left.setBorder(new EmptyBorder(8, 0, 8, 0));

        JLabel chef = new JLabel("◔");
        chef.setForeground(GOLD);
        chef.setFont(new Font("Serif", Font.BOLD, 30));

        JLabel brand = new JLabel("DineEase AI");
        brand.setForeground(GOLD);
        brand.setFont(new Font("Serif", Font.BOLD, 28));

        JLabel divider = new JLabel("|");
        divider.setForeground(new Color(255, 255, 255, 110));
        divider.setFont(new Font("SansSerif", Font.PLAIN, 24));
        divider.setBorder(new EmptyBorder(0, 12, 0, 12));

        JLabel system = new JLabel("Restaurant Management System");
        system.setForeground(new Color(255, 255, 255, 230));
        system.setFont(new Font("SansSerif", Font.PLAIN, 18));

        left.add(chef);
        left.add(brand);
        left.add(divider);
        left.add(system);

        JPanel right = new JPanel(new FlowLayout(FlowLayout.RIGHT, 22, 0));
        right.setOpaque(false);
        right.setBorder(new EmptyBorder(8, 0, 8, 0));

        String[] nav = {"Better Management", "Better Food", "Happier Guests"};
        for (int i = 0; i < nav.length; i++) {
            JLabel item = new JLabel(nav[i]);
            item.setForeground(WHITE);
            item.setFont(new Font("SansSerif", Font.PLAIN, 17));
            if (i > 0) {
                JLabel sep = new JLabel("|");
                sep.setForeground(new Color(255, 255, 255, 100));
                sep.setFont(new Font("SansSerif", Font.PLAIN, 18));
                sep.setBorder(new EmptyBorder(0, 14, 0, 14));
                right.add(sep);
            }
            right.add(item);
        }

        header.add(left, BorderLayout.WEST);
        header.add(right, BorderLayout.EAST);
        return header;
    }

    private JPanel createHeroPanel() {
        HeroBackgroundPanel hero = new HeroBackgroundPanel();
        hero.setLayout(new BorderLayout());
        hero.setBorder(new EmptyBorder(0, 0, 0, 0));

        JPanel content = new JPanel(new BorderLayout());
        content.setOpaque(false);
        content.setBorder(new EmptyBorder(32, 44, 0, 34));

        JPanel left = new JPanel();
        left.setOpaque(false);
        left.setLayout(new BoxLayout(left, BoxLayout.Y_AXIS));

        JLabel line1 = new JLabel("Transform Your");
        line1.setFont(new Font("Serif", Font.BOLD, 62));
        line1.setForeground(WHITE);
        line1.setAlignmentX(Component.LEFT_ALIGNMENT);

        JLabel line2 = new JLabel("Restaurant Business");
        line2.setFont(new Font("Serif", Font.BOLD, 62));
        line2.setForeground(GOLD_SOFT);
        line2.setAlignmentX(Component.LEFT_ALIGNMENT);

        JLabel line3 = new JLabel("with DineEase AI");
        line3.setFont(new Font("Serif", Font.BOLD, 62));
        line3.setForeground(WHITE);
        line3.setAlignmentX(Component.LEFT_ALIGNMENT);

        JLabel subtitle = new JLabel("A complete restaurant management system designed to simplify operations, improve efficiency and help you serve better.");
        subtitle.setFont(new Font("SansSerif", Font.PLAIN, 23));
        subtitle.setForeground(new Color(255, 255, 255, 235));
        subtitle.setAlignmentX(Component.LEFT_ALIGNMENT);
        subtitle.setBorder(new EmptyBorder(16, 0, 18, 0));
        subtitle.setMaximumSize(new Dimension(620, 100));

        JPanel badgeRow = new JPanel(new GridLayout(1, 4, 18, 0));
        badgeRow.setOpaque(false);
        badgeRow.setMaximumSize(new Dimension(640, 120));
        badgeRow.add(createHeroFeature("↗", "Streamline\nOperations"));
        badgeRow.add(createHeroFeature("◔", "Save\nTime"));
        badgeRow.add(createHeroFeature("◎", "Better\nCustomer Experience"));
        badgeRow.add(createHeroFeature("✓", "Grow\nYour Business"));

        left.add(line1);
        left.add(line2);
        left.add(line3);
        left.add(subtitle);
        left.add(badgeRow);

        JPanel right = new JPanel(new FlowLayout(FlowLayout.RIGHT, 0, 10));
        right.setOpaque(false);
        right.add(createLoginCard());

        content.add(left, BorderLayout.WEST);
        content.add(right, BorderLayout.EAST);
        hero.add(content, BorderLayout.CENTER);
        return hero;
    }

    private JPanel createHeroFeature(String icon, String text) {
        JPanel panel = new JPanel();
        panel.setOpaque(false);
        panel.setLayout(new BorderLayout(10, 0));

        JPanel iconWrap = new JPanel(new GridBagLayout());
        iconWrap.setOpaque(false);
        JLabel iconLabel = new JLabel(icon);
        iconLabel.setFont(new Font("SansSerif", Font.BOLD, 26));
        iconLabel.setForeground(GOLD);
        iconWrap.add(iconLabel);

        JLabel label = new JLabel("<html>" + text.replace("\n", "<br>") + "</html>");
        label.setFont(new Font("SansSerif", Font.BOLD, 14));
        label.setForeground(WHITE);
        label.setHorizontalAlignment(SwingConstants.CENTER);

        panel.add(iconWrap, BorderLayout.NORTH);
        panel.add(label, BorderLayout.CENTER);
        return panel;
    }

    private JPanel createLoginCard() {
        JPanel card = new JPanel();
        card.setLayout(new BoxLayout(card, BoxLayout.Y_AXIS));
        card.setPreferredSize(new Dimension(470, 540));
        card.setBackground(new Color(250, 243, 232));
        card.setBorder(new EmptyBorder(28, 30, 24, 30));

        JPanel logoRow = new JPanel(new FlowLayout(FlowLayout.CENTER, 10, 0));
        logoRow.setOpaque(false);
        JLabel chef = new JLabel("◔");
        chef.setFont(new Font("Serif", Font.BOLD, 40));
        chef.setForeground(GOLD);
        logoRow.add(chef);

        JLabel title = new JLabel("DineEase AI");
        title.setFont(new Font("Serif", Font.BOLD, 42));
        title.setForeground(TEXT_DARK);
        logoRow.add(title);

        JLabel subtitle = new JLabel("Restaurant Management System");
        subtitle.setFont(new Font("SansSerif", Font.PLAIN, 12));
        subtitle.setForeground(TEXT_MUTED);
        subtitle.setAlignmentX(Component.CENTER_ALIGNMENT);

        JLabel welcome = new JLabel("Welcome Back!");
        welcome.setFont(new Font("Serif", Font.BOLD, 34));
        welcome.setForeground(TEXT_DARK);
        welcome.setAlignmentX(Component.LEFT_ALIGNMENT);
        welcome.setBorder(new EmptyBorder(22, 0, 8, 0));

        JLabel hint = new JLabel("Sign in to your account to continue");
        hint.setFont(new Font("SansSerif", Font.PLAIN, 17));
        hint.setForeground(TEXT_MUTED);
        hint.setAlignmentX(Component.LEFT_ALIGNMENT);
        hint.setBorder(new EmptyBorder(0, 0, 18, 0));

        JTextField username = new JTextField();
        username.setText("Username");
        username.setForeground(new Color(130, 118, 106));
        username.setFont(new Font("SansSerif", Font.PLAIN, 18));
        username.setBackground(INPUT_BG);
        username.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(INPUT_BORDER, 1),
                new EmptyBorder(12, 14, 12, 14)
        ));

        JPasswordField password = new JPasswordField();
        password.setText("Password");
        password.setForeground(new Color(130, 118, 106));
        password.setFont(new Font("SansSerif", Font.PLAIN, 18));
        password.setBackground(INPUT_BG);
        password.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(INPUT_BORDER, 1),
                new EmptyBorder(12, 14, 12, 14)
        ));

        JPanel rememberRow = new JPanel(new BorderLayout());
        rememberRow.setOpaque(false);
        rememberRow.setBorder(new EmptyBorder(12, 0, 12, 0));

        JCheckBox remember = new JCheckBox("Remember me");
        remember.setOpaque(false);
        remember.setForeground(TEXT_DARK);
        remember.setFont(new Font("SansSerif", Font.PLAIN, 14));

        JLabel forgot = new JLabel("Forgot password?");
        forgot.setForeground(GOLD);
        forgot.setFont(new Font("SansSerif", Font.BOLD, 14));
        forgot.setHorizontalAlignment(SwingConstants.RIGHT);

        rememberRow.add(remember, BorderLayout.WEST);
        rememberRow.add(forgot, BorderLayout.EAST);

        JButton login = new JButton("Login");
        login.setBackground(GOLD);
        login.setForeground(WHITE);
        login.setFont(new Font("SansSerif", Font.BOLD, 20));
        login.setFocusPainted(false);
        login.setBorder(new EmptyBorder(12, 16, 12, 16));
        login.setCursor(new Cursor(Cursor.HAND_CURSOR));
        login.addActionListener(e -> JOptionPane.showMessageDialog(this, "Login ready for backend integration.", "DineEase AI", JOptionPane.INFORMATION_MESSAGE));

        JPanel securePanel = new JPanel(new BorderLayout());
        securePanel.setOpaque(false);
        securePanel.setBorder(new EmptyBorder(16, 0, 0, 0));

        JLabel leftLine = new JLabel();
        leftLine.setBorder(BorderFactory.createMatteBorder(0, 0, 1, 0, new Color(120, 102, 89, 120)));
        JLabel rightLine = new JLabel();
        rightLine.setBorder(BorderFactory.createMatteBorder(0, 0, 1, 0, new Color(120, 102, 89, 120)));

        JLabel secureLabel = new JLabel("Secure Login");
        secureLabel.setForeground(TEXT_DARK);
        secureLabel.setFont(new Font("SansSerif", Font.PLAIN, 13));
        secureLabel.setHorizontalAlignment(SwingConstants.CENTER);

        securePanel.add(leftLine, BorderLayout.WEST);
        securePanel.add(secureLabel, BorderLayout.CENTER);
        securePanel.add(rightLine, BorderLayout.EAST);

        card.add(logoRow);
        card.add(subtitle);
        card.add(welcome);
        card.add(hint);
        card.add(username);
        card.add(Box.createVerticalStrut(12));
        card.add(password);
        card.add(rememberRow);
        card.add(login);
        card.add(securePanel);

        return card;
    }

    private JPanel createFeatureStrip() {
        JPanel strip = new JPanel(new GridLayout(1, 5, 0, 0));
        strip.setBackground(CREAM);
        strip.setBorder(new EmptyBorder(0, 24, 0, 24));

        strip.add(createFeatureItem("Table Management", "Efficiently manage table bookings and seating.", "◪"));
        strip.add(createFeatureItem("Order Management", "Track and manage orders in real-time.", "▣"));
        strip.add(createFeatureItem("Inventory Control", "Keep track of stock levels and avoid shortages.", "▤"));
        strip.add(createFeatureItem("Billing & Payments", "Fast and secure billing with multiple payment options.", "▥"));
        strip.add(createFeatureItem("Reports & Analytics", "Get insights to make better decisions.", "▨"));

        return strip;
    }

    private JPanel createFeatureItem(String title, String description, String icon) {
        JPanel panel = new JPanel(new BorderLayout(14, 0));
        panel.setOpaque(false);
        panel.setBorder(new EmptyBorder(18, 12, 18, 12));

        JPanel iconWrap = new JPanel(new GridBagLayout());
        iconWrap.setOpaque(false);
        iconWrap.setPreferredSize(new Dimension(54, 54));

        JLabel iconLabel = new JLabel(icon);
        iconLabel.setFont(new Font("SansSerif", Font.PLAIN, 22));
        iconLabel.setForeground(TEXT_DARK);
        iconWrap.add(iconLabel);

        JPanel textPanel = new JPanel();
        textPanel.setOpaque(false);
        textPanel.setLayout(new BoxLayout(textPanel, BoxLayout.Y_AXIS));

        JLabel titleLabel = new JLabel(title);
        titleLabel.setFont(new Font("SansSerif", Font.BOLD, 15));
        titleLabel.setForeground(TEXT_DARK);

        JLabel desc = new JLabel("<html>" + description + "</html>");
        desc.setFont(new Font("SansSerif", Font.PLAIN, 12));
        desc.setForeground(TEXT_MUTED);
        desc.setMaximumSize(new Dimension(200, 40));

        textPanel.add(titleLabel);
        textPanel.add(Box.createVerticalStrut(4));
        textPanel.add(desc);

        panel.add(iconWrap, BorderLayout.WEST);
        panel.add(textPanel, BorderLayout.CENTER);
        return panel;
    }

    private JPanel createFooter() {
        JPanel footer = new JPanel(new BorderLayout());
        footer.setBackground(DARK_BROWN_ALT);
        footer.setBorder(new EmptyBorder(16, 28, 16, 28));

        JPanel left = new JPanel(new FlowLayout(FlowLayout.LEFT, 10, 0));
        left.setOpaque(false);

        JLabel chef = new JLabel("◔");
        chef.setForeground(GOLD);
        chef.setFont(new Font("Serif", Font.BOLD, 24));
        left.add(chef);

        JLabel brand = new JLabel("DineEase AI");
        brand.setForeground(GOLD);
        brand.setFont(new Font("Serif", Font.BOLD, 22));
        left.add(brand);

        JLabel dot = new JLabel("•");
        dot.setForeground(GOLD);
        dot.setFont(new Font("SansSerif", Font.BOLD, 18));
        left.add(dot);

        JLabel subtitle = new JLabel("Restaurant Management System");
        subtitle.setForeground(new Color(255, 255, 255, 220));
        subtitle.setFont(new Font("SansSerif", Font.PLAIN, 15));
        left.add(subtitle);

        JLabel version = new JLabel("v1.0");
        version.setForeground(new Color(255, 255, 255, 180));
        version.setFont(new Font("SansSerif", Font.PLAIN, 15));

        footer.add(left, BorderLayout.WEST);
        footer.add(version, BorderLayout.EAST);
        return footer;
    }

    private static class HeroBackgroundPanel extends JPanel {
        @Override
        protected void paintComponent(Graphics g) {
            super.paintComponent(g);
            Graphics2D g2 = (Graphics2D) g.create();
            g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

            GradientPaint bg = new GradientPaint(0, 0, new Color(23, 17, 12), getWidth(), getHeight(), new Color(54, 38, 28));
            g2.setPaint(bg);
            g2.fillRect(0, 0, getWidth(), getHeight());

            g2.setColor(new Color(77, 56, 37, 120));
            g2.fillRoundRect(0, 0, getWidth(), 150, 0, 0);

            for (int i = 0; i < 7; i++) {
                int x = 100 + i * 160;
                g2.setColor(new Color(255, 214, 130, 120));
                g2.fillOval(x, 26, 17, 17);
            }

            g2.setColor(new Color(89, 62, 42, 160));
            g2.fillRoundRect(45, 168, getWidth() - 110, 160, 18, 18);
            g2.fillRoundRect(110, 212, getWidth() - 190, 130, 18, 18);

            g2.setColor(new Color(106, 71, 45));
            g2.fillRoundRect(120, 440, 760, 190, 32, 32);

            int[] chairsX = {130, 250, 420, 590, 710, 830};
            for (int x : chairsX) {
                g2.setColor(new Color(123, 84, 60));
                g2.fillRoundRect(x, 470, 80, 150, 18, 18);
            }

            g2.setColor(new Color(246, 243, 240));
            for (int i = 0; i < 9; i++) {
                g2.fillOval(200 + i * 78, 430, 32, 24);
            }

            g2.setColor(new Color(211, 181, 123, 180));
            g2.fillRoundRect(330, 430, 280, 32, 22, 22);
            g2.fillRoundRect(350, 445, 130, 12, 8, 8);

            g2.dispose();
        }
    }
}
