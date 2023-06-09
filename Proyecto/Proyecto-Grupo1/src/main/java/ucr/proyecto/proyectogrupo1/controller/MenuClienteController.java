package ucr.proyecto.proyectogrupo1.controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TextField;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;
import ucr.proyecto.proyectogrupo1.HelloApplication;

import java.io.IOException;

public class MenuClienteController {

    @FXML
    private BorderPane bp;

    @FXML
    private ScrollPane scrollPane;

    @FXML
    private TextField txtBuscar;

    @FXML
    private TextField txtNombreUsuario;

    @FXML
    private TextField txtNombreUsuario1;

    @FXML
    void buscarOnAction(ActionEvent event) {

    }

    @FXML
    void carritoOnAction(ActionEvent event) {
        FXMLLoader fxmlLoader = new FXMLLoader(HelloApplication.class.getResource("clienteCarrito.fxml"));
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

    @FXML
    void reporteOnAction(ActionEvent event) {
        FXMLLoader fxmlLoader = new FXMLLoader(HelloApplication.class.getResource("reporteParaClientes.fxml"));
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

    @FXML
    void exitOnAction(ActionEvent event) {
        System.exit(0);
    }

}
