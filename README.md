
## **Description**

The `EcommerceApp` is a Java Swing-based desktop application designed for managing a simple E-commerce system.
It provides a clean, modern white-themed graphical user interface to manage **Users**, **Products**, and **Orders** efficiently.
The application uses a **CardLayout** to switch between panels and stores all data **in-memory** using ArrayLists.

Key features include:

* Dashboard displaying summary statistics.
* CRUD operations for Users and Products.
* Order viewing and status management.
* Interactive GUI components styled with a modern, minimalistic white theme.
* Separation of functionality into modular inner classes for scalability.

---

## **README**

### **1. Overview**

`EcommerceApp` is an all-in-one management system for Users, Products, and Orders built entirely with Java Swing.
It demonstrates advanced Swing usage, event handling, component styling, and basic data management.

---

### **2. Technologies Used**

* Java SE (JDK 8+)
* Java Swing (GUI)
* Java AWT (Event Handling and Layouts)
* Object-Oriented Programming (OOP)
* In-memory data structures (ArrayList, Vector)

---

### **3. Features**

* **Dashboard Panel:** Displays total users, products, pending orders, and total sales.
* **User Management:** Add, edit, and remove users.
* **Product Management:** Add, edit, and remove products with price and stock control.
* **Order Management:** View order details and update order status.
* **Reusable Components:** Forms and tables styled consistently across panels.
* **Dynamic Updates:** Real-time refresh of all data panels after every change.

---

### **4. How to Run**

1. Save the code as `EcommerceApp.java`.
2. Compile:

   ```bash
   javac EcommerceApp.java
   ```
3. Run:

   ```bash
   java EcommerceApp
   ```
4. The application window will open with the Dashboard as the default view.

---

### **5. Project Structure**

* **Main Class:** `EcommerceApp` – Initializes GUI, theme, and navigation.
* **Inner Classes:**

  * `User`, `Product`, `Order`, `OrderItem` – Data models.
  * `DashboardPanel`, `UserManagementPanel`, `ProductManagementPanel`, `OrderManagementPanel` – GUI panels.
  * `UserFormPanel`, `ProductFormPanel` – Dialog forms for data entry.

---

### **6. Data Handling**

All data is stored in **ArrayLists** (no database integration).
Dummy data for Users, Products, and Orders is automatically generated at startup.
Each entity is assigned a unique ID using a UUID-based generator.

---

### **7. Customization**

* Modify color constants to adjust the UI theme.
* Add persistence by integrating a database (e.g., JDBC or JSON serialization).
* Extend functionality by implementing search or filter features in each management panel.


