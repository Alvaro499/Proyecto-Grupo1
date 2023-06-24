package ucr.proyecto.proyectogrupo1.controller;

import javafx.beans.property.ReadOnlyObjectWrapper;
import javafx.beans.property.ReadOnlyStringWrapper;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
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

public class MantenimientoProductosController {

    @FXML
    private TableView<List<String>> tableView;
    @FXML
    private TableColumn<List<String>, Image> img;
    @FXML
    private TableColumn<List<String>, String> name;
    @FXML
    private TableColumn<List<String>, String> price;
    @FXML
    private TableColumn<List<String>, String> stock;
    @FXML
    private TableColumn<List<String>, String> Stockmin;
    @FXML
    private TableColumn<List<String>, String> supplier;
    @FXML
    private TableColumn<List<String>, String> dec;
    private AVL product;
    private AVL supplierName;
    @FXML
    private TextField fieldID;
    private TextInputDialog dialog;
    private Alert alert;
    private ObservableList<List<String>> selectedItems;

    @FXML
    public void initialize() throws ListException, TreeException {
        product = Utility.getProductAVL();
        supplierName = Utility.getSupplierAVL();

        selectedItems = tableView.getSelectionModel().getSelectedItems();

        this.img.setCellValueFactory(data ->
                new ReadOnlyObjectWrapper<>(new Image(data.getValue().get(0))));

        this.name.setCellValueFactory(data ->
                new ReadOnlyStringWrapper(data.getValue().get(1)));

        this.dec.setCellValueFactory(data ->
                new ReadOnlyStringWrapper(data.getValue().get(2)));

        this.price.setCellValueFactory(data ->
                new ReadOnlyStringWrapper(data.getValue().get(3)));

        this.stock.setCellValueFactory(data ->
                new ReadOnlyStringWrapper(data.getValue().get(4)));

        this.Stockmin.setCellValueFactory(data ->
                new ReadOnlyStringWrapper(data.getValue().get(5)));

        this.supplier.setCellValueFactory(data ->
                new ReadOnlyStringWrapper(data.getValue().get(6)));

        this.img.setCellFactory(col -> new ImageTableCell<>());
        if (!product.isEmpty()) {
            tableView.setItems(getData());
        }
        alert = FXUtility.alert("Menu Productos", "Desplay Productos");
        alert.setAlertType(Alert.AlertType.ERROR);
    }

    private ObservableList<List<String>> getData() throws TreeException {
        ObservableList<List<String>> data = FXCollections.observableArrayList();
        for (int i = 0; i < product.size(); i++) {
            List<String> arrayList = new ArrayList<>();
            Product p = (Product) product.get(i);
            arrayList.add(p.getUrl_img());
            arrayList.add(p.getName());
            arrayList.add(p.getDescription());
            arrayList.add("₡" + String.valueOf(p.getPrice()));
            arrayList.add(String.valueOf(p.getCurrentStock()));
            arrayList.add(String.valueOf(p.getMinimunStock()));

            for (int j = 0; j < supplierName.size(); j++) {//Agarra ID de la tabla supplier y lo compara con IDsupplier de la tabla Product, para saber el nombre del proveedor del libro
                Supplier s = (Supplier) supplierName.get(j);
                if (s.getID().equals(p.getSupplierID()))
                    arrayList.add(s.getName());
            }
            data.add(arrayList);
        }
        return data;
    }

    @FXML
    void btnAgregarNuevoProducto(ActionEvent event) {
        FXMLLoader fxmlLoader = new FXMLLoader(HelloApplication.class.getResource("newProduct.fxml"));
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
        if (!product.isEmpty()) {
            try {
                tableView.setItems(getData());
            } catch (TreeException e) {
                throw new RuntimeException(e);
            }
        }
    }

    @FXML
    void btnBuscarCliente(ActionEvent event) {

    }


    @FXML
    void btnEliminar(ActionEvent event) {
        product = Utility.getProductAVL();
        for (List<String> s : selectedItems) {
            try {
                String nombre = s.get(1);
                System.out.println(nombre);
                Product newProduct = getProduct(nombre);//obtener el producto con el mismo nombre
                product.remove(newProduct);
                Utility.setProductAVL(product);

                alert.setHeaderText("The product: ");
                alert.setContentText(nombre + " was delete");
                alert.setAlertType(Alert.AlertType.INFORMATION);
                alert.show();
            } catch (TreeException e) {
                throw new RuntimeException(e);
            }
        }
        tableView.getItems().clear();
        if (!product.isEmpty()) {
            try {
                tableView.setItems(getData());
            } catch (TreeException e) {
                throw new RuntimeException(e);
            }
        }
    }

    private Product getProduct(String nombre) throws TreeException {
        product = Utility.getProductAVL();
        Product p = null;
        Integer n = product.size();
        for (int i = 0; i < n; i++) {
            p = (Product) product.get(i);
            if (p.getName().trim().equalsIgnoreCase(nombre.trim())) {
                return p;
            }
        }
        return p;
    }

    private static class ImageTableCell<S> extends TableCell<S, Image> {
        private final ImageView imageView;

        public ImageTableCell() {
            this.imageView = new ImageView();
            this.imageView.setFitHeight(300); // Ajusta la altura de la imagen
            this.imageView.setFitWidth(200); // Ajusta el ancho de la imagen
            setGraphic(imageView);
        }

        @FXML
        protected void updateItem(Image image, boolean empty) {
            super.updateItem(image, empty);

            if (image == null || empty) {
                imageView.setImage(null);
            } else {
                imageView.setImage(image);
            }
        }
    }
}
