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
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;
import ucr.proyecto.proyectogrupo1.HelloApplication;
import ucr.proyecto.proyectogrupo1.TDA.AVL;
import ucr.proyecto.proyectogrupo1.TDA.ListException;
import ucr.proyecto.proyectogrupo1.TDA.TreeException;
import ucr.proyecto.proyectogrupo1.domain.Product;
import ucr.proyecto.proyectogrupo1.util.Utility;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class MenuClienteController {

    @FXML
    private BorderPane bp;

    @FXML
    private TextField txtBuscar;

    @FXML
    private TextField txtNombreUsuario;

    @FXML
    private TextField txtNombreUsuario1;
    @FXML
    private TableColumn<List<String>, Image> columnLibro;
    @FXML
    private TableColumn<List<String>, String> columnDetalles;
    private AVL product;
    @FXML
    private TableView<List<String>> tableView;

    @FXML
    public void initialize() throws ListException, TreeException {
        product = Utility.getProductAVL();

        this.columnLibro.setCellValueFactory(data ->
                new ReadOnlyObjectWrapper<>(new Image(data.getValue().get(0))));
        this.columnDetalles.setCellValueFactory(data ->
                new ReadOnlyStringWrapper(data.getValue().get(1)));

        this.columnLibro.setCellFactory(col -> new ImageTableCell<>());

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
            arrayList.add(p.getDescription() + "\n₡" + p.getPrice());
            data.add(arrayList);
        }
        return data;
    }
    @FXML
    void buscarOnAction(ActionEvent event) {

    }

    @FXML
    void carritoOnAction(ActionEvent event) {
        FXMLLoader fxmlLoader = new FXMLLoader(HelloApplication.class.getResource("clienteCarrito.fxml"));
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
    void reporteOnAction(ActionEvent event) {
        FXMLLoader fxmlLoader = new FXMLLoader(HelloApplication.class.getResource("reporteParaClientes.fxml"));
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
    void exitOnAction(ActionEvent event) {
        System.exit(0);
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
