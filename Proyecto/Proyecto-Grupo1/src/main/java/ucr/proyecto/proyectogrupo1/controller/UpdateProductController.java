package ucr.proyecto.proyectogrupo1.controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.layout.VBox;
import ucr.proyecto.proyectogrupo1.TDA.AVL;
import ucr.proyecto.proyectogrupo1.TDA.ListException;
import ucr.proyecto.proyectogrupo1.TDA.SinglyLinkedList;
import ucr.proyecto.proyectogrupo1.TDA.TreeException;
import ucr.proyecto.proyectogrupo1.domain.Product;
import ucr.proyecto.proyectogrupo1.domain.Supplier;
import ucr.proyecto.proyectogrupo1.util.FXUtility;
import ucr.proyecto.proyectogrupo1.util.Utility;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class UpdateProductController {

    @FXML
    private Button confirmar;

    @FXML
    private VBox tela;

    @FXML
    private TextField txtDetalles;

    @FXML
    private TextField txtPrice;

    @FXML
    private TextField txtProveedor;
    @FXML
    private TextField txtCodigo;

    @FXML
    private TextField txtStockMin;

    @FXML
    private TextField txtTitulo;

    private AVL allProduct;
    private Alert alert;
    private String priceBackup;
    private String stickMinBackup;
    private String detalleBakcup;

    public void initializeAux() throws ListException, TreeException {
        this.alert = FXUtility.alert("", "");
        //Solo mostrar, no editar
        txtTitulo.setEditable(false);
        txtProveedor.setEditable(false);
        txtCodigo.setEditable(false);
        txtDetalles.setEditable(false);

        detalleBakcup = txtDetalles.getText();
        priceBackup = txtPrice.getText();
        stickMinBackup = txtStockMin.getText();

        allProduct = Utility.getProductAVL();
    }

    @FXML
    void borrarOnAction(ActionEvent event) {

        txtDetalles.setText(detalleBakcup);
        txtPrice.setText(priceBackup);
        txtStockMin.setText(stickMinBackup);
    }

    public void setProduct(String name,String detail,String price,String stockMin,String supplier,String code) {
        txtTitulo.setText(name);
        txtDetalles.setText(detail);
        txtPrice.setText(price.replace("₡",""));
        txtStockMin.setText(stockMin);
        txtProveedor.setText(supplier);
        txtCodigo.setText(code);
        //txtID.setText(String.valueOf(auxSupplier.getID()));
        //txtPlazoEntrega.setText(String.valueOf(auxSupplier.getPlazoEntrega()));
    }

    @FXML
    void confirmarOnAction(ActionEvent event) throws TreeException {

        if (isValid()){

            for (int i = 0; i < allProduct.size() ; i++) {
                Product actualProduct = (Product) allProduct.get(i);
                if (actualProduct.getID().equals(txtCodigo.getText())){

                    //Acedemos al cliente desde la TDA para una modficacion rapida
                    ((Product) allProduct.get(i)).setMinimunStock(Integer.valueOf(txtStockMin.getText()));
                    ((Product) allProduct.get(i)).setPrice(Double.valueOf(txtPrice.getText()));
                    //((Product) allProduct.get(i)).setDescription(txtDetalles.getText());
                    break;
                }
            }
            Utility.setProductAVL(allProduct);
            alert.setAlertType(Alert.AlertType.INFORMATION);
            alert.setContentText("Proveedor actualizado existosamente");
            alert.showAndWait();
        }else{
            alert.setAlertType(Alert.AlertType.INFORMATION);
            alert.setContentText("Por favor, verifique que los campos no esten vacíos y que contengan la informacion correcta para cada campo");
            alert.showAndWait();
        }

    }

    public boolean isValid(){

        if (txtPrice.getText().isEmpty()  || txtPrice.getText() == null || !containsText(txtPrice.getText()) || txtPrice.getText().equals("0")){
            return false;
        }
        if (txtDetalles.getText().isEmpty()  || txtDetalles.getText() == null){
            return false;
        }

        if (txtStockMin.getText().isEmpty()  || txtStockMin.getText() == null || !containsText(txtStockMin.getText()) || txtPrice.getText().equals("0")){
            return false;
        }
        return true;
    }

    //Verificar que el string tenga un formato email
    private boolean containsEmail(String txtEmail) {
        //String patronEmail = "^[A-Za-z0-9+_.-]+@[A-Za-z0-9.-]+$";
        //txtEmail.matches("^[A-Za-z0-9+_.-]+@[A-Za-z0-9.-]+$");

        //Compilamos el patron de correos con un objeto Pattern
        Pattern pattern = Pattern.compile("^[A-Za-z0-9+_.-]+@[A-Za-z0-9.-]+$");

        //Compilamos el texto del email con el patron
        Matcher compiler = pattern.matcher((CharSequence) txtEmail);

        //comprobamos si entra en el patron
        return compiler.matches();
    }

    //Verificar que solo contenga numeros el telefono
    public boolean containsText(String textFieldContent) {
        return textFieldContent.matches("\\d*");
    }

}
