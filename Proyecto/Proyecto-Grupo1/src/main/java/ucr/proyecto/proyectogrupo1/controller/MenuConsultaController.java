package ucr.proyecto.proyectogrupo1.controller;

import javafx.beans.property.ReadOnlyObjectWrapper;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.layout.BorderPane;
import ucr.proyecto.proyectogrupo1.PDF.PDF;
import ucr.proyecto.proyectogrupo1.TDA.AVL;
import ucr.proyecto.proyectogrupo1.TDA.ListException;
import ucr.proyecto.proyectogrupo1.TDA.TreeException;
import ucr.proyecto.proyectogrupo1.domain.*;
import ucr.proyecto.proyectogrupo1.util.FXUtility;
import ucr.proyecto.proyectogrupo1.util.Utility;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

public class MenuConsultaController {

    @FXML
    private BorderPane bp;

    @FXML
    private TableColumn<List<String>, String> columnCantidadReporteDemanda;

    @FXML
    private TableColumn<List<String>, String> columnCantidadReporteOrden;

    @FXML
    private TableColumn<List<String>, String> columnClienteReporteDemanda;

    @FXML
    private TableColumn<List<String>, String> columnCodigoFacturaReporteDemanda;

    @FXML
    private TableColumn<List<String>, String> columnCodigoLibroReporteDemanda;

    @FXML
    private TableColumn<List<String>, String> columnCodigoLibroReporteInventario;

    @FXML
    private TableColumn<List<String>, String> columnCodigoReporteCostoProducto;

    @FXML
    private TableColumn<List<String>, String> columnCostoReporteOrden;

    @FXML
    private TableColumn<List<String>, String> columnDescriptionReporteInventario;

    @FXML
    private TableColumn<List<String>, String> columnEditorialReporteCostoProducto;

    @FXML
    private TableColumn<List<String>, String> columnEditorialReporteInventario;

    @FXML
    private TableColumn<List<String>, String> columnEditorialReporteOrden;

    @FXML
    private TableColumn<List<String>, String> columnEstadoReporteOrden;

    @FXML
    private TableColumn<List<String>, String> columnFechaReporteDemanda;

    @FXML
    private TableColumn<List<String>, String> columnFechaReporteOrden;

    @FXML
    private TableColumn<List<String>, String> columnLibroReporteInventario;

    @FXML
    private TableColumn<List<String>, String> columnLibroReporteOrden;

    @FXML
    private TableColumn<List<String>, String> columnNombreReporteCostoProducto;

    @FXML
    private TableColumn<List<String>, String> columnOrdenReporteOrden;

    @FXML
    private TableColumn<List<String>, String> columnPrecionUReporteCostoProducto;

    @FXML
    private TableColumn<List<String>, String> columnStockReporteCostoProducto;

    @FXML
    private TableColumn<List<String>, String> columnStockReporteInventario;

    @FXML
    private TableColumn<List<String>, String> columnTotalReporteCostoProducto;

    @FXML
    private TableView<List<String>> tableViewReporteCostoProducto;

    @FXML
    private TableView<List<String>> tableViewReporteDemanda;

    @FXML
    private TableView<List<String>> tableViewReporteInventario;

    @FXML
    private TableView<List<String>> tableViewReporteOrden;

    @FXML
    private TextField txtNombreUsuario1;
    private AVL inventario;
    private AVL pedidosCliente;
    private AVL pedidosProveedor;
    private Alert alert;
    private ArrayList<String> reporteProveedores;
    private ArrayList<String> reporteCliente;
    private ArrayList<String> reporteInventario;
    private ArrayList<String> reporteCostoProductos;
    private AVL bitacora;
    private LocalDateTime hoy;

    @FXML
    public void initialize() throws ListException, TreeException {
        hoy = LocalDateTime.now().withNano(0);

        bitacora = Utility.getBinnacle();

        reporteProveedores = new ArrayList<>();
        reporteCliente = new ArrayList<>();
        reporteInventario = new ArrayList<>();
        reporteCostoProductos = new ArrayList<>();

        inventario = Utility.getProductAVL();
        pedidosCliente = Utility.getSaleDetail();
        pedidosProveedor = Utility.getOrderDetail();
        alert = FXUtility.alert("Menu Admin Reportes", "Desplay Reportes");
        alert.setAlertType(Alert.AlertType.ERROR);

        this.columnFechaReporteDemanda.setCellValueFactory(data ->
                new ReadOnlyObjectWrapper<>(data.getValue().get(0)));
        this.columnCodigoFacturaReporteDemanda.setCellValueFactory(data ->
                new ReadOnlyObjectWrapper<>(data.getValue().get(1)));
        this.columnClienteReporteDemanda.setCellValueFactory(data ->
                new ReadOnlyObjectWrapper<>(data.getValue().get(2)));
        this.columnCodigoLibroReporteDemanda.setCellValueFactory(data ->
                new ReadOnlyObjectWrapper<>(data.getValue().get(3)));
        this.columnCantidadReporteDemanda.setCellValueFactory(data ->
                new ReadOnlyObjectWrapper<>(data.getValue().get(4)));

        if (!pedidosCliente.isEmpty()) {
            tableViewReporteDemanda.setItems(getDataDemanada());
        }
        this.columnCodigoLibroReporteInventario.setCellValueFactory(data ->
                new ReadOnlyObjectWrapper<>(data.getValue().get(0)));
        this.columnLibroReporteInventario.setCellValueFactory(data ->
                new ReadOnlyObjectWrapper<>(data.getValue().get(1)));
        this.columnDescriptionReporteInventario.setCellValueFactory(data ->
                new ReadOnlyObjectWrapper<>(data.getValue().get(2)));
        this.columnEditorialReporteInventario.setCellValueFactory(data ->
                new ReadOnlyObjectWrapper<>(data.getValue().get(3)));
        this.columnStockReporteInventario.setCellValueFactory(data ->
                new ReadOnlyObjectWrapper<>(data.getValue().get(4)));

        if (!inventario.isEmpty()) {
            tableViewReporteInventario.setItems(getDataInventario());
        }

        this.columnEditorialReporteCostoProducto.setCellValueFactory(data ->
                new ReadOnlyObjectWrapper<>(data.getValue().get(0)));
        this.columnCodigoReporteCostoProducto.setCellValueFactory(data ->
                new ReadOnlyObjectWrapper<>(data.getValue().get(1)));
        this.columnNombreReporteCostoProducto.setCellValueFactory(data ->
                new ReadOnlyObjectWrapper<>(data.getValue().get(2)));
        this.columnStockReporteCostoProducto.setCellValueFactory(data ->
                new ReadOnlyObjectWrapper<>(data.getValue().get(3)));
        this.columnPrecionUReporteCostoProducto.setCellValueFactory(data ->
                new ReadOnlyObjectWrapper<>(data.getValue().get(4)));
        this.columnTotalReporteCostoProducto.setCellValueFactory(data ->
                new ReadOnlyObjectWrapper<>(data.getValue().get(5)));

        if (!inventario.isEmpty()) {
            tableViewReporteCostoProducto.setItems(getDataCostoProducto());
        }

        this.columnOrdenReporteOrden.setCellValueFactory(data ->
                new ReadOnlyObjectWrapper<>(data.getValue().get(0)));
        this.columnEditorialReporteOrden.setCellValueFactory(data ->
                new ReadOnlyObjectWrapper<>(data.getValue().get(1)));
        this.columnLibroReporteOrden.setCellValueFactory(data ->
                new ReadOnlyObjectWrapper<>(data.getValue().get(2)));
        this.columnCantidadReporteOrden.setCellValueFactory(data ->
                new ReadOnlyObjectWrapper<>(data.getValue().get(3)));
        this.columnFechaReporteOrden.setCellValueFactory(data ->
                new ReadOnlyObjectWrapper<>(data.getValue().get(4)));
        this.columnEstadoReporteOrden.setCellValueFactory(data ->
                new ReadOnlyObjectWrapper<>(data.getValue().get(5)));
        this.columnCostoReporteOrden.setCellValueFactory(data ->
                new ReadOnlyObjectWrapper<>(data.getValue().get(6)));

        if (!pedidosProveedor.isEmpty()) {
            tableViewReporteOrden.setItems(getDataOrden());
        }
    }

    private ObservableList<List<String>> getDataOrden() throws TreeException {
        ObservableList<List<String>> data = FXCollections.observableArrayList();
        reporteProveedores.add("Codigo de la Orden");
        reporteProveedores.add("Editorial");
        reporteProveedores.add("Codigo del Libro");
        reporteProveedores.add("Cantidad Comprada");
        reporteProveedores.add("Fecha de la Orden");
        reporteProveedores.add("Estado de la Orden");
        reporteProveedores.add("Costo de la Orden");
        Integer n = pedidosProveedor.size();
        for (int i = 0; i < n; i++) {
            List<String> array = new ArrayList<>();
            OrderDetail od = (OrderDetail) pedidosProveedor.get(i);
            Order o = getOrder(od.getOrderID());
            array.add(String.valueOf(od.getOrderID()));//Codigo de la Orden
            array.add(o.getSupplierName());//Editorial
            array.add(od.getProductID());//Codigo del Libro
            array.add(od.getQuantity());//Cantidad comprada
            array.add(o.getOrderDate());//Fecha de la orden
            array.add(o.getOrderStatus());//Estado de la orden
            array.add(String.valueOf(o.getTotalCost()));//Costo de la orden
            reporteProveedores.addAll(array);
            data.add(array);
        }
        return data;
    }

    private ObservableList<List<String>> getDataCostoProducto() throws TreeException {
        ObservableList<List<String>> data = FXCollections.observableArrayList();
        reporteCostoProductos.add("Editorial");
        reporteCostoProductos.add("Codigo del Libro");
        reporteCostoProductos.add("Nombre del Libro");
        reporteCostoProductos.add("Cantidad Disponible");
        reporteCostoProductos.add("Precio Unidad");
        reporteCostoProductos.add("Total Colones");
        Integer n = inventario.size();
        Double total = 0.0;
        for (int i = 0; i < n; i++) {
            List<String> array = new ArrayList<>();
            Product newProduct = (Product) inventario.get(i);
            Double totalLibro = newProduct.getCurrentStock() * newProduct.getPrice();
            array.add(getSupplier(newProduct.getSupplierID()).getName());//Editorial del libro
            array.add(newProduct.getID());//Codigo Libro
            array.add(newProduct.getName());//Nombre Libro
            array.add(String.valueOf(newProduct.getCurrentStock()));//Cantidad Stock Disponible
            array.add(String.valueOf(newProduct.getPrice()));//Precio Libro
            array.add(String.valueOf(totalLibro));//Precio de todas las copias del mismo libro
            total += totalLibro;
            reporteCostoProductos.addAll(array);
            data.add(array);
        }
        for (int i = 0; i < 5; i++) {
            reporteCostoProductos.add("----");
        }
        reporteCostoProductos.add(String.valueOf(total));
        return data;
    }

    private ObservableList<List<String>> getDataInventario() throws TreeException {
        ObservableList<List<String>> data = FXCollections.observableArrayList();
        reporteInventario.add("Codigo");
        reporteInventario.add("Nombre");
        reporteInventario.add("Descripcion");
        reporteInventario.add("Editorial");
        reporteInventario.add("Stock");
        Integer n = inventario.size();
        for (int i = 0; i < n; i++) {
            ArrayList<String> array = new ArrayList<>();
            Product newProduct = (Product) inventario.get(i);
            array.add(newProduct.getID());
            array.add(newProduct.getName());
            array.add(newProduct.getDescription());
            array.add(getSupplier(newProduct.getSupplierID()).getName());
            array.add(String.valueOf(newProduct.getCurrentStock()));
            reporteInventario.addAll(array);
            data.add(array);
        }
        return data;
    }

    private ObservableList<List<String>> getDataDemanada() throws TreeException {
        ObservableList<List<String>> data = FXCollections.observableArrayList();
        reporteCliente.add("Fecha factura");
        reporteCliente.add("Codigo factura");
        reporteCliente.add("Cliente");
        reporteCliente.add("Codigo libro");
        reporteCliente.add("Cantidad del libro");
        Integer n = pedidosCliente.size();
        for (int i = 0; i < n; i++) {
            List<String> array = new ArrayList<>();
            SaleDetail newSaleDetail = (SaleDetail) pedidosCliente.get(i);
            if (newSaleDetail.getOrder_canceled()) {
                array.add(getSale(newSaleDetail.getSaleID()).getSaleDate());//Fecha de la factura
                array.add(String.valueOf(newSaleDetail.getSaleID()));//Codigo de la factura
                array.add(String.valueOf(getSale(newSaleDetail.getSaleID()).getCustomerID()));//Cedula Cliente
                array.add(newSaleDetail.getProductID());//Codigo del libro
                array.add(String.valueOf(newSaleDetail.getQuantity()));//Cantidad del libro
                reporteCliente.addAll(array);
                data.add(array);
            }
        }
        return data;
    }

    private Product getProduct(String id) throws TreeException {
        AVL productAVL = Utility.getProductAVL();
        Product p = null;
        Integer n = productAVL.size();
        for (int i = 0; i < n; i++) {
            p = (Product) productAVL.get(i);
            if (p.getID().equals(id)) {
                return p;
            }
        }
        return p;
    }

    private Supplier getSupplier(Integer id) throws TreeException {
        AVL supplierAVL = Utility.getSupplierAVL();
        Supplier s = null;
        Integer n = supplierAVL.size();
        for (int i = 0; i < n; i++) {
            s = (Supplier) supplierAVL.get(i);
            if (s.getID().equals(id)) {
                return s;
            }
        }
        return s;
    }

    private Order getOrder(Integer id) throws TreeException {
        AVL orderAVL = Utility.getOrder();
        Order o = null;
        Integer n = orderAVL.size();
        for (int i = 0; i < n; i++) {
            o = (Order) orderAVL.get(i);
            if (o.getId().equals(id)) {
                return o;
            }
        }
        return o;
    }

    private Sale getSale(Integer id) throws TreeException {
        AVL saleAVL = Utility.getSale();
        Sale s = null;
        Integer n = saleAVL.size();
        for (int i = 0; i < n; i++) {
            s = (Sale) saleAVL.get(i);
            if (s.getID().equals(id)) {
                return s;
            }
        }
        return s;
    }

    @FXML
    void exitOnAction(ActionEvent event) {
        System.exit(0);
    }

    @FXML
    void reporteClientesOnAction(ActionEvent event) {
        PDF.crearPDF("Reporte_Demanda_Producto", "Reporte Demanda", 5, reporteCliente);
        bitacora.add(new Binnacle(String.valueOf(hoy),Utility.getIDClient(),"El usuario consulta genero un reporte de Demanda"));
        Utility.setBinnacle(bitacora);
        alert.setHeaderText("Report created");
        alert.setContentText(PDF.getDocumento());
        alert.setAlertType(Alert.AlertType.INFORMATION);
        alert.show();
    }

    @FXML
    void reporteCostoProductosOnAction(ActionEvent event) {
        PDF.crearPDF("Reporte_Costo_Producto", "Reporte Costo Producto", 6, reporteCostoProductos);
        bitacora.add(new Binnacle(String.valueOf(hoy),Utility.getIDClient(),"El usuario consulta genero un reporte de Costo Producto"));
        Utility.setBinnacle(bitacora);
        alert.setHeaderText("Report created");
        alert.setContentText(PDF.getDocumento());
        alert.setAlertType(Alert.AlertType.INFORMATION);
        alert.show();
    }

    @FXML
    void reporteInventarioOnAction(ActionEvent event) {
        PDF.crearPDF("Reporte_Inventario", "Reporte Inventario", 5, reporteInventario);
        bitacora.add(new Binnacle(String.valueOf(hoy),Utility.getIDClient(),"El usuario consulta genero un reporte de Inventario"));
        Utility.setBinnacle(bitacora);
        alert.setHeaderText("Report created");
        alert.setContentText(PDF.getDocumento());
        alert.setAlertType(Alert.AlertType.INFORMATION);
        alert.show();
    }

    @FXML
    void reporteProveedoresOnAction(ActionEvent event) {
        PDF.crearPDF("Reporte_Ordenes", "Reporte Orden", 7, reporteProveedores);
        bitacora.add(new Binnacle(String.valueOf(hoy),Utility.getIDClient(),"El usuario consulta genero un reporte de Ordenes"));
        Utility.setBinnacle(bitacora);
        alert.setHeaderText("Report created");
        alert.setContentText(PDF.getDocumento());
        alert.setAlertType(Alert.AlertType.INFORMATION);
        alert.show();
    }


}
