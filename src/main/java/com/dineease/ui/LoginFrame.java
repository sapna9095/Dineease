package com.dineease.ui;

import com.dineease.model.User;
import com.dineease.service.AuthService;
import com.dineease.service.MockAuthService;
import com.dineease.service.UserSession;
import com.dineease.util.FontUtil;
import com.dineease.util.ImageUtil;
import com.dineease.util.Theme;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.awt.event.*;
import java.awt.image.BufferedImage;

public class LoginFrame extends JFrame {

    private final AuthService authService = new com.dineease.service.DBAuthService();
    private final RoundedTextField usernameField = new RoundedTextField("Username");
    private final RoundedPasswordField passwordField = new RoundedPasswordField("Password");
    private final JCheckBox rememberCheck = new JCheckBox("Remember me");
    private final JLabel statusLabel = new JLabel();
    private JButton primaryLoginButton;

    private static final Color HEADER_BG = new Color(42, 26, 18);
    private static final Color FOREGROUND_CREAM = new Color(250, 243, 232);
    private static final Color GOLD = new Color(197, 139, 43);
    private static final Color GOLD_SOFT = new Color(212, 160, 58);
    private static final Color DARK_BROWN = new Color(43, 27, 18);
    private static final Color MUTED = new Color(107, 91, 78);
    private static final Color INPUT_BG = new Color(255, 253, 248);
    private static final Color INPUT_BORDER = new Color(227, 216, 200);

    public LoginFrame() {
        super("DineEase AI");
        Theme.apply();
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setContentPane(createRootPanel());

        GraphicsEnvironment ge = GraphicsEnvironment.getLocalGraphicsEnvironment();
        Rectangle maxBounds = ge.getMaximumWindowBounds();
        int width = Math.min(1536, Math.max(1280, maxBounds.width));
        int height = Math.min(1024, Math.max(800, maxBounds.height));

        setMinimumSize(new Dimension(1280, 800));
        setPreferredSize(new Dimension(width, height));
        setSize(width, height);
        setLocationRelativeTo(null);
        setVisible(true);

        SwingUtilities.invokeLater(() -> {
            if (getRootPane() != null) {
                getRootPane().requestFocusInWindow();
            }
        });
    }

    private JPanel createRootPanel() {
        JPanel root = new JPanel(new BorderLayout());
        root.setBackground(new Color(214, 206, 196));
        root.add(createTopBar(), BorderLayout.NORTH);
        root.add(createHeroSection(), BorderLayout.CENTER);
        root.add(createBottomPanel(), BorderLayout.SOUTH);
        return root;
    }

    private JPanel createTopBar() {
        JPanel topBar = new JPanel(new GridBagLayout());
        topBar.setBackground(HEADER_BG);
        topBar.setBorder(new EmptyBorder(0, 22, 0, 55));
        topBar.setPreferredSize(new Dimension(0, 80));
        topBar.setMinimumSize(new Dimension(0, 80));
        topBar.setMaximumSize(new Dimension(Integer.MAX_VALUE, 80));

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.gridy = 0;
        gbc.weighty = 1;
        gbc.anchor = GridBagConstraints.CENTER;
        gbc.fill = GridBagConstraints.NONE;

        JPanel leftGroup = new JPanel(new FlowLayout(FlowLayout.LEFT, 14, 0));
        leftGroup.setOpaque(false);
        JLabel chefIcon = new JLabel(IconFactory.chefHat(36, GOLD));
        JLabel brand = new JLabel("DineEase AI");
        brand.setFont(FontUtil.getPlayfairDisplay(32f, Font.BOLD));
        brand.setForeground(GOLD);

        JSeparator divider = new JSeparator(SwingConstants.VERTICAL);
        divider.setPreferredSize(new Dimension(1, 28));
        divider.setForeground(new Color(255, 255, 255, 30));

        JLabel subtitle = new JLabel("Restaurant Management System");
        subtitle.setFont(FontUtil.getNunito(16f, Font.PLAIN));
        subtitle.setForeground(new Color(255, 255, 255, 220));

        leftGroup.add(chefIcon);
        leftGroup.add(brand);
        leftGroup.add(divider);
        leftGroup.add(subtitle);

        JPanel rightGroup = new JPanel(new FlowLayout(FlowLayout.RIGHT, 18, 0));
        rightGroup.setOpaque(false);
        String[] items = {"Better Management", "Better Food", "Happier Guests"};
        for (int i = 0; i < items.length; i++) {
            JLabel label = new JLabel(items[i]);
            label.setFont(FontUtil.getNunito(15f, Font.BOLD));
            label.setForeground(new Color(255, 255, 255, 220));
            rightGroup.add(label);
            if (i < items.length - 1) {
                JSeparator sep = new JSeparator(SwingConstants.VERTICAL);
                sep.setPreferredSize(new Dimension(1, 18));
                sep.setForeground(new Color(255, 255, 255, 70));
                rightGroup.add(sep);
            }
        }

        gbc.gridx = 0;
        gbc.weightx = 1;
        gbc.anchor = GridBagConstraints.WEST;
        topBar.add(leftGroup, gbc);

        gbc.gridx = 1;
        gbc.weightx = 0;
        gbc.anchor = GridBagConstraints.EAST;
        topBar.add(rightGroup, gbc);
        return topBar;
    }

    private JPanel createBottomPanel() {
        JPanel bottomPanel = new JPanel(new BorderLayout());
        bottomPanel.setBackground(new Color(214, 206, 196));
        bottomPanel.setPreferredSize(new Dimension(0, 240));
        bottomPanel.setMinimumSize(new Dimension(0, 240));
        bottomPanel.setMaximumSize(new Dimension(Integer.MAX_VALUE, 240));
        bottomPanel.add(createFeaturesStrip(), BorderLayout.NORTH);
        bottomPanel.add(createFooterPanel(), BorderLayout.SOUTH);
        return bottomPanel;
    }

    private JPanel createFeaturesStrip() {
        JPanel strip = new JPanel(new GridLayout(1, 5, 0, 0));
        strip.setBackground(new Color(250, 243, 232));
        strip.setPreferredSize(new Dimension(0, 165));
        strip.setMinimumSize(new Dimension(0, 165));
        strip.setMaximumSize(new Dimension(Integer.MAX_VALUE, 165));

        String[] titles = {"Table Management", "Order Management", "Inventory Control", "Billing & Payments", "Reports & Analytics"};
        String[] descriptions = {
                "Efficiently manage table bookings and seating.",
                "Track and manage orders in real-time.",
                "Keep track of stock levels and avoid shortages.",
                "Fast and secure billing with multiple payment options.",
                "Get insights to make better decisions."
        };
        Icon[] icons = {
                IconFactory.utensils(30, DARK_BROWN),
                IconFactory.clipboard(30, DARK_BROWN),
                IconFactory.cube(30, DARK_BROWN),
                IconFactory.clipboardList(30, DARK_BROWN),
                IconFactory.barChart(30, DARK_BROWN)
        };

        for (int i = 0; i < titles.length; i++) {
            JPanel item = new JPanel(new GridBagLayout());
            item.setOpaque(false);
            item.setBorder(BorderFactory.createCompoundBorder(
                    BorderFactory.createMatteBorder(0, 0, 0, i < titles.length - 1 ? 1 : 0, new Color(227, 216, 200)),
                    new EmptyBorder(0, 24, 0, 16)
            ));
            item.setPreferredSize(new Dimension(0, 165));

            GridBagConstraints c = new GridBagConstraints();
            c.gridy = 0;
            c.anchor = GridBagConstraints.CENTER;
            c.fill = GridBagConstraints.NONE;
            c.insets = new Insets(0, 0, 0, 16);

            FeatureIconCircle iconCircle = new FeatureIconCircle(icons[i]);
            c.gridx = 0;
            item.add(iconCircle, c);

            JPanel textPanel = new JPanel();
            textPanel.setOpaque(false);
            textPanel.setLayout(new BoxLayout(textPanel, BoxLayout.Y_AXIS));
            textPanel.setPreferredSize(new Dimension(190, 80));

            JLabel titleLabel = new JLabel(titles[i]);
            titleLabel.setFont(FontUtil.getNunito(15f, Font.BOLD));
            titleLabel.setForeground(DARK_BROWN);
            titleLabel.setAlignmentX(Component.LEFT_ALIGNMENT);

            JLabel descLabel = new JLabel("<html><div style='width:190px'>" + descriptions[i] + "</div></html>");
            descLabel.setFont(FontUtil.getNunito(12f, Font.PLAIN));
            descLabel.setForeground(new Color(107, 91, 78));
            descLabel.setAlignmentX(Component.LEFT_ALIGNMENT);

            textPanel.add(titleLabel);
            textPanel.add(Box.createVerticalStrut(3));
            textPanel.add(descLabel);

            c.gridx = 1;
            c.insets = new Insets(0, 0, 0, 0);
            item.add(textPanel, c);
            strip.add(item);
        }

        return strip;
    }

    private JPanel createFooterPanel() {
        JPanel footer = new JPanel(new BorderLayout());
        footer.setBackground(HEADER_BG);
        footer.setPreferredSize(new Dimension(0, 75));
        footer.setMinimumSize(new Dimension(0, 75));
        footer.setMaximumSize(new Dimension(Integer.MAX_VALUE, 75));
        footer.setBorder(new EmptyBorder(0, 20, 0, 20));

        JPanel leftWrap = new JPanel(new FlowLayout(FlowLayout.LEFT, 0, 0));
        leftWrap.setOpaque(false);
        leftWrap.add(new JLabel(IconFactory.chefHat(30, GOLD)));

        JPanel centerWrap = new JPanel(new FlowLayout(FlowLayout.CENTER, 8, 0));
        centerWrap.setOpaque(false);
        JLabel brand = new JLabel("DineEase AI");
        brand.setFont(FontUtil.getNunito(15f, Font.BOLD));
        brand.setForeground(Color.WHITE);
        JLabel bullet = new JLabel("•");
        bullet.setFont(FontUtil.getNunito(15f, Font.BOLD));
        bullet.setForeground(GOLD);
        JLabel subtitle = new JLabel("Restaurant Management System");
        subtitle.setFont(FontUtil.getNunito(15f, Font.PLAIN));
        subtitle.setForeground(Color.WHITE);
        centerWrap.add(brand);
        centerWrap.add(bullet);
        centerWrap.add(subtitle);

        JPanel rightWrap = new JPanel(new FlowLayout(FlowLayout.RIGHT, 0, 0));
        rightWrap.setOpaque(false);
        JLabel version = new JLabel("v1.0");
        version.setFont(FontUtil.getNunito(15f, Font.BOLD));
        version.setForeground(new Color(255, 255, 255, 180));
        rightWrap.add(version);

        footer.add(leftWrap, BorderLayout.WEST);
        footer.add(centerWrap, BorderLayout.CENTER);
        footer.add(rightWrap, BorderLayout.EAST);
        return footer;
    }

    private JComponent createHeroSection() {
        HeroBackgroundPanel heroPanel = new HeroBackgroundPanel();
        heroPanel.setLayout(new BorderLayout());
        heroPanel.setPreferredSize(new Dimension(1536, 760));
        heroPanel.setBorder(new EmptyBorder(0, 0, 0, 0));

        JPanel left = createHeroLeftPanel();
        JPanel right = createHeroRightPanel();
        JPanel inner = new JPanel(new BorderLayout());
        inner.setOpaque(false);
        inner.setBorder(new EmptyBorder(40, 0, 24, 0));
        inner.add(left, BorderLayout.WEST);
        inner.add(right, BorderLayout.EAST);

        heroPanel.addComponentListener(new ComponentAdapter() {
            @Override
            public void componentResized(ComponentEvent e) {
                boolean compact = heroPanel.getHeight() < 690;
                inner.setBorder(new EmptyBorder(compact ? 40 : 40, 0, compact ? 24 : 24, 0));
                left.setBorder(new EmptyBorder(compact ? 8 : 0, 34, 0, 0));
                if (compact) {
                    left.setPreferredSize(new Dimension(760, 500));
                } else {
                    left.setPreferredSize(new Dimension(760, 600));
                }
            }
        });

        heroPanel.add(inner, BorderLayout.CENTER);

        JScrollPane scrollPane = new JScrollPane(heroPanel);
        scrollPane.setBorder(null);
        scrollPane.setOpaque(false);
        scrollPane.getViewport().setOpaque(false);
        scrollPane.getViewport().setBorder(null);
        scrollPane.setHorizontalScrollBarPolicy(ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);
        scrollPane.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_AS_NEEDED);
        return scrollPane;
    }

    private JPanel createHeroLeftPanel() {
        JPanel left = new JPanel();
        left.setOpaque(false);
        left.setLayout(new BoxLayout(left, BoxLayout.Y_AXIS));
        left.setBorder(new EmptyBorder(0, 34, 0, 0));
        left.setPreferredSize(new Dimension(760, 600));

        JLabel title = new JLabel("<html><span style='letter-spacing:0.2px;'>Transform Your<br><span style='color:#d4a03a;'>Restaurant Business</span><br>with DineEase AI</span></html>");
        title.setFont(FontUtil.getPlayfairDisplay(52f, Font.BOLD));
        title.setForeground(Color.WHITE);
        title.setAlignmentX(Component.LEFT_ALIGNMENT);
        title.setMaximumSize(new Dimension(700, 240));

        JLabel description = new JLabel("<html><body style='width:540px; margin:0; line-height:1.4;'>A complete restaurant management system designed to simplify operations, improve efficiency and help you serve better.</body></html>");
        description.setFont(FontUtil.getNunito(22f, Font.PLAIN));
        description.setForeground(new Color(255, 255, 255, 230));
        description.setAlignmentX(Component.LEFT_ALIGNMENT);
        description.setBorder(new EmptyBorder(8, 0, 0, 0));

        JPanel featureRow = new JPanel(new GridLayout(1, 4, 0, 0));
        featureRow.setOpaque(false);
        featureRow.setAlignmentX(Component.LEFT_ALIGNMENT);
        featureRow.setPreferredSize(new Dimension(540, 110));
        featureRow.setMaximumSize(new Dimension(540, 110));
        featureRow.setBorder(new EmptyBorder(18, 0, 12, 0));

        String[] names = {"Streamline\nOperations", "Save\nTime", "Better\nCustomer Experience", "Grow\nYour Business"};
        Icon[] icons = {
                IconFactory.trendingUp(40, GOLD_SOFT),
                IconFactory.clock(40, GOLD_SOFT),
                IconFactory.users(40, GOLD_SOFT),
                IconFactory.shieldCheck(40, GOLD_SOFT)
        };

        for (int i = 0; i < names.length; i++) {
            JPanel cell = new JPanel();
            cell.setOpaque(false);
            cell.setLayout(new BoxLayout(cell, BoxLayout.Y_AXIS));
            cell.setBorder(BorderFactory.createMatteBorder(0, 0, 0, i < names.length - 1 ? 1 : 0, new Color(255, 255, 255, 30)));
            cell.setPreferredSize(new Dimension(135, 90));
            cell.setMaximumSize(new Dimension(135, 90));
            cell.setAlignmentX(Component.CENTER_ALIGNMENT);

            JLabel iconLabel = new JLabel(icons[i]);
            iconLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
            JLabel text = new JLabel("<html><center>" + names[i].replace("\n", "<br>") + "</center></html>");
            text.setForeground(Color.WHITE);
            text.setFont(FontUtil.getNunito(16f, Font.BOLD));
            text.setHorizontalAlignment(SwingConstants.CENTER);
            text.setAlignmentX(Component.CENTER_ALIGNMENT);

            cell.add(Box.createVerticalStrut(8));
            cell.add(iconLabel);
            cell.add(Box.createVerticalStrut(8));
            cell.add(text);
            featureRow.add(cell);
        }

        left.add(title);
        left.add(Box.createVerticalStrut(10));
        left.add(description);
        left.add(featureRow);
        return left;
    }

    private JPanel createHeroRightPanel() {
        JPanel right = new JPanel(new GridBagLayout());
        right.setOpaque(false);
        right.setBorder(new EmptyBorder(0, 0, 0, 18));
        right.setPreferredSize(new Dimension(560, 600));
        right.add(createLoginCard());
        return right;
    }

    private JPanel createLoginCard() {
        JPanel card = new JPanel(new GridBagLayout());
        card.setOpaque(true);
        card.setBackground(new Color(250, 243, 232, 245));
        card.setPreferredSize(new Dimension(480, 610));
        card.setMinimumSize(new Dimension(480, 610));
        card.setMaximumSize(new Dimension(480, 610));
        card.setBorder(new EmptyBorder(40, 40, 24, 40));

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.gridx = 0;
        gbc.weightx = 1;
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.anchor = GridBagConstraints.WEST;
        gbc.insets = new Insets(0, 0, 10, 0);

        JLabel logo = new JLabel();
        logo.setHorizontalAlignment(SwingConstants.CENTER);
        logo.setIcon(IconFactory.chefHat(44, GOLD));
        logo.setText("<html><span style='color:#C58B2B'>DineEase</span> <span style='color:#2B1B12'>AI</span></html>");
        logo.setFont(FontUtil.getPlayfairDisplay(32f, Font.BOLD));
        logo.setHorizontalTextPosition(SwingConstants.RIGHT);
        logo.setIconTextGap(8);
        logo.setOpaque(false);
        logo.setPreferredSize(new Dimension(400, 42));
        gbc.gridy = 0;
        card.add(logo, gbc);

        JLabel caption = new JLabel("Restaurant Management System");
        caption.setHorizontalAlignment(SwingConstants.CENTER);
        caption.setFont(FontUtil.getNunito(13f, Font.PLAIN));
        caption.setForeground(new Color(107, 91, 78));
        caption.setPreferredSize(new Dimension(400, 18));
        gbc.gridy = 1;
        card.add(caption, gbc);

        JLabel welcome = new JLabel("Welcome Back!");
        welcome.setHorizontalAlignment(SwingConstants.LEFT);
        welcome.setFont(FontUtil.getPlayfairDisplay(34f, Font.BOLD));
        welcome.setForeground(DARK_BROWN);
        welcome.setPreferredSize(new Dimension(400, 44));
        gbc.gridy = 2;
        gbc.insets = new Insets(10, 0, 0, 0);
        card.add(welcome, gbc);

        JLabel helper = new JLabel("Sign in to your account to continue");
        helper.setHorizontalAlignment(SwingConstants.LEFT);
        helper.setFont(FontUtil.getNunito(15.5f, Font.PLAIN));
        helper.setForeground(MUTED);
        helper.setPreferredSize(new Dimension(400, 24));
        gbc.gridy = 3;
        gbc.insets = new Insets(0, 0, 18, 0);
        card.add(helper, gbc);

        gbc.gridy = 4;
        gbc.insets = new Insets(0, 0, 10, 0);
        card.add(createInputRow(IconFactory.user(22, DARK_BROWN), usernameField, "Username"), gbc);

        gbc.gridy = 5;
        gbc.insets = new Insets(0, 0, 10, 0);
        card.add(createPasswordRow(), gbc);

        JPanel rememberRow = new JPanel(new BorderLayout());
        rememberRow.setOpaque(false);
        rememberRow.setPreferredSize(new Dimension(400, 28));
        rememberRow.setMinimumSize(new Dimension(400, 28));
        rememberRow.setMaximumSize(new Dimension(400, 28));
        rememberRow.setBorder(new EmptyBorder(2, 0, 0, 0));

        rememberCheck.setOpaque(false);
        rememberCheck.setForeground(DARK_BROWN);
        rememberCheck.setFont(FontUtil.getNunito(13.5f, Font.PLAIN));
        rememberCheck.setFocusPainted(false);
        rememberCheck.setBorder(BorderFactory.createEmptyBorder());

        JLabel forgot = new JLabel("Forgot password?");
        forgot.setFont(FontUtil.getNunito(13.5f, Font.BOLD));
        forgot.setForeground(GOLD);
        forgot.setHorizontalAlignment(SwingConstants.RIGHT);
        forgot.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
        forgot.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                JOptionPane.showMessageDialog(LoginFrame.this,
                        "Password reset instructions have been sent to your registered email.",
                        "DineEase AI",
                        JOptionPane.INFORMATION_MESSAGE);
            }
        });

        rememberRow.add(rememberCheck, BorderLayout.WEST);
        rememberRow.add(forgot, BorderLayout.EAST);
        gbc.gridy = 6;
        gbc.insets = new Insets(0, 0, 12, 0);
        card.add(rememberRow, gbc);

        primaryLoginButton = new JButton("Login");
        primaryLoginButton.setFont(FontUtil.getNunito(17f, Font.BOLD));
        primaryLoginButton.setBackground(GOLD);
        primaryLoginButton.setForeground(Color.WHITE);
        primaryLoginButton.setFocusPainted(false);
        primaryLoginButton.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
        primaryLoginButton.setBorder(BorderFactory.createEmptyBorder(12, 16, 12, 16));
        primaryLoginButton.setPreferredSize(new Dimension(400, 54));
        primaryLoginButton.setMinimumSize(new Dimension(400, 54));
        primaryLoginButton.setMaximumSize(new Dimension(400, 54));
        primaryLoginButton.setMargin(new Insets(0, 0, 0, 0));
        primaryLoginButton.setHorizontalTextPosition(SwingConstants.RIGHT);
        primaryLoginButton.setIcon(IconFactory.arrowRight(18, Color.WHITE));
        primaryLoginButton.setIconTextGap(8);
        primaryLoginButton.addActionListener(this::handleLoginAction);
        gbc.gridy = 7;
        gbc.insets = new Insets(0, 0, 12, 0);
        card.add(primaryLoginButton, gbc);

        JPanel secureRow = createSecureLoginRow();
        secureRow.setPreferredSize(new Dimension(400, 28));
        secureRow.setMinimumSize(new Dimension(400, 28));
        secureRow.setMaximumSize(new Dimension(400, 28));
        gbc.gridy = 8;
        gbc.insets = new Insets(0, 0, 0, 0);
        card.add(secureRow, gbc);

        statusLabel.setHorizontalAlignment(SwingConstants.CENTER);
        statusLabel.setPreferredSize(new Dimension(400, 18));
        gbc.gridy = 9;
        gbc.insets = new Insets(8, 0, 0, 0);
        card.add(statusLabel, gbc);

        return card;
    }

    private JPanel createInputRow(Icon icon, JComponent field, String placeholder) {
        RoundedInputPanel row = new RoundedInputPanel(new BorderLayout(12, 0));
        row.setOpaque(false);
        row.setBackground(new Color(255, 253, 248));
        row.setPreferredSize(new Dimension(400, 56));
        row.setMinimumSize(new Dimension(400, 56));
        row.setMaximumSize(new Dimension(400, 56));

        JLabel iconLabel = new JLabel(icon);
        iconLabel.setBorder(new EmptyBorder(0, 16, 0, 0));
        row.add(iconLabel, BorderLayout.WEST);

        if (field instanceof RoundedTextField) {
            ((RoundedTextField) field).setPlaceholder(placeholder);
            ((RoundedTextField) field).setOpaque(false);
            ((RoundedTextField) field).setBorder(BorderFactory.createEmptyBorder());
            ((RoundedTextField) field).setBackground(new Color(0, 0, 0, 0));
            ((RoundedTextField) field).setForeground(DARK_BROWN);
            ((RoundedTextField) field).setFont(FontUtil.getNunito(17f, Font.PLAIN));
            ((RoundedTextField) field).setMargin(new Insets(0, 0, 0, 0));
            ((RoundedTextField) field).putClientProperty("JTextField.placeholderText", "Username");
        }

        row.add(field, BorderLayout.CENTER);
        return row;
    }

    private JPanel createPasswordRow() {
        RoundedInputPanel row = new RoundedInputPanel(new BorderLayout(8, 0));
        row.setOpaque(false);
        row.setBackground(new Color(255, 253, 248));
        row.setPreferredSize(new Dimension(400, 56));
        row.setMinimumSize(new Dimension(400, 56));
        row.setMaximumSize(new Dimension(400, 56));

        JLabel iconLabel = new JLabel(IconFactory.lock(22, DARK_BROWN));
        iconLabel.setBorder(new EmptyBorder(0, 16, 0, 0));
        row.add(iconLabel, BorderLayout.WEST);

        passwordField.setPlaceholder("Password");
        passwordField.setOpaque(false);
        passwordField.setBorder(BorderFactory.createEmptyBorder());
        passwordField.setBackground(new Color(0, 0, 0, 0));
        passwordField.setForeground(DARK_BROWN);
        passwordField.setMargin(new Insets(0, 0, 0, 0));
        passwordField.setFont(FontUtil.getNunito(17f, Font.PLAIN));
        passwordField.putClientProperty("JTextField.placeholderText", "Password");
        row.add(passwordField, BorderLayout.CENTER);

        JButton eyeButton = new JButton(passwordField.isVisiblePassword() ? IconFactory.eye(18, DARK_BROWN) : IconFactory.eyeOff(18, DARK_BROWN));
        eyeButton.setFocusPainted(false);
        eyeButton.setBorder(BorderFactory.createEmptyBorder(0, 0, 0, 12));
        eyeButton.setContentAreaFilled(false);
        eyeButton.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
        eyeButton.addActionListener(e -> {
            passwordField.toggle();
            eyeButton.setIcon(passwordField.isVisiblePassword() ? IconFactory.eye(18, DARK_BROWN) : IconFactory.eyeOff(18, DARK_BROWN));
        });
        row.add(eyeButton, BorderLayout.EAST);
        return row;
    }

    private JPanel createSecureLoginRow() {
        JPanel row = new JPanel(new GridBagLayout());
        row.setOpaque(false);
        row.setPreferredSize(new Dimension(400, 28));
        row.setMaximumSize(new Dimension(400, 28));

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.gridy = 0;
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.anchor = GridBagConstraints.CENTER;

        JPanel leftLine = new JPanel();
        leftLine.setBackground(new Color(123, 104, 88));
        leftLine.setPreferredSize(new Dimension(80, 1));

        JPanel rightLine = new JPanel();
        rightLine.setBackground(new Color(123, 104, 88));
        rightLine.setPreferredSize(new Dimension(80, 1));

        JLabel secure = new JLabel("Secure Login");
        secure.setIcon(IconFactory.shieldCheck(14, GOLD));
        secure.setFont(FontUtil.getNunito(12.5f, Font.BOLD));
        secure.setForeground(DARK_BROWN);
        secure.setIconTextGap(6);

        gbc.gridx = 0;
        gbc.weightx = 1;
        row.add(leftLine, gbc);

        gbc.gridx = 1;
        gbc.weightx = 0;
        row.add(secure, gbc);

        gbc.gridx = 2;
        gbc.weightx = 1;
        row.add(rightLine, gbc);

        return row;
    }

    private static class RoundedInputPanel extends JPanel {
        RoundedInputPanel(LayoutManager layout) {
            super(layout);
        }

        @Override
        protected void paintComponent(Graphics g) {
            super.paintComponent(g);
            Graphics2D g2 = (Graphics2D) g.create();
            g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
            g2.setColor(new Color(255, 253, 248));
            g2.fillRoundRect(0, 0, getWidth(), getHeight(), 16, 16);
            g2.setColor(new Color(227, 216, 200));
            g2.drawRoundRect(0, 0, getWidth() - 1, getHeight() - 1, 16, 16);
            g2.dispose();
        }
    }

    private static class FeatureIconCircle extends JPanel {
        FeatureIconCircle(Icon icon) {
            setOpaque(false);
            setPreferredSize(new Dimension(60, 60));
            setMinimumSize(new Dimension(60, 60));
            setMaximumSize(new Dimension(60, 60));
            setLayout(new GridBagLayout());
            JLabel label = new JLabel(icon);
            add(label);
        }

        @Override
        protected void paintComponent(Graphics g) {
            super.paintComponent(g);
            Graphics2D g2 = (Graphics2D) g.create();
            g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
            g2.setColor(new Color(241, 224, 190));
            g2.fillOval(0, 0, getWidth(), getHeight());
            g2.dispose();
        }
    }

    private void handleLoginAction(ActionEvent e) {
        String username = usernameField.getText() == null ? "" : usernameField.getText().trim();
        String password = new String(passwordField.getPassword());

        statusLabel.setText("");

        try {
            UserSession session = authService.login(username, password);
            statusLabel.setForeground(new Color(34, 124, 89));
            statusLabel.setText("Welcome, " + session.getUsername() + "! Redirecting...");
            loginButtonStatus(false, "Logging in...");

            SwingWorker<Void, Void> worker = new SwingWorker<>() {
                @Override
                protected Void doInBackground() {
                    try {
                        Thread.sleep(700);
                    } catch (InterruptedException ignored) {
                        Thread.currentThread().interrupt();
                    }
                    return null;
                }

                @Override
                protected void done() {
                    loginButtonStatus(true, "Login");
                    openDashboard(session);
                }
            };
            worker.execute();
        } catch (IllegalArgumentException ex) {
            if (username.isEmpty()) {
                statusLabel.setForeground(new Color(215, 75, 69));
                statusLabel.setText("Username is required.");
            } else if (password.isEmpty()) {
                statusLabel.setForeground(new Color(215, 75, 69));
                statusLabel.setText("Password is required.");
            } else {
                statusLabel.setForeground(new Color(215, 75, 69));
                statusLabel.setText(ex.getMessage());
            }
        }
    }

    private void loginButtonStatus(boolean enabled, String text) {
        if (primaryLoginButton != null) {
            primaryLoginButton.setEnabled(enabled);
            primaryLoginButton.setText(text);
        }
    }

    private void openDashboard(UserSession session) {
        SwingUtilities.invokeLater(() -> {
            dispose();
            if (session != null) {
                new com.dineease.ui.MainFrame(session);
            }
        });
    }

    private static class HeroBackgroundPanel extends JPanel {
        private final BufferedImage background = ImageUtil.loadBufferedImage("/images/hero.jpg");

        @Override
        protected void paintComponent(Graphics g) {
            super.paintComponent(g);
            Graphics2D g2 = (Graphics2D) g.create();
            g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
            g2.setRenderingHint(RenderingHints.KEY_INTERPOLATION, RenderingHints.VALUE_INTERPOLATION_BILINEAR);
            if (background != null) {
                drawCoverImage(g2, background, getWidth(), getHeight());
            }

            Paint overlay = new GradientPaint(
                    0, 0, new Color(42, 26, 18, 220),
                    getWidth(), 0, new Color(42, 26, 18, 50)
            );
            g2.setPaint(overlay);
            g2.fillRect(0, 0, getWidth(), getHeight());
            g2.dispose();
        }

        private void drawCoverImage(Graphics2D g2, BufferedImage img, int w, int h) {
            double scale = Math.max((double) w / img.getWidth(), (double) h / img.getHeight());
            int drawW = (int) Math.round(img.getWidth() * scale);
            int drawH = (int) Math.round(img.getHeight() * scale);
            int x = (w - drawW) / 2;
            int y = (h - drawH) / 2;
            g2.drawImage(img, x, y, drawW, drawH, this);
        }
    }

    private static class RoundedTextField extends JTextField {
        private String placeholder;

        RoundedTextField(String placeholder) {
            this.placeholder = placeholder;
            setText("");
            setOpaque(false);
            setBorder(BorderFactory.createEmptyBorder());
            setBackground(new Color(0, 0, 0, 0));
            addFocusListener(new FocusAdapter() {
                @Override
                public void focusGained(FocusEvent e) {
                    repaint();
                }

                @Override
                public void focusLost(FocusEvent e) {
                    repaint();
                }
            });
        }

        void setPlaceholder(String placeholder) {
            this.placeholder = placeholder;
        }

        @Override
        protected void paintComponent(Graphics g) {
            super.paintComponent(g);
            if (getText().isEmpty() && !hasFocus()) {
                Graphics2D g2 = (Graphics2D) g.create();
                g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
                g2.setColor(new Color(138, 127, 118));
                g2.setFont(getFont());
                g2.drawString(placeholder, 0, getHeight() / 2 + 6);
                g2.dispose();
            }
        }
    }

    private static class RoundedPasswordField extends JPasswordField {
        private String placeholder;
        private boolean visible = false;

        RoundedPasswordField(String placeholder) {
            this.placeholder = placeholder;
            setEchoChar('•');
            setText("");
            setOpaque(false);
            setBorder(BorderFactory.createEmptyBorder());
            setBackground(new Color(0, 0, 0, 0));
        }

        void setPlaceholder(String placeholder) {
            this.placeholder = placeholder;
        }

        boolean isVisiblePassword() {
            return visible;
        }

        void toggle() {
            visible = !visible;
            setEchoChar(visible ? (char) 0 : '•');
            repaint();
        }

        @Override
        protected void paintComponent(Graphics g) {
            super.paintComponent(g);
            if (getPassword().length == 0 && !hasFocus()) {
                Graphics2D g2 = (Graphics2D) g.create();
                g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
                g2.setColor(new Color(138, 127, 118));
                g2.setFont(getFont());
                g2.drawString(placeholder, 0, getHeight() / 2 + 6);
                g2.dispose();
            }
        }
    }
}


