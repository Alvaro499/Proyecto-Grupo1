package ucr.proyecto.proyectogrupo1.controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.TextField;
import javafx.scene.layout.BorderPane;
import javafx.scene.text.Text;
import ucr.proyecto.proyectogrupo1.HelloApplication;
import ucr.proyecto.proyectogrupo1.TDA.AVL;
import ucr.proyecto.proyectogrupo1.domain.Binnacle;
import ucr.proyecto.proyectogrupo1.util.Utility;

import java.io.IOException;
import java.time.LocalDateTime;

public class MenuAdministradorController {

    @FXML
    private BorderPane bp;

    @FXML
    private TextField txtNombreUsuario;

    @FXML
    private TextField txtNombreUsuario1;

    @FXML
    private Text nombreLibreria;
    private AVL bitacora;
    LocalDateTime fecha;

    @FXML
    public void initialize(){
        nombreLibreria.setText(Utility.getNombreLibreria());
        bitacora = Utility.getBinnacle();
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
        fecha = LocalDateTime.now();
        bitacora.add(new Binnacle(String.valueOf(fecha.withNano(0)), Utility.getIDClient(),
                "Se ingresó a configuración de sistemas"));
        Utility.setBinnacle(bitacora);
        loadPage("configuracionSistmea.fxml");
    }

    @FXML
    void controlCostosOnAction(ActionEvent event) {
        fecha = LocalDateTime.now();
        bitacora.add(new Binnacle(String.valueOf(fecha.withNano(0)), Utility.getIDClient(),
                "Se ingresó a control de costos"));
        Utility.setBinnacle(bitacora);
        loadPage("controlDeCostos.fxml");
    }

    @FXML
    void controlInventariosOnAction(ActionEvent event) {
        fecha = LocalDateTime.now();
        bitacora.add(new Binnacle(String.valueOf(fecha.withNano(0)), Utility.getIDClient(),
                "Se ingresó a control de inventario"));
        Utility.setBinnacle(bitacora);
        loadPage("controlInventario.fxml");
    }

    @FXML
    void gestionPedidosOnAction(ActionEvent event) {
        fecha = LocalDateTime.now();
        bitacora.add(new Binnacle(String.valueOf(fecha.withNano(0)), Utility.getIDClient(),
                "Se ingresó a gestión de pedidos"));
        Utility.setBinnacle(bitacora);
        loadPage("gestionPedidos.fxml");
    }

    @FXML
    void mantenimientoClientesOnAction(ActionEvent event) {
        fecha = LocalDateTime.now();
        bitacora.add(new Binnacle(String.valueOf(fecha.withNano(0)), Utility.getIDClient(),
                "Se ingresó a mantenimiento de clientes"));
        Utility.setBinnacle(bitacora);
        loadPage("mantenimientoClientes.fxml");
    }

    @FXML
    void mantenimientoProductosOnAction(ActionEvent event) {
        fecha = LocalDateTime.now();
        bitacora.add(new Binnacle(String.valueOf(fecha.withNano(0)), Utility.getIDClient(),
                "Se ingresó a mantenimiento de productos"));
        Utility.setBinnacle(bitacora);
        loadPage("mantenimientoProductos.fxml");
    }

    @FXML
    void mantenimientoProveedoresOnAction(ActionEvent event) {
        fecha = LocalDateTime.now();
        bitacora.add(new Binnacle(String.valueOf(fecha.withNano(0)), Utility.getIDClient(),
                "Se ingresó a mantenimiento de proveedores"));
        Utility.setBinnacle(bitacora);
        loadPage("mantenimientoProveedor.fxml");
    }

    @FXML
    void previsionDemandaOnAction(ActionEvent event) {
        fecha = LocalDateTime.now();
        bitacora.add(new Binnacle(String.valueOf(fecha.withNano(0)), Utility.getIDClient(),
                "Se ingresó a previsión de demanda"));
        Utility.setBinnacle(bitacora);
        loadPage("previsionDemanda.fxml");
    }

    @FXML
    void reportesOnAction(ActionEvent event) {
        fecha = LocalDateTime.now();
        bitacora.add(new Binnacle(String.valueOf(fecha.withNano(0)), Utility.getIDClient(),
                "Se ingresó a reportes"));
        Utility.setBinnacle(bitacora);
        loadPage("reportes.fxml");
    }
    @FXML
    void exitOnAction(ActionEvent event) {
        fecha = LocalDateTime.now();
        bitacora.add(new Binnacle(String.valueOf(fecha.withNano(0)), Utility.getIDClient(),
                "Salió del sistema"));
        Utility.setBinnacle(bitacora);
        System.exit(0);
    }

}

