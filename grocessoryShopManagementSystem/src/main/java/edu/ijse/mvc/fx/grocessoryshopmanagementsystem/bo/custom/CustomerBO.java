package edu.ijse.mvc.fx.grocessoryshopmanagementsystem.bo.custom;

import edu.ijse.mvc.fx.grocessoryshopmanagementsystem.bo.SuperBO;
import edu.ijse.mvc.fx.grocessoryshopmanagementsystem.dto.CustomerDTO;

import java.util.ArrayList;

public interface CustomerBO extends SuperBO {
    String addCustomer(CustomerDTO dto) throws Exception;
    String updateCustomer(CustomerDTO dto) throws Exception;
    String deleteCustomer(String id) throws Exception;
    CustomerDTO searchCustomer(int id) throws Exception;
    ArrayList<CustomerDTO> getAllCustomers() throws Exception;
    void printCustomerReports();
}
