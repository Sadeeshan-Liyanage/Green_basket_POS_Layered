package edu.ijse.mvc.fx.grocessoryshopmanagementsystem.dao.custom.impl;

import edu.ijse.mvc.fx.grocessoryshopmanagementsystem.dao.CRUDUtil;
import edu.ijse.mvc.fx.grocessoryshopmanagementsystem.dao.custom.CustomerDAO;
import edu.ijse.mvc.fx.grocessoryshopmanagementsystem.entity.Customer;

import java.sql.ResultSet;
import java.util.ArrayList;

public class CustomerDAOImpl implements CustomerDAO {


    @Override
    public ArrayList<Customer> getAll() throws Exception {
        ResultSet rst = CRUDUtil.execute("SELECT * FROM Customer");
        ArrayList<Customer> list = new ArrayList<>();
        while (rst.next()) {
            list.add(new Customer(
                    rst.getInt("customer_id"),
                    rst.getString("CustomerName"),
                    rst.getInt("CustomerNumber"),
                    rst.getString("address")
            ));
        }
        return list;
    }

    @Override
    public boolean save(Customer c) throws Exception {
        return CRUDUtil.execute(
                "INSERT INTO Customer (CustomerName, CustomerNumber, address) VALUES (?,?,?)",
                c.getCustomerName(), c.getCustomerNumber(), c.getAddress()
        );
    }

    @Override
    public boolean update(Customer c) throws Exception {
        return CRUDUtil.execute(
                "UPDATE Customer SET CustomerName=?, CustomerNumber=?, address=? WHERE customer_id=?",
                c.getCustomerName(), c.getCustomerNumber(), c.getAddress(), c.getCustomerId()
        );
    }

    @Override
    public boolean delete(String id) throws Exception {
        return CRUDUtil.execute("DELETE FROM Customer WHERE customer_id=?", Integer.parseInt(id));
    }

    @Override
    public Customer search(String id) throws Exception {
        ResultSet rst = CRUDUtil.execute("SELECT * FROM Customer WHERE customer_id=?", Integer.parseInt(id));
        if (rst.next()) {
            return new Customer(
                    rst.getInt("customer_id"),
                    rst.getString("CustomerName"),
                    rst.getInt("CustomerNumber"),
                    rst.getString("address")
            );
        }
        return null;
    }

}

