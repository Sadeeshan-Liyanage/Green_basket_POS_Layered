module edu.ijse.mvc.fx.grocessoryshopmanagementsystem {
    requires javafx.controls;
    requires javafx.fxml;
    requires java.sql;
    requires static lombok;
    requires java.desktop;
    requires jasperreports;
    requires java.base;
    requires javafx.graphics;

    opens edu.ijse.mvc.fx.grocessoryshopmanagementsystem to javafx.fxml;
    opens edu.ijse.mvc.fx.grocessoryshopmanagementsystem.controller to javafx.fxml;
    opens edu.ijse.mvc.fx.grocessoryshopmanagementsystem.dto to javafx.base;
    opens edu.ijse.mvc.fx.grocessoryshopmanagementsystem.tm to javafx.base;
    opens edu.ijse.mvc.fx.grocessoryshopmanagementsystem.entity to javafx.base;

    exports edu.ijse.mvc.fx.grocessoryshopmanagementsystem;
    exports edu.ijse.mvc.fx.grocessoryshopmanagementsystem.controller; // optional
    exports edu.ijse.mvc.fx.grocessoryshopmanagementsystem.dto;
}
