package edu.ijse.mvc.fx.grocessoryshopmanagementsystem.bo.custom.impl;

import edu.ijse.mvc.fx.grocessoryshopmanagementsystem.bo.custom.OrderBO;
import edu.ijse.mvc.fx.grocessoryshopmanagementsystem.dao.DAOFactory;
import edu.ijse.mvc.fx.grocessoryshopmanagementsystem.dao.custom.OrderDAO;
import edu.ijse.mvc.fx.grocessoryshopmanagementsystem.dto.OrderDTO;
import edu.ijse.mvc.fx.grocessoryshopmanagementsystem.entity.Order;
import edu.ijse.mvc.fx.grocessoryshopmanagementsystem.entity.OrderDetail;
import javafx.collections.ObservableList;

import java.util.Map;

public class OrderBOImpl implements OrderBO {

    private final OrderDAO orderDAO = (OrderDAO) DAOFactory.getInstance().getDAO(DAOFactory.DAOType.ORDER);

    @Override
    public boolean placeOrder(OrderDTO dto, ObservableList<OrderDetail> detailList) throws Exception {
        Order order = new Order(0, dto.getOrderDate(), dto.getCustomerId(), dto.getUserId());
        return orderDAO.placeOrder(order, detailList);
    }

    @Override
    public String getNextOrderId() throws Exception {
        return orderDAO.getNextOrderId();
    }

    @Override
    public String getCustomerIdByName(String name) throws Exception {
        return orderDAO.getCustomerIdByName(name);
    }

    @Override
    public String[] getItemDetails(String itemId) throws Exception {
        return orderDAO.getItemDetails(itemId);
    }

    @Override
    public Map<String, Integer> getMonthlyOrderCount() throws Exception {
        return orderDAO.getMonthlyOrderCount();
    }
}

