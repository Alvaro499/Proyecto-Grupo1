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
import ucr.proyecto.proyectogrupo1.domain.Binnacle;
import ucr.proyecto.proyectogrupo1.domain.Customer;
import ucr.proyecto.proyectogrupo1.domain.Supplier;
import ucr.proyecto.proyectogrupo1.util.FXUtility;
import ucr.proyecto.proyectogrupo1.util.Utility;

import java.time.LocalDateTime;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class UpdateSupplierController {

    @FXML
    private Button confirmar;

    @FXML
    private VBox tela;

    @FXML
    private TextField txtDireccion;

    @FXML
    private TextField txtEmail;

    @FXML
    private TextField txtID;

    @FXML
    private TextField txtNombre;

    @FXML
    private TextField txtPlazoEntrega;

    @FXML
    private TextField txtTelefono;

    private AVL allSupplier;
    private Alert alert;
    private String phoneBackup;
    private String adressBackup;
    private String emailBakcup;
    private String plazoEntregaBakcup;
    private AVL bitacora;

    public void initializeAux() throws ListException, TreeException {
        bitacora = Utility.getBinnacle();
        this.alert = FXUtility.alert("", "");
        //Solo mostrar, no editar
        txtNombre.setEditable(false);

        adressBackup = txtDireccion.getText();
        phoneBackup = txtTelefono.getText();
        emailBakcup = txtEmail.getText();
        //plazoEntregaBakcup = txtPlazoEntrega.getText();

        allSupplier = Utility.getSupplierAVL();
    }

    public void setSupplier(String name, Integer phone, String email, String address) {
        txtDireccion.setText(address);
        txtEmail.setText(email);
        txtTelefono.setText(String.valueOf(phone));
        txtNombre.setText(name);
        //txtID.setText(String.valueOf(auxSupplier.getID()));
        //txtPlazoEntrega.setText(String.valueOf(auxSupplier.getPlazoEntrega()));
    }

    @FXML
    void borrarOnAction(ActionEvent event) {

        txtDireccion.setText(adressBackup);
        txtEmail.setText(emailBakcup);
        txtTelefono.setText(phoneBackup);
        txtPlazoEntrega.setText(plazoEntregaBakcup);
    }

    @FXML
    void confirmarOnAction(ActionEvent event) throws TreeException {
        LocalDateTime fecha = LocalDateTime.now();
        if (isValid()){

            for (int i = 0; i < allSupplier.size() ; i++) {
                Supplier actualSupplier = (Supplier) allSupplier.get(i);
                if (actualSupplier.getName().equals(txtNombre.getText())){

                    //Acedemos al cliente desde la TDA para una modficacion rapida
                    ((Supplier) allSupplier.get(i)).setPhoneNumber(Integer.valueOf(txtTelefono.getText()));
                    ((Supplier) allSupplier.get(i)).setAddress(txtDireccion.getText());
                    ((Supplier) allSupplier.get(i)).setEmail(txtEmail.getText());
                    break;
                }
            }
            Utility.setSupplierAVL(allSupplier);
            bitacora.add(new Binnacle(String.valueOf(fecha.withNano(0)), Utility.getIDClient(),
                    "Actualización proveedor"));
            Utility.setBinnacle(bitacora);
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

        if (txtTelefono.getText().isEmpty()  || txtTelefono.getText() == null || !containsText(txtTelefono.getText())){
            return false;
        }
        if (txtDireccion.getText().isEmpty()  || txtDireccion.getText() == null){
            return false;
        }

        if (txtEmail.getText().isEmpty()  || txtEmail.getText() == null || !containsEmail(txtEmail.getText())){
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
