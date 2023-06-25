package ucr.proyecto.proyectogrupo1.controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import ucr.proyecto.proyectogrupo1.PDF.PDF;
import ucr.proyecto.proyectogrupo1.TDA.AVL;
import ucr.proyecto.proyectogrupo1.TDA.ListException;
import ucr.proyecto.proyectogrupo1.TDA.TreeException;
import ucr.proyecto.proyectogrupo1.domain.*;
import ucr.proyecto.proyectogrupo1.util.FXUtility;
import ucr.proyecto.proyectogrupo1.util.Utility;

import java.time.LocalDateTime;
import java.util.ArrayList;

public class ReportesController {
    private AVL inventario;
    private AVL pedidosCliente;
    private AVL pedidosProveedor;
    private Alert alert;
    private AVL bitacora;

    @FXML
    public void initialize() throws ListException, TreeException {
        bitacora = Utility.getBinnacle();
        inventario = Utility.getProductAVL();
        pedidosCliente = Utility.getSaleDetail();
        pedidosProveedor = Utility.getOrderDetail();
        alert = FXUtility.alert("Menu Admin Reportes", "Desplay Reportes");
        alert.setAlertType(Alert.AlertType.ERROR);
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
    void onActionReporteInventario(ActionEvent event) {//reporte de todos los productos
        LocalDateTime fecha = LocalDateTime.now();
        ArrayList<String> array = new ArrayList<>();
        try {
            if (!inventario.isEmpty()) {
                Integer n = inventario.size();
                array.add("Codigo");
                array.add("Nombre");
                array.add("Descripcion");
                array.add("Editorial");
                array.add("Stock");
                for (int i = 0; i < n; i++) {
                    Product newProduct = (Product) inventario.get(i);
                    array.add(newProduct.getID());
                    array.add(newProduct.getName());
                    array.add(newProduct.getDescription());
                    array.add(getSupplier(newProduct.getSupplierID()).getName());
                    array.add(String.valueOf(newProduct.getCurrentStock()));
                }
                PDF.crearPDF("Reporte_Inventario", "Reporte Inventario", 5, array);
                bitacora.add(new Binnacle(String.valueOf(fecha.withNano(0)), Utility.getIDClient(),
                        "Reporte Inventario Creado"));
                Utility.setBinnacle(bitacora);
                alert.setHeaderText("Report created");
                alert.setContentText(PDF.getDocumento());
                alert.setAlertType(Alert.AlertType.INFORMATION);
                alert.show();
            }else {
                alert.setHeaderText("Inventario está vacío");
                alert.setContentText(PDF.getDocumento());
                alert.setAlertType(Alert.AlertType.INFORMATION);
                alert.show();
            }
        } catch (TreeException e) {
            throw new RuntimeException(e);
        }
    }

    @FXML
    void onActionReportePedidosProveedor(ActionEvent event) {//reporte de los pedidos al proveedor
        LocalDateTime fecha = LocalDateTime.now();
        ArrayList<String> array = new ArrayList<>();
        try {
            if (!pedidosProveedor.isEmpty()) {
                Integer n = pedidosProveedor.size();
                array.add("Codigo de la Orden");
                array.add("Editorial");
                array.add("Nombre del Libro");
                array.add("Codigo del Libro");
                array.add("Cantidad Comprada");
                array.add("Fecha de la Orden");
                array.add("Estado de la Orden");
                array.add("Costo de la Orden");
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
                PDF.crearPDF("Reporte_Ordenes", "Reporte Orden", 8, array);
                bitacora.add(new Binnacle(String.valueOf(fecha.withNano(0)), Utility.getIDClient(),
                        "Reporte Pedidos Proveedor Creado"));
                Utility.setBinnacle(bitacora);
                alert.setHeaderText("Report created");
                alert.setContentText(PDF.getDocumento());
                alert.setAlertType(Alert.AlertType.INFORMATION);
                alert.show();
            }else {
                alert.setHeaderText("No se han realizado pedidos a proveedores");
                alert.setContentText(PDF.getDocumento());
                alert.setAlertType(Alert.AlertType.INFORMATION);
                alert.show();
            }
        } catch (TreeException e) {
            throw new RuntimeException(e);
        }
    }

    @FXML
    void onActionReporteProductos(ActionEvent event) {//reporte de los productos que hay y su precio; ademas del total
        LocalDateTime fecha = LocalDateTime.now();
        ArrayList<String> array = new ArrayList<>();
        try {
            if (!inventario.isEmpty()) {
                Integer n = inventario.size();
                Double total = 0.0;
                array.add("Editorial");
                array.add("Codigo del Libro");
                array.add("Nombre del Libro");
                array.add("Cantidad Disponible");
                array.add("Precio Unidad");
                array.add("Total Colones");
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

                PDF.crearPDF("Reporte_Costo_Producto", "Reporte Costo Producto", 6, array);
                bitacora.add(new Binnacle(String.valueOf(fecha.withNano(0)), Utility.getIDClient(),
                        "Reporte Productos Creado"));
                Utility.setBinnacle(bitacora);
                alert.setHeaderText("Report created");
                alert.setContentText(PDF.getDocumento());
                alert.setAlertType(Alert.AlertType.INFORMATION);
                alert.show();
            }else {
                alert.setHeaderText("Inventario está vacío");
                alert.setContentText(PDF.getDocumento());
                alert.setAlertType(Alert.AlertType.INFORMATION);
                alert.show();
            }
        } catch (TreeException e) {
            throw new RuntimeException(e);
        }
    }

    @FXML
    void onActionReportesPedidosClientes(ActionEvent event) {//Reporte de todos los pedidos de los clientes
        LocalDateTime fecha = LocalDateTime.now();
        ArrayList<String> array = new ArrayList<>();
        try {
            if (pedidosCliente.root != null || pedidosCliente.isEmpty()) {
                Integer n = pedidosCliente.size();
                array.add("Fecha factura");
                array.add("Codigo factura");
                array.add("Cliente");
                array.add("Codigo libro");
                array.add("Cantidad del libro");
                for (int i = 0; i < n; i++) {
                    SaleDetail newSaleDetail = (SaleDetail) pedidosCliente.get(i);
                    if (newSaleDetail.getOrder_canceled()) {//Solo si la factura esta pagada
                        array.add(getSale(newSaleDetail.getSaleID()).getSaleDate());//Fecha de la factura
                        array.add(String.valueOf(newSaleDetail.getSaleID()));//Codigo de la factura
                        array.add(String.valueOf(getSale(newSaleDetail.getSaleID()).getCustomerID()));//Cedula Cliente
                        array.add(newSaleDetail.getProductID());//Codigo del libro
                        array.add(String.valueOf(newSaleDetail.getQuantity()));//Cantidad del libro
                    }
                }
                PDF.crearPDF("Reporte_Demanda_Producto", "Reporte Demanda", 5, array);
                bitacora.add(new Binnacle(String.valueOf(fecha.withNano(0)), Utility.getIDClient(),
                        "Reporte Pedidos Cliente Creado"));
                Utility.setBinnacle(bitacora);
                alert.setHeaderText("Report created");
                alert.setContentText(PDF.getDocumento());
                alert.setAlertType(Alert.AlertType.INFORMATION);
                alert.show();
            }else {
                alert.setHeaderText("No hay Pedidos Clientes");
                alert.setContentText(PDF.getDocumento());
                alert.setAlertType(Alert.AlertType.INFORMATION);
                alert.show();
            }
        } catch (TreeException e) {
            throw new RuntimeException(e);
        }
    }

}
