package edu.ijse.mvc.fx.grocessoryshopmanagementsystem.entity;

public class Employee {
    private int emp_id;
    private String emp_Name;
    private String emp_Address;
    private Double emp_salary;

    @Override
    public String toString() {
        return "Employee{" +
                "emp_id=" + emp_id +
                ", emp_Name='" + emp_Name + '\'' +
                ", emp_Address='" + emp_Address + '\'' +
                ", emp_salary=" + emp_salary +
                '}';
    }

    public int getEmp_id() {
        return emp_id;
    }

    public void setEmp_id(int emp_id) {
        this.emp_id = emp_id;
    }

    public String getEmp_Name() {
        return emp_Name;
    }

    public void setEmp_Name(String emp_Name) {
        this.emp_Name = emp_Name;
    }

    public String getEmp_Address() {
        return emp_Address;
    }

    public void setEmp_Address(String emp_Address) {
        this.emp_Address = emp_Address;
    }

    public Double getEmp_salary() {
        return emp_salary;
    }

    public void setEmp_salary(Double emp_salary) {
        this.emp_salary = emp_salary;
    }

    public Employee(int emp_id, String emp_Name, String emp_Address, Double emp_salary) {
        this.emp_id = emp_id;
        this.emp_Name = emp_Name;
        this.emp_Address = emp_Address;
        this.emp_salary = emp_salary;
    }
}
