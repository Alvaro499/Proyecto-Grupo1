package ucr.proyecto.proyectogrupo1.controller;

import javafx.beans.property.ReadOnlyObjectWrapper;
import javafx.beans.property.ReadOnlyStringWrapper;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import ucr.proyecto.proyectogrupo1.TDA.AVL;
import ucr.proyecto.proyectogrupo1.TDA.BST;
import ucr.proyecto.proyectogrupo1.TDA.SinglyLinkedList;
import ucr.proyecto.proyectogrupo1.TDA.TreeException;
import ucr.proyecto.proyectogrupo1.domain.Binnacle;
import ucr.proyecto.proyectogrupo1.domain.Product;
import ucr.proyecto.proyectogrupo1.domain.Supplier;
import ucr.proyecto.proyectogrupo1.util.FXUtility;
import ucr.proyecto.proyectogrupo1.util.Utility;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

public class PrevisionDemandaController {

    @FXML
    private TableView<List<String>> tableView;
    @FXML
    private TableColumn<List<String>, String> idProducto;
    @FXML
    private TableColumn<List<String>, String> nombre;
    @FXML
    private TableColumn<List<String>, String> precio;
    @FXML
    private TableColumn<List<String>, String> stockActual;
    @FXML
    private TableColumn<List<String>, String> stockMin;
    @FXML
    private TableColumn<List<String>, String> proveedor;
    @FXML
    private TableColumn<List<String>, String> stockRecomendado;

    private Alert alert;
    private BST previsionDemandaBST = new BST();
    private AVL supplierName; //proveedor

    private AVL product;
    private boolean isCalculated = false;
    private AVL bitacora;

    private AVL saleDatail;
    private SinglyLinkedList cliente; //table client
    private Integer clienteID;
    private ArrayList<String> reporte;
    private ObservableList<List<String>> data;

    @FXML
    public void initialize() {
        this.alert = FXUtility.alert("", "");
        product = Utility.getProductAVL();
        supplierName = Utility.getSupplierAVL();
        saleDatail = Utility.getSaleDetail();
        cliente = Utility.getClientSinglyLinkedList();
        clienteID = Utility.getIDClient();
        reporte = new ArrayList<String>();
        bitacora = Utility.getBinnacle();

        this.idProducto.setCellValueFactory(data ->
                new ReadOnlyObjectWrapper<>(data.getValue().get(0)));

        this.nombre.setCellValueFactory(data ->
                new ReadOnlyStringWrapper(data.getValue().get(1)));

        this.precio.setCellValueFactory(data ->
                new ReadOnlyStringWrapper(data.getValue().get(2)));

        this.stockActual.setCellValueFactory(data ->
                new ReadOnlyStringWrapper(data.getValue().get(3)));
        this.stockMin.setCellValueFactory(data ->
                new ReadOnlyStringWrapper(data.getValue().get(4)));

        this.proveedor.setCellValueFactory(data ->
                new ReadOnlyStringWrapper(data.getValue().get(5)));

//        this.stockRecomendado.setCellValueFactory(data ->
//                new ReadOnlyStringWrapper(data.getValue().get(6)));

        if (!product.isEmpty()) {
            try {
                tableView.setItems(getData());

            } catch (TreeException e) {
                throw new RuntimeException(e);
            }
        }

        alert = FXUtility.alert("Menu Reporte", "Desplay Reporte");
        alert.setAlertType(Alert.AlertType.ERROR);
    }

    private ObservableList<List<String>> getData() throws TreeException {
        data = FXCollections.observableArrayList();


        for (int i = 0; i < product.size(); i++) {
            List<String> arrayList = new ArrayList<>();
            Product p = (Product) product.get(i);
            arrayList.add(String.valueOf(p.getID()));
            reporte.add(String.valueOf(p.getID()));

            arrayList.add(p.getName());
            reporte.add(p.getName());

            arrayList.add("₡" + (p.getPrice()));
            reporte.add("₡" + (p.getPrice()));

            arrayList.add(String.valueOf(p.getCurrentStock()));
            reporte.add(String.valueOf(p.getCurrentStock()));

            arrayList.add(String.valueOf(p.getMinimunStock()));
            reporte.add(String.valueOf(p.getMinimunStock()));

            Supplier proveedor = getSupplier(p.getSupplierID());
            arrayList.add(proveedor.getName());
            reporte.add(proveedor.getName());

            arrayList.add(String.valueOf(previsionDemanda(p, p.getVentaAlDia(), proveedor.getPlazoEntrega())));
            reporte.add(String.valueOf(previsionDemanda(p, p.getVentaAlDia(), proveedor.getPlazoEntrega())));
            //Para el arbol de prevision demanda, guardamos tanto la recomendacion de stcck como el id del producto
            previsionDemandaBST.add(previsionDemanda(p,p.getVentaAlDia(), proveedor.getPlazoEntrega())+"_"+p.getID());

            data.add(arrayList);
        }
        return data;
    }




    private int previsionDemanda(Product product, int demandaDiaria, int plazoEntrga) {
        //Punto de pedido = (demanda diaria * plazo de entrega) + stock de seguridad(stock min)
        //Stock de segruidad = P máximo de entrega - Plazo de entrega) * Demanda diaria

        int puntoPedido = 0;
        int stockSeguridad = product.getMinimunStock();

        puntoPedido = (demandaDiaria * plazoEntrga) + stockSeguridad;

        return puntoPedido + product.getCurrentStock();
    }

    @FXML
    void actualizarInventarioOnAction(ActionEvent event) throws TreeException {
        LocalDateTime fecha = LocalDateTime.now();
        if (isCalculated == true){

            for (int i = 0; i < previsionDemandaBST.size() ; i++) {

                //Obtenemos el id del producto y su prevision concatenados por un _ como string
                String previsionData = (String) previsionDemandaBST.get(i);
                String[] previsionID = previsionData.split("_");

                for (int j = 0; j < product.size(); j++) {

                    //Recorremos los productos 1 por 1 para comparar ID's
                    Product p = (Product) product.get(j);

                    //Si el ID del producto actual es  igual al ID del producto que esta en la
                    //TDA de previsiones, cambiamos su stock
                    if (previsionID[1].equals(p.getID())){
                        ((Product) product.get(j)).setCurrentStock(Integer.valueOf(previsionID[0]));
                    }
                }
            }
            bitacora.add(new Binnacle(String.valueOf(fecha.withNano(0)), Utility.getIDClient(),"Se actualizó el inventario"));
            Utility.setBinnacle(bitacora);
            alert.setAlertType(Alert.AlertType.INFORMATION);
            alert.setContentText("Stock actualizado");
            alert.showAndWait();
            isCalculated = false;
            //Actualizamos el archivo de productos con la TDA ya actualizada con el stock nuevo
            Utility.setProductAVL(product);
            initialize();


        }else{
            alert.setAlertType(Alert.AlertType.INFORMATION);
            alert.setContentText("No se ha calculado la previsión del stock");
            alert.showAndWait();
        }
    }

    private Supplier getSupplier(int ID) {
        Supplier s = null;
        try {
            Integer n = supplierName.size();
            for (int i = 0; i < n; i++) {
                s = (Supplier) supplierName.get(i);
                if (s.getID().equals(ID)) {
                    return s;
                }
            }
        } catch (TreeException e) {
            throw new RuntimeException(e);
        }
        return s;
    }

    @FXML
    void generarPrevisionDemandaOnAction(ActionEvent event) throws TreeException {
        LocalDateTime fecha = LocalDateTime.now();
        bitacora.add(new Binnacle(String.valueOf(fecha.withNano(0)), Utility.getIDClient(),"Se calculó la previsión de stock"));
        Utility.setBinnacle(bitacora);
        alert.setAlertType(Alert.AlertType.INFORMATION);
        alert.setContentText("Previsión de stock calculada");
        alert.showAndWait();
        isCalculated = true;
        this.stockRecomendado.setCellValueFactory(data ->
                new ReadOnlyStringWrapper(data.getValue().get(6)));

        tableView.setItems(getData());
        tableView.refresh();
    }

}