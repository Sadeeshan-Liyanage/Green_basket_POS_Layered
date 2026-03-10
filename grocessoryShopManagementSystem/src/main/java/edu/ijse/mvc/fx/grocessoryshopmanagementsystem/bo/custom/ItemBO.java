package edu.ijse.mvc.fx.grocessoryshopmanagementsystem.bo.custom;

import edu.ijse.mvc.fx.grocessoryshopmanagementsystem.bo.SuperBO;
import edu.ijse.mvc.fx.grocessoryshopmanagementsystem.dto.ItemDTO;

import java.sql.SQLException;
import java.util.ArrayList;

public interface ItemBO extends SuperBO {

    String saveItem(ItemDTO dto) throws Exception;
    String updateItem(ItemDTO dto) throws Exception;
    String deleteItem(String id) throws Exception;
    ItemDTO getItem(int id) throws Exception;
    ArrayList<ItemDTO> getAllItems() throws Exception;


    int getTotalItemCount() throws Exception;
    int getLowStockCount() throws Exception;
    int getExpiredItemCount() throws Exception;
    ArrayList<ItemDTO> getLowStockItems() throws Exception;
}
