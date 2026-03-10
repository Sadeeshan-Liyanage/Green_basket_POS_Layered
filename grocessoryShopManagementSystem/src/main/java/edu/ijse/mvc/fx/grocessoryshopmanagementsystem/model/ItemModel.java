package edu.ijse.mvc.fx.grocessoryshopmanagementsystem.model;

import edu.ijse.mvc.fx.grocessoryshopmanagementsystem.db.DBConnection;
import edu.ijse.mvc.fx.grocessoryshopmanagementsystem.dto.ItemDTO;
import edu.ijse.mvc.fx.grocessoryshopmanagementsystem.util.CrudUtil;

import java.sql.*;
import java.util.ArrayList;
import java.util.HashMap;

public class ItemModel {

    public String saveItem(ItemDTO item) throws Exception {
        boolean isSaved = CrudUtil.execute(
                "INSERT INTO Item (itemName, category, unitPrice, quantity, expiryDate, supplier_Id) VALUES (?,?,?,?,?,?)",
                item.getItemName(), item.getCategory(), item.getUnitPrice(),
                item.getQuantity(), item.getExpiryDate(), item.getSupplier_Id());

        return isSaved ? "Item Saved Successfully!" : "Save Failed!";
    }

    public String updateItem(ItemDTO item) throws Exception {
        boolean isUpdated = CrudUtil.execute("UPDATE Item SET itemName=?, category=?, unitPrice=?, quantity=?, expiryDate=?, supplier_Id=? WHERE itemId=?",
                item.getItemName(), item.getCategory(), item.getUnitPrice(),
                item.getQuantity(), item.getExpiryDate(), item.getSupplier_Id(), item.getItemId()
        );

        return isUpdated ? "Item Updated Successfully!" : "Update Failed!";
    }

    public String deleteItem(int itemId) throws Exception {
        boolean isDeleted = CrudUtil.execute("DELETE FROM Item WHERE itemId=?",itemId);
        return isDeleted ? "Item Deleted!" : "Delete Failed!";
    }

    public ItemDTO getItem(int itemId) throws Exception {
        ResultSet rst = CrudUtil.executeQuery("SELECT * FROM Item WHERE itemId=?",itemId);
        if (rst.next()) {
            return new ItemDTO(rst.getInt(1), rst.getString(2), rst.getString(3), rst.getDouble(4), rst.getInt(5), rst.getString(6), rst.getInt(7));
        }
        return null;
    }

    public ArrayList<ItemDTO> getAllItems() throws Exception {
        ResultSet rst = CrudUtil.executeQuery(  "SELECT * FROM Item");
        ArrayList<ItemDTO> items = new ArrayList<>();
        while (rst.next()) {
            items.add(new ItemDTO(rst.getInt(1), rst.getString(2), rst.getString(3), rst.getDouble(4), rst.getInt(5), rst.getString(6), rst.getInt(7)));
        }
        return items;
    }

    public int getTotalItemCount() throws SQLException, ClassNotFoundException {
        ResultSet rs = CrudUtil.executeQuery("SELECT COUNT(*) FROM Item");

        if (rs.next()) {
            return rs.getInt(1);
        }
        return 0;
    }

    public int getLowStockCount() throws SQLException, ClassNotFoundException {
        ResultSet rs = CrudUtil.executeQuery("SELECT COUNT(*) FROM Item WHERE quantity < 10");

        if (rs.next()) {
            return rs.getInt(1);
        }
        return 0;
    }


    public int getExpiredItemCount() throws SQLException, ClassNotFoundException {
        ResultSet rs = CrudUtil.executeQuery("SELECT COUNT(*) FROM Item WHERE expiryDate <= CURDATE()");

        if (rs.next()) {
            return rs.getInt(1);
        }
        return 0;
    }

    public ArrayList<ItemDTO> getLowStockItems() throws SQLException, ClassNotFoundException {
        ArrayList<ItemDTO> lowStockList = new ArrayList<>();
        ResultSet rs = CrudUtil.executeQuery("SELECT * FROM Item WHERE quantity < 10 ORDER BY quantity ASC LIMIT 4");

        while (rs.next()) {
            ItemDTO item = new ItemDTO(
                    rs.getInt("itemId"),
                    rs.getString("itemName"),
                    rs.getString("category"),
                    rs.getDouble("unitPrice"),
                    rs.getInt("quantity"),
                    rs.getString("expiryDate"),
                    rs.getInt("supplier_Id")
            );
            lowStockList.add(item);
        }
        return lowStockList;
    }
}