package edu.ijse.mvc.fx.grocessoryshopmanagementsystem.bo.custom;

import edu.ijse.mvc.fx.grocessoryshopmanagementsystem.bo.SuperBO;
import edu.ijse.mvc.fx.grocessoryshopmanagementsystem.dto.OrderDTO;
import edu.ijse.mvc.fx.grocessoryshopmanagementsystem.entity.OrderDetail;
import javafx.collections.ObservableList;

import java.util.Map;

public interface OrderBO extends SuperBO {
    boolean placeOrder(OrderDTO orderDTO, ObservableList<OrderDetail> detailList) throws Exception;
    String getNextOrderId() throws Exception;
    String getCustomerIdByName(String name) throws Exception;
    String[] getItemDetails(String itemId) throws Exception;
    Map<String, Integer> getMonthlyOrderCount() throws Exception;
}
