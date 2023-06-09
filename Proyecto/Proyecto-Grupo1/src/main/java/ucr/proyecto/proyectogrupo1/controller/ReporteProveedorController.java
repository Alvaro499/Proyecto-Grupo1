package ucr.proyecto.proyectogrupo1.controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;

public class ReporteProveedorController
{
    @FXML
    private TableView tableView;
    @FXML
    private TableColumn idCl;
    @FXML
    private TableColumn nombreCl;
    @FXML
    private TableColumn productosCl;
    @FXML
    private TableColumn telefonoCl;
    @FXML
    private TableColumn emailCl;
    @FXML
    private TableColumn direccionCl;

    @FXML
    public void initialize() {
    }

    @FXML
    void actualizarOnAction(ActionEvent event) {

    }
    @FXML
    void generarReporteOnAction(ActionEvent event) {

    }
}