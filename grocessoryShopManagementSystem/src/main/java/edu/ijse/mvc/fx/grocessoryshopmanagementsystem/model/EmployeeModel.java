package edu.ijse.mvc.fx.grocessoryshopmanagementsystem.model;

import edu.ijse.mvc.fx.grocessoryshopmanagementsystem.db.DBConnection;
import edu.ijse.mvc.fx.grocessoryshopmanagementsystem.dto.EmployeeDTO;
import edu.ijse.mvc.fx.grocessoryshopmanagementsystem.util.CrudUtil;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class EmployeeModel {

    public String saveEmployee(EmployeeDTO employee) throws Exception {

        boolean isSaved = CrudUtil.execute(
                "INSERT INTO Employee (emp_Name, emp_Address, emp_salary) VALUES (?, ?, ?)",
        employee.getEmp_Name(),employee.getEmp_Address(),employee.getEmp_salary()
        );

        return isSaved? "Employee Saved Successfully!" : "Save Failed!";
    }

    public String updateEmployee(EmployeeDTO employee) throws Exception {

        boolean isUpdated = CrudUtil.execute("UPDATE Employee SET emp_Name=?, emp_Address=?, emp_salary=? WHERE emp_id=?",
        employee.getEmp_Name(),employee.getEmp_Address(),employee.getEmp_salary(),employee.getEmp_id()
        );

        return isUpdated ? "Employee Updated Successfully!" : "Update Failed!";
    }

    public String deleteEmployee(int employeeId) throws Exception {
        boolean isDeleted = CrudUtil.execute("DELETE FROM Employee WHERE emp_id=?",
        employeeId);

        return isDeleted ? "Employee Deleted Successfully!" : "Delete Failed!";
    }

    public EmployeeDTO getEmployee(int employeeId) throws Exception {
        ResultSet rst = CrudUtil.executeQuery("SELECT * FROM Employee WHERE emp_id=?",employeeId);

        if (rst.next()) {
            return new EmployeeDTO(
                    rst.getInt(1), rst.getString(2), rst.getString(3), rst.getDouble(4));
        }
        return null;
    }

    public List<EmployeeDTO> getAllEmployees() throws Exception {
        List<EmployeeDTO> list = new ArrayList<>();

        ResultSet rst = CrudUtil.executeQuery("SELECT * FROM Employee");

        while (rst.next()) {
            list.add(new EmployeeDTO(
                    rst.getInt(1), rst.getString(2), rst.getString(3), rst.getDouble(4)));
        }
        return list;
    }
}