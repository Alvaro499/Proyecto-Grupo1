package ucr.proyecto.proyectogrupo1.controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.stage.Stage;
import ucr.proyecto.proyectogrupo1.HelloApplication;

import java.io.IOException;

public class MantenimientoProveedorController {

    @FXML
    private TableColumn<?, ?> direccionCl;

    @FXML
    private TableColumn<?, ?> emailCl;

    @FXML
    private TableColumn<?, ?> idCl;

    @FXML
    private TableColumn<?, ?> nombreCl;

    @FXML
    private TableColumn<?, ?> productosCl;

    @FXML
    private TableView<?> tableView;

    @FXML
    private TableColumn<?, ?> telefonoCl;

    @FXML
    void btnAgregarNuevoProveedor(ActionEvent event) {
        FXMLLoader fxmlLoader = new FXMLLoader(HelloApplication.class.getResource("newProveedor.fxml"));
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
