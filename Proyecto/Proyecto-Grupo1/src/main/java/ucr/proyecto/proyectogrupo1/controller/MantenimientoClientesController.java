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
import ucr.proyecto.proyectogrupo1.TDA.CircularLinkedList;
import ucr.proyecto.proyectogrupo1.TDA.ListException;
import ucr.proyecto.proyectogrupo1.TDA.SinglyLinkedList;
import ucr.proyecto.proyectogrupo1.TDA.TreeException;
import ucr.proyecto.proyectogrupo1.domain.Customer;
import ucr.proyecto.proyectogrupo1.domain.Security;
import ucr.proyecto.proyectogrupo1.util.Utility;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class MantenimientoClientesController {

    @FXML
    private TableView<List<String>> tableView;
    @FXML
    private TableColumn<List<String>, String> tableID;
    @FXML
    private TableColumn<List<String>, String> tableName;
    @FXML
    private TableColumn<List<String>, String> tablePhone;
    @FXML
    private TableColumn<List<String>, String> tableEmail;
    @FXML
    private TableColumn<List<String>, String> tableAddress;
    @FXML
    private TableColumn<List<String>, String> tableUser;
    private CircularLinkedList login; //table security
    private SinglyLinkedList client; //table client

    @FXML
    public void initialize() throws ListException, TreeException {
        login = Utility.getLoginCircularLinkedList();
        client = Utility.getClientSinglyLinkedList();

        //ObservableList<List<String>> selectedItems = tableView.getSelectionModel().getSelectedItems();

        this.tableID.setCellValueFactory(data ->
                new ReadOnlyObjectWrapper<>(data.getValue().get(0)));
        this.tableName.setCellValueFactory(data ->
                new ReadOnlyObjectWrapper<>(data.getValue().get(1)));
        this.tableUser.setCellValueFactory(data ->
                new ReadOnlyObjectWrapper<>(data.getValue().get(2)));
        this.tablePhone.setCellValueFactory(data ->
                new ReadOnlyObjectWrapper<>(data.getValue().get(3)));
        this.tableEmail.setCellValueFactory(data ->
                new ReadOnlyObjectWrapper<>(data.getValue().get(4)));
        this.tableAddress.setCellValueFactory(data ->
                new ReadOnlyObjectWrapper<>(data.getValue().get(5)));

        if (!client.isEmpty()) {
            tableView.setItems(getData());
        }
    }

    private ObservableList<List<String>> getData() throws ListException {
        ObservableList<List<String>> data = FXCollections.observableArrayList();
        for (int i = 1; i <= client.size(); i++) {
            List<String> arrayList = new ArrayList<>();
            Customer c = (Customer) client.getNode(i).data;
            arrayList.add(String.valueOf(c.getID()));
            arrayList.add(c.getName());
            for (int j = 1; j <= login.size(); j++) {
                Security s = (Security) login.getNode(j).data;
                if (c.getID().equals(s.getCustomerID())) {
                    arrayList.add(s.getUser());
                    break;
                }
            }
            arrayList.add(c.getPhoneNumber());
            arrayList.add(c.getEmail());
            arrayList.add(c.getAddress());
            data.add(arrayList);
        }
        return data;
    }

    @FXML
    void btnEliminarCliente(ActionEvent event) {

    }

    @FXML
    void btnAgregarNuevoCliente(ActionEvent event) {
        FXMLLoader fxmlLoader = new FXMLLoader(HelloApplication.class.getResource("newClient.fxml"));
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
    public void btnBuscarCliente(ActionEvent actionEvent) {
    }
}
