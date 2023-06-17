package ucr.proyecto.proyectogrupo1.controller;

import javafx.beans.property.ReadOnlyObjectWrapper;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.stage.Stage;
import ucr.proyecto.proyectogrupo1.HelloApplication;
import ucr.proyecto.proyectogrupo1.TDA.AVL;
import ucr.proyecto.proyectogrupo1.TDA.ListException;
import ucr.proyecto.proyectogrupo1.TDA.TreeException;
import ucr.proyecto.proyectogrupo1.domain.Product;
import ucr.proyecto.proyectogrupo1.domain.Supplier;
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
    public void initialize() throws ListException, TreeException {
        product = Utility.getProductAVL();
        supplier = Utility.getSupplierAVL();

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

        if (!product.isEmpty()) {
            tableView.setItems(getData());
        }
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

}
