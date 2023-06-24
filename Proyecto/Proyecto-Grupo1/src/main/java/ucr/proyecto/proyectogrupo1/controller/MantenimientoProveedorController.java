package ucr.proyecto.proyectogrupo1.controller;

import javafx.beans.property.ReadOnlyObjectWrapper;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
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
import ucr.proyecto.proyectogrupo1.domain.Customer;
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
    void btnBuscarCliente(ActionEvent event) throws TreeException {
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
            String actualName = nameFiltered.get(0).toLowerCase();

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
        tableView.getItems().clear();
        if (!supplier.isEmpty()) {
            tableView.setItems(getData());
        }
    }

    @FXML
    void btnActualizarProveedor(ActionEvent event) throws IOException, ListException, TreeException {

        selectedItems = tableView.getSelectionModel().getSelectedItems();

        //Si no hay nada seleccionado, no abrir la ventana
        if (selectedItems != null || !selectedItems.isEmpty()){

            System.out.println("Datos de selectedItems: " + selectedItems);
            //Integer id = Integer.valueOf(selectedItems.get(0).get(0));
            String name = selectedItems.get(0).get(0);
            Integer phone = Integer.valueOf(selectedItems.get(0).get(1));
            String email = selectedItems.get(0).get(2);
            String adress = selectedItems.get(0).get(3);
            //Integer delivery = Integer.valueOf(selectedItems.get(0).get(5));


            //Supplier auxSupplier = new Supplier(name,phone,email,adress);

            FXMLLoader fxmlLoader = new FXMLLoader(HelloApplication.class.getResource("updateSupplier.fxml"));
            Parent root = fxmlLoader.load();

            UpdateSupplierController updateSupplierController = fxmlLoader.getController();
            updateSupplierController.setSupplier(name,phone,email,adress);
            //updateSupplierController.setSupplier(auxSupplier);

            Scene scene = new Scene(root);
            Stage stage = new Stage();
            stage.setScene(scene);
            stage.setResizable(false);
            stage.show();

            updateSupplierController.initializeAux();

        }else{
            alert.setAlertType(Alert.AlertType.INFORMATION);
            alert.setContentText("Por favor seleccione alguno de los clientes");
            alert.showAndWait();
        }
    }

    @FXML
    void btnRefrescar(ActionEvent event) throws ListException, TreeException {
        initialize();
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