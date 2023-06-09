package ucr.proyecto.proyectogrupo1.controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;

public class ReporteProveedorController
{
    @javafx.fxml.FXML
    private TableView tableView;
    @javafx.fxml.FXML
    private TableColumn idCl;
    @javafx.fxml.FXML
    private TableColumn nombreCl;
    @javafx.fxml.FXML
    private TableColumn productosCl;
    @javafx.fxml.FXML
    private TableColumn telefonoCl;
    @javafx.fxml.FXML
    private TableColumn emailCl;
    @javafx.fxml.FXML
    private TableColumn direccionCl;

    @javafx.fxml.FXML
    public void initialize() {
    }

    @FXML
    void actualizarOnAction(ActionEvent event) {

    }
    @FXML
    void generarReporteOnAction(ActionEvent event) {

    }
}