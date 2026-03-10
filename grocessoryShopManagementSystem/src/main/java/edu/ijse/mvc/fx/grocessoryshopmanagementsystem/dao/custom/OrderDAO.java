package edu.ijse.mvc.fx.grocessoryshopmanagementsystem.dao.custom;

import edu.ijse.mvc.fx.grocessoryshopmanagementsystem.dao.CrudDAO;
import edu.ijse.mvc.fx.grocessoryshopmanagementsystem.entity.Order;
import edu.ijse.mvc.fx.grocessoryshopmanagementsystem.entity.OrderDetail;
import javafx.collections.ObservableList;

import java.util.Map;

public interface OrderDAO extends CrudDAO<Order> {
    boolean placeOrder(Order order, ObservableList<OrderDetail> detailList) throws Exception;
    String getNextOrderId() throws Exception;
    String getCustomerIdByName(String name) throws Exception;
    String[] getItemDetails(String itemId) throws Exception;
    Map<String, Integer> getMonthlyOrderCount() throws Exception;
}
