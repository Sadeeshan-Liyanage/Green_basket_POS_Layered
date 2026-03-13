package edu.ijse.mvc.fx.grocessoryshopmanagementsystem.controller;

import edu.ijse.mvc.fx.grocessoryshopmanagementsystem.App;
import edu.ijse.mvc.fx.grocessoryshopmanagementsystem.bo.BOFactory;
import edu.ijse.mvc.fx.grocessoryshopmanagementsystem.bo.custom.OrderBO;
import edu.ijse.mvc.fx.grocessoryshopmanagementsystem.dto.OrderDTO;
import edu.ijse.mvc.fx.grocessoryshopmanagementsystem.entity.OrderDetail;

import edu.ijse.mvc.fx.grocessoryshopmanagementsystem.tm.ItemTM;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import java.io.IOException;
import java.sql.SQLException;
import java.time.LocalDate;

public class OrderViewController {

    private final OrderBO orderBO = (OrderBO) BOFactory.getInstance().getBO(BOFactory.BOType.ORDER);
    private final ObservableList<ItemTM> cartList = FXCollections.observableArrayList();

    @FXML private Button btnAddToOrder;
    @FXML private Button btnBack;
    @FXML private Button btnRemove;


    @FXML private TableColumn<ItemTM, Integer> colItemID;
    @FXML private TableColumn<ItemTM, String> colItemName;
    @FXML private TableColumn<ItemTM, Integer> colQuantity;
    @FXML private TableColumn<ItemTM, Double> colSubTotal;
    @FXML private TableColumn<ItemTM, Double> colUnitPrice;

    @FXML private DatePicker dpOrderDate;
    @FXML private TableView<ItemTM> tblOrderCart;

    @FXML private TextField txtCustomerID;
    @FXML private TextField txtEmployeeID;
    @FXML private TextField txtItemID;
    @FXML private TextField txtOrderID;
    @FXML private TextField txtQuantity;
    @FXML private TextField txtSearchCustomerName;
    @FXML private Label lblNetTotal;

    @FXML
    public void initialize() {

        colItemID.setCellValueFactory(new PropertyValueFactory<>("itemId"));
        colItemName.setCellValueFactory(new PropertyValueFactory<>("itemName"));
        colUnitPrice.setCellValueFactory(new PropertyValueFactory<>("unitPrice"));
        colQuantity.setCellValueFactory(new PropertyValueFactory<>("quantity"));
        colSubTotal.setCellValueFactory(new PropertyValueFactory<>("subTotal"));

        tblOrderCart.setItems(cartList);

        dpOrderDate.setValue(LocalDate.now());


        setNextOrderId();
        txtOrderID.setEditable(false);
    }

    private void setNextOrderId() {
        try {

            txtOrderID.setText(orderBO.getNextOrderId());
        } catch (Exception e) {
            System.err.println("Order ID error: " + e.getMessage());
        }
    }

    @FXML
    void handleSearchCustomer(KeyEvent event) {
        if (event.getCode() == KeyCode.ENTER) {
            try {
                String name = txtSearchCustomerName.getText().trim();
                if (name.isEmpty()) return;

                String customerId = orderBO.getCustomerIdByName(name);
                if (customerId != null) {
                    txtCustomerID.setText(customerId);
                    txtItemID.requestFocus();
                } else {
                    new Alert(Alert.AlertType.WARNING, "Customer not found!").show();
                    txtCustomerID.clear();
                }
            } catch (Exception e) {
                new Alert(Alert.AlertType.ERROR, "Search Error: " + e.getMessage()).show();
            }
        }
    }

    @FXML
    void handleSearchItem(KeyEvent event) {
        if (event.getCode() == KeyCode.ENTER) {
            try {
                String id = txtItemID.getText().trim();
                String qtyStr = txtQuantity.getText().trim();

                if (id.isEmpty() || qtyStr.isEmpty()) {
                    new Alert(Alert.AlertType.WARNING, "Please enter both Item ID and Quantity!").show();
                    return;
                }

                int qty = Integer.parseInt(qtyStr);
                String[] details = orderBO.getItemDetails(id);

                if (details != null) {
                    String name = details[0];
                    double price = Double.parseDouble(details[1]);
                    double total = price * qty;

                    boolean isExists = false;
                    for (ItemTM tm : cartList) {
                        if (tm.getItemId() == Integer.parseInt(id)) {
                            tm.setQuantity(tm.getQuantity() + qty);
                            tm.setSubTotal(tm.getUnitPrice() * tm.getQuantity());
                            isExists = true;
                            break;
                        }
                    }

                    if (!isExists) {

                        cartList.add(new ItemTM(Integer.parseInt(id), name, price, qty, total));
                    }

                    tblOrderCart.refresh();
                    calculateNetTotal();
                    clearItemFields();

                } else {
                    new Alert(Alert.AlertType.WARNING, "Item Not Found!").show();
                }
            } catch (NumberFormatException e) {
                new Alert(Alert.AlertType.ERROR, "Invalid Quantity! Please enter a number.").show();
            } catch (Exception e) {
                e.printStackTrace();
                new Alert(Alert.AlertType.ERROR, "System Error: " + e.getMessage()).show();
            }
        }
    }

    private void clearItemFields() {
        txtItemID.clear();
        txtQuantity.clear();
        txtItemID.requestFocus();
    }


    @FXML
    void handleAddToOrder(ActionEvent event) {
        try {

            int customerId = Integer.parseInt(txtCustomerID.getText());
            int userId = Integer.parseInt(txtEmployeeID.getText());
            String date = dpOrderDate.getValue().toString();

            if (cartList.isEmpty()) {
                new Alert(Alert.AlertType.WARNING, "Cart is empty!").show();
                return;
            }

            ObservableList<OrderDetail> detailList = FXCollections.observableArrayList();
            for (ItemTM tm : cartList) {
                detailList.add(new OrderDetail(0, tm.getItemId(), tm.getQuantity(), tm.getUnitPrice()));
            }

            OrderDTO orderDTO = new OrderDTO(0, date, customerId, userId);
            boolean isSaved = orderBO.placeOrder(orderDTO, detailList);

            if (isSaved) {
                new Alert(Alert.AlertType.INFORMATION, "Order Saved Successfully!").show();
                clearAll();
            } else {
                new Alert(Alert.AlertType.ERROR, "Order Failed!").show();
            }

        } catch (NumberFormatException e) {
            new Alert(Alert.AlertType.ERROR, "Please enter valid Numbers for IDs!").show();
        } catch (SQLException | ClassNotFoundException e) {
            e.printStackTrace();
            new Alert(Alert.AlertType.ERROR, "System Error: " + e.getMessage()).show();
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }


    private void clearAll() {
        cartList.clear();
        txtCustomerID.clear();
        txtSearchCustomerName.clear();
        lblNetTotal.setText("Net Total: 0.00");
        setNextOrderId();
    }

    private void calculateNetTotal() {
        double netTotal = 0;
        for (ItemTM tm : cartList) {
            netTotal += tm.getSubTotal();
        }

        lblNetTotal.setText(String.format("%.2f", netTotal));
        System.out.println("Net Total: " + netTotal);
    }

    @FXML
    void handleRemoveItem(ActionEvent event) {

        ItemTM selectedItem = tblOrderCart.getSelectionModel().getSelectedItem();

        if (selectedItem != null) {

            Alert alert = new Alert(Alert.AlertType.CONFIRMATION,
                    "Are you sure you want to remove this item?", ButtonType.YES, ButtonType.NO);

            alert.showAndWait().ifPresent(response -> {
                if (response == ButtonType.YES) {

                    cartList.remove(selectedItem);


                    tblOrderCart.refresh();
                    calculateNetTotal();

                    new Alert(Alert.AlertType.INFORMATION, "Item removed!").show();
                }
            });
        } else {

            new Alert(Alert.AlertType.WARNING, "Please select an item from the table to remove!").show();
        }
    }

    @FXML
    void handleBack(ActionEvent event) {
        try {
            App.setRoot("Dashboard");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}