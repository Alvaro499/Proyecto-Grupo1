package ucr.proyecto.proyectogrupo1.controller;

import javafx.beans.property.ReadOnlyObjectWrapper;
import javafx.beans.property.ReadOnlyStringWrapper;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.BorderPane;
import javafx.scene.text.Text;
import ucr.proyecto.proyectogrupo1.TDA.AVL;
import ucr.proyecto.proyectogrupo1.TDA.ListException;
import ucr.proyecto.proyectogrupo1.TDA.SinglyLinkedList;
import ucr.proyecto.proyectogrupo1.TDA.TreeException;
import ucr.proyecto.proyectogrupo1.domain.Customer;
import ucr.proyecto.proyectogrupo1.domain.Product;
import ucr.proyecto.proyectogrupo1.domain.Sale;
import ucr.proyecto.proyectogrupo1.domain.SaleDetail;
import ucr.proyecto.proyectogrupo1.email.EnvioCorreos;
import ucr.proyecto.proyectogrupo1.util.Utility;

import java.util.ArrayList;
import java.util.List;

public class ClienteCarritoController {

    @FXML
    private BorderPane bp;

    @FXML
    private TableColumn<List<String>, String> columnCantidad;

    @FXML
    private TableColumn<List<String>, String> columnCantidadDisponible;

    @FXML
    private TableColumn<List<String>, String> columnDetalles;

    @FXML
    private TableColumn<List<String>, String> columnIDSales;

    @FXML
    private TableColumn<List<String>, Image> columnIMGLibro;

    @FXML
    private TableColumn<List<String>, String> columnPrecio;

    @FXML
    private ScrollPane scrollPaneTotal;

    @FXML
    private TableView<List<String>> tableView;
    private Integer idClient;
    private AVL sale;
    private AVL saleDetail;
    private AVL product;
    //private Product newProduct;
    @FXML
    private Text txtCompraTotal;
    //Obtener las filas seleccionadas
    private ObservableList<List<String>> selectedItems;
    // Obtener la lista de elementos de la TableView
    private ObservableList<List<String>> tables;
    private SinglyLinkedList client; //table client

    @FXML
    public void initialize() throws TreeException {
        idClient = Utility.getIDClient();
        sale = Utility.getSale();
        saleDetail = Utility.getSaleDetail();
        product = Utility.getProductAVL();
        client = Utility.getClientSinglyLinkedList();

        // Configurar el modo de selección múltiple
        tableView.getSelectionModel().setSelectionMode(SelectionMode.MULTIPLE);
        selectedItems = tableView.getSelectionModel().getSelectedItems();

        this.columnIDSales.setCellValueFactory(data ->
                new ReadOnlyStringWrapper(data.getValue().get(0)));
        this.columnIMGLibro.setCellValueFactory(data ->
                new ReadOnlyObjectWrapper<>(new Image(data.getValue().get(1))));
        this.columnDetalles.setCellValueFactory(data ->
                new ReadOnlyStringWrapper(data.getValue().get(2)));
        this.columnCantidad.setCellValueFactory(data ->
                new ReadOnlyStringWrapper(data.getValue().get(3)));
        this.columnCantidadDisponible.setCellValueFactory(data ->
                new ReadOnlyStringWrapper(data.getValue().get(4)));
        this.columnPrecio.setCellValueFactory(data ->
                new ReadOnlyStringWrapper(data.getValue().get(5)));

        this.columnIMGLibro.setCellFactory(col -> new ImageTableCell<>());

        if (!sale.isEmpty()) {
            tableView.setItems(getData());
        }
        tables = tableView.getItems();
    }

    private ObservableList<List<String>> getData() throws TreeException {
        Double total = 0.0;
        ObservableList<List<String>> data = FXCollections.observableArrayList();
        for (int i = 0; i < saleDetail.size(); i++) {//buscamos en todas las tablas SaleDetail
            SaleDetail newSaleDetail = (SaleDetail) saleDetail.get(i);

            if (!newSaleDetail.getOrder_canceled())
                for (int j = 0; j < sale.size(); j++) {//buscamos la tabla Sale relacionada a la tabla SaleDetail
                    Sale newSale = (Sale) sale.get(j);
                    if (newSaleDetail.getSaleID().equals(newSale.getID()) && newSale.getCustomerID().equals(idClient)) {//hay que escoger solo los que les pertenece al cliente

                        for (int k = 0; k < product.size(); k++) {//buscamos el libro
                            Product newProduct = (Product) product.get(k);
                            if (newSaleDetail.getProductID().equalsIgnoreCase(newProduct.getID())) {
                                List<String> arratList = new ArrayList<>();
                                arratList.add(String.valueOf(newSale.getID()));
                                arratList.add(newProduct.getUrl_img());
                                arratList.add(newProduct.getDescription());
                                arratList.add(String.valueOf(newSaleDetail.getQuantity()));
                                arratList.add(String.valueOf(newProduct.getCurrentStock()));
                                arratList.add(String.valueOf(newProduct.getPrice()));
                                data.add(arratList);
                                total += (newProduct.getPrice() * newSaleDetail.getQuantity());
                                txtCompraTotal.setText("₡ " + total);
                                break;
                            }
                        }
                    }
                }
        }
        return data;
    }

    @FXML
    void comprarOnAction(ActionEvent event) throws TreeException, ListException {
        String correoContenido = "";
        EnvioCorreos correo = new EnvioCorreos();
        for (List<String> table : tables) {
            //Buscamos en SaleDetail
            SaleDetail newSaleDetail = getSaleDetail(table.get(0).trim());
            if (newSaleDetail != null) {// && !newSaleDetail.getOrder_canceled()) { //si lo encuentra, Buscamos su tabla Sale correspondiente
                Sale newSale = getSale(table.get(0).trim());
                Product newProduct = getProduct(newSaleDetail.getProductID());
                if (newSale != null) {
                    if (disponibilidad(newSaleDetail.getProductID(), newSaleDetail.getQuantity())) { //Siempre que haya disponibilidad
                        //Eliminamos
                        newSaleDetail.setOrder_canceled(true);
                        //saleDetail.remove(newSaleDetail);
                        //sale.remove(newSale);
                        //Actualizamos
                        Utility.setSaleDetail(saleDetail);
                        Utility.setSale(sale);
                        //Restamos la cantidad disponible
                        restarCantidadDisponible(newSaleDetail.getProductID(), newSaleDetail.getQuantity());
                        //Creamos el correo factura
                        Customer newCliente = getCustomer(newSale.getCustomerID());
                        correo.setEmailTo(newCliente.getEmail());
                        correo.setSubject("Factura de Laberinto de Libros");
                        correoContenido += "<br>Libro: " + newProduct.getName() + "<br>" + newProduct.getDescription() + "<br>Cantidad: " + Integer.parseInt(table.get(3)) + "<br>Precio individual: " + newProduct.getPrice();
                    }
                }
            } else {
                //no se encontro el SaleDetail
            }
        }
        correoContenido += "<p>Gracias por comprar en nuestra tienda!</p><br>Precio total: " + txtCompraTotal.getText() + "<br>Este correo fue enviado de manera automatica, por favor no responder.";
        correo.setContent(correoContenido);
        correo.sendEmail();
    }

    private Sale getSale(String id) throws TreeException {
        Sale newSale = null;
        Integer n = sale.size();
        for (int i = 0; i < n; i++) {
            newSale = (Sale) sale.get(i);
            if (String.valueOf(newSale.getID()).trim().equalsIgnoreCase(id.trim())) {
                return newSale;
            }
        }
        return newSale;
    }

    private SaleDetail getSaleDetail(String id) throws TreeException {
        SaleDetail newSaleDetail = null;
        Integer n = saleDetail.size();
        for (int i = 0; i < n; i++) {
            newSaleDetail = (SaleDetail) saleDetail.get(i);
            if (String.valueOf(newSaleDetail.getSaleID()).trim().equalsIgnoreCase(id.trim())) {
                return newSaleDetail;
            }
        }
        return newSaleDetail;
    }

    private Product getProduct(String id) throws TreeException {
        Product newProduct = null;
        Integer n = product.size();
        for (int i = 0; i < n; i++) {
            newProduct = (Product) product.get(i);
            if (String.valueOf(newProduct.getID()).trim().equalsIgnoreCase(id.trim())) {
                return newProduct;
            }
        }
        return newProduct;
    }

    private Customer getCustomer(Integer id) throws ListException {
        Customer newCustomer = null;
        Integer n = client.size();
        for (int i = 1; i <= n; i++) {
            newCustomer = (Customer) client.getNode(i).data;
            if (newCustomer.getID() == id) {
                return newCustomer;
            }
        }
        return newCustomer;
    }

    private void restarCantidadDisponible(String id, Integer cantidad) throws TreeException {
        for (int i = 0; i < product.size(); i++) {
            Product p = (Product) product.get(i);
            if (id.equalsIgnoreCase(p.getID())) {//buscar el libro
                product.remove(p);
                p.setCurrentStock(p.getCurrentStock() - cantidad);
                product.add(p);
                Utility.setProductAVL(product);
                break;
            }
        }
    }

    private boolean disponibilidad(String id, Integer cantidad) throws TreeException {
        boolean disponible = false;
        for (int i = 0; i < product.size(); i++) {
            Product p = (Product) product.get(i);
            if (p.getID().equalsIgnoreCase(id) && p.getCurrentStock() >= cantidad) {//buscamos el libro y si lo encontramos, verificamos que exista la cantidad requerida
                disponible = true;
                break;
            }
        }
        return disponible;
    }

    @FXML
    void eliminarOnAction(ActionEvent event) throws TreeException {
        for (List<String> s : selectedItems) {//de todos los productos seleccionados
            for (int i = 0; i < saleDetail.size(); i++) {
                SaleDetail removeSaleDetail = (SaleDetail) saleDetail.get(i);
                if (String.valueOf(removeSaleDetail.getSaleID()).equalsIgnoreCase(s.get(0).trim()) && Integer.parseInt(s.get(3)) <= 1) {//Si la cantidad es 1, hay que borrar Sale y SaleDetail
                    saleDetail.remove(removeSaleDetail);
                    for (int j = 0; j < sale.size(); j++) {//hay que buscar el Sale que corresponde para borrarlo
                        Sale removeSale = (Sale) sale.get(j);
                        if (removeSale.getID().equals(removeSaleDetail.getSaleID())) {
                            sale.remove(removeSale);
                            break;
                        }
                    }
                    break;
                } else if (String.valueOf(removeSaleDetail.getSaleID()).equalsIgnoreCase(s.get(0).trim())) {//Si la cantidad de personas es mayor a 1, solo bajamos el quantity
                    removeSaleDetail.setQuantity(removeSaleDetail.getQuantity() - 1);
                    break;
                }
            }
        }
        Utility.setSale(sale);
        Utility.setSaleDetail(saleDetail);
        if (!sale.isEmpty()) {
            tableView.setItems(getData());
        }
    }

    @FXML
    void exitOnAction(ActionEvent event) {
        //System.exit(0);
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
