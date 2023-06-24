package ucr.proyecto.proyectogrupo1.controller;

import javafx.beans.property.ReadOnlyStringWrapper;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import ucr.proyecto.proyectogrupo1.PDF.PDF;
import ucr.proyecto.proyectogrupo1.TDA.AVL;
import ucr.proyecto.proyectogrupo1.TDA.ListException;
import ucr.proyecto.proyectogrupo1.TDA.SinglyLinkedList;
import ucr.proyecto.proyectogrupo1.TDA.TreeException;
import ucr.proyecto.proyectogrupo1.domain.*;
import ucr.proyecto.proyectogrupo1.email.EnvioCorreos;
import ucr.proyecto.proyectogrupo1.util.FXUtility;
import ucr.proyecto.proyectogrupo1.util.Utility;

import java.io.File;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

public class ReporteParaClientesController {

    @FXML
    private TableView<List<String>> tableView;
    @FXML
    private TableColumn<List<String>, String> columnNoPedido;
    @FXML
    private TableColumn<List<String>, String> columnFecha;
    @FXML
    private TableColumn<List<String>, String> columnProducto;
    @FXML
    private TableColumn<List<String>, String> columnCantidad;
    private AVL newSale;
    private AVL newSaleDetail;
    private AVL newProduct;
    private SinglyLinkedList client; //table client
    Alert alert;
    private Integer clientID;
    @FXML
    private TableColumn<List<String>, String> columnPrecioUnidad;
    private ArrayList<String> reporte;
    private LocalDateTime hoy;

    private AVL bitacora;
    @FXML
    public void initialize() throws ListException, TreeException {
        bitacora = Utility.getBinnacle();

        hoy = LocalDateTime.now().withNano(0);

        clientID = Utility.getIDClient();
        newSale = Utility.getSale();
        newSaleDetail = Utility.getSaleDetail();
        newProduct = Utility.getProductAVL();
        client = Utility.getClientSinglyLinkedList();
        reporte = new ArrayList<String>();

        this.columnNoPedido.setCellValueFactory(data ->
                new ReadOnlyStringWrapper(data.getValue().get(0)));
        this.columnFecha.setCellValueFactory(data ->
                new ReadOnlyStringWrapper(data.getValue().get(1)));
        this.columnProducto.setCellValueFactory(data ->
                new ReadOnlyStringWrapper(data.getValue().get(2)));
        this.columnCantidad.setCellValueFactory(data ->
                new ReadOnlyStringWrapper(data.getValue().get(3)));
        this.columnPrecioUnidad.setCellValueFactory(data ->
                new ReadOnlyStringWrapper(data.getValue().get(4)));

        alert = FXUtility.alert("Menu Reporte", "Desplay Reporte");
        alert.setAlertType(Alert.AlertType.ERROR);

        if (!newSaleDetail.isEmpty()) {
            tableView.setItems(getData());
        }
    }

    private ObservableList<List<String>> getData() throws TreeException {
        ObservableList<List<String>> data = FXCollections.observableArrayList();
        Integer n = newSaleDetail.size();
        for (int i = 0; i < n; i++) {
            List<String> arrayList = new ArrayList<>();
            Sale s = (Sale) newSale.get(i);
            SaleDetail sd = getSaleDetail(s.getID());
            //Para saber las facturas ya canceladas por el cliente hay que obtener
            //1. la tabla Sale y su SaleDetail correspondiente
            //2. que SaleDatail este cancelado
            //3. que las dos tablas les corresponda al cliente que lo solicito
            if (sd != null && sd.getOrder_canceled() && s.getCustomerID().equals(clientID)) {
                arrayList.add(String.valueOf(sd.getSaleID()));
                arrayList.add(String.valueOf(s.getSaleDate()));
                arrayList.add(getProduct(sd.getProductID()).getName());
                arrayList.add(String.valueOf(sd.getQuantity()));
                arrayList.add(String.valueOf(getProduct(sd.getProductID()).getPrice()));

                //guardar la info en reporte
                reporte.add(String.valueOf(sd.getSaleID()));
                reporte.add(String.valueOf(s.getSaleDate()));
                reporte.add(getProduct(sd.getProductID()).getName());
                reporte.add(String.valueOf(sd.getQuantity()));
                reporte.add(String.valueOf(getProduct(sd.getProductID()).getPrice()));

                data.add(arrayList);
            }
        }
        return data;
    }

    private Product getProduct(String productID) throws TreeException {
        Product p = null;
        Integer n = newProduct.size();
        for (int i = 0; i < n; i++) {
            p = (Product) newProduct.get(i);
            if (p.getID().equalsIgnoreCase(productID)) {
                return p;
            }
        }
        return p;
    }

    private SaleDetail getSaleDetail(Integer id) throws TreeException {
        SaleDetail sd = null;
        Integer n = newSaleDetail.size();
        for (int i = 0; i < n; i++) {
            sd = (SaleDetail) newSaleDetail.get(i);
            if (sd.getSaleID().equals(id)) {
                return sd;
            }
        }
        return sd;
    }

    private Customer getCustomer(Integer id) throws ListException {
        Customer c = null;
        Integer n = client.size();
        for (int i = 1; i <= n; i++) {
            c = (Customer) client.getNode(i).data;
            if (c.getID().equals(id)) {
                return c;
            }
        }
        return c;
    }

    @FXML
    void ReporteOnAction(ActionEvent event) throws ListException {
        String correoCliente = getCustomer(clientID).getEmail().trim();
        String mensaje = FXUtility.alertYesNo("Generador de reporte", "El reporte sera enviar a: ", correoCliente);
        if (mensaje.equalsIgnoreCase("YES")) {
            EnvioCorreos correos = new EnvioCorreos();
            correos.setEmailTo(correoCliente);
            correos.setSubject("Historial de compras");
            PDF.crearPDF("Reporte_Compras", "Reporte", 5, reporte);
            correos.setContent("<br>Por favor no contestar este correo.");
            correos.setAttachmentFile(new File(PDF.getDocumento()));
            correos.sendEmail();
            //PDF.eliminar();
            //bitacora
            bitacora.add(new Binnacle(String.valueOf(hoy),Utility.getIDClient(),"El usuario genero un reporte al correo: " + correoCliente));
            Utility.setBinnacle(bitacora);
            //fin bitacora
        }
    }
}
