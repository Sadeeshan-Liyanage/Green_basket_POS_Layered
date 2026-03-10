package edu.ijse.mvc.fx.grocessoryshopmanagementsystem.model;

import edu.ijse.mvc.fx.grocessoryshopmanagementsystem.db.DBConnection;
import edu.ijse.mvc.fx.grocessoryshopmanagementsystem.dto.CustomerDTO;


import edu.ijse.mvc.fx.grocessoryshopmanagementsystem.util.CrudUtil;
import net.sf.jasperreports.engine.*;
import net.sf.jasperreports.view.JasperViewer;

import java.io.InputStream;
import java.sql.*;
import java.util.ArrayList;

public class CustomerModel {

    public String addCustomer(CustomerDTO dto) throws Exception {

        boolean isSaved = CrudUtil.execute("INSERT INTO Customer (CustomerName, CustomerNumber, address) VALUES (?, ?, ?)",
        dto.getCustomerName(), dto.getCustomerNumber(), dto.getAddress()
        );
        return isSaved ? "Customer Saved!" : "Save Failed!";
    }

    public ArrayList<CustomerDTO> getAllCustomer() throws Exception {
        ArrayList<CustomerDTO> list = new ArrayList<>();
        ResultSet rst = CrudUtil.executeQuery("SELECT * FROM Customer");
        while (rst.next()) {
            list.add(new CustomerDTO(rst.getInt(1), rst.getString(2), rst.getInt(3), rst.getString(4)));
        }
        return list;
    }

    public CustomerDTO searchCustomer(int id) throws Exception {
        ResultSet rst = CrudUtil.executeQuery("SELECT * FROM Customer WHERE customer_id=?");

        if (rst.next()) {
            return new CustomerDTO(rst.getInt(1), rst.getString(2), rst.getInt(3), rst.getString(4));
        }
            return null;
    }

    public String updateCustomer(CustomerDTO dto) throws Exception {
        boolean isUpdated = CrudUtil.execute("UPDATE Customer SET CustomerName=?, CustomerNumber=?, address=? WHERE customer_id=?",
        dto.getCustomerName(),dto.getCustomerNumber(),dto.getAddress(),dto.getCustomerId()
        );
        return isUpdated ? "Updated!" : "Update Failed!";
    }

    public String deleteCustomer(String id) throws Exception {
        boolean isDeleted = CrudUtil.execute("DELETE FROM Customer WHERE customer_id=?", Integer.parseInt(id));
        return isDeleted ? "Deleted!" : "Delete Failed!";
    }

    public void printCustomerReports() {
        try {
            Connection conn = DBConnection.getInstance().getConnection();

            InputStream inputStream = getClass().getResourceAsStream("/edu/ijse/mvc/fx/grocessoryshopmanagementsystem/report/allCustomer.jrxml");

            if (inputStream == null) {
                System.err.println("Report file not found!");
                return;
            }


            JasperReport jr = JasperCompileManager.compileReport(inputStream);
            JasperPrint jp = JasperFillManager.fillReport(jr, null, conn);


            JasperViewer viewer = new JasperViewer(jp, false);
            viewer.setTitle("All Customer Report");
            viewer.setVisible(true);

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}