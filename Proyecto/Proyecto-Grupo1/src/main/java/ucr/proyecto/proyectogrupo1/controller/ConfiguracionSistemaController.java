package ucr.proyecto.proyectogrupo1.controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.TextField;
import javafx.scene.image.ImageView;
import ucr.proyecto.proyectogrupo1.util.Utility;

public class ConfiguracionSistemaController {

    @FXML
    private ImageView logoImagen;

    @FXML
    private TextField txtLogoEditar;

    @FXML
    private TextField txtnombreEditar;

    @FXML
    void confirmarCambiosOnAction(ActionEvent event) {
        if (txtnombreEditar.getText()!= "")
            Utility.setNombreSistema(txtnombreEditar.getText());
    }

    @FXML
    void editarLogoOnAction(ActionEvent event) {

    }

}
