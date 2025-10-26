import javax.swing.*;
import javax.swing.border.EmptyBorder;
import javax.swing.border.LineBorder;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.JTableHeader;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.UUID;
import java.util.Vector;

/**
 * A modern, white-themed E-commerce Management System application using Java Swing.
 *
 * This application manages Users, Products, and Orders. It uses a CardLayout
 * to switch between different management panels. Data is stored in-memory
 * using ArrayLists.
 */
public class EcommerceApp extends JFrame {

    // --- Modern White Theme Colors ---
    private static final Color COLOR_BACKGROUND = new Color(245, 245, 250); // Light lavender-white
    private static final Color COLOR_NAV_BAR = new Color(255, 255, 255);    // Pure White
    private static final Color COLOR_PANEL = new Color(255, 255, 255);       // Pure White
    private static final Color COLOR_TEXT = new Color(30, 30, 40);           // Dark text
    private static final Color COLOR_TEXT_LIGHT = new Color(100, 100, 110);    // Lighter text
    private static final Color COLOR_PRIMARY = new Color(0, 123, 255);     // Bright Blue
    private static final Color COLOR_DANGER = new Color(220, 53, 69);      // Red
    private static final Color COLOR_SUCCESS = new Color(40, 167, 69);     // Green
    private static final Color COLOR_BORDER = new Color(220, 220, 225);     // Light gray border

    // --- Fonts ---
    private static final Font FONT_HEADER = new Font("Segoe UI", Font.BOLD, 24);
    private static final Font FONT_NAV = new Font("Segoe UI", Font.BOLD, 16);
    private static final Font FONT_BODY = new Font("Segoe UI", Font.PLAIN, 14);
    private static final Font FONT_BODY_BOLD = new Font("Segoe UI", Font.BOLD, 14);
    private static final Font FONT_TABLE_HEADER = new Font("Segoe UI", Font.BOLD, 14);

    // --- Main Layout Components ---
    private CardLayout cardLayout;
    private JPanel mainContentPanel;
    private JPanel navigationPanel;

    // --- "Database" - In-memory lists ---
    public List<User> users = new ArrayList<>();
    public List<Product> products = new ArrayList<>();
    public List<Order> orders = new ArrayList<>();

    // --- Panels (Screens) ---
    private DashboardPanel dashboardPanel;
    private UserManagementPanel userPanel;
    private ProductManagementPanel productPanel;
    private OrderManagementPanel orderPanel;

    /**
     * Constructor: Sets up the entire application.
     */
    public EcommerceApp() {
        // --- 1. Set up Main Window ---
        setTitle("E-commerce Management System");
        setSize(1200, 800);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        getContentPane().setBackground(COLOR_BACKGROUND);
        setLayout(new BorderLayout());

        // --- 2. Add Dummy Data (for demo) ---
        addDummyData();

        // --- 3. Create Navigation Panel (West) ---
        createNavigationPanel();
        add(navigationPanel, BorderLayout.WEST);

        // --- 4. Create Main Content Panel (Center) ---
        cardLayout = new CardLayout();
        mainContentPanel = new JPanel(cardLayout);
        mainContentPanel.setBackground(COLOR_BACKGROUND);
        mainContentPanel.setBorder(new EmptyBorder(10, 10, 10, 10));

        // --- 5. Create and Add Panels (Screens) ---
        dashboardPanel = new DashboardPanel(this);
        userPanel = new UserManagementPanel(this);
        productPanel = new ProductManagementPanel(this);
        orderPanel = new OrderManagementPanel(this);

        mainContentPanel.add(dashboardPanel, "DASHBOARD");
        mainContentPanel.add(userPanel, "USERS");
        mainContentPanel.add(productPanel, "PRODUCTS");
        mainContentPanel.add(orderPanel, "ORDERS");

        add(mainContentPanel, BorderLayout.CENTER);

        // --- 6. Finalize ---
        cardLayout.show(mainContentPanel, "DASHBOARD"); // Show dashboard first
        setVisible(true);
    }

    /**
     * Creates the left-side navigation bar with buttons.
     */
    private void createNavigationPanel() {
        navigationPanel = new JPanel();
        navigationPanel.setLayout(new BoxLayout(navigationPanel, BoxLayout.Y_AXIS));
        navigationPanel.setBackground(COLOR_NAV_BAR);
        navigationPanel.setPreferredSize(new Dimension(200, 0));
        navigationPanel.setBorder(BorderFactory.createMatteBorder(0, 0, 0, 1, COLOR_BORDER));

        // Title
        JLabel navTitle = new JLabel("E-Commerce");
        navTitle.setFont(FONT_HEADER);
        navTitle.setForeground(COLOR_TEXT);
        navTitle.setAlignmentX(Component.CENTER_ALIGNMENT);
        navTitle.setBorder(new EmptyBorder(20, 10, 20, 10));
        navigationPanel.add(navTitle);

        // Add buttons
        navigationPanel.add(createNavButton("Dashboard", "DASHBOARD"));
        navigationPanel.add(createNavButton("Users", "USERS"));
        navigationPanel.add(createNavButton("Products", "PRODUCTS"));
        navigationPanel.add(createNavButton("Orders", "ORDERS"));

        navigationPanel.add(Box.createVerticalGlue()); // Pushes buttons to the top
    }

    /**
     * Helper to create a styled navigation button.
     */
    private JButton createNavButton(String text, String cardName) {
        JButton button = new JButton(text);
        button.setFont(FONT_NAV);
        button.setForeground(COLOR_TEXT_LIGHT);
        button.setBackground(COLOR_NAV_BAR);
        button.setFocusPainted(false);
        button.setBorder(new EmptyBorder(15, 25, 15, 25));
        button.setHorizontalAlignment(SwingConstants.LEFT);
        button.setCursor(new Cursor(Cursor.HAND_CURSOR));
        button.setMaximumSize(new Dimension(Integer.MAX_VALUE, button.getPreferredSize().height));

        // Hover effect
        button.addMouseListener(new MouseAdapter() {
            public void mouseEntered(MouseEvent evt) {
                button.setBackground(COLOR_BACKGROUND);
                button.setForeground(COLOR_PRIMARY);
            }
            public void mouseExited(MouseEvent evt) {
                button.setBackground(COLOR_NAV_BAR);
                button.setForeground(COLOR_TEXT_LIGHT);
            }
        });

        // Action
        button.addActionListener(e -> cardLayout.show(mainContentPanel, cardName));
        return button;
    }

    /**
     * Helper to create styled JButtons for forms.
     */
    public static void styleButton(JButton button, Color color) {
        button.setBackground(color);
        button.setForeground(Color.WHITE);
        button.setFont(FONT_BODY_BOLD);
        button.setFocusPainted(false);
        button.setBorder(new EmptyBorder(10, 15, 10, 15));
        button.setCursor(new Cursor(Cursor.HAND_CURSOR));
    }

    /**
     * Helper to style a JTable with the modern theme.
     */
    public static void styleTable(JTable table, JScrollPane scrollPane) {
        table.setBackground(COLOR_PANEL);
        table.setForeground(COLOR_TEXT);
        table.setGridColor(COLOR_BORDER);
        table.setFont(FONT_BODY);
        table.setRowHeight(30);
        table.setSelectionBackground(COLOR_PRIMARY);
        table.setSelectionForeground(Color.WHITE);
        table.setBorder(null);

        JTableHeader header = table.getTableHeader();
        header.setBackground(COLOR_BACKGROUND);
        header.setForeground(COLOR_TEXT);
        header.setFont(FONT_TABLE_HEADER);
        header.setBorder(new LineBorder(COLOR_BORDER));

        scrollPane.getViewport().setBackground(COLOR_PANEL);
        scrollPane.setBorder(new LineBorder(COLOR_BORDER));
    }

    /**
     * Refreshes all panels that might show summary data.
     */
    public void refreshAllPanels() {
        dashboardPanel.updateStats();
        userPanel.loadTableData();
        productPanel.loadTableData();
        orderPanel.loadTableData();
    }

    /**
     * Populates the in-memory lists with sample data.
     */
    private void addDummyData() {
        // Add Users
        User u1 = new User(genId(), "anoop_v", "anoop.v@example.com");
        User u2 = new User(genId(), "jane_doe", "jane.d@web.com");
        User u3 = new User(genId(), "alex_smith", "asmith@mail.net");
        users.addAll(List.of(u1, u2, u3));

        // Add Products
        Product p1 = new Product(genId(), "Laptop Pro", 1299.99, 50);
        Product p2 = new Product(genId(), "Wireless Mouse", 49.99, 150);
        Product p3 = new Product(genId(), "4K Monitor", 399.00, 75);
        Product p4 = new Product(genId(), "Mechanical Keyboard", 119.50, 120);
        products.addAll(List.of(p1, p2, p3, p4));

        // Add Orders
        Order o1 = new Order(genId(), u1, new Date(), "Shipped");
        o1.addItem(p1, 1);
        o1.addItem(p2, 1);
        orders.add(o1);

        Order o2 = new Order(genId(), u2, new Date(), "Pending");
        o2.addItem(p3, 2);
        o2.addItem(p4, 1);
        orders.add(o2);
    }

    /**
     * Generates a simple unique ID.
     */
    public static String genId() {
        return UUID.randomUUID().toString().substring(0, 8).toUpperCase();
    }

    // =========================================================================
    // --- Data Model Inner Classes ---
    // =========================================================================

    class User {
        String id;
        String username;
        String email;
        public User(String id, String username, String email) {
            this.id = id; this.username = username; this.email = email;
        }
    }

    class Product {
        String id;
        String name;
        double price;
        int stock;
        public Product(String id, String name, double price, int stock) {
            this.id = id; this.name = name; this.price = price; this.stock = stock;
        }
    }

    class OrderItem {
        Product product;
        int quantity;
        public OrderItem(Product product, int quantity) {
            this.product = product; this.quantity = quantity;
        }
        public double getTotalPrice() {
            return product.price * quantity;
        }
    }

    class Order {
        String id;
        User user;
        List<OrderItem> items = new ArrayList<>();
        double totalPrice;
        Date orderDate;
        String status; // e.g., "Pending", "Shipped", "Delivered"

        public Order(String id, User user, Date orderDate, String status) {
            this.id = id; this.user = user; this.orderDate = orderDate; this.status = status;
        }

        public void addItem(Product product, int quantity) {
            items.add(new OrderItem(product, quantity));
            recalculateTotal();
        }
        public void recalculateTotal() {
            totalPrice = items.stream().mapToDouble(OrderItem::getTotalPrice).sum();
        }
    }

    // =========================================================================
    // --- Panel Inner Classes (The "Screens") ---
    // =========================================================================

    /**
     * Dashboard Panel: Shows high-level statistics.
     */
    class DashboardPanel extends JPanel {
        private EcommerceApp app;
        private JLabel userCountLabel;
        private JLabel productCountLabel;
        private JLabel orderCountLabel;
        private JLabel salesTotalLabel;

        public DashboardPanel(EcommerceApp app) {
            this.app = app;
            setBackground(COLOR_BACKGROUND);
            setLayout(new BorderLayout());
            setBorder(new EmptyBorder(10, 10, 10, 10));

            JLabel title = new JLabel("Dashboard Overview");
            title.setFont(FONT_HEADER);
            title.setForeground(COLOR_TEXT);
            title.setBorder(new EmptyBorder(0, 0, 15, 0));
            add(title, BorderLayout.NORTH);

            JPanel statsPanel = new JPanel(new GridLayout(1, 4, 20, 20)); // 1 row, 4 cols
            statsPanel.setBackground(COLOR_BACKGROUND);

            userCountLabel = createStatCard("Total Users", "0");
            productCountLabel = createStatCard("Total Products", "0");
            orderCountLabel = createStatCard("Pending Orders", "0");
            salesTotalLabel = createStatCard("Total Sales", "$0.00");

            statsPanel.add(userCountLabel);
            statsPanel.add(productCountLabel);
            statsPanel.add(orderCountLabel);
            statsPanel.add(salesTotalLabel);
            
            add(statsPanel, BorderLayout.CENTER);
            updateStats();
        }
        
        private JLabel createStatCard(String title, String value) {
            JLabel card = new JLabel("<html><body style='padding: 15px;'>"
                    + "<div style='font-size: 12px; color: rgb(100,100,110);'>" + title + "</div>"
                    + "<div style='font-size: 24px; font-weight: bold; padding-top: 10px;'>" + value + "</div>"
                    + "</body></html>");
            card.setOpaque(true);
            card.setBackground(COLOR_PANEL);
            card.setBorder(new LineBorder(COLOR_BORDER));
            card.setVerticalAlignment(SwingConstants.TOP);
            return card;
        }

        public void updateStats() {
            int userCount = app.users.size();
            int productCount = app.products.size();
            long pendingOrders = app.orders.stream().filter(o -> "Pending".equals(o.status)).count();
            double totalSales = app.orders.stream().filter(o -> !"Pending".equals(o.status)).mapToDouble(o -> o.totalPrice).sum();

            userCountLabel.setText("<html><body style='padding: 15px;'>"
                    + "<div style='font-size: 12px; color: rgb(100,100,110);'>Total Users</div>"
                    + "<div style='font-size: 24px; font-weight: bold; padding-top: 10px;'>" + userCount + "</div>"
                    + "</body></html>");
            productCountLabel.setText("<html><body style='padding: 15px;'>"
                    + "<div style='font-size: 12px; color: rgb(100,100,110);'>Total Products</div>"
                    + "<div style='font-size: 24px; font-weight: bold; padding-top: 10px;'>" + productCount + "</div>"
                    + "</body></html>");
            orderCountLabel.setText("<html><body style='padding: 15px;'>"
                    + "<div style='font-size: 12px; color: rgb(100,100,110);'>Pending Orders</div>"
                    + "<div style='font-size: 24px; font-weight: bold; padding-top: 10px;'>" + pendingOrders + "</div>"
                    + "</body></html>");
            salesTotalLabel.setText("<html><body style='padding: 15px;'>"
                    + "<div style='font-size: 12px; color: rgb(100,100,110);'>Total Sales</div>"
                    + "<div style='font-size: 24px; font-weight: bold; padding-top: 10px;'>" + String.format("$%.2f", totalSales) + "</div>"
                    + "</body></html>");
        }
    }

    /**
     * Base Panel for managing data (Users, Products).
     * Provides a common structure: a table and buttons for CRUD.
     */
    abstract class BaseManagementPanel extends JPanel {
        protected EcommerceApp app;
        protected JTable table;
        protected DefaultTableModel tableModel;
        protected JButton addButton;
        protected JButton editButton;
        protected JButton removeButton;

        public BaseManagementPanel(EcommerceApp app, String title, String[] columnNames) {
            this.app = app;
            setBackground(COLOR_BACKGROUND);
            setLayout(new BorderLayout(0, 10));
            setBorder(new EmptyBorder(10, 10, 10, 10));

            // Title
            JLabel titleLabel = new JLabel(title);
            titleLabel.setFont(FONT_HEADER);
            titleLabel.setForeground(COLOR_TEXT);
            titleLabel.setBorder(new EmptyBorder(0, 0, 10, 0));
            add(titleLabel, BorderLayout.NORTH);

            // Table
            tableModel = new DefaultTableModel(columnNames, 0) {
                @Override
                public boolean isCellEditable(int row, int column) {
                    return false; // Make table read-only
                }
            };
            table = new JTable(tableModel);
            JScrollPane scrollPane = new JScrollPane(table);
            styleTable(table, scrollPane);
            add(scrollPane, BorderLayout.CENTER);

            // Button Panel
            JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.LEFT, 10, 0));
            buttonPanel.setBackground(COLOR_BACKGROUND);

            addButton = new JButton("Add New");
            styleButton(addButton, COLOR_PRIMARY);
            
            editButton = new JButton("Edit Selected");
            styleButton(editButton, COLOR_SUCCESS);

            removeButton = new JButton("Remove Selected");
            styleButton(removeButton, COLOR_DANGER);

            buttonPanel.add(addButton);
            buttonPanel.add(editButton);
            buttonPanel.add(removeButton);
            add(buttonPanel, BorderLayout.SOUTH);
            
            // Add listeners (must be done in subclass)
        }
        
        public abstract void loadTableData();
    }

    /**
     * User Management Panel: Manage CRUD for Users.
     */
    class UserManagementPanel extends BaseManagementPanel {
        public UserManagementPanel(EcommerceApp app) {
            super(app, "User Management", new String[]{"ID", "Username", "Email"});
            
            addButton.addActionListener(e -> onAdd());
            editButton.addActionListener(e -> onEdit());
            removeButton.addActionListener(e -> onRemove());
        }
        
        @Override
        public void loadTableData() {
            tableModel.setRowCount(0); // Clear table
            for (User user : app.users) {
                Vector<String> row = new Vector<>();
                row.add(user.id);
                row.add(user.username);
                row.add(user.email);
                tableModel.addRow(row);
            }
        }
        
        private void onAdd() {
            UserFormPanel form = new UserFormPanel(null);
            int result = JOptionPane.showConfirmDialog(app, form, "Add New User", 
                JOptionPane.OK_CANCEL_OPTION, JOptionPane.PLAIN_MESSAGE);
            
            if (result == JOptionPane.OK_OPTION) {
                String username = form.getUsername();
                String email = form.getEmail();
                if (!username.isEmpty() && !email.isEmpty()) {
                    app.users.add(new User(genId(), username, email));
                    app.refreshAllPanels();
                } else {
                    JOptionPane.showMessageDialog(app, "All fields are required.", "Error", JOptionPane.ERROR_MESSAGE);
                }
            }
        }
        
        private void onEdit() {
            int selectedRow = table.getSelectedRow();
            if (selectedRow == -1) {
                JOptionPane.showMessageDialog(app, "Please select a user to edit.", "Warning", JOptionPane.WARNING_MESSAGE);
                return;
            }
            
            String userId = (String) tableModel.getValueAt(selectedRow, 0);
            User user = app.users.stream().filter(u -> u.id.equals(userId)).findFirst().orElse(null);
            
            if (user == null) return;
            
            UserFormPanel form = new UserFormPanel(user);
            int result = JOptionPane.showConfirmDialog(app, form, "Edit User", 
                JOptionPane.OK_CANCEL_OPTION, JOptionPane.PLAIN_MESSAGE);
                
            if (result == JOptionPane.OK_OPTION) {
                user.username = form.getUsername();
                user.email = form.getEmail();
                app.refreshAllPanels();
            }
        }
        
        private void onRemove() {
            int selectedRow = table.getSelectedRow();
            if (selectedRow == -1) {
                JOptionPane.showMessageDialog(app, "Please select a user to remove.", "Warning", JOptionPane.WARNING_MESSAGE);
                return;
            }
            
            int confirm = JOptionPane.showConfirmDialog(app, "Are you sure you want to remove this user?", "Confirm Removal", JOptionPane.YES_NO_OPTION, JOptionPane.WARNING_MESSAGE);
            if (confirm == JOptionPane.YES_OPTION) {
                String userId = (String) tableModel.getValueAt(selectedRow, 0);
                app.users.removeIf(u -> u.id.equals(userId));
                app.refreshAllPanels();
            }
        }
    }
    
    /**
     * Product Management Panel: Manage CRUD for Products.
     */
    class ProductManagementPanel extends BaseManagementPanel {
        public ProductManagementPanel(EcommerceApp app) {
            super(app, "Product Management", new String[]{"ID", "Name", "Price", "Stock"});
            
            addButton.addActionListener(e -> onAdd());
            editButton.addActionListener(e -> onEdit());
            removeButton.addActionListener(e -> onRemove());
        }
        
        @Override
        public void loadTableData() {
            tableModel.setRowCount(0); // Clear table
            for (Product p : app.products) {
                Vector<Object> row = new Vector<>();
                row.add(p.id);
                row.add(p.name);
                row.add(String.format("%.2f", p.price));
                row.add(p.stock);
                tableModel.addRow(row);
            }
        }
        
        private void onAdd() {
            ProductFormPanel form = new ProductFormPanel(null);
            int result = JOptionPane.showConfirmDialog(app, form, "Add New Product", 
                JOptionPane.OK_CANCEL_OPTION, JOptionPane.PLAIN_MESSAGE);
            
            if (result == JOptionPane.OK_OPTION) {
                try {
                    String name = form.getProductName();
                    double price = form.getPrice();
                    int stock = form.getStock();
                    app.products.add(new Product(genId(), name, price, stock));
                    app.refreshAllPanels();
                } catch (Exception e) {
                    JOptionPane.showMessageDialog(app, "Invalid input. Check price and stock.", "Error", JOptionPane.ERROR_MESSAGE);
                }
            }
        }
        
        private void onEdit() {
            int selectedRow = table.getSelectedRow();
            if (selectedRow == -1) {
                JOptionPane.showMessageDialog(app, "Please select a product to edit.", "Warning", JOptionPane.WARNING_MESSAGE);
                return;
            }
            
            String productId = (String) tableModel.getValueAt(selectedRow, 0);
            Product p = app.products.stream().filter(prod -> prod.id.equals(productId)).findFirst().orElse(null);
            
            if (p == null) return;
            
            ProductFormPanel form = new ProductFormPanel(p);
            int result = JOptionPane.showConfirmDialog(app, form, "Edit Product", 
                JOptionPane.OK_CANCEL_OPTION, JOptionPane.PLAIN_MESSAGE);
                
            if (result == JOptionPane.OK_OPTION) {
                try {
                    p.name = form.getProductName();
                    p.price = form.getPrice();
                    p.stock = form.getStock();
                    app.refreshAllPanels();
                } catch (Exception e) {
                    JOptionPane.showMessageDialog(app, "Invalid input. Check price and stock.", "Error", JOptionPane.ERROR_MESSAGE);
                }
            }
        }
        
        private void onRemove() {
            int selectedRow = table.getSelectedRow();
            if (selectedRow == -1) {
                JOptionPane.showMessageDialog(app, "Please select a product to remove.", "Warning", JOptionPane.WARNING_MESSAGE);
                return;
            }
            
            int confirm = JOptionPane.showConfirmDialog(app, "Are you sure you want to remove this product?", "Confirm Removal", JOptionPane.YES_NO_OPTION, JOptionPane.WARNING_MESSAGE);
            if (confirm == JOptionPane.YES_OPTION) {
                String productId = (String) tableModel.getValueAt(selectedRow, 0);
                app.products.removeIf(p -> p.id.equals(productId));
                app.refreshAllPanels();
            }
        }
    }

    /**
     * Order Management Panel: View Orders and update status.
     */
    class OrderManagementPanel extends BaseManagementPanel {
        private SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm");
        
        public OrderManagementPanel(EcommerceApp app) {
            super(app, "Order Management", new String[]{"ID", "User", "Total Price", "Date", "Status"});
            
            // Customize buttons for Orders
            addButton.setText("View Details");
            addButton.addActionListener(e -> onViewDetails());
            
            editButton.setText("Update Status");
            editButton.addActionListener(e -> onUpdateStatus());
            
            removeButton.setVisible(false); // Can't remove orders
        }
        
        @Override
        public void loadTableData() {
            tableModel.setRowCount(0); // Clear table
            for (Order o : app.orders) {
                Vector<Object> row = new Vector<>();
                row.add(o.id);
                row.add(o.user.username);
                row.add(String.format("$%.2f", o.totalPrice));
                row.add(dateFormat.format(o.orderDate));
                row.add(o.status);
                tableModel.addRow(row);
            }
        }
        
        private Order getSelectedOrder() {
            int selectedRow = table.getSelectedRow();
            if (selectedRow == -1) {
                JOptionPane.showMessageDialog(app, "Please select an order.", "Warning", JOptionPane.WARNING_MESSAGE);
                return null;
            }
            String orderId = (String) tableModel.getValueAt(selectedRow, 0);
            return app.orders.stream().filter(o -> o.id.equals(orderId)).findFirst().orElse(null);
        }
        
        private void onViewDetails() {
            Order order = getSelectedOrder();
            if (order == null) return;
            
            StringBuilder details = new StringBuilder();
            details.append("Order ID: ").append(order.id).append("\n");
            details.append("User: ").append(order.user.username).append("\n");
            details.append("Status: ").append(order.status).append("\n");
            details.append("Total: $").append(String.format("%.2f", order.totalPrice)).append("\n\n");
            details.append("Items:\n");
            
            for (OrderItem item : order.items) {
                details.append(String.format("- %s (Qty: %d) @ $%.2f ea.\n", 
                    item.product.name, item.quantity, item.product.price));
            }
            
            JTextArea textArea = new JTextArea(details.toString());
            textArea.setEditable(false);
            textArea.setOpaque(false);
            textArea.setFont(FONT_BODY);
            JScrollPane scrollPane = new JScrollPane(textArea);
            scrollPane.setPreferredSize(new Dimension(400, 300));
            JOptionPane.showMessageDialog(app, scrollPane, "Order Details", JOptionPane.INFORMATION_MESSAGE);
        }
        
        private void onUpdateStatus() {
            Order order = getSelectedOrder();
            if (order == null) return;
            
            String[] statuses = {"Pending", "Shipped", "Delivered", "Cancelled"};
            String newStatus = (String) JOptionPane.showInputDialog(
                app, "Select new status for Order " + order.id, "Update Status",
                JOptionPane.PLAIN_MESSAGE, null, statuses, order.status);
                
            if (newStatus != null && !newStatus.equals(order.status)) {
                order.status = newStatus;
                app.refreshAllPanels();
            }
        }
    }

    // =========================================================================
    // --- Reusable Form Panels for Dialogs ---
    // =========================================================================

    /**
     * A reusable form panel for adding/editing a User.
     */
    class UserFormPanel extends JPanel {
        private JTextField usernameField;
        private JTextField emailField;

        public UserFormPanel(User user) {
            setLayout(new GridLayout(0, 2, 10, 10));
            setBackground(COLOR_PANEL);
            
            usernameField = new JTextField(20);
            emailField = new JTextField(20);

            if (user != null) {
                usernameField.setText(user.username);
                emailField.setText(user.email);
            }

            add(new JLabel("Username:"));
            add(usernameField);
            add(new JLabel("Email:"));
            add(emailField);
        }
        public String getUsername() { return usernameField.getText(); }
        public String getEmail() { return emailField.getText(); }
    }
    
    /**
     * A reusable form panel for adding/editing a Product.
     */
    class ProductFormPanel extends JPanel {
        private JTextField nameField;
        private JSpinner priceSpinner;
        private JSpinner stockSpinner;

        public ProductFormPanel(Product product) {
            setLayout(new GridLayout(0, 2, 10, 10));
            setBackground(COLOR_PANEL);

            nameField = new JTextField(20);
            priceSpinner = new JSpinner(new SpinnerNumberModel(0.0, 0.0, 100000.0, 0.01));
            stockSpinner = new JSpinner(new SpinnerNumberModel(0, 0, 10000, 1));
            
            // Fix for spinner text field width
            JSpinner.NumberEditor priceEditor = new JSpinner.NumberEditor(priceSpinner, "#.##");
            priceSpinner.setEditor(priceEditor);
            JSpinner.NumberEditor stockEditor = new JSpinner.NumberEditor(stockSpinner, "#");
            stockSpinner.setEditor(stockEditor);


            if (product != null) {
                nameField.setText(product.name);
                priceSpinner.setValue(product.price);
                stockSpinner.setValue(product.stock);
            }

            add(new JLabel("Product Name:"));
            add(nameField);
            add(new JLabel("Price ($):"));
            add(priceSpinner);
            add(new JLabel("Stock:"));
            add(stockSpinner);
        }
        public String getProductName() { return nameField.getText(); }
        public double getPrice() { return (Double) priceSpinner.getValue(); }
        public int getStock() { return (Integer) stockSpinner.getValue(); }
    }


    /**
     * Main method: Sets up UIManager defaults and runs the app.
     */
    public static void main(String[] args) {
        // --- Set global UI defaults for the white theme ---
        try {
            // Set Nimbus L&F for a cleaner base
            for (UIManager.LookAndFeelInfo info : UIManager.getInstalledLookAndFeels()) {
                if ("Nimbus".equals(info.getName())) {
                    UIManager.setLookAndFeel(info.getClassName());
                    break;
                }
            }
        } catch (Exception e) {
            // If Nimbus is not available, default will be used.
        }
        
        // Override Nimbus defaults with our theme
        UIManager.put("control", COLOR_BACKGROUND); // General background
        UIManager.put("nimbusBase", COLOR_PRIMARY); // Button colors, highlights
        UIManager.put("nimbusLightBackground", COLOR_PANEL); // Text field/list background
        UIManager.put("text", COLOR_TEXT); // Default text color
        UIManager.put("nimbusSelectionBackground", COLOR_PRIMARY);
        
        UIManager.put("Panel.background", COLOR_BACKGROUND);
        UIManager.put("Label.foreground", COLOR_TEXT);
        UIManager.put("TextField.background", COLOR_PANEL);
        UIManager.put("TextField.foreground", COLOR_TEXT);
        UIManager.put("TextField.border", new LineBorder(COLOR_BORDER));
        UIManager.put("Button.background", COLOR_PRIMARY);
        UIManager.put("Button.foreground", Color.WHITE);
        UIManager.put("Button.font", FONT_BODY_BOLD);
        UIManager.put("Button.border", new EmptyBorder(10, 15, 10, 15));
        
        // Run the GUI creation on the Event Dispatch Thread (EDT)
        SwingUtilities.invokeLater(EcommerceApp::new);
    }
}
