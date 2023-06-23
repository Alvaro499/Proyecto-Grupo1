package ucr.proyecto.proyectogrupo1.controller;

import javafx.beans.property.ReadOnlyObjectWrapper;
import javafx.beans.property.ReadOnlyStringWrapper;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import ucr.proyecto.proyectogrupo1.TDA.AVL;
import ucr.proyecto.proyectogrupo1.TDA.QueueException;
import ucr.proyecto.proyectogrupo1.TDA.SinglyLinkedList;
import ucr.proyecto.proyectogrupo1.TDA.TreeException;
import ucr.proyecto.proyectogrupo1.domain.OrderDetail;
import ucr.proyecto.proyectogrupo1.domain.Product;
import ucr.proyecto.proyectogrupo1.domain.Supplier;
import ucr.proyecto.proyectogrupo1.util.FXUtility;
import ucr.proyecto.proyectogrupo1.util.Utility;

import java.util.ArrayList;
import java.util.List;

public class PrevisionDemandaController {

    @FXML
    private TableView<List<String>> tableView;
    @FXML
    private TableColumn<List<String>, String> idProducto;
    @FXML
    private TableColumn<List<String>, String> nombre;
    @FXML
    private TableColumn<List<String>, String> precio;
    @FXML
    private TableColumn<List<String>, String> stockActual;
    @FXML
    private TableColumn<List<String>, String> stockMin;
    @FXML
    private TableColumn<List<String>, String> proveedor;
    @FXML
    private TableColumn<List<String>, String> stockRecomendado;

    private Alert alert;
    private AVL supplierName; //proveedor

    private AVL product;

    private AVL orderDatail;
    private SinglyLinkedList cliente; //table client
    private Integer clienteID;
    private ArrayList<String> reporte;

    @FXML
    public void initialize() {
        product = Utility.getProductAVL();
        supplierName = Utility.getSupplierAVL();
        orderDatail = Utility.getOrderDetail();
        cliente = Utility.getClientSinglyLinkedList();
        clienteID = Utility.getIDClient();
        reporte = new ArrayList<String>();


        this.idProducto.setCellValueFactory(data ->
                new ReadOnlyObjectWrapper<>(data.getValue().get(0)));

        this.nombre.setCellValueFactory(data ->
                new ReadOnlyStringWrapper(data.getValue().get(1)));

        this.precio.setCellValueFactory(data ->
                new ReadOnlyStringWrapper(data.getValue().get(2)));

        this.stockActual.setCellValueFactory(data ->
                new ReadOnlyStringWrapper(data.getValue().get(3)));
        this.stockMin.setCellValueFactory(data ->
                new ReadOnlyStringWrapper(data.getValue().get(4)));

        this.proveedor.setCellValueFactory(data ->
                new ReadOnlyStringWrapper(data.getValue().get(5)));

        this.stockRecomendado.setCellValueFactory(data ->
                new ReadOnlyStringWrapper(data.getValue().get(6)));

        if (!product.isEmpty()) {
            try {
                tableView.setItems(getData());

            } catch (TreeException e) {
                throw new RuntimeException(e);
            }
        }

     /*   alert = FXUtility.alert("Menu Reporte", "Desplay Reporte");
        alert.setAlertType(Alert.AlertType.ERROR);*/
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

            arrayList.add(String.valueOf(p.getMinimunStock()));
            reporte.add(String.valueOf(p.getMinimunStock()));


            for (int j = 0; j < supplierName.size(); j++) {//Agarra ID de la tabla supplier y lo compara con IDsupplier de la tabla Product, para saber el nombre del proveedor del libro
                Supplier s = (Supplier) supplierName.get(j);
                if (s.getID().equals(p.getSupplierID())) {
                    arrayList.add(s.getName());
                    reporte.add(s.getName());

                    arrayList.add(String.valueOf(previsondemanda(p,5,s.getPlazoEntrega())));
                    reporte.add(String.valueOf(previsondemanda(p,5,s.getPlazoEntrega())));
                }
            }
            /*for (int j = 0; j < orderDatail.size(); j++) {
                OrderDetail orderD = (OrderDetail) orderDatail.get(j);
                arrayList.add(String.valueOf(previsondemanda(p,5, orderD.getPlazoEntrega())));
                reporte.add(String.valueOf(previsondemanda(p,5, orderD.getPlazoEntrega())));
            }*/
            data.add(arrayList);
        }
        return data;
    }


    private int previsondemanda(Product product, int demandaDiaria, int plazoEntrga){
        //Punto de pedido = (demanda diaria * plazo de entrega) + stock de seguridad(stock min)
        //Stock de segruidad = P máximo de entrega - Plazo de entrega) * Demanda diaria

        int puntoPedido = 0;
        int stockSeguridad = product.getMinimunStock();

        puntoPedido = (demandaDiaria * plazoEntrga) + stockSeguridad;

        return puntoPedido;
    }

    @FXML
    void actualizarInventarioOnAction(ActionEvent event) {

    }

    @FXML
    void generarPrevisionDemandaOnAction(ActionEvent event) {

    }

}
