package edu.ijse.mvc.fx.grocessoryshopmanagementsystem.dao.custom.impl;

import edu.ijse.mvc.fx.grocessoryshopmanagementsystem.dao.custom.SupplierDAO;
import edu.ijse.mvc.fx.grocessoryshopmanagementsystem.entity.Supplier;
//import edu.ijse.mvc.fx.grocessoryshopmanagementsystem.util.CrudUtil;
import edu.ijse.mvc.fx.grocessoryshopmanagementsystem.dao.CRUDUtil;
import java.sql.ResultSet;
import java.util.ArrayList;

public class SupplierDAOImpl implements SupplierDAO {
    @Override
    public ArrayList<Supplier> getAll() throws Exception {
        ArrayList<Supplier> list = new ArrayList<>();
        ResultSet rst = CRUDUtil.executeQuery("SELECT * FROM Supplier");
        while (rst.next()) {
            list.add(mapSupplier(rst));
        }
        return list;
    }

    @Override
    public boolean save(Supplier s) throws Exception {
        if (s.getSupplierId() == 0) {
            return CRUDUtil.execute(
                    "INSERT INTO Supplier (supplierName, contactNumber, address) VALUES (?, ?, ?)",
                    s.getSupplierName(), s.getContactNumber(), s.getAddress()
            );
        } else {
            return CRUDUtil.execute(
                    "INSERT INTO Supplier (supplier_id, supplierName, contactNumber, address) VALUES (?, ?, ?, ?)",
                    s.getSupplierId(), s.getSupplierName(), s.getContactNumber(), s.getAddress()
            );
        }
    }

    @Override
    public boolean update(Supplier s) throws Exception {
        return CRUDUtil.execute(
                "UPDATE Supplier SET supplierName=?, contactNumber=?, address=? WHERE supplier_id=?",
                s.getSupplierName(), s.getContactNumber(), s.getAddress(), s.getSupplierId()
        );
    }

    @Override
    public boolean delete(String id) throws Exception {
        return CRUDUtil.execute("DELETE FROM Supplier WHERE supplier_id=?", Integer.parseInt(id));
    }

    @Override
    public Supplier search(String id) throws Exception {
        ResultSet rst = CRUDUtil.executeQuery("SELECT * FROM Supplier WHERE supplier_id=?", Integer.parseInt(id));
        if (rst.next()) return mapSupplier(rst);
        return null;
    }

    private Supplier mapSupplier(ResultSet rst) throws Exception {
        return new Supplier(
                rst.getInt("supplier_id"),
                rst.getString("supplierName"),
                rst.getString("contactNumber"),
                rst.getString("address")
        );
    }
}
