package ucr.proyecto.proyectogrupo1.controller;

import javafx.beans.property.ReadOnlyObjectWrapper;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import ucr.proyecto.proyectogrupo1.HelloApplication;
import ucr.proyecto.proyectogrupo1.TDA.AVL;
import ucr.proyecto.proyectogrupo1.TDA.ListException;
import ucr.proyecto.proyectogrupo1.TDA.TreeException;
import ucr.proyecto.proyectogrupo1.domain.Product;
import ucr.proyecto.proyectogrupo1.domain.Supplier;
import ucr.proyecto.proyectogrupo1.util.FXUtility;
import ucr.proyecto.proyectogrupo1.util.Utility;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class MantenimientoProveedorController {

    @FXML
    private TableView<List<String>> tableView;
    @FXML
    private TableColumn<List<String>, String> tableName;
    @FXML
    private TableColumn<List<String>, String> tablePhone;
    @FXML
    private TableColumn<List<String>, String> tableEmail;
    @FXML
    private TableColumn<List<String>, String> tableAddress;
    @FXML
    private TableColumn<List<String>, String> tableBook;
    private AVL product;
    private AVL supplier;
    @FXML
    private TextField fieldID;
    private Alert alert;
    private ObservableList<List<String>> selectedItems;

    @FXML
    public void initialize() throws ListException, TreeException {
        product = Utility.getProductAVL();
        supplier = Utility.getSupplierAVL();

        selectedItems = tableView.getSelectionModel().getSelectedItems();

        this.tableName.setCellValueFactory(data ->
                new ReadOnlyObjectWrapper<>(data.getValue().get(0)));
        this.tablePhone.setCellValueFactory(data ->
                new ReadOnlyObjectWrapper<>(data.getValue().get(1)));
        this.tableEmail.setCellValueFactory(data ->
                new ReadOnlyObjectWrapper<>(data.getValue().get(2)));
        this.tableAddress.setCellValueFactory(data ->
                new ReadOnlyObjectWrapper<>(data.getValue().get(3)));
        this.tableBook.setCellValueFactory(data ->
                new ReadOnlyObjectWrapper<>(data.getValue().get(4)));

        if (!supplier.isEmpty()) {
            tableView.setItems(getData());
        }

        alert = FXUtility.alert("Menu Proveedor", "Desplay Proveedor");
        alert.setAlertType(Alert.AlertType.ERROR);
    }

    private ObservableList<List<String>> getData() throws TreeException {
        ObservableList<List<String>> data = FXCollections.observableArrayList();
        for (int i = 0; i < supplier.size(); i++) {
            List<String> arrayList = new ArrayList<>();

            Supplier s = (Supplier) supplier.get(i);
            arrayList.add(s.getName());
            arrayList.add(String.valueOf(s.getPhoneNumber()));
            arrayList.add(s.getEmail());
            arrayList.add(s.getAddress());

            String book = "";
            int ID = s.getID();
            if (!product.isEmpty())
                for (int j = 0; j < product.size(); j++) {
                    Product p = (Product) product.get(j);
                    if (ID == p.getSupplierID()) {
                        book += p.getName() + "\n";
                    }
                }
            arrayList.add(book);
            data.add(arrayList);
        }
        return data;
    }

    @FXML
    void btnAgregarNuevoProveedor(ActionEvent event) {
        FXMLLoader fxmlLoader = new FXMLLoader(HelloApplication.class.getResource("newProveedor.fxml"));
        Scene scene = null;
        try {
            scene = new Scene(fxmlLoader.load());
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        Stage stage = new Stage();
        stage.setScene(scene);
        stage.setResizable(false);
        stage.show();
    }

    @FXML
    void btnBuscarCliente(ActionEvent event) {

    }

    @FXML
    void btnEliminar(ActionEvent event) throws TreeException {
        for (List<String> s : selectedItems) {
            Supplier supplier = getProveedor(s.get(0));
            try {
                if (getMercaderia(supplier.getID())) {
                    alert.setHeaderText("There are products from the supplier");
                    alert.setContentText("Please delete them before");
                    alert.setAlertType(Alert.AlertType.INFORMATION);
                    alert.show();
                } else {//si no hay mercaderia con ese proveedor, podemos borrar
                    AVL deleteSupplier = Utility.getSupplierAVL();
                    deleteSupplier.remove(supplier);
                    Utility.setSupplierAVL(deleteSupplier);

                    alert.setHeaderText("the supplier " + supplier.getName());
                    alert.setContentText("was eliminated");
                    alert.setAlertType(Alert.AlertType.INFORMATION);
                    alert.show();
                }
            } catch (TreeException e) {
                alert.setContentText("There was an error in the process");
                alert.showAndWait();
                throw new RuntimeException(e);
            }
        }
        if (!supplier.isEmpty()) {
            tableView.setItems(getData());
        }
    }

    private boolean getMercaderia(Integer ID) throws TreeException {
        AVL p = Utility.getProductAVL();
        if (!p.isEmpty()) {
            Integer n = p.size();
            for (int i = 0; i < n; i++) {
                Product product = (Product) p.get(i);
                if (product.getSupplierID().equals(ID)) {
                    return true;
                }
            }
        }
        return false;
    }

    private Supplier getProveedor(String Nombre) {
        supplier = Utility.getSupplierAVL();
        Supplier s = null;
        try {
            Integer n = supplier.size();
            for (int i = 0; i < n; i++) {
                s = (Supplier) supplier.get(i);
                if (s.getName().equalsIgnoreCase(Nombre)) {
                    return s;
                }
            }
        } catch (TreeException e) {
            throw new RuntimeException(e);
        }
        return s;
    }
}