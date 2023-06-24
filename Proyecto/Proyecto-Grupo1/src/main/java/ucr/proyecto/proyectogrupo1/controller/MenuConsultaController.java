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
import ucr.proyecto.proyectogrupo1.TDA.AVL;
import ucr.proyecto.proyectogrupo1.TDA.ListException;
import ucr.proyecto.proyectogrupo1.TDA.TreeException;
import ucr.proyecto.proyectogrupo1.domain.*;
import ucr.proyecto.proyectogrupo1.util.FXUtility;
import ucr.proyecto.proyectogrupo1.util.Utility;

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

    @FXML
    public void initialize() throws ListException, TreeException {
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
        this.columnOrdenReporteOrden.setCellValueFactory(data ->
                new ReadOnlyObjectWrapper<>(data.getValue().get(1)));
        this.columnOrdenReporteOrden.setCellValueFactory(data ->
                new ReadOnlyObjectWrapper<>(data.getValue().get(2)));
        this.columnOrdenReporteOrden.setCellValueFactory(data ->
                new ReadOnlyObjectWrapper<>(data.getValue().get(3)));
        this.columnOrdenReporteOrden.setCellValueFactory(data ->
                new ReadOnlyObjectWrapper<>(data.getValue().get(4)));
        this.columnOrdenReporteOrden.setCellValueFactory(data ->
                new ReadOnlyObjectWrapper<>(data.getValue().get(5)));
        this.columnOrdenReporteOrden.setCellValueFactory(data ->
                new ReadOnlyObjectWrapper<>(data.getValue().get(6)));

        if (!pedidosProveedor.isEmpty()) {
            tableViewReporteOrden.setItems(getDataOrden());
        }
    }

    private ObservableList<List<String>> getDataOrden() throws TreeException {
        ObservableList<List<String>> data = FXCollections.observableArrayList();

        Integer n = pedidosProveedor.size();
        List<String> array = new ArrayList<>();
        for (int i = 0; i < n; i++) {
            OrderDetail od = (OrderDetail) pedidosProveedor.get(i);
            Order o = getOrder(od.getOrderID());
            array.add(String.valueOf(od.getOrderID()));//Codigo de la Orden
            array.add(o.getSupplierName());//Editorial
            array.add(getProduct(od.getProductID()).getName());//Nombre del Libro
            array.add(od.getProductID());//Codigo del Libro
            array.add(od.getQuantity());//Cantidad comprada
            array.add(o.getOrderDate());//Fecha de la orden
            array.add(o.getOrderStatus());//Estado de la orden
            array.add(String.valueOf(o.getTotalCost()));//Costo de la orden
        }
        data.add(array);
        return data;
    }

    private ObservableList<List<String>> getDataCostoProducto() throws TreeException {
        ObservableList<List<String>> data = FXCollections.observableArrayList();
        Integer n = inventario.size();
        List<String> array = new ArrayList<>();
        Double total = 0.0;
        for (int i = 0; i < n; i++) {
            Product newProduct = (Product) inventario.get(i);
            Double totalLibro = newProduct.getCurrentStock() * newProduct.getPrice();
            array.add(getSupplier(newProduct.getSupplierID()).getName());//Editorial del libro
            array.add(newProduct.getID());//Codigo Libro
            array.add(newProduct.getName());//Nombre Libro
            array.add(String.valueOf(newProduct.getCurrentStock()));//Cantidad Stock Disponible
            array.add(String.valueOf(newProduct.getPrice()));//Precio Libro
            array.add(String.valueOf(totalLibro));//Precio de todas las copias del mismo libro
            total += totalLibro;
        }
        for (int i = 0; i < 5; i++) {
            array.add("----");
        }
        array.add(String.valueOf(total));
        data.add(array);
        return data;
    }

    private ObservableList<List<String>> getDataInventario() throws TreeException {
        ObservableList<List<String>> data = FXCollections.observableArrayList();
        Integer n = inventario.size();
        ArrayList<String> array = new ArrayList<>();
        for (int i = 0; i < n; i++) {
            Product newProduct = (Product) inventario.get(i);
            array.add(newProduct.getID());
            array.add(newProduct.getName());
            array.add(newProduct.getDescription());
            array.add(getSupplier(newProduct.getSupplierID()).getName());
            array.add(String.valueOf(newProduct.getCurrentStock()));
        }
        data.add(array);
        return data;
    }

    private ObservableList<List<String>> getDataDemanada() throws TreeException {
        ObservableList<List<String>> data = FXCollections.observableArrayList();
        Integer n = pedidosCliente.size();
        List<String> array = new ArrayList<>();
        for (int i = 0; i < n; i++) {
            SaleDetail newSaleDetail = (SaleDetail) pedidosCliente.get(i);
            if (newSaleDetail.getOrder_canceled()) {
                array.add(getSale(newSaleDetail.getSaleID()).getSaleDate());//Fecha de la factura
                array.add(String.valueOf(newSaleDetail.getSaleID()));//Codigo de la factura
                array.add(String.valueOf(getSale(newSaleDetail.getSaleID()).getCustomerID()));//Cedula Cliente
                array.add(newSaleDetail.getProductID());//Codigo del libro
                array.add(String.valueOf(newSaleDetail.getQuantity()));//Cantidad del libro
            }
        }
        data.add(array);
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

    }

    @FXML
    void reporteClientesOnAction(ActionEvent event) {

    }

    @FXML
    void reporteProductosOnAction(ActionEvent event) {

    }

    @FXML
    void reporteProveedoresOnAction(ActionEvent event) {

    }

}
