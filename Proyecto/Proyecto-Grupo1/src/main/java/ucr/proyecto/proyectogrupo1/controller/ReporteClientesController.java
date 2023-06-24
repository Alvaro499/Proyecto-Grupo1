package ucr.proyecto.proyectogrupo1.controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.TableView;
import ucr.proyecto.proyectogrupo1.TDA.AVL;
import ucr.proyecto.proyectogrupo1.domain.Binnacle;
import ucr.proyecto.proyectogrupo1.util.Utility;

import java.time.LocalDateTime;

public class ReporteClientesController {
    @FXML
    private TableView tableView;
    private AVL bitacora;
    LocalDateTime fecha;
    @FXML
    public void initialize() {

        /*bitacora = Utility.getBinnacle();
        fecha = LocalDateTime.now();
        bitacora.add(new Binnacle(String.valueOf(fecha.withNano(0)), Utility.getIDClient(),""));
        Utility.setBinnacle(bitacora);*/
    }

    @FXML
    void actualizarOnAction(ActionEvent event) {

    }
    @FXML
    void generarReporteOnAction(ActionEvent event) {

    }
}