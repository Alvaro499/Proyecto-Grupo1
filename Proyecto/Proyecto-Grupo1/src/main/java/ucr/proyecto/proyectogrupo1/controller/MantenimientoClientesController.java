package ucr.proyecto.proyectogrupo1.controller;

import javafx.beans.property.ReadOnlyObjectWrapper;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.stage.Stage;
import ucr.proyecto.proyectogrupo1.HelloApplication;
import ucr.proyecto.proyectogrupo1.TDA.*;
import ucr.proyecto.proyectogrupo1.domain.Binnacle;
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
    private ObservableList<List<String>> selectedItems;
    @FXML
    private TextField fieldID;
    private AVL bitacora;

    @FXML
    public void initialize() throws ListException, TreeException {
        // Configurar el modo de selección múltiple
        tableView.getSelectionModel().setSelectionMode(SelectionMode.MULTIPLE);

       /* LocalDateTime fecha = LocalDateTime.now();
        bitacora = Utility.getBinnacle();
        bitacora.add(new Binnacle(String.valueOf(fecha.withNano(0)), Utility.getIDClient(),"Calcular costo total"));
        Utility.setBinnacle(bitacora);*/


        login = Utility.getLoginCircularLinkedList();
        client = Utility.getClientSinglyLinkedList();

        selectedItems = tableView.getSelectionModel().getSelectedItems();

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
    void btnEliminarCliente(ActionEvent event) throws ListException {//revizar
        for (List<String> s : selectedItems) {
            int l = login.size();
            int c = client.size();
            for (int i = 1; i <= l; i++) {
                Security security = (Security) login.getNode(i).data;
                if (Integer.parseInt(s.get(0).trim()) == security.getCustomerID()) {
                    login.remove(security);
                    Utility.setLoginCircularLinkedList(login);
                    break;
                }
            }
            for (int i = 1; i <= c; i++) {
                Customer customer = (Customer) client.getNode(i).data;
                if (Integer.parseInt(s.get(0).trim()) == customer.getID()) {
                    client.remove(customer);
                    Utility.setClientSinglyLinkedList(client);
                    break;
                }
            }
            client = Utility.getClientSinglyLinkedList();
            login = Utility.getLoginCircularLinkedList();
        }
        tableView.getItems().clear();
        if (!client.isEmpty()) {
            tableView.setItems(getData());
        }
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
    public void btnBuscarCliente(ActionEvent actionEvent) throws ListException {
        //Capturamos la info del buscador
        String searchText = fieldID.getText().toLowerCase();

        if (searchText.isEmpty()) {
            tableView.setItems(getData()); // Restaurar los datos originales
            return;
        }

        //Creamos una lista que va guardando aquellos datos que conicidan con los del textField buscador
        ObservableList<List<String>> filterList = FXCollections.observableArrayList();

        //Recorremos las listas de datos de cada fila del tablewView, ya que en el getData este se settea con ArrayLists
        //List<String> newData = getData();
        for(List<String> nameFiltered : getData()){
            //Obtenemos el elemento que queremos capturar, en este caso el ID del empleado
            String actualName = nameFiltered.get(1).toLowerCase();

            //Comparamos el dato de la fila con el del textField
            if (actualName.contains(searchText)){
                //se actualiza la lista con los datos similares
                filterList.add(nameFiltered);
            }
        }
        //setteamos nuevamente el tableView
        tableView.setItems(filterList);
        //tableView.refresh();
        //https://www.w3schools.com/java/ref_string_contains.asphttps://www.w3schools.com/java/ref_string_contains.asp
        //https://www.youtube.com/watch?v=FeTrcNBVWtg
    }
}
