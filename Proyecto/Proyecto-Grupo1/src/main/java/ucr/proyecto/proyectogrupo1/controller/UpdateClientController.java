package ucr.proyecto.proyectogrupo1.controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import ucr.proyecto.proyectogrupo1.HelloApplication;
import ucr.proyecto.proyectogrupo1.TDA.AVL;
import ucr.proyecto.proyectogrupo1.TDA.ListException;
import ucr.proyecto.proyectogrupo1.TDA.SinglyLinkedList;
import ucr.proyecto.proyectogrupo1.TDA.TreeException;
import ucr.proyecto.proyectogrupo1.domain.Customer;
import ucr.proyecto.proyectogrupo1.util.FXUtility;
import ucr.proyecto.proyectogrupo1.util.Utility;

import java.io.IOException;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class UpdateClientController {

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
    private TextField txtTelefono;

    private SinglyLinkedList allCustomer;
    private Alert alert;
    private String phoneBackup;
    private String adressBackup;
    private String emailBakcup;

//    @FXML
//    public void initialize() throws ListException, TreeException {
//        this.alert = FXUtility.alert("", "");
//        //Solo mostrar, no editar
//        txtNombre.setEditable(false);
//        txtID.setEditable(false);
//    }

    public void initializeAux() throws ListException, TreeException {
        this.alert = FXUtility.alert("", "");
        //Solo mostrar, no editar
        txtNombre.setEditable(false);
        txtID.setEditable(false);

        adressBackup = txtDireccion.getText();
        phoneBackup = txtTelefono.getText();
        emailBakcup = txtEmail.getText();

        allCustomer = Utility.getClientSinglyLinkedList();
    }

    @FXML
    void borrarOnAction(ActionEvent event) {
        txtDireccion.setText(adressBackup);
        txtEmail.setText(emailBakcup);
        txtTelefono.setText(phoneBackup);
    }

    @FXML
    void confirmarOnAction(ActionEvent event) throws TreeException, ListException {

        if (isValid()){

            for (int i = 1; i <= allCustomer.size() ; i++) {
                Customer actualCustomer = (Customer) allCustomer.getNode(i).data;
                if (actualCustomer.getID().equals(Integer.valueOf(txtID.getText()))){

                    //Acedemos al cliente desde la TDA para una modficacion rapida
                    ((Customer) allCustomer.getNode(i).data).setPhoneNumber(txtTelefono.getText());
                    ((Customer) allCustomer.getNode(i).data).setAddress(txtDireccion.getText());
                    ((Customer) allCustomer.getNode(i).data).setEmail(txtEmail.getText());
                    break;
                }
            }
            Utility.setClientSinglyLinkedList(allCustomer);
            alert.setAlertType(Alert.AlertType.INFORMATION);
            alert.setContentText("Usuario actualizado existosamente");
            alert.showAndWait();
        }else{
            alert.setAlertType(Alert.AlertType.INFORMATION);
            alert.setContentText("Por favor, verifique que los campos no esten vacíos y que contengan la informacion correcta para cada campo");
            alert.showAndWait();
        }
    }

    public void setClient(Customer auxCustomer) {
        txtDireccion.setText(auxCustomer.getAddress());
        txtEmail.setText(auxCustomer.getEmail());
        txtTelefono.setText(auxCustomer.getPhoneNumber());
        txtNombre.setText(auxCustomer.getName());
        txtID.setText(String.valueOf(auxCustomer.getID()));
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
