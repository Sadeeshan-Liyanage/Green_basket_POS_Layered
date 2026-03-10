package edu.ijse.mvc.fx.grocessoryshopmanagementsystem.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Data
public class EmployeeDTO {

    private int emp_id;
    private String emp_Name;
    private String emp_Address;
    private Double emp_salary;

}