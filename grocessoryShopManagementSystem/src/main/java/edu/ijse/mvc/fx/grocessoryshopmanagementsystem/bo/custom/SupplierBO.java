package edu.ijse.mvc.fx.grocessoryshopmanagementsystem.bo.custom;

import edu.ijse.mvc.fx.grocessoryshopmanagementsystem.bo.SuperBO;
import edu.ijse.mvc.fx.grocessoryshopmanagementsystem.dto.SupplierDTO;

import java.util.ArrayList;

public interface SupplierBO extends SuperBO {
    String addSupplier(SupplierDTO dto) throws Exception;
    String updateSupplier(SupplierDTO dto) throws Exception;
    String deleteSupplier(int id) throws Exception;
    SupplierDTO getSupplier(int id) throws Exception;
    ArrayList<SupplierDTO> getAllSuppliers() throws Exception;
    void printSupplierReport();
}
