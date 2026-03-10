package edu.ijse.mvc.fx.grocessoryshopmanagementsystem.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Data
public class OrderDTO {


    private int orderId;
    private String orderDate;
    private int customerId;
    private int userId;

}