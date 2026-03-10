package edu.ijse.mvc.fx.grocessoryshopmanagementsystem.dao.custom;

import edu.ijse.mvc.fx.grocessoryshopmanagementsystem.dao.CrudDAO;
import edu.ijse.mvc.fx.grocessoryshopmanagementsystem.entity.Item;

import java.sql.SQLException;
import java.util.ArrayList;

public interface ItemDAO extends CrudDAO<Item> {

    int getTotalItemCount() throws Exception;
    int getLowStockCount() throws Exception;
    int getExpiredItemCount() throws Exception;
    ArrayList<Item> getLowStockItems() throws Exception;
}
