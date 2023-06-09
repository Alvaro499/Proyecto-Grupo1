package ucr.proyecto.proyectogrupo1.controller;

import javafx.beans.property.ReadOnlyObjectWrapper;
import javafx.beans.property.ReadOnlyStringWrapper;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.stage.Stage;
import ucr.proyecto.proyectogrupo1.HelloApplication;
import ucr.proyecto.proyectogrupo1.TDA.AVL;
import ucr.proyecto.proyectogrupo1.TDA.ListException;
import ucr.proyecto.proyectogrupo1.TDA.TreeException;
import ucr.proyecto.proyectogrupo1.domain.Binnacle;
import ucr.proyecto.proyectogrupo1.domain.Customer;
import ucr.proyecto.proyectogrupo1.domain.Product;
import ucr.proyecto.proyectogrupo1.domain.Supplier;
import ucr.proyecto.proyectogrupo1.util.FXUtility;
import ucr.proyecto.proyectogrupo1.util.Utility;

import java.io.IOException;
import java.time.LocalDateTime;
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
    @FXML
    private TableColumn<List<String>,String> idTituloColumn;
    private AVL product;
    private AVL supplierName;
    @FXML
    private TextField fieldID;
    private TextInputDialog dialog;
    private Alert alert;
    private AVL bitacora;
    private ObservableList<List<String>> selectedItems;

    @FXML
    public void initialize() throws ListException, TreeException {
        product = Utility.getProductAVL();
        supplierName = Utility.getSupplierAVL();

        bitacora = Utility.getBinnacle();

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
        this.idTituloColumn.setCellValueFactory(data ->
                new ReadOnlyStringWrapper(data.getValue().get(7)));

        idTituloColumn.setVisible(false);
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
            arrayList.add(p.getID());//-->ID del libro
            data.add(arrayList);
        }
        return data;
    }

    @FXML
    void btnAgregarNuevoProducto(ActionEvent event) {
        LocalDateTime fecha = LocalDateTime.now();
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
                bitacora.add(new Binnacle(String.valueOf(fecha.withNano(0)), Utility.getIDClient(),
                        "Se añade un nuevo producto"));
                Utility.setBinnacle(bitacora);
            } catch (TreeException e) {
                throw new RuntimeException(e);
            }
        }
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


    @FXML
    void btnEliminar(ActionEvent event) {
        LocalDateTime fecha = LocalDateTime.now();
        product = Utility.getProductAVL();
        for (List<String> s : selectedItems) {
            try {
                String nombre = s.get(1);
                System.out.println(nombre);
                Product newProduct = getProduct(nombre);//obtener el producto con el mismo nombre
                product.remove(newProduct);
                Utility.setProductAVL(product);
                bitacora.add(new Binnacle(String.valueOf(fecha.withNano(0)), Utility.getIDClient(),
                        "Se elimina un producto"));
                Utility.setBinnacle(bitacora);
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

    @FXML
    void btnActualizar(ActionEvent event) throws ListException, TreeException, IOException {
        selectedItems = tableView.getSelectionModel().getSelectedItems();

        //Si no hay nada seleccionado, no abrir la ventana
        if (selectedItems != null || !selectedItems.isEmpty()){

            System.out.println("Orden de productos: "+selectedItems);
            String name = selectedItems.get(0).get(1);
            String detail = selectedItems.get(0).get(2);
            String price = selectedItems.get(0).get(3);
            String stockMin = selectedItems.get(0).get(5);
            String supplier = selectedItems.get(0).get(6);
            String code = selectedItems.get(0).get(7);

            FXMLLoader fxmlLoader = new FXMLLoader(HelloApplication.class.getResource("updateProduct.fxml"));
            Parent root = fxmlLoader.load();


             UpdateProductController updateProductController = fxmlLoader.getController();

            updateProductController.setProduct(name,detail,price,stockMin,supplier,code);

            Scene scene = new Scene(root);
            Stage stage = new Stage();
            stage.setScene(scene);
            stage.setResizable(false);
            stage.show();

            updateProductController.initializeAux();

//            if (!stage.isShowing()){
//                initialize();
//            }
        }else{
            alert.setAlertType(Alert.AlertType.INFORMATION);
            alert.setContentText("Por favor seleccione alguno de los prodcutos");
            alert.showAndWait();
        }
    }

    @FXML
    void btnRefrescar(ActionEvent event) throws ListException, TreeException {

        initialize();
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
            LocalDateTime fecha = LocalDateTime.now();
            super.updateItem(image, empty);

            if (image == null || empty) {
                imageView.setImage(null);
            } else {
                imageView.setImage(image);
            }
        }
    }
}
