package edu.ijse.mvc.fx.grocessoryshopmanagementsystem;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;

public class App extends Application {

    private static Scene scene;

    @Override
    public void start(Stage stage) throws IOException {
        Parent root = loadFXML("login");
        //Parent root = loadFXML("Dashboard");
        scene = new Scene(root, 1150, 783);

        stage.setMaximized(true);

       // stage.setResizable(false);
        stage.setScene(scene);
        stage.show();
    }

    public static void setRoot(String fxml) throws IOException {
        if (scene != null) {
            scene.setRoot(loadFXML(fxml));
        } else {
            throw new IllegalStateException("Scene has not been initialized. Ensure setRoot is called after start() finishes.");
        }
    }

    private static Parent loadFXML(String fxml) throws IOException {
        String fullPath = "/edu/ijse/mvc/fx/grocessoryshopmanagementsystem/" + fxml + ".fxml";
        FXMLLoader fxmlLoader = new FXMLLoader(App.class.getResource(fullPath));
        return fxmlLoader.load();
    }

    public static void main(String[] args) {
        launch();
    }
}