package ucr.proyecto.proyectogrupo1.controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextField;
import javafx.scene.image.ImageView;
import ucr.proyecto.proyectogrupo1.PDF.PDF;
import ucr.proyecto.proyectogrupo1.TDA.AVL;
import ucr.proyecto.proyectogrupo1.TDA.ListException;
import ucr.proyecto.proyectogrupo1.TDA.SinglyLinkedList;
import ucr.proyecto.proyectogrupo1.domain.Binnacle;
import ucr.proyecto.proyectogrupo1.util.FXUtility;
import ucr.proyecto.proyectogrupo1.util.Utility;

import java.io.IOException;
import java.time.LocalDateTime;
import java.util.ArrayList;

public class ConfiguracionSistemaController {

    @FXML
    private ComboBox<String> nombreSistemaCbx;

    @FXML
    private ComboBox<String> choiceBoxPath;
    private AVL bitacora;
    private SinglyLinkedList nombres;
    private SinglyLinkedList filesPath;
    private Alert alert;
    private Alert alert1;

    private ArrayList<String> nombreSistema;

    @FXML
    public void initialize() {
        try {
            bitacora = Utility.getBinnacle();
            nombres = Utility.getNombreSistema();
            filesPath = Utility.getFilesPathSinglyLinkedList();
            chargeProduct();
            nombreSistema = new ArrayList<>();


            for (int i = 1; i <= nombres.size(); i++) {
                nombreSistema.add((String) nombres.getNode(i).data);
            }


            nombreSistemaCbx.getItems().addAll(nombreSistema);

            alert1 = FXUtility.alert("Configuration", "Desplay Configuration");
            alert1.setAlertType(Alert.AlertType.ERROR);
            alert = FXUtility.alert("Configuration", "Desplay Configuration");
            alert.setAlertType(Alert.AlertType.ERROR);

        } catch (ListException e) {
            throw new RuntimeException(e);
        }

    }

    public void chargeProduct() throws ListException {
        //Como los choiceBox solo pueden ser cargados manualmente dato por dato, o por
        //ArrayList, llenamos uno de estos ultimos usando un recorrid de AVL para extraer sus datos

        choiceBoxPath.getItems().addAll(
                (String) filesPath.getNode(1).data,
                (String) filesPath.getNode(2).data,
                (String) filesPath.getNode(3).data
        );
    }

    @FXML
    void confirmarCambiosOnAction(ActionEvent event) {


        LocalDateTime fecha = LocalDateTime.now();
        if (nombreSistemaCbx.getValue() != null) {
            Utility.setNombreLibreria(nombreSistemaCbx.getValue());
            alert1.setHeaderText("Configuración");
            alert1.setContentText("Por favor vuelva a inicar sesión");
            alert1.setAlertType(Alert.AlertType.INFORMATION);
            alert1.show();
        }
        try {
            if (choiceBoxPath.getValue() != null) {
                Utility.changePathConfig(choiceBoxPath.getValue());
                alert.setHeaderText("Configuración");
                alert.setContentText("La ruta ha sido cambiada");
                alert.setAlertType(Alert.AlertType.INFORMATION);
                alert.show();
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }


        bitacora.add(new Binnacle(String.valueOf(fecha.withNano(0)), Utility.getIDClient(), "Se realizaron cambios en configuración de sistema"));
        Utility.setBinnacle(bitacora);

    }

    @FXML
    void editarLogoOnAction(ActionEvent event) {

    }

}
