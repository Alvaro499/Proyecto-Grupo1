package ucr.proyecto.proyectogrupo1.controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.TextField;
import ucr.proyecto.proyectogrupo1.TDA.AVL;
import ucr.proyecto.proyectogrupo1.TDA.ListException;
import ucr.proyecto.proyectogrupo1.TDA.TreeException;
import ucr.proyecto.proyectogrupo1.domain.Product;
import ucr.proyecto.proyectogrupo1.domain.Supplier;
import ucr.proyecto.proyectogrupo1.util.Utility;

public class NewProductController {

    @FXML
    private TextField fieldID;
    @FXML
    private TextField fieldURL;
    @FXML
    private TextField fieldName;
    @FXML
    private TextField fielDesc;
    @FXML
    private TextField fieldPrice;
    @FXML
    private TextField fieldStock;
    @FXML
    private TextField fieldStickmin;
    @FXML
    private TextField fieldSupplier;
    private AVL product;
    private AVL supplier;

    @FXML
    public void initialize() throws ListException, TreeException {
        product = Utility.getProductAVL();
        supplier = Utility.getSupplierAVL();
    }

    @FXML
    void borrarOnAction(ActionEvent event) {
        fielDesc.setText("");
        fieldID.setText("");
        fieldName.setText("");
        fieldStickmin.setText("");
        fieldSupplier.setText("");
        fieldStock.setText("");
        fieldURL.setText("");
        fieldPrice.setText("");
    }

    @FXML
    void confirmarOnAction(ActionEvent event) throws TreeException {
        for (int i = 0; i < supplier.size(); i++) {
            Supplier s = (Supplier) supplier.get(i);
            System.out.println(s.getName());
            System.out.println(fieldSupplier.getText());
            if (s.getName().equalsIgnoreCase(fieldSupplier.getText())) {//si encuantra una editorial con el nombre igual que fieldSupplier, se guarda
                product.add(new Product(Integer.parseInt(fieldID.getText()), s.getID(), fielDesc.getText(), fieldName.getText(), Double.parseDouble(fieldPrice.getText()), Integer.parseInt(fieldStock.getText()), Integer.parseInt(fieldStickmin.getText()), fieldURL.getText()));
                borrarOnAction(new ActionEvent());
                Utility.setProductAVL(product);
                System.out.println("Producto guardado");

                break;
            }

            System.out.println("No existe esa editorial en la base de datos");
        }
    }
}
