package ucr.proyecto.proyectogrupo1.controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.TextField;
import ucr.proyecto.proyectogrupo1.TDA.AVL;
import ucr.proyecto.proyectogrupo1.TDA.ListException;
import ucr.proyecto.proyectogrupo1.TDA.TreeException;
import ucr.proyecto.proyectogrupo1.domain.Sale;
import ucr.proyecto.proyectogrupo1.domain.Supplier;
import ucr.proyecto.proyectogrupo1.util.Utility;

import java.util.Random;

public class NewProveedorController {
    private AVL supplier;
    private Alert alert;
    @FXML
    private TextField txtDireccionActual;

    @FXML
    private TextField txtEmail;

    @FXML
    private TextField txtID;

    @FXML
    private TextField txtProveedor;

    @FXML
    private TextField txtTelefono;

    @FXML
    public void initialize() throws ListException, TreeException {
        supplier = Utility.getSupplierAVL();
        txtID.setEditable(false);
        txtID.setText(String.valueOf(getID()).trim());
    }

    @FXML
    void borrarOnAction(ActionEvent event) {
        txtDireccionActual.setText("");
        txtEmail.setText("");
        txtProveedor.setText("");
        txtTelefono.setText("");
    }

    @FXML
    void confirmarOnAction(ActionEvent event) {
        if (completo()) {
            Supplier s = new Supplier(Integer.parseInt(txtID.getText()), txtProveedor.getText(), Integer.parseInt(txtTelefono.getText()), txtEmail.getText(), txtDireccionActual.getText());
            supplier.add(s);
            Utility.setSupplierAVL(supplier);
        }
    }

    private boolean completo() {
        if (txtProveedor.getText() != "" && txtEmail.getText() != "" && txtTelefono.getText() != "" && txtDireccionActual.getText() != "") {
            return true;
        }
        return false;
    }

    private Integer getID() throws TreeException {
        Integer id = 0;
        do {
            Random random = new Random();
            id = random.nextInt(Integer.MAX_VALUE);  // Genera un número aleatorio dentro del rango de Integer
        } while (existeID(id));
        return id;
    }

    private boolean existeID(Integer id) throws TreeException {
        supplier = Utility.getSale();
        boolean existe = false;
        if (!supplier.isEmpty()) {
            for (int i = 0; i < supplier.size(); i++) {
                Sale s = (Sale) supplier.get(i);
                if (id == s.getID()) {
                    existe = true;
                    break;
                }
            }
        }
        return existe;
    }
}