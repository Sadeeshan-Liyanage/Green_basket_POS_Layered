package edu.ijse.mvc.fx.grocessoryshopmanagementsystem.bo;

import edu.ijse.mvc.fx.grocessoryshopmanagementsystem.bo.custom.impl.*;
//import edu.ijse.mvc.fx.grocessoryshopmanagementsystem.bo.impl.*;

public class BOFactory {
    private static BOFactory instance;
    private BOFactory() {}

    public static BOFactory getInstance() {
        return instance == null ? instance = new BOFactory() : instance;
    }

    public enum BOType {
        CUSTOMER, ITEM, EMPLOYEE, SUPPLIER, ORDER, REPORT, USER
    }

    public SuperBO getBO(BOType type) {
        switch (type) {
            case CUSTOMER: return new CustomerBOImpl();
            case ITEM:     return new ItemBOImpl();
            case EMPLOYEE: return new EmployeeBOImpl();
            case SUPPLIER: return new SupplierBOImpl();
            case ORDER:    return new OrderBOImpl();
            case REPORT:    return new ReportBOImpl();
            case USER:     return new UserBOImpl();
            default:       return null;
        }
    }
}