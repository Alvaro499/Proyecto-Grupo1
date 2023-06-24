package ucr.proyecto.proyectogrupo1.controller;

import javafx.beans.property.ReadOnlyObjectWrapper;
import javafx.beans.property.ReadOnlyStringWrapper;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextArea;
import ucr.proyecto.proyectogrupo1.PDF.PDF;
import ucr.proyecto.proyectogrupo1.TDA.*;
import ucr.proyecto.proyectogrupo1.domain.Binnacle;
import ucr.proyecto.proyectogrupo1.domain.Customer;
import ucr.proyecto.proyectogrupo1.domain.Product;
import ucr.proyecto.proyectogrupo1.domain.Supplier;
import ucr.proyecto.proyectogrupo1.email.EnvioCorreos;
import ucr.proyecto.proyectogrupo1.util.FXUtility;
import ucr.proyecto.proyectogrupo1.util.Utility;

import java.io.File;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

public class ControlDeCostosController {

    @FXML
    private TableView<List<String>> tableView;
    @FXML
    private TableColumn<List<String>, String> id;
    @FXML
    private TableColumn<List<String>, String> producto;
    @FXML
    private TableColumn<List<String>, String> precio;
    @FXML
    private TableColumn<List<String>, String> stockActual;
    @FXML
    private TableColumn<List<String>, String> proveedor;
    @FXML
    private TableColumn<List<String>, String> costoTotal;

    private AVL supplierName;

    private AVL bitacora;

    private AVL product;
    private SinglyLinkedList cliente; //table client
    private Integer clienteID;
    private ArrayList<String> reporte;
    private HeaderLinkedQueue headerLinkedQueue = new HeaderLinkedQueue();
    private HeaderLinkedQueue headerLinkedQueue2 = new HeaderLinkedQueue();
    Alert alert;
    private double costoTotalTProductos;
    private double costoTotalCProductos;

    @FXML
    private TextArea textAreaCosto;
    //backup
    private AVL productBackup;
    private AVL supplierBackup;
    private SinglyLinkedList clienteBackup;



    @FXML
    public void initialize() {
        /*productBackup = Utility.getProductAVL();
        supplierBackup = Utility.getSupplierAVL();
        clienteBackup*/


        product = Utility.getProductAVL();
        supplierName = Utility.getSupplierAVL();
        cliente = Utility.getClientSinglyLinkedList();
        clienteID = Utility.getIDClient();
        reporte = new ArrayList<String>();



        this.id.setCellValueFactory(data ->
                new ReadOnlyObjectWrapper<>(data.getValue().get(0)));

        this.producto.setCellValueFactory(data ->
                new ReadOnlyStringWrapper(data.getValue().get(1)));

        this.precio.setCellValueFactory(data ->
                new ReadOnlyStringWrapper(data.getValue().get(2)));

        this.stockActual.setCellValueFactory(data ->
                new ReadOnlyStringWrapper(data.getValue().get(3)));

        this.proveedor.setCellValueFactory(data ->
                new ReadOnlyStringWrapper(data.getValue().get(4)));

        this.costoTotal.setCellValueFactory(data ->
                new ReadOnlyStringWrapper(data.getValue().get(5)));

        if (!product.isEmpty()) {
            try {
                tableView.setItems(getData());
                //Utility.setCostos(headerLinkedQueue);
            } catch (TreeException e) {
                throw new RuntimeException(e);
            }
        }

        alert = FXUtility.alert("Menu Reporte", "Desplay Reporte");
        alert.setAlertType(Alert.AlertType.ERROR);
    }

    private ObservableList<List<String>> getData() throws TreeException {
        ObservableList<List<String>> data = FXCollections.observableArrayList();


        for (int i = 0; i < product.size(); i++) {
            List<String> arrayList = new ArrayList<>();
            Product p = (Product) product.get(i);
            arrayList.add(String.valueOf(p.getID()));
            reporte.add(String.valueOf(p.getID()));

            arrayList.add(p.getName());
            reporte.add(p.getName());

            arrayList.add("₡" + (p.getPrice()));
            reporte.add("₡" + (p.getPrice()));

            arrayList.add(String.valueOf(p.getCurrentStock()));
            reporte.add(String.valueOf(p.getCurrentStock()));

            for (int j = 0; j < supplierName.size(); j++) {//Agarra ID de la tabla supplier y lo compara con IDsupplier de la tabla Product, para saber el nombre del proveedor del libro
                Supplier s = (Supplier) supplierName.get(j);
                if (s.getID().equals(p.getSupplierID())) {
                    arrayList.add(s.getName());
                    reporte.add(s.getName());
                }
            }
            try {

                costoTotalCProductos = 0.0;
                headerLinkedQueue.enQueue(p.getPrice() * p.getCurrentStock());
                Object element =headerLinkedQueue.deQueue();
                double value = (double) element;
                arrayList.add("₡" + value);
                reporte.add("₡" + value);

            } catch (QueueException e) {
                throw new RuntimeException(e);
            }

            data.add(arrayList);
        }
        return data;
    }


    @FXML
    void calcularCostoOnAction(ActionEvent event) throws TreeException {
        LocalDateTime fecha = LocalDateTime.now();
        try {

            for (int i = 0; i < product.size(); i++) {
                Product p = (Product) product.get(i);
                headerLinkedQueue.enQueue(p.getPrice() * p.getCurrentStock());
            }

            costoTotalTProductos = 0.0;
            while (!headerLinkedQueue.isEmpty()) {
                Object element = headerLinkedQueue.front();
                double value = (double) element;
                costoTotalTProductos += value;
                headerLinkedQueue2.enQueue(headerLinkedQueue.deQueue());
            }

            textAreaCosto.setText(String.valueOf(costoTotalTProductos));

            while (!headerLinkedQueue2.isEmpty()) {
                headerLinkedQueue.enQueue(headerLinkedQueue2.deQueue());
            }

            bitacora.add(new Binnacle(fecha.withNano(0), Utility.getIDClient(),"Calcular costo total"));

        } catch (QueueException e) {
            throw new RuntimeException(e);
        }
    }

    private Customer getCustomer(Integer id) throws ListException {
        Customer c = null;
        Integer n = cliente.size();
        for (int i = 1; i <= n; i++) {
            c = (Customer) cliente.getNode(i).data;
            if (c.getID() == id) {
                return c;
            }
        }
        return c;
    }

    @FXML
    void reporteOnAction(ActionEvent event) throws ListException {
        /*String correoCliente = getCustomer(clienteID).getEmail().trim();
        String mensaje = FXUtility.alertYesNo("Generador de reporte", "El reporte se enviará a: ", correoCliente);
        if (mensaje.equalsIgnoreCase("YES")) {*/

            for (int i = 0; i < 4; i++) {
                reporte.add("");
            }
            reporte.add(String.valueOf(costoTotalTProductos));

        /*    EnvioCorreos correos = new EnvioCorreos();
            correos.setEmailTo(correoCliente);
            correos.setSubject("Reporte de Costos");*/
            PDF.crearPDF("Reporte_Costos","Reporte Costos",5,reporte);

        //    correos.setContent("Por favor no contestar este correo.");
            /*correos.setAttachmentFile(new File(PDF.getDocumento()));
            correos.sendEmail();*/
        }

}
