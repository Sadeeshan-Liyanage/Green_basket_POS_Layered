package edu.ijse.mvc.fx.grocessoryshopmanagementsystem.dao.custom.impl;

import edu.ijse.mvc.fx.grocessoryshopmanagementsystem.dao.CRUDUtil;
import edu.ijse.mvc.fx.grocessoryshopmanagementsystem.dao.custom.EmployeeDAO;
import edu.ijse.mvc.fx.grocessoryshopmanagementsystem.entity.Employee;

import java.sql.ResultSet;
import java.util.ArrayList;

public class EmployeeDAOImpl implements EmployeeDAO {

    @Override
    public ArrayList<Employee> getAll() throws Exception {
        ResultSet rst = CRUDUtil.execute("SELECT * FROM Employee");
        ArrayList<Employee> list = new ArrayList<>();
        while (rst.next()) {
            list.add(new Employee(
                    rst.getInt("emp_id"),
                    rst.getString("emp_Name"),
                    rst.getString("emp_Address"),
                    rst.getDouble("emp_salary")
            ));
        }
        return list;
    }

    @Override
    public boolean save(Employee e) throws Exception {
        return CRUDUtil.execute(
                "INSERT INTO Employee (emp_Name, emp_Address, emp_salary) VALUES (?, ?, ?)",
                e.getEmp_Name(), e.getEmp_Address(), e.getEmp_salary()
        );
    }

    @Override
    public boolean update(Employee e) throws Exception {
        return CRUDUtil.execute(
                "UPDATE Employee SET emp_Name=?, emp_Address=?, emp_salary=? WHERE emp_id=?",
                e.getEmp_Name(), e.getEmp_Address(), e.getEmp_salary(), e.getEmp_id()
        );
    }

    @Override
    public boolean delete(String id) throws Exception {
        return CRUDUtil.execute(
                "DELETE FROM Employee WHERE emp_id=?",
                Integer.parseInt(id)
        );
    }

    @Override
    public Employee search(String id) throws Exception {
        ResultSet rst = CRUDUtil.execute(
                "SELECT * FROM Employee WHERE emp_id=?",
                Integer.parseInt(id)
        );
        if (rst.next()) {
            return new Employee(
                    rst.getInt("emp_id"),
                    rst.getString("emp_Name"),
                    rst.getString("emp_Address"),
                    rst.getDouble("emp_salary")
            );
        }
        return null;
    }
}
