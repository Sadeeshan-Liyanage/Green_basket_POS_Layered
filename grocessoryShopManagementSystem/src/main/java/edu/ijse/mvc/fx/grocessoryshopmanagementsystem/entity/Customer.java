package edu.ijse.mvc.fx.grocessoryshopmanagementsystem.entity;

public class Customer {
    private int customerId;
    private String customerName;
    private int customerNumber;
    private String address;

    @Override
    public String toString() {
        return "Customer{" +
                "customerId=" + customerId +
                ", customerName='" + customerName + '\'' +
                ", customerNumber=" + customerNumber +
                ", address='" + address + '\'' +
                '}';
    }

    public int getCustomerId() {
        return customerId;
    }

    public void setCustomerId(int customerId) {
        this.customerId = customerId;
    }

    public String getCustomerName() {
        return customerName;
    }

    public void setCustomerName(String customerName) {
        this.customerName = customerName;
    }

    public int getCustomerNumber() {
        return customerNumber;
    }

    public void setCustomerNumber(int customerNumber) {
        this.customerNumber = customerNumber;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public Customer(int customerId, String customerName, int customerNumber, String address) {
        this.customerId = customerId;
        this.customerName = customerName;
        this.customerNumber = customerNumber;
        this.address = address;
    }
}
