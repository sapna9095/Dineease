# DineEase AI – Admin Dashboard

A desktop Restaurant Management application built with **Java 17 (Swing)** and **MySQL 8**, featuring the "DineEase AI – Admin Dashboard" matching the target design pixel-for-pixel with live real-time MySQL database connectivity.

---

## Tech Stack & Architecture

- **Java**: Java 17+ (Swing, Graphics2D)
- **Look & Feel**: FlatLaf (`com.formdev:flatlaf:3.6` and `flatlaf-extras`)
- **Database**: MySQL 8.0+ via `mysql-connector-j`
- **Charts**: Custom pixel-perfect Graphics2D Area/Line chart with gold gradient and hover tooltips; custom-painted donut chart with segment gaps and live order counts.
- **Icons**: SVG & Material Icons via `ikonli-swing` and local vector resources.
- **Fonts**: Bundled Serif (*Playfair Display*, *Georgia*) and Clean Sans-Serif (*Segoe UI*, *Nunito*).
- **Concurrency**: `SwingWorker` for non-blocking asynchronous database operations and EDT safety; auto-refresh timer running every 5 seconds; 1-second live calendar and clock.

---

## Database Setup

1. Make sure MySQL 8 is running locally.
2. Edit `db.properties` in the project root:
   ```properties
   db.host=localhost
   db.port=3306
   db.name=dineease
   db.user=root
   db.password=YOUR_PASSWORD
   ```
3. The database `dineease` and required tables (`users`, `menu_items`, `inventory_items`, `restaurant_tables`, `orders`, `order_items`, `bills`) will be **auto-created** on first launch if they don't already exist. You can also manually review `src/main/resources/schema.sql`.
4. No sample or fake data is seeded. When the database is empty, the dashboard accurately displays zeros and clean empty states.
5. On the first launch, use the login screen to sign in with your administrator credentials (e.g., `admin@dineease.com` / `admin123`). The initial admin account will be automatically initialized into the MySQL `users` table.

---

## Project Structure

```
com.dineease
  ├── Main.java
  ├── db/
  │     └── DBConnection.java (Reads db.properties, auto-creates tables)
  ├── model/
  │     ├── User.java
  │     ├── MenuItem.java
  │     ├── InventoryItem.java
  │     ├── RestaurantTable.java
  │     ├── Order.java
  │     ├── OrderItem.java
  │     ├── Bill.java
  │     ├── DashboardStats.java
  │     ├── SalesData.java
  │     ├── SalesPoint.java
  │     ├── OrderStatusCount.java
  │     └── OrderRow.java
  ├── dao/
  │     └── DashboardDAO.java (Real SQL aggregation, zero hardcoding)
  ├── service/
  │     ├── DashboardService.java
  │     ├── DashboardServiceImpl.java
  │     ├── AuthService.java
  │     ├── DBAuthService.java
  │     └── UserSession.java
  ├── ui/
  │     ├── MainFrame.java (Main window with CardLayout)
  │     ├── SidebarPanel.java (Fixed dark brown sidebar, active pill, user role)
  │     ├── DashboardPanel.java (Header, stat cards, charts, tables, footer)
  │     ├── LoginFrame.java (Branded secure login & registration screen)
  │     └── components/
  │           ├── StatCard.java (6 colored stat cards with trend lines)
  │           ├── SalesChartPanel.java (Custom Graphics2D area chart with hover tooltips)
  │           ├── DonutChartPanel.java (Custom Graphics2D donut chart with status breakdowns)
  │           ├── TableStatusPanel.java (Available, Occupied, Reserved, Total counts)
  │           ├── RecentOrdersTable.java (Latest 5 orders with pill status badges)
  │           ├── RoundedPanel.java (Cards with radius 12-16 & soft shadow)
  │           └── StatusBadge.java (Pill badges for Served, Ready, Preparing, Pending, Cancelled)
```

---

## How to Run

Compile and run using the compiled classes and dependencies:

```powershell
# Compile all source files
$cp = Get-Content cp.txt
javac -encoding UTF-8 -cp "$cp;target\classes" -d target\classes (Get-ChildItem -Path src\main\java -Filter "*.java" -Recurse | ForEach-Object { $_.FullName })

# Run the application
java -Dsun.java2d.uiScale=1 -Dflatlaf.uiScale=1 -cp "target\classes;$cp" com.dineease.Main
```

Alternatively, if Maven CLI (`mvn`) is installed on your PATH:
```bash
mvn clean package
java -jar target/DineEase-1.0.jar
```

