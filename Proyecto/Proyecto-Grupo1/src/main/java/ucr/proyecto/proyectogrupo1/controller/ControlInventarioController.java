package ucr.proyecto.proyectogrupo1.controller;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import ucr.proyecto.proyectogrupo1.TDA.AVL;
import ucr.proyecto.proyectogrupo1.TDA.TreeException;
import ucr.proyecto.proyectogrupo1.domain.Product;
import ucr.proyecto.proyectogrupo1.domain.Supplier;
import ucr.proyecto.proyectogrupo1.util.FXUtility;
import ucr.proyecto.proyectogrupo1.util.Utility;

import java.util.ArrayList;
import java.util.List;

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
    private AVL product;//Lista Global
    private AVL supplierName;
    private Alert alert;
    private TextInputDialog dialog;
    private boolean isStockUpdate= false;

    @FXML
    public void initialize() throws TreeException {
        //this.alert = utily util.Utility.FXUtility.alert("", "");

        this.alert = FXUtility.alert("", "");
        //Se toman los datos de los archivos en las TDA
        product = Utility.getProductAVL();
        supplierName = Utility.getSupplierAVL();


        //Creamos un ObservableList a partir del AVL, donde se crearan copias
        //de los productos extraidos de los archivos, estas copias, contienen info
        //basica para la tabla, con la idea de no mostrar los demas atributos innecesarios,'
        //ademas o se guarda la informacion mas facil.
        //La idea es que el checkbox, al seleccionarlo nos brinde la informacion de la fila
        //en la que esta, y al actualizar los datos, estos se modifican en el AVL original
        getProduct = FXCollections.observableArrayList();
        for (int i = 1; i <= product.size() ; i++) {
            //Construyo una copia de la informacion origial, pero
            //usando el primer constructor (el nuevo)
            //incompleta, solo para mostrar en la tabla
            Product p = (Product) product.get(i);

            int ID = p.getId();
            int supplierID = p.getSupplierID();
            String productName = p.getName();
            Double productPrice = p.getPrice();
            int currentStock = p.getCurrentStock();
            int minimunStock = p.getMinimunStock();

            //Primer constructor (solo en esta ocasion)
            Product secondConstructProduct = new Product(ID,supplierID,productName,productPrice,currentStock,minimunStock);
            getProduct.add(secondConstructProduct);
        }

        //Setteamos las columnas para que reciban los datos
        tableColumnID.setCellValueFactory(
                new PropertyValueFactory<Product,String>("id")
        );
        tableColumnName.setCellValueFactory(
                new PropertyValueFactory<Product,String>("name")
        );
        tableColumnPrice.setCellValueFactory(
                new PropertyValueFactory<Product,String>("price")
        );
        tableColumnActualStock.setCellValueFactory(
                new PropertyValueFactory<Product,String>("currentStock")
        );
        tableColumnMinimunStock.setCellValueFactory(
                new PropertyValueFactory<Product,String>("minimunStock")
        );

        tableColumnSupplier.setCellValueFactory(
                new PropertyValueFactory<Product,String>("supplierID")
        );
        tableColumnSelected.setCellValueFactory(
                new PropertyValueFactory<Product,String>("checkBox")
        );

        //Colocamos la informacion de las columnas en las tabla
        if (!product.isEmpty()) {
            tableView.setItems(getProduct);
        }
    }

    @FXML
    void automaticUpdateOnAction(ActionEvent event) throws TreeException {

        //Todos los checkbox se seleccionan
        //Le agregamos a cada producto un 75% de su stock minimo para evitar desabastecimiento
        for (Product p: getProduct) {
            p.setCurrentStock( 75*(p.getMinimunStock() / 100));
            //Si alguna casilla estaba activada la desactivamos para no presentar confusiones despues de la
            //actualizacion automatica
            if (p.getCheckBox().isSelected()) p.getCheckBox().setSelected(false);
        }
        isStockUpdate = true;
        alert.setAlertType(Alert.AlertType.INFORMATION);
        alert.setContentText("El stock de todos los productos ha sido actualizado");
        alert.showAndWait();
        tableView.refresh();
        //initialize();
    }

    @FXML
    void confirmOnAction(ActionEvent event) throws TreeException {
        if (isStockUpdate == true){

            int i = 0;
            //Recorremos el la lista Observable y comparamos con el AVL cuales ID son iguales, entonces, modificadmos
            //en el AVL el nuevo stock
            while(i <= getProduct.size()){

                for (int j = 1; j < product.size() ; j++) {
                    Product actualProductoAVL = (Product) product.get(j);
                    if (getProduct.get(i).getId().equals(actualProductoAVL.getId())){

                        //le pasamos el stock modificado al AVL
                        ((Product) product.get(j)).setCurrentStock(getProduct.get(i).getCurrentStock());
                    }
                }
                i++;
            }
            alert.setAlertType(Alert.AlertType.INFORMATION);
            alert.setContentText("Datos actualizados");
            alert.showAndWait();
            isStockUpdate = false;
            initialize();

        }else{
            alert.setAlertType(Alert.AlertType.INFORMATION);
            alert.setContentText("No hay cambios de stock que confirmar");
            alert.showAndWait();
        }
    }

    @FXML
    void updateStockOnAction(ActionEvent event) {

        //CREAMOS UNA NUEVA LISTA
        ObservableList<Product> productListToUpdate = FXCollections.observableArrayList();

        //Se recogen aquellos checkBox seleccionados
        for(Product bean : getProduct) {
            //si al menos una checkBox es seleccionada (obtiene la informacion de la tabla)
            //la guardamos en una nueva ObservableList
            if(bean.getCheckBox().isSelected()) {
                productListToUpdate.add(bean);
            }
        }

        /*Verificar:
            1-Si hay contenido en el textField del stock
            2-Si hay alguna checkBox marcada
        */
        if (productListToUpdate.isEmpty()){

            alert.setAlertType(Alert.AlertType.INFORMATION);
            alert.setContentText("Seleccione alguno de los productos");
            alert.showAndWait();

        }else{
            //Verificar restricciones del textField
            if (textFieldNewStock.getText().isEmpty() || textFieldNewStock.getText().isBlank() || !containsText(textFieldNewStock.getText())){
                alert.setAlertType(Alert.AlertType.INFORMATION);
                alert.setContentText("Por favor, verifique que haya seleccionado alguna casilla y que el campo del stock no este vacio");
                alert.showAndWait();
            }else {
                //Si hay al menos una casilla seleccionada y  el campo del stock es adecuado, realizar la modificacion
                changeObservableList(productListToUpdate, Integer.parseInt(textFieldNewStock.getText()));
                tableView.refresh();
                isStockUpdate = true;
            }
        }
    }

    //REALIZAR LOS CAMBIOS DE STOCK EN LOS PRODUCTOS SELECCIONADOS
    public void changeObservableList(ObservableList<Product> productListToUpdate, int newStock){

        //productListToUpdate contiene a aquellos Productos seleccionados cuyo stock a ser cambiado, pero antes
        //verificamos en que posicion se ubica dentro del ObservableList local, y lo reemplazamos ahi mismo
        for (int i = 0; i < productListToUpdate.size() ; i++) {

            if (getProduct.contains(productListToUpdate.get(i)) ){
                getProduct.get(i).setCurrentStock(newStock);
            }
        }
        //Al final el ObservableList getProduct tiene los productos cuyo stock fue modificado, ahora estos productos
        //se actualizaran dentro de la AVL al momento de confirmar los cambios
    }

    //VERIFICAR QUE EL CAMPO DE MODIFICACION DEL STOCK ACEPTE SOLO NUMEROS
    public boolean containsText(String textFieldContent){
        return textFieldContent.matches("\\d*");
    }


    @FXML
    void searchOnAction(ActionEvent event) throws TreeException {
    }

}
