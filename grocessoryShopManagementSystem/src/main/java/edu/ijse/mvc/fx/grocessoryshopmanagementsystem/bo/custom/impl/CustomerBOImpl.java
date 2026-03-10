package edu.ijse.mvc.fx.grocessoryshopmanagementsystem.bo.custom.impl;

import edu.ijse.mvc.fx.grocessoryshopmanagementsystem.bo.custom.CustomerBO;
import edu.ijse.mvc.fx.grocessoryshopmanagementsystem.dao.DAOFactory;
import edu.ijse.mvc.fx.grocessoryshopmanagementsystem.dao.custom.CustomerDAO;
import edu.ijse.mvc.fx.grocessoryshopmanagementsystem.db.DBConnection;
import edu.ijse.mvc.fx.grocessoryshopmanagementsystem.dto.CustomerDTO;
import edu.ijse.mvc.fx.grocessoryshopmanagementsystem.entity.Customer;
import net.sf.jasperreports.engine.JasperCompileManager;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.engine.JasperReport;
import net.sf.jasperreports.view.JasperViewer;

import java.io.InputStream;
import java.sql.Connection;
import java.util.ArrayList;

public class CustomerBOImpl implements CustomerBO {

    CustomerDAO customerDAO = (CustomerDAO) DAOFactory.getInstance().getDAO(DAOFactory.DAOType.CUSTOMER);

    @Override
    public String addCustomer(CustomerDTO dto) throws Exception {

        Customer entity = new Customer(0, dto.getCustomerName(), dto.getCustomerNumber(), dto.getAddress());
        return customerDAO.save(entity) ? "Customer Saved!" : "Save Failed!";
    }

    @Override
    public String updateCustomer(CustomerDTO dto) throws Exception {
        Customer entity = new Customer(dto.getCustomerId(), dto.getCustomerName(), dto.getCustomerNumber(), dto.getAddress());
        return customerDAO.update(entity) ? "Updated!" : "Update Failed!";
    }

    @Override
    public String deleteCustomer(String id) throws Exception {
        return customerDAO.delete(id) ? "Deleted!" : "Delete Failed!";
    }

    @Override
    public CustomerDTO searchCustomer(int id) throws Exception {
        Customer c = customerDAO.search(String.valueOf(id));
        if (c == null) return null;
        return new CustomerDTO(c.getCustomerId(), c.getCustomerName(), c.getCustomerNumber(), c.getAddress());
    }

    @Override
    public ArrayList<CustomerDTO> getAllCustomers() throws Exception {
        ArrayList<CustomerDTO> dtoList = new ArrayList<>();
        for (Customer c : customerDAO.getAll()) {
            dtoList.add(new CustomerDTO(c.getCustomerId(), c.getCustomerName(), c.getCustomerNumber(), c.getAddress()));
        }
        return dtoList;
    }

    @Override
    public void printCustomerReports() {
        try {
            Connection conn = DBConnection.getInstance().getConnection();

            InputStream inputStream = getClass().getResourceAsStream(
                    "/edu/ijse/mvc/fx/grocessoryshopmanagementsystem/Report/allCustomer.jrxml"
            );

            if (inputStream == null) {
                System.err.println("Report file not found!");
                return;
            }

            JasperReport jasperReport = JasperCompileManager.compileReport(inputStream);
            JasperPrint jasperPrint = JasperFillManager.fillReport(jasperReport, null, conn);

            JasperViewer viewer = new JasperViewer(jasperPrint, false);
            viewer.setTitle("All Customer Report");
            viewer.setVisible(true);

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
