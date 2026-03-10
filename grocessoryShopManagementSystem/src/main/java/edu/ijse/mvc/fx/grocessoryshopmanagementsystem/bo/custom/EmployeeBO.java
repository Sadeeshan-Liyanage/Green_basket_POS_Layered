package edu.ijse.mvc.fx.grocessoryshopmanagementsystem.bo.custom;

import edu.ijse.mvc.fx.grocessoryshopmanagementsystem.bo.SuperBO;
import edu.ijse.mvc.fx.grocessoryshopmanagementsystem.dto.EmployeeDTO;

import java.util.ArrayList;

public interface EmployeeBO extends SuperBO {

    String saveEmployee(EmployeeDTO dto) throws Exception;
    String updateEmployee(EmployeeDTO dto) throws Exception;
    String deleteEmployee(String id) throws Exception;
    EmployeeDTO getEmployee(int id) throws Exception;
    ArrayList<EmployeeDTO> getAllEmployees() throws Exception;
}
