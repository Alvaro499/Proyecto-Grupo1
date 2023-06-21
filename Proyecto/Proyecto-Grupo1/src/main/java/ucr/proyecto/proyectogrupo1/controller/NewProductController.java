package ucr.proyecto.proyectogrupo1.controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.TextField;
import ucr.proyecto.proyectogrupo1.TDA.AVL;
import ucr.proyecto.proyectogrupo1.TDA.ListException;
import ucr.proyecto.proyectogrupo1.TDA.TreeException;
import ucr.proyecto.proyectogrupo1.domain.Product;
import ucr.proyecto.proyectogrupo1.domain.Supplier;
import ucr.proyecto.proyectogrupo1.util.Utility;

import javax.swing.*;

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
    private AVL product;
    private AVL supplier;
    private String[] nameSupplier;
    private boolean encontrado = false;
    @FXML
    private ChoiceBox<String> choiceBoxProduct;
    private Integer nProduct;

    @FXML
    public void initialize() throws ListException, TreeException {
        product = Utility.getProductAVL();
        supplier = Utility.getSupplierAVL();
        nameSupplier = new String[supplier.size()];
        for (int i = 0; i < nameSupplier.length ; i++) {
            Supplier s = (Supplier) supplier.get(i);
            nameSupplier[i] = s.getName();
        }
        choiceBoxProduct.getItems().addAll(nameSupplier);
    }

    @FXML
    void borrarOnAction(ActionEvent event) throws TreeException {
        fielDesc.setText("");
        fieldID.setText("");
        fieldName.setText("");
        fieldStickmin.setText("");
        fieldStock.setText("");
        fieldURL.setText("");
        fieldPrice.setText("");
    }

    @FXML
    void confirmarOnAction(ActionEvent event) throws TreeException, ListException {
        for (int i = 0; i < supplier.size(); i++) {
            Supplier s = (Supplier) supplier.get(i);
            if (s.getName().equalsIgnoreCase(choiceBoxProduct.getValue())) {//si encuantra una editorial con el nombre igual que fieldSupplier, se guarda
                product.add(new Product(fieldID.getText(), s.getID(), fielDesc.getText(), fieldName.getText(), Double.parseDouble(fieldPrice.getText()), Integer.parseInt(fieldStock.getText()), Integer.parseInt(fieldStickmin.getText()), fieldURL.getText()));
                System.out.println(s);
                borrarOnAction(new ActionEvent());
                Utility.setProductAVL(product);
                i = supplier.size();
                encontrado = true;
                JOptionPane.showMessageDialog(null,"Producto guardado");
            }
        }
        if (encontrado == false) {
            System.out.println("No existe esa editorial en la base de datos");
        }
    }
}
