package ucr.proyecto.proyectogrupo1.controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.TableView;
import javafx.stage.Stage;
import ucr.proyecto.proyectogrupo1.HelloApplication;

import java.io.IOException;

public class MantenimientoProductosController {

    @FXML
    private TableView<?> tableView;


    @FXML
    void btnAgregarNuevoProducto(ActionEvent event) {
        FXMLLoader fxmlLoader = new FXMLLoader(HelloApplication.class.getResource("newProduct.fxml"));
        Scene scene = null;
        try {
            scene = new Scene(fxmlLoader.load());
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        Stage stage= new Stage();
        stage.setScene(scene);
        stage.setResizable(false);
        stage.show();
    }

}
