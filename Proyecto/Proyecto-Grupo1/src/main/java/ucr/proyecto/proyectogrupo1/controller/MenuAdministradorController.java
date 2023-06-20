package ucr.proyecto.proyectogrupo1.controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.TextField;
import javafx.scene.layout.BorderPane;
import javafx.scene.text.Text;
import ucr.proyecto.proyectogrupo1.HelloApplication;
import ucr.proyecto.proyectogrupo1.TDA.ListException;
import ucr.proyecto.proyectogrupo1.TDA.TreeException;
import ucr.proyecto.proyectogrupo1.util.Utility;

import java.io.IOException;

public class MenuAdministradorController {

    @FXML
    private BorderPane bp;

    @FXML
    private TextField txtNombreUsuario;

    @FXML
    private TextField txtNombreUsuario1;

    @FXML
    private Text nombreSistema;


    @FXML
    public void initialize() throws ListException, TreeException {
        nombreSistema.setText(Utility.getNombreSistema());
    }


    private void loadPage(String page) {
        FXMLLoader fxmlLoader = new FXMLLoader(HelloApplication.class.getResource(page));
        try {
            this.bp.setCenter(fxmlLoader.load());
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }


    @FXML
    void configuracionSistemaOnAction(ActionEvent event) {
        loadPage("configuracionSistmea.fxml");
    }

    @FXML
    void controlCostosOnAction(ActionEvent event) {
        loadPage("controlDeCostos.fxml");
    }

    @FXML
    void controlInventariosOnAction(ActionEvent event) {
        loadPage("controlInventario.fxml");
    }

    @FXML
    void gestionPedidosOnAction(ActionEvent event) {
        loadPage("gestionPedidos.fxml");
    }

    @FXML
    void mantenimientoClientesOnAction(ActionEvent event) {
        loadPage("mantenimientoClientes.fxml");
    }

    @FXML
    void mantenimientoProductosOnAction(ActionEvent event) {
        loadPage("mantenimientoProductos.fxml");
    }

    @FXML
    void mantenimientoProveedoresOnAction(ActionEvent event) {
        loadPage("mantenimientoProveedor.fxml");
    }

    @FXML
    void previsionDemandaOnAction(ActionEvent event) {
        loadPage("previsionDemanda.fxml");
    }

    @FXML
    void reportesOnAction(ActionEvent event) {
        loadPage("reportes.fxml");
    }
    @FXML
    void exitOnAction(ActionEvent event) {
        System.exit(0);
    }

    public void carritoOnAction(ActionEvent actionEvent) {
    }

    public void buscarOnAction(ActionEvent actionEvent) {
    }

    public void gestiónOnAction(ActionEvent actionEvent) {
    }

    public void reporteOnAction(ActionEvent actionEvent) {
    }
}

