package ucr.proyecto.proyectogrupo1.controller;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import ucr.proyecto.proyectogrupo1.PDF.PDF;
import ucr.proyecto.proyectogrupo1.TDA.*;
import ucr.proyecto.proyectogrupo1.domain.Binnacle;
import ucr.proyecto.proyectogrupo1.domain.Product;
import ucr.proyecto.proyectogrupo1.util.FXUtility;
import ucr.proyecto.proyectogrupo1.util.Utility;

import java.io.IOException;
import java.time.LocalDateTime;
import java.util.ArrayList;

public class ControlInventarioController {

    @FXML
    private TextField txtBuscar;
    @FXML
    private TextField textFieldNewStock;
    @FXML
    private TableView tableView;
    @FXML
    private TableColumn tableColumnSelected;
    @FXML
    private TableColumn tableColumnID;
    @FXML
    private TableColumn tableColumnName;
    @FXML
    private TableColumn tableColumnPrice;
    @FXML
    private TableColumn tableColumnActualStock;
    @FXML
    private TableColumn tableColumnMinimunStock;
    @FXML
    private TableColumn tableColumnSupplier;

    private ObservableList<Product> getProduct; //ObservableList Local
    private BTree product;//Lista Global
    private AVL supplierName;
    private Alert alert;
    private TextInputDialog dialog;
    private boolean isStockUpdate = false;
    private ArrayList<String> report;
    private AVL bitacora;

    @FXML
    public void initialize() throws TreeException, QueueException, IOException {
        bitacora = Utility.getBinnacle();

        this.alert = FXUtility.alert("", "");
        //Se toman los datos de los archivos en las TDA
        //product = Utility.getProductAVL();
        product = Utility.getInventaryBtree();
        supplierName = Utility.getSupplierAVL();
        report = new ArrayList<String>();


        //Creamos un ObservableList a partir del AVL, donde se crearan copias
        //de los productos extraidos de los archivos, estas copias, contienen info
        //basica para la tabla, con la idea de no mostrar los demas atributos innecesarios,'
        //ademas o se guarda la informacion mas facil.
        //La idea es que el checkbox, al seleccionarlo nos brinde la informacion de la fila
        //en la que esta, y al actualizar los datos, estos se modifican en el AVL original
        getProduct = FXCollections.observableArrayList();
        for (int i = 0; i < product.size(); i++) {
            //Construyo una copia de la informacion origial, pero
            //usando el primer constructor (el nuevo)
            //incompleta, solo para mostrar en la tabla
            Product p = (Product) product.get(i);

            String ID = p.getID();
            int supplierID = p.getSupplierID();
            String productName = p.getName();
            Double productPrice = p.getPrice();
            int currentStock = p.getCurrentStock();
            int minimunStock = p.getMinimunStock();

            //Para el reporte PDF
            report.add(String.valueOf(ID));
            report.add(productName);
            report.add(String.valueOf(currentStock));
            report.add(String.valueOf(minimunStock));

            //Primer constructor (solo en esta ocasion)
            Product secondConstructProduct = new Product(ID, supplierID, productName, productPrice, currentStock, minimunStock);
            getProduct.add(secondConstructProduct);
        }

        //Setteamos las columnas para que reciban los datos
        tableColumnName.setCellValueFactory(
                new PropertyValueFactory<Product, String>("name")
        );
        tableColumnPrice.setCellValueFactory(
                new PropertyValueFactory<Product, String>("price")
        );
        tableColumnActualStock.setCellValueFactory(
                new PropertyValueFactory<Product, String>("currentStock")
        );
        tableColumnMinimunStock.setCellValueFactory(
                new PropertyValueFactory<Product, String>("minimunStock")
        );

//        tableColumnSupplier.setCellValueFactory(
//                new PropertyValueFactory<Product,String>("supplierID")
//        );
        tableColumnSelected.setCellValueFactory(
                new PropertyValueFactory<Product, String>("checkBox")
        );

        //Colocamos la informacion de las columnas en las tabla
        if (!product.isEmpty()) {
            tableView.setItems(getProduct);
        }

    }

    @FXML
    void automaticUpdateOnAction(ActionEvent event) throws TreeException {
        LocalDateTime fecha = LocalDateTime.now();
        //Todos los checkbox se seleccionan
        //Le agregamos a cada producto un 75% de su stock minimo para evitar desabastecimiento
        for (Product p : getProduct) {
            p.setCurrentStock((75 * (p.getMinimunStock()) / 100) + p.getCurrentStock());
            //Si alguna casilla estaba activada la desactivamos para no presentar confusiones despues de la
            //actualizacion automatica
            if (p.getCheckBox().isSelected()) p.getCheckBox().setSelected(false);
        }
        isStockUpdate = true;
        System.out.println("El stock de todos los productos ha sido actualizado");
        bitacora.add(new Binnacle(String.valueOf(fecha.withNano(0)), Utility.getIDClient(),"El stock de todos los productos ha sido actualizado"));
        Utility.setBinnacle(bitacora);
        alert.setAlertType(Alert.AlertType.INFORMATION);
        alert.setContentText("El stock de todos los productos ha sido actualizado");
        alert.showAndWait();
        textFieldNewStock.setText("");
        tableView.refresh();
        //initialize();
    }

    @FXML
    void confirmOnAction(ActionEvent event) throws TreeException, QueueException, IOException, ListException {
        LocalDateTime fecha = LocalDateTime.now();
        if (isStockUpdate == true) {

            //Recorremos el la lista Observable y comparamos con el AVL cuales ID son iguales, entonces, modificadmos
            //en el AVL el nuevo stock

            for (int j = 0; j < product.size(); j++) {
                for (int i = 0; i < getProduct.size(); ++i) {

                    Product actualProductoAVL = (Product) product.get(j);
                    if (getProduct.get(i).getID().equals(actualProductoAVL.getID())) {
                        //le pasamos el stock modificado al AVL
                        ((Product) product.get(j)).setCurrentStock(getProduct.get(i).getCurrentStock());
                    }
                }
            }
            bitacora.add(new Binnacle(String.valueOf(fecha.withNano(0)), Utility.getIDClient(),"Se confirmaron los cambios de datos inventario "));
            Utility.setBinnacle(bitacora);
            alert.setAlertType(Alert.AlertType.INFORMATION);
            alert.setContentText("Datos actualizados");
            alert.showAndWait();
            //System.out.println("Se han confirmado los datos actualizados");
            Utility.setInventaryBtree(product);//actualizamos la lista global
            textFieldNewStock.setText("");
            initialize();

        } else {
            alert.setAlertType(Alert.AlertType.INFORMATION);
            alert.setContentText("No hay cambios de stock que confirmar");
            alert.showAndWait();
            //System.out.println("No hay cambios de stock que confirmar");
        }
    }

    @FXML
    void updateStockOnAction(ActionEvent event) {

        LocalDateTime fecha = LocalDateTime.now();

        //CREAMOS UNA NUEVA LISTA
        ObservableList<Product> productListToUpdate = FXCollections.observableArrayList();

        //Se recogen aquellos checkBox seleccionados
        for (Product bean : getProduct) {
            //si al menos una checkBox es seleccionada (obtiene la informacion de la tabla)
            //la guardamos en una nueva ObservableList
            if (bean.getCheckBox().isSelected()) {
                productListToUpdate.add(bean);
            }
        }
        for (Product p : productListToUpdate) {
            System.out.println(p.toString());
        }

        /*Verificar:
            1-Si hay contenido en el textField del stock
            2-Si hay alguna checkBox marcada
        */
        if (productListToUpdate.isEmpty()) {

            alert.setAlertType(Alert.AlertType.INFORMATION);
            alert.setContentText("Seleccione alguno de los productos");
            alert.showAndWait();
            //System.out.println("Seleccione alguno de los productos");

        } else {
            //Verificar restricciones del textField
            if (textFieldNewStock.getText().isEmpty() || textFieldNewStock.getText().isBlank() || !containsText(textFieldNewStock.getText())) {
                alert.setAlertType(Alert.AlertType.INFORMATION);
                alert.setContentText("Por favor, verifique que haya seleccionado alguna casilla y que el campo del stock no este vacio");
                alert.showAndWait();
                //System.out.println("Por favor, verifique que haya seleccionado alguna casilla y que el campo del stock no este vacio");
            } else {
                //Si hay al menos una casilla seleccionada y  el campo del stock es adecuado, realizar la modificacion
                changeObservableList(productListToUpdate, Integer.parseInt(textFieldNewStock.getText()));
                bitacora.add(new Binnacle(String.valueOf(fecha.withNano(0)), Utility.getIDClient(),"Se actualizó el stock"));
                Utility.setBinnacle(bitacora);
                tableView.refresh();
                isStockUpdate = true;
            }
        }
    }

    //REALIZAR LOS CAMBIOS DE STOCK EN LOS PRODUCTOS SELECCIONADOS
    public void changeObservableList(ObservableList<Product> productListToUpdate, int newStock) {

        //productListToUpdate contiene a aquellos Productos seleccionados cuyo stock a ser cambiado, pero antes
        //verificamos en que posicion se ubica dentro del ObservableList local, y lo reemplazamos ahi mismo

        //Como este tipo de Constructor de Producto contiene un atributo checkbox, debemos desactivar las casillas antes de comparar
        //ya que, sino el contains de los ObservableList no puede indentificar si elemento del ObservableList contiene al que
        //le esta pasadno la otra lista, ya que uno puede tener el checkbox activado o desactivado
        //Deseleccionamos los checkbox despues de obtener los datos de aquellos seleccionados

        for (Product productCheck : getProduct) {
            if (productCheck.getCheckBox().isSelected()) {
                productCheck.getCheckBox().setSelected(false);
            }
        }

        for (int i = 0; i < productListToUpdate.size(); i++) {
            for (int j = 0; j < getProduct.size(); j++) {

                if (productListToUpdate.get(i).getID().equals(getProduct.get(j).getID())) {
                    getProduct.get(j).setCurrentStock(newStock);
                }
            }
        }
        //Al final el ObservableList getProduct tiene los productos cuyo stock fue modificado, ahora estos productos
        //se actualizaran dentro de la AVL al momento de confirmar los cambios
    }

    //VERIFICAR QUE EL CAMPO DE MODIFICACION DEL STOCK ACEPTE SOLO NUMEROS
    public boolean containsText(String textFieldContent) {
        return textFieldContent.matches("\\d*");
    }


    @FXML
    void searchOnAction(ActionEvent event) throws TreeException {
        LocalDateTime fecha = LocalDateTime.now();

        //Capturamos la info del buscador
        String searchText = txtBuscar.getText().toLowerCase();

        if (searchText.isEmpty()) {
            tableView.setItems(getProduct); // Restaurar los datos originales
            return;
        }
        //Creamos un nuevo ObservableList aux que capturara la coincidencia que obtenga
        //el ObservableList original con el metodo propio "filtered
        ObservableList<Product> filteredList = getProduct.filtered(product1 ->
                product1.getName().toLowerCase().contains(searchText));

        tableView.setItems(filteredList);
        bitacora.add(new Binnacle(String.valueOf(fecha.withNano(0)), Utility.getIDClient(),"Se realizó una busquea en control de inventario"));
        Utility.setBinnacle(bitacora);
        //Metodo Filtered
        //https://www-tabnine-com.translate.goog/code/java/methods/javafx.collections.ObservableList/filtered?_x_tr_sl=en&_x_tr_tl=es&_x_tr_hl=es&_x_tr_pto=sc&_x_tr_hist=true
    }

    @FXML
    void reportBtnOnAction(ActionEvent event) {
        LocalDateTime fecha = LocalDateTime.now();
        //Si no se han realizado cambios en el stock original, no se puede realizar el reporte. Es decir,
        //hace reportes hasta que se hayan modificado alguno que otro stock
        bitacora.add(new Binnacle(String.valueOf(fecha.withNano(0)), Utility.getIDClient(),"Se realizó el reporte de control de inventario"));
        Utility.setBinnacle(bitacora);
        PDF.crearPDF("Stock actual", "Reporte", 4, report);

    }
}
