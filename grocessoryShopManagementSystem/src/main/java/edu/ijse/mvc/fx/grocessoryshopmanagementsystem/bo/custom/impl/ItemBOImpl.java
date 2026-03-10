package edu.ijse.mvc.fx.grocessoryshopmanagementsystem.bo.custom.impl;

import edu.ijse.mvc.fx.grocessoryshopmanagementsystem.bo.custom.ItemBO;
import edu.ijse.mvc.fx.grocessoryshopmanagementsystem.dao.DAOFactory;
import edu.ijse.mvc.fx.grocessoryshopmanagementsystem.dao.custom.ItemDAO;
import edu.ijse.mvc.fx.grocessoryshopmanagementsystem.dto.ItemDTO;
import edu.ijse.mvc.fx.grocessoryshopmanagementsystem.entity.Item;

import java.sql.SQLException;
import java.util.ArrayList;

public class ItemBOImpl implements ItemBO {

    ItemDAO itemDAO = (ItemDAO) DAOFactory.getInstance().getDAO(DAOFactory.DAOType.ITEM);

    private ItemDTO toDTO(Item item) {
        return new ItemDTO(
                item.getItemId(), item.getItemName(), item.getCategory(),
                item.getUnitPrice(), item.getQuantity(),
                item.getExpiryDate(), item.getSupplier_Id()
        );
    }


    private Item toEntity(ItemDTO dto) {
        return new Item(
                dto.getItemId(), dto.getItemName(), dto.getCategory(),
                dto.getUnitPrice(), dto.getQuantity(),
                dto.getExpiryDate(), dto.getSupplier_Id()
        );
    }

    @Override
    public String saveItem(ItemDTO dto) throws Exception {
        return itemDAO.save(toEntity(dto)) ? "Item Saved Successfully!" : "Save Failed!";
    }

    @Override
    public String updateItem(ItemDTO dto) throws Exception {
        return itemDAO.update(toEntity(dto)) ? "Item Updated Successfully!" : "Update Failed!";
    }

    @Override
    public String deleteItem(String id) throws Exception {
        return itemDAO.delete(id) ? "Item Deleted!" : "Delete Failed!";
    }

    @Override
    public ItemDTO getItem(int id) throws Exception {
        Item item = itemDAO.search(String.valueOf(id));
        return item != null ? toDTO(item) : null;
    }

    @Override
    public ArrayList<ItemDTO> getAllItems() throws Exception {
        ArrayList<ItemDTO> list = new ArrayList<>();
        for (Item item : itemDAO.getAll()) {
            list.add(toDTO(item));
        }
        return list;
    }

    @Override
    public int getTotalItemCount() throws Exception {
        return itemDAO.getTotalItemCount();
    }

    @Override
    public int getLowStockCount() throws Exception {
        return itemDAO.getLowStockCount();
    }

    @Override
    public int getExpiredItemCount() throws Exception {
        return itemDAO.getExpiredItemCount();
    }

    @Override
    public ArrayList<ItemDTO> getLowStockItems() throws Exception {
        ArrayList<ItemDTO> list = new ArrayList<>();
        for (Item item : itemDAO.getLowStockItems()) {
            list.add(toDTO(item));
        }
        return list;
    }

}
