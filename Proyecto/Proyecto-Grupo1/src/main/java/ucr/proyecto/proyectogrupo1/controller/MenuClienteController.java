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
import javafx.scene.layout.BorderPane;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import ucr.proyecto.proyectogrupo1.HelloApplication;
import ucr.proyecto.proyectogrupo1.TDA.AVL;
import ucr.proyecto.proyectogrupo1.TDA.ListException;
import ucr.proyecto.proyectogrupo1.TDA.TreeException;
import ucr.proyecto.proyectogrupo1.domain.Product;
import ucr.proyecto.proyectogrupo1.domain.Sale;
import ucr.proyecto.proyectogrupo1.domain.SaleDetail;
import ucr.proyecto.proyectogrupo1.util.Utility;

import java.io.IOException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class MenuClienteController {

    @FXML
    private BorderPane bp;

    @FXML
    private TextField txtBuscar;

    @FXML
    private TableColumn<List<String>, Image> columnLibro;
    @FXML
    private TableColumn<List<String>, String> columnDetalles;
    private AVL product;
    private ObservableList<List<String>> selectedItems;
    @FXML
    private TableView tableView;
    @FXML
    private TableColumn<List<String>, String> columnCodigo;
    @FXML
    private Text textID;
    private AVL sale;
    private AVL saleDetail;
    private LocalDate hoy;

    @FXML
    public void initialize() throws ListException, TreeException {
        hoy = LocalDate.now();
        // Configurar el modo de selección múltiple
        tableView.getSelectionModel().setSelectionMode(SelectionMode.MULTIPLE);
        selectedItems = tableView.getSelectionModel().getSelectedItems();

        sale = Utility.getSale();
        saleDetail = Utility.getSaleDetail();
        product = Utility.getProductAVL();

        textID.setText("ID: " + Utility.getIDClient());

        this.columnCodigo.setCellValueFactory(data ->
                new ReadOnlyStringWrapper(data.getValue().get(0)));

        this.columnLibro.setCellValueFactory(data ->
                new ReadOnlyObjectWrapper<>(new Image(data.getValue().get(1))));

        this.columnDetalles.setCellValueFactory(data ->
                new ReadOnlyStringWrapper(data.getValue().get(2)));

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
            arrayList.add(p.getID());
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
    void carritoOnAction(ActionEvent event) throws TreeException {
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
    void agregarOnAction(ActionEvent event) throws TreeException {
        boolean encontrado = false;
        sale = Utility.getSale();
        saleDetail = Utility.getSaleDetail();

        for (List<String> s : selectedItems) {//de todos los productos seleccionados
            String IDProduct = s.get(0);
            try {
                if (!saleDetail.isEmpty() && !sale.isEmpty())
                    for (int i = 0; i < saleDetail.size(); i++) {
                        SaleDetail newSaleDetail = (SaleDetail) saleDetail.get(i);
                        String product = newSaleDetail.getProductID();

                        if (product.equalsIgnoreCase(IDProduct)) {//si hay un SaleDetail que tenga el mismo producto seleccionado, hay que revizar si es del mismo cliente
                            Integer saleID = newSaleDetail.getSaleID();//para encontrar su tabla Sale
                            for (int j = 0; j < sale.size(); j++) {
                                Sale newSale = (Sale) sale.get(j);
                                //si la fk de SaleDetail es igual al pk de Sale, hay que ver si es del mismo cliente
                                if (saleID == newSale.getID() && newSale.getCustomerID() == Integer.parseInt(textID.getText().substring(3).trim())) {
                                    encontrado = true;

                                    saleDetail.remove(newSaleDetail);
                                    newSaleDetail.setQuantity(newSaleDetail.getQuantity() + 1);
                                    saleDetail.add(newSaleDetail);

                                    sale.remove(newSale);
                                    newSale.setSaleDate(hoy);
                                    sale.add(newSale);

                                    //actualizamos
                                    Utility.setSaleDetail(saleDetail);
                                }
                            }
                        }
                    }
                if (!encontrado) {
                    double precioLibro = 0;

                    Sale newSale = new Sale( //llenar Sale
                            getIDSale(),
                            hoy,
                            Integer.parseInt(textID.getText().substring(3).trim()),
                            IDProduct);
                    sale.add(newSale);
                    Utility.setSale(sale);

                    for (int i = 0; i < product.size(); i++) {
                        Product newProduct = (Product) product.get(i);
                        if (IDProduct.equalsIgnoreCase(newProduct.getID())) {
                            precioLibro = newProduct.getPrice();
                        }
                    }

                    SaleDetail newSaleDetail = new SaleDetail(newSale.getID(), IDProduct, 1, precioLibro);
                    saleDetail.add(newSaleDetail);
                    Utility.setSaleDetail(saleDetail);
                }
            } catch (TreeException e) {
                throw new RuntimeException(e);
            }
            Utility.setSale(sale);
        }
    }

    private Integer getIDSale() throws TreeException {
        Integer id = 0;
        do {
            Random random = new Random();
            id = random.nextInt(Integer.MAX_VALUE);  // Genera un número aleatorio dentro del rango de Integer
        } while (existeIDSale(id));
        return id;
    }

    private boolean existeIDSale(Integer id) throws TreeException {
        sale = Utility.getSale();
        saleDetail = Utility.getSaleDetail();
        boolean existe = false;
        if (!sale.isEmpty()) {
            for (int i = 0; i < sale.size(); i++) {
                Sale s = (Sale) sale.get(i);
                if (id == s.getID()) {
                    existe = true;
                    break;
                }
            }
        }
        return existe;
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
