package edu.ijse.mvc.fx.grocessoryshopmanagementsystem.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Data
public class CustomerDTO {

    private int customerId;
    private String customerName;
    private int customerNumber;
    private String address;

}
