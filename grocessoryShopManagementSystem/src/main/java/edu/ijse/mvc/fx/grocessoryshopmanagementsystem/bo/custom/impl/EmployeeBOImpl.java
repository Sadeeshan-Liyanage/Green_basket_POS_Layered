package edu.ijse.mvc.fx.grocessoryshopmanagementsystem.bo.custom.impl;

import edu.ijse.mvc.fx.grocessoryshopmanagementsystem.bo.custom.EmployeeBO;
import edu.ijse.mvc.fx.grocessoryshopmanagementsystem.dao.DAOFactory;
import edu.ijse.mvc.fx.grocessoryshopmanagementsystem.dao.custom.EmployeeDAO;
import edu.ijse.mvc.fx.grocessoryshopmanagementsystem.dto.EmployeeDTO;
import edu.ijse.mvc.fx.grocessoryshopmanagementsystem.entity.Employee;

import java.util.ArrayList;

public class EmployeeBOImpl implements EmployeeBO {
    EmployeeDAO employeeDAO = (EmployeeDAO) DAOFactory.getInstance().getDAO(DAOFactory.DAOType.EMPLOYEE);

    @Override
    public String saveEmployee(EmployeeDTO dto) throws Exception {
        Employee entity = new Employee(0, dto.getEmp_Name(), dto.getEmp_Address(), dto.getEmp_salary());
        return employeeDAO.save(entity) ? "Employee Saved Successfully!" : "Save Failed!";
    }

    @Override
    public String updateEmployee(EmployeeDTO dto) throws Exception {
        Employee entity = new Employee(dto.getEmp_id(), dto.getEmp_Name(), dto.getEmp_Address(), dto.getEmp_salary());
        return employeeDAO.update(entity) ? "Employee Updated Successfully!" : "Update Failed!";
    }

    @Override
    public String deleteEmployee(String id) throws Exception {
        return employeeDAO.delete(id) ? "Employee Deleted Successfully!" : "Delete Failed!";
    }

    @Override
    public EmployeeDTO getEmployee(int id) throws Exception {
        Employee e = employeeDAO.search(String.valueOf(id));
        if (e == null) return null;
        return new EmployeeDTO(e.getEmp_id(), e.getEmp_Name(), e.getEmp_Address(), e.getEmp_salary());
    }

    @Override
    public ArrayList<EmployeeDTO> getAllEmployees() throws Exception {
        ArrayList<EmployeeDTO> list = new ArrayList<>();
        for (Employee e : employeeDAO.getAll()) {
            list.add(new EmployeeDTO(e.getEmp_id(), e.getEmp_Name(), e.getEmp_Address(), e.getEmp_salary()));
        }
        return list;
    }
}
