package edu.ijse.mvc.fx.grocessoryshopmanagementsystem.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor

public class ItemDTO {
    private int itemId;
    private String itemName;
    private String category;
    private double unitPrice;
    private int quantity;
    private String expiryDate;
    private int supplier_Id;

    @Override
    public String toString() {
        return "ItemDTO{" +
                "itemId=" + itemId +
                ", itemName='" + itemName + '\'' +
                ", category='" + category + '\'' +
                ", unitPrice=" + unitPrice +
                ", quantity=" + quantity +
                ", expiryDate='" + expiryDate + '\'' +
                ", supplier_Id=" + supplier_Id +
                '}';
    }

    public int getItemId() {
        return itemId;
    }

    public void setItemId(int itemId) {
        this.itemId = itemId;
    }

    public String getItemName() {
        return itemName;
    }

    public void setItemName(String itemName) {
        this.itemName = itemName;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public double getUnitPrice() {
        return unitPrice;
    }

    public void setUnitPrice(double unitPrice) {
        this.unitPrice = unitPrice;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public String getExpiryDate() {
        return expiryDate;
    }

    public void setExpiryDate(String expiryDate) {
        this.expiryDate = expiryDate;
    }

    public int getSupplier_Id() {
        return supplier_Id;
    }

    public void setSupplier_Id(int supplier_Id) {
        this.supplier_Id = supplier_Id;
    }
}