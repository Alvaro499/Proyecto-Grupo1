package ucr.proyecto.proyectogrupo1.controller;

import javafx.beans.property.ReadOnlyObjectWrapper;
import javafx.beans.property.ReadOnlyStringWrapper;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextArea;
import javafx.scene.image.Image;
import ucr.proyecto.proyectogrupo1.TDA.AVL;
import ucr.proyecto.proyectogrupo1.TDA.HeaderLinkedQueue;
import ucr.proyecto.proyectogrupo1.TDA.QueueException;
import ucr.proyecto.proyectogrupo1.TDA.TreeException;
import ucr.proyecto.proyectogrupo1.domain.Product;
import ucr.proyecto.proyectogrupo1.domain.Supplier;
import ucr.proyecto.proyectogrupo1.util.Utility;

import java.util.ArrayList;
import java.util.List;

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

    private AVL product;

    private HeaderLinkedQueue headerLinkedQueue = new HeaderLinkedQueue();
    private HeaderLinkedQueue headerLinkedQueue2 = new HeaderLinkedQueue();

    private double costoTotalTProductos;

    @FXML
    private TextArea textAreaCosto;


    @FXML
    public void initialize() {
        product = Utility.getProductAVL();
        supplierName = Utility.getSupplierAVL();

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
            } catch (TreeException e) {
                throw new RuntimeException(e);
            }
        }
    }

    private ObservableList<List<String>> getData() throws TreeException {
        ObservableList<List<String>> data = FXCollections.observableArrayList();


        for (int i = 0; i < product.size(); i++) {
            List<String> arrayList = new ArrayList<>();
            Product p = (Product) product.get(i);
            arrayList.add(String.valueOf(p.getID()));
            arrayList.add(p.getName());
            arrayList.add("₡" + (p.getPrice()));
            arrayList.add(String.valueOf(p.getCurrentStock()));
            for (int j = 0; j < supplierName.size(); j++) {//Agarra ID de la tabla supplier y lo compara con IDsupplier de la tabla Product, para saber el nombre del proveedor del libro
                Supplier s = (Supplier) supplierName.get(j);
                if (s.getID().equals(p.getSupplierID()))
                    arrayList.add(s.getName());
            }
            try {
                headerLinkedQueue.enQueue(p.getPrice() * p.getCurrentStock());
                arrayList.add("₡" + headerLinkedQueue.deQueue());
            } catch (QueueException e) {
                throw new RuntimeException(e);
            }

            data.add(arrayList);
        }
        return data;
    }


    @FXML
    void calcularCostoOnAction(ActionEvent event) throws TreeException {
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


        } catch (QueueException e) {
            throw new RuntimeException(e);
        }
    }


}
