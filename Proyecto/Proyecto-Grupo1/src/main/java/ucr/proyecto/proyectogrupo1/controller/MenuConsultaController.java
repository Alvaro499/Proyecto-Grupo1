package ucr.proyecto.proyectogrupo1.controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.TextField;
import javafx.scene.layout.BorderPane;
import ucr.proyecto.proyectogrupo1.HelloApplication;

import java.io.IOException;

public class MenuConsultaController {

    @FXML
    private TextField txtNombreUsuario;

    @FXML
    private TextField txtNombreUsuario1;

    @FXML
    private BorderPane bp;

    private void loadPage(String page) {
        FXMLLoader fxmlLoader = new FXMLLoader(HelloApplication.class.getResource(page));
        try {
            this.bp.setCenter(fxmlLoader.load());
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    @FXML
    void reporteClientesOnAction(ActionEvent event) {
        loadPage("reporteClientes.fxml");
    }

    @FXML
    void reporteProductosOnAction(ActionEvent event) {
        loadPage("reporteProductos.fxml");
    }

    @FXML
    void reporteProveedoresOnAction(ActionEvent event) {
        loadPage("reporteProveedor.fxml");
    }

    @FXML
    void exitOnAction(ActionEvent actionEvent) {
        System.exit(0);
    }
}


