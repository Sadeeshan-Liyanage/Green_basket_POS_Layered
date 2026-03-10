package edu.ijse.mvc.fx.grocessoryshopmanagementsystem.model;

import edu.ijse.mvc.fx.grocessoryshopmanagementsystem.db.DBConnection;
import edu.ijse.mvc.fx.grocessoryshopmanagementsystem.tm.ItemTM;
import edu.ijse.mvc.fx.grocessoryshopmanagementsystem.util.CrudUtil;
import javafx.collections.ObservableList;

import java.sql.*;
import java.util.LinkedHashMap;
import java.util.Map;

public class OrderModel {

    public boolean placeOrder(int orderId, int customerId, int userId, String date, ObservableList<ItemTM> cartList) throws SQLException, ClassNotFoundException {
        Connection connection = DBConnection.getInstance().getConnection();;
        try {
            connection.setAutoCommit(false);


            String orderSql = "INSERT INTO Orders (orderDate, customerId, userId) VALUES (?, ?, ?)";


            PreparedStatement stm1 = connection.prepareStatement(orderSql, Statement.RETURN_GENERATED_KEYS);
            stm1.setString(1, date);
            stm1.setInt(2, customerId);
            stm1.setInt(3, userId);

            if (stm1.executeUpdate() > 0) {

                ResultSet generatedKeys = stm1.getGeneratedKeys();
                int newOrderId = 0;
                if (generatedKeys.next()) {
                    newOrderId = generatedKeys.getInt(1);
                }



                for (ItemTM item : cartList) {
                    boolean isDetailSaved = executeUpdateInsideTransaction(connection,
                            "INSERT INTO Order_Item VALUES (?, ?, ?, ?)",
                            newOrderId, item.getItemId(), item.getQuantity(), item.getUnitPrice());

                    if (!isDetailSaved) {
                        connection.rollback();
                        return false;
                    }


                    boolean isStockUpdated = executeUpdateInsideTransaction(connection,
                            "UPDATE Item SET quantity = quantity - ? WHERE itemId = ?",
                    item.getQuantity(), item.getItemId());

                    if(!isStockUpdated) {
                        connection.rollback();
                        return false;
                    }
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
    private boolean executeUpdateInsideTransaction(Connection conn, String sql, Object... args) throws SQLException {
        PreparedStatement pstm = conn.prepareStatement(sql);
        for (int i = 0; i < args.length; i++) {
            pstm.setObject(i + 1, args[i]);
        }
        return pstm.executeUpdate() > 0;
    }



    public String getNextOrderId() throws Exception {
        ResultSet rst = CrudUtil.executeQuery("SELECT order_Id FROM Orders ORDER BY order_Id DESC LIMIT 1");
        if (rst.next()) {
            return String.valueOf(rst.getInt(1) + 1);
        }
        return "1";
    }


    public String getCustomerIdByName(String name) throws Exception {
        ResultSet rst = CrudUtil.executeQuery("SELECT customer_id FROM Customer WHERE customerName = ? LIMIT 1",name);

        if (rst.next()) {
            return String.valueOf(rst.getInt(1));
        }
        return null;
    }

    public String[] getItemDetails(String itemId) throws Exception {
        ResultSet rst = CrudUtil.executeQuery("SELECT itemName, unitPrice FROM Item WHERE itemId = ?",itemId);

        if (rst.next()) {
            return new String[]{rst.getString(1), rst.getString(2)};
        }
        return null;
    }

    public Map<String, Integer> getMonthlyOrderCount() {
        Map<String, Integer> chartData = new LinkedHashMap<>();
        String query = "SELECT MONTHNAME(STR_TO_DATE(orderDate, '%Y-%m-%d')) AS month_name, " +
                "COUNT(order_Id) AS order_count " +
                "FROM Orders " +
                "GROUP BY month_name, MONTH(STR_TO_DATE(orderDate, '%Y-%m-%d')) " +
                "ORDER BY MONTH(STR_TO_DATE(orderDate, '%Y-%m-%d'))";

        try {
            ResultSet rs = CrudUtil.executeQuery(query);
            while (rs.next()) {
                chartData.put(rs.getString("month_name"), rs.getInt("order_count"));
            }
        } catch (SQLException | ClassNotFoundException e) {
            e.printStackTrace();
        }
        return chartData;
    }

}