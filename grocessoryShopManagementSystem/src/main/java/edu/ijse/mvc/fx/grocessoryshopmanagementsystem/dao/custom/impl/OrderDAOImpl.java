package edu.ijse.mvc.fx.grocessoryshopmanagementsystem.dao.custom.impl;

import edu.ijse.mvc.fx.grocessoryshopmanagementsystem.dao.custom.OrderDAO;
import edu.ijse.mvc.fx.grocessoryshopmanagementsystem.db.DBConnection;
import edu.ijse.mvc.fx.grocessoryshopmanagementsystem.entity.Order;
import edu.ijse.mvc.fx.grocessoryshopmanagementsystem.entity.OrderDetail;
//import edu.ijse.mvc.fx.grocessoryshopmanagementsystem.util.CrudUtil;
import edu.ijse.mvc.fx.grocessoryshopmanagementsystem.dao.CRUDUtil;
import javafx.collections.ObservableList;

import java.sql.*;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.Map;

public class OrderDAOImpl implements OrderDAO {

    @Override
    public ArrayList<Order> getAll() throws Exception {
        ArrayList<Order> list = new ArrayList<>();
        ResultSet rst = CRUDUtil.executeQuery("SELECT * FROM Orders");
        while (rst.next()) {
            list.add(new Order(
                    rst.getInt("order_Id"),
                    rst.getString("orderDate"),
                    rst.getInt("customerId"),
                    rst.getInt("userId")
            ));
        }
        return list;
    }

    @Override
    public boolean save(Order order) throws Exception {
        return CRUDUtil.execute(
                "INSERT INTO Orders (orderDate, customerId, userId) VALUES (?, ?, ?)",
                order.getOrderDate(), order.getCustomerId(), order.getUserId()
        );
    }

    @Override
    public boolean update(Order order) throws Exception {
        return CRUDUtil.execute(
                "UPDATE Orders SET orderDate=?, customerId=?, userId=? WHERE order_Id=?",
                order.getOrderDate(), order.getCustomerId(), order.getUserId(), order.getOrderId()
        );
    }

    @Override
    public boolean delete(String id) throws Exception {
        return CRUDUtil.execute("DELETE FROM Orders WHERE order_Id=?", Integer.parseInt(id));
    }

    @Override
    public Order search(String id) throws Exception {
        ResultSet rst = CRUDUtil.executeQuery("SELECT * FROM Orders WHERE order_Id=?", Integer.parseInt(id));
        if (rst.next()) {
            return new Order(
                    rst.getInt("order_Id"),
                    rst.getString("orderDate"),
                    rst.getInt("customerId"),
                    rst.getInt("userId")
            );
        }
        return null;
    }

    @Override
    public boolean placeOrder(Order order, ObservableList<OrderDetail> detailList) throws Exception {
        Connection connection = DBConnection.getInstance().getConnection();
        try {
            connection.setAutoCommit(false);

            PreparedStatement stm1 = connection.prepareStatement(
                    "INSERT INTO Orders (orderDate, customerId, userId) VALUES (?, ?, ?)",
                    Statement.RETURN_GENERATED_KEYS
            );
            stm1.setString(1, order.getOrderDate());
            stm1.setInt(2, order.getCustomerId());
            stm1.setInt(3, order.getUserId());

            if (stm1.executeUpdate() > 0) {
                ResultSet generatedKeys = stm1.getGeneratedKeys();
                int newOrderId = 0;
                if (generatedKeys.next()) {
                    newOrderId = generatedKeys.getInt(1);
                }

                for (OrderDetail detail : detailList) {
                    boolean isDetailSaved = executeInTransaction(connection,
                            "INSERT INTO Order_Item VALUES (?, ?, ?, ?)",
                            newOrderId, detail.getItemId(), detail.getQuantity(), detail.getUnitPrice());

                    if (!isDetailSaved) { connection.rollback(); return false; }

                    boolean isStockUpdated = executeInTransaction(connection,
                            "UPDATE Item SET quantity = quantity - ? WHERE itemId = ?",
                            detail.getQuantity(), detail.getItemId());

                    if (!isStockUpdated) { connection.rollback(); return false; }
                }

                connection.commit();
                return true;
            }

            connection.rollback();
            return false;

        } catch (SQLException e) {
            if (connection != null) connection.rollback();
            throw e;
        } finally {
            if (connection != null) connection.setAutoCommit(true);
        }
    }

    @Override
    public String getNextOrderId() throws Exception {
        ResultSet rst = CRUDUtil.executeQuery("SELECT order_Id FROM Orders ORDER BY order_Id DESC LIMIT 1");
        if (rst.next()) return String.valueOf(rst.getInt(1) + 1);
        return "1";
    }

    @Override
    public String getCustomerIdByName(String name) throws Exception {
        ResultSet rst = CRUDUtil.executeQuery(
                "SELECT customer_id FROM Customer WHERE customerName = ? LIMIT 1", name);
        if (rst.next()) return String.valueOf(rst.getInt(1));
        return null;
    }

    @Override
    public String[] getItemDetails(String itemId) throws Exception {
        ResultSet rst = CRUDUtil.executeQuery(
                "SELECT itemName, unitPrice FROM Item WHERE itemId = ?", itemId);
        if (rst.next()) return new String[]{rst.getString(1), rst.getString(2)};
        return null;
    }

    @Override
    public Map<String, Integer> getMonthlyOrderCount() throws Exception {
        Map<String, Integer> chartData = new LinkedHashMap<>();
        ResultSet rs = CRUDUtil.executeQuery(
                "SELECT MONTHNAME(STR_TO_DATE(orderDate, '%Y-%m-%d')) AS month_name, " +
                        "COUNT(order_Id) AS order_count FROM Orders " +
                        "GROUP BY month_name, MONTH(STR_TO_DATE(orderDate, '%Y-%m-%d')) " +
                        "ORDER BY MONTH(STR_TO_DATE(orderDate, '%Y-%m-%d'))");
        while (rs.next()) {
            chartData.put(rs.getString("month_name"), rs.getInt("order_count"));
        }
        return chartData;
    }

    private boolean executeInTransaction(Connection conn, String sql, Object... args) throws SQLException {
        PreparedStatement pstm = conn.prepareStatement(sql);
        for (int i = 0; i < args.length; i++) pstm.setObject(i + 1, args[i]);
        return pstm.executeUpdate() > 0;
    }
}
