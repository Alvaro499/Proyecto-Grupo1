package ucr.proyecto.proyectogrupo1.controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.layout.BorderPane;
import ucr.proyecto.proyectogrupo1.HelloApplication;

import java.io.IOException;

public class HelloController {
    private BorderPane bp;
    @FXML
    private TextField txtNombreUsuario;
    @FXML
    private PasswordField txtContraseña;

    private void loadPage(String page) {
        FXMLLoader fxmlLoader = new FXMLLoader(HelloApplication.class.getResource(page));
        try {
            this.bp.setCenter(fxmlLoader.load());
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    @FXML
    void btnEntrar(ActionEvent event) {
        loadPage("plantilla.fxml");
    }
}