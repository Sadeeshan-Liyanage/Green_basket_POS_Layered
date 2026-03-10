package edu.ijse.mvc.fx.grocessoryshopmanagementsystem.entity;

public class Order {
    private int orderId;
    private String orderDate;
    private int customerId;
    private int userId;

    @Override
    public String toString() {
        return "Order{" +
                "orderId=" + orderId +
                ", orderDate='" + orderDate + '\'' +
                ", customerId=" + customerId +
                ", userId=" + userId +
                '}';
    }

    public int getOrderId() {
        return orderId;
    }

    public void setOrderId(int orderId) {
        this.orderId = orderId;
    }

    public String getOrderDate() {
        return orderDate;
    }

    public void setOrderDate(String orderDate) {
        this.orderDate = orderDate;
    }

    public int getCustomerId() {
        return customerId;
    }

    public void setCustomerId(int customerId) {
        this.customerId = customerId;
    }

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public Order(int orderId, String orderDate, int customerId, int userId) {
        this.orderId = orderId;
        this.orderDate = orderDate;
        this.customerId = customerId;
        this.userId = userId;
    }
}
