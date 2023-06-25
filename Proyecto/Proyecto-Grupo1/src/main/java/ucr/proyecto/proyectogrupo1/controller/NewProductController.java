package ucr.proyecto.proyectogrupo1.controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.TextField;
import ucr.proyecto.proyectogrupo1.PDF.PDF;
import ucr.proyecto.proyectogrupo1.TDA.AVL;
import ucr.proyecto.proyectogrupo1.TDA.ListException;
import ucr.proyecto.proyectogrupo1.TDA.TreeException;
import ucr.proyecto.proyectogrupo1.domain.Binnacle;
import ucr.proyecto.proyectogrupo1.domain.Product;
import ucr.proyecto.proyectogrupo1.domain.Supplier;
import ucr.proyecto.proyectogrupo1.util.FXUtility;
import ucr.proyecto.proyectogrupo1.util.Utility;

import javax.swing.*;
import java.time.LocalDateTime;

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
    private Alert alert;
    private String[] nameSupplier;
    private boolean encontrado = false;
    @FXML
    private ChoiceBox<String> choiceBoxProduct;
    private Integer nProduct;
    private AVL bitacora;

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

        bitacora = Utility.getBinnacle();
        alert = FXUtility.alert("New Product", "Desplay New Products");
        alert.setAlertType(Alert.AlertType.ERROR);
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
        LocalDateTime fecha = LocalDateTime.now();
        if (Utility.validarNumeros(fieldID.getText()) && Utility.validarNumeros(fieldPrice.getText()) && Utility.validarNumeros(fieldStock.getText()) && Utility.validarNumeros(fieldStickmin.getText())){
            for (int i = 0; i < supplier.size(); i++) {
                Supplier s = (Supplier) supplier.get(i);
                if (s.getName().equalsIgnoreCase(choiceBoxProduct.getValue())) {//si encuantra una editorial con el nombre igual que fieldSupplier, se guarda
                    product.add(new Product(fieldID.getText(), s.getID(), fielDesc.getText(), fieldName.getText(), Double.parseDouble(fieldPrice.getText()), Integer.parseInt(fieldStock.getText()), Integer.parseInt(fieldStickmin.getText()), fieldURL.getText()));
                    System.out.println(s);
                    borrarOnAction(new ActionEvent());
                    Utility.setProductAVL(product);
                    i = supplier.size();
                    encontrado = true;
                    bitacora.add(new Binnacle(String.valueOf(fecha.withNano(0)), Utility.getIDClient(),"Se agregó un nuevo producto"));
                    Utility.setBinnacle(bitacora);
                    alert.setHeaderText("Producto agregado");
                    alert.setContentText(PDF.getDocumento());
                    alert.setAlertType(Alert.AlertType.INFORMATION);
                    alert.show();
                }
            }
        }else {
            alert.setHeaderText("Por favor, verifique que los campos no esten vacíos y que contengan la informacion correcta para cada campo");
            alert.setContentText(PDF.getDocumento());
            alert.setAlertType(Alert.AlertType.INFORMATION);
            alert.show();
        }

    }
}
