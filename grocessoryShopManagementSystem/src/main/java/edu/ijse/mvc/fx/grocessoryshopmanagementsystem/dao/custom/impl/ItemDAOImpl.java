package edu.ijse.mvc.fx.grocessoryshopmanagementsystem.dao.custom.impl;

import edu.ijse.mvc.fx.grocessoryshopmanagementsystem.dao.CRUDUtil;
import edu.ijse.mvc.fx.grocessoryshopmanagementsystem.dao.custom.ItemDAO;
import edu.ijse.mvc.fx.grocessoryshopmanagementsystem.entity.Item;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public class ItemDAOImpl implements ItemDAO {

    @Override
    public ArrayList<Item> getAll() throws Exception {
        ResultSet rst = CRUDUtil.execute("SELECT * FROM Item");
        ArrayList<Item> list = new ArrayList<>();
        while (rst.next()) {
            list.add(mapItem(rst));
        }
        return list;
    }

    @Override
    public boolean save(Item item) throws Exception {
        return CRUDUtil.execute(
                "INSERT INTO Item (itemName, category, unitPrice, quantity, expiryDate, supplier_Id) VALUES (?,?,?,?,?,?)",
                item.getItemName(), item.getCategory(), item.getUnitPrice(),
                item.getQuantity(), item.getExpiryDate(), item.getSupplier_Id()
        );
    }

    @Override
    public boolean update(Item item) throws Exception {
        return CRUDUtil.execute(
                "UPDATE Item SET itemName=?, category=?, unitPrice=?, quantity=?, expiryDate=?, supplier_Id=? WHERE itemId=?",
                item.getItemName(), item.getCategory(), item.getUnitPrice(),
                item.getQuantity(), item.getExpiryDate(), item.getSupplier_Id(), item.getItemId()
        );
    }

    @Override
    public boolean delete(String id) throws Exception {
        return CRUDUtil.execute("DELETE FROM Item WHERE itemId=?", Integer.parseInt(id));
    }

    @Override
    public Item search(String id) throws Exception {
        ResultSet rst = CRUDUtil.execute("SELECT * FROM Item WHERE itemId=?", Integer.parseInt(id));
        if (rst.next()) return mapItem(rst);
        return null;
    }

    @Override
    public int getTotalItemCount() throws Exception {
        ResultSet rs = CRUDUtil.execute("SELECT COUNT(*) FROM Item");
        return rs.next() ? rs.getInt(1) : 0;
    }

    @Override
    public int getLowStockCount() throws Exception {
        ResultSet rs = CRUDUtil.execute("SELECT COUNT(*) FROM Item WHERE quantity < 10");
        return rs.next() ? rs.getInt(1) : 0;
    }

    @Override
    public int getExpiredItemCount() throws Exception {
        ResultSet rs = CRUDUtil.execute("SELECT COUNT(*) FROM Item WHERE expiryDate <= CURDATE()");
        return rs.next() ? rs.getInt(1) : 0;
    }

    @Override
    public ArrayList<Item> getLowStockItems() throws Exception {
        ResultSet rs = CRUDUtil.execute(
                "SELECT * FROM Item WHERE quantity < 10 ORDER BY quantity ASC LIMIT 4"
        );
        ArrayList<Item> list = new ArrayList<>();
        while (rs.next()) list.add(mapItem(rs));
        return list;
    }


    private Item mapItem(ResultSet rst) throws Exception {
        return new Item(
                rst.getInt("itemId"),
                rst.getString("itemName"),
                rst.getString("category"),
                rst.getDouble("unitPrice"),
                rst.getInt("quantity"),
                rst.getString("expiryDate"),
                rst.getInt("supplier_Id")
        );
    }
}
