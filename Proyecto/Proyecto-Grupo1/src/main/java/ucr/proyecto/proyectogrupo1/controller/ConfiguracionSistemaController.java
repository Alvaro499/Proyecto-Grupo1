package ucr.proyecto.proyectogrupo1.controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.TextField;
import javafx.scene.image.ImageView;
import ucr.proyecto.proyectogrupo1.TDA.AVL;
import ucr.proyecto.proyectogrupo1.domain.Binnacle;
import ucr.proyecto.proyectogrupo1.util.Utility;

import java.time.LocalDateTime;

public class ConfiguracionSistemaController {

    @FXML
    private ImageView logoImagen;

    @FXML
    private TextField txtLogoEditar;

    @FXML
    private TextField txtnombreEditar;

    private AVL bitacora;

    @FXML
    public void initialize(){

        bitacora = Utility.getBinnacle();

    }


    @FXML
    void confirmarCambiosOnAction(ActionEvent event) {
        LocalDateTime fecha = LocalDateTime.now();
        if (txtnombreEditar.getText()!= "") {
            Utility.setNombreSistema(txtnombreEditar.getText());
            bitacora.add(new Binnacle(String.valueOf(fecha.withNano(0)), Utility.getIDClient(),"Se realizaron cambios en configuración de sistema"));
            Utility.setBinnacle(bitacora);
        }
    }

}
