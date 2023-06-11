package ucr.proyecto.proyectogrupo1.controller;

import javafx.beans.property.ReadOnlyObjectWrapper;
import javafx.beans.property.ReadOnlyStringWrapper;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.TableCell;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
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

    private AVL product;
    private AVL supplierName;

    @FXML
    public void initialize() throws ListException, TreeException {
        product = Utility.getProductAVL();
        supplierName = Utility.getSupplierAVL();

        this.img.setCellValueFactory(data ->
                new ReadOnlyObjectWrapper<>(new Image(data.getValue().get(0))));

        this.name.setCellValueFactory(data ->
                new ReadOnlyStringWrapper(data.getValue().get(1)));

        this.price.setCellValueFactory(data ->
                new ReadOnlyStringWrapper(data.getValue().get(2)));

        this.stock.setCellValueFactory(data ->
                new ReadOnlyStringWrapper(data.getValue().get(3)));

        this.Stockmin.setCellValueFactory(data ->
                new ReadOnlyStringWrapper(data.getValue().get(4)));

        this.supplier.setCellValueFactory(data ->
                new ReadOnlyStringWrapper(data.getValue().get(5)));

        this.img.setCellFactory(col -> new ImageTableCell<>());

        if (!product.isEmpty()) {
            tableView.setItems(getData());
        }
    }

    private ObservableList<List<String>> getData() throws TreeException {
        ObservableList<List<String>> data = FXCollections.observableArrayList();
        for (int i = 0; i < product.size(); i++) {
            List<String> arrayList = new ArrayList<>();
            Product p = (Product) product.get(i);
            arrayList.add(p.getUrl_img());
            arrayList.add(p.getName());
            arrayList.add(String.valueOf(p.getPrice()));
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
    }

    private static class ImageTableCell<S> extends TableCell<S, Image> {
        private final ImageView imageView;

        public ImageTableCell() {
            this.imageView = new ImageView();
            this.imageView.setFitHeight(300); // Ajusta la altura de la imagen
            this.imageView.setFitWidth(200); // Ajusta el ancho de la imagen
            setGraphic(imageView);
        }

        @Override
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
