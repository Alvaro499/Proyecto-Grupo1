package ucr.proyecto.proyectogrupo1.controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.Cursor;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.layout.VBox;
import ucr.proyecto.proyectogrupo1.Segurity.Cryptographic;
import ucr.proyecto.proyectogrupo1.TDA.*;
import ucr.proyecto.proyectogrupo1.domain.Binnacle;
import ucr.proyecto.proyectogrupo1.domain.Customer;
import ucr.proyecto.proyectogrupo1.domain.Security;
import ucr.proyecto.proyectogrupo1.email.EnvioCorreos;
import ucr.proyecto.proyectogrupo1.util.FXUtility;
import ucr.proyecto.proyectogrupo1.util.Utility;

import java.time.LocalDateTime;

public class NewClientController {

    @FXML
    private TextField txtEmail;

    @FXML
    private TextField txtID;

    @FXML
    private TextField txtNombre;

    @FXML
    private TextField txtTelefono;

    @FXML
    private TextField txtDireccion;

    @FXML
    private TextField txtUsuario;

    @FXML
    private TextField txtContra;

    @FXML
    private VBox tela;

    @FXML
    private Button confirmar;
    private LocalDateTime instant;
    private CircularLinkedList login;
    private SinglyLinkedList client;
    private Alert alert;
    private AVL bitacora;
    @FXML
    public void initialize() throws ListException, TreeException {
        instant = LocalDateTime.now().withNano(0);
        alert = FXUtility.alert("Menu Proveedor", "Desplay Proveedor");
        alert.setAlertType(Alert.AlertType.ERROR);
        login = Utility.getLoginCircularLinkedList();
        client = Utility.getClientSinglyLinkedList();
        bitacora = Utility.getBinnacle();
    }

    @FXML
    void borrarOnAction(ActionEvent event) {
        txtEmail.setText("");
        txtID.setText("");
        txtNombre.setText("");
        txtTelefono.setText("");
        txtDireccion.setText("");
        txtUsuario.setText("");
        txtContra.setText("");
    }

    @FXML
    void confirmarOnAction(ActionEvent event) throws ListException {
        LocalDateTime fecha = LocalDateTime.now();
        if (validarCampos()) {
            confirmar.setCursor(Cursor.WAIT);
            confirmar.setDisable(true);

            int id = Integer.parseInt(txtID.getText());
            String nombre = txtNombre.getText();
            String telefono = txtTelefono.getText();
            String email = txtEmail.getText();
            String direccion = txtDireccion.getText();
            String usuario = txtUsuario.getText();
            String contrasena = Cryptographic.codificar(txtContra.getText().trim()).trim();

            if (!existeID(id) && !existeUser(usuario)) {
                client.add(new Customer(id, nombre, telefono, email, direccion));
                login.add(new Security(id, usuario, contrasena));

                Utility.setLoginCircularLinkedList(login);
                Utility.setClientSinglyLinkedList(client);

                bitacora.add(new Binnacle(String.valueOf(fecha.withNano(0)), Utility.getIDClient(),"Se creó un nuevo cliente"));
                Utility.setBinnacle(bitacora);

                EnvioCorreos correo = new EnvioCorreos();
                correo.setEmailTo(email.trim());
                correo.setSubject("Cuenta Registrada");
                correo.setContent("Gracias " + nombre + " por registrarse en nuestra biblioteca<br>Usuario: " + usuario + "<br>Contraseña: " + contrasena + "<br>Estes es un mensaje generado de manera automática el " + instant + ", por favor no responder");
                correo.sendEmail();

                borrarOnAction(new ActionEvent());
            } else {
                txtID.setPromptText("El ID o el Usuario ya está registrado");
            }

            confirmar.setCursor(Cursor.HAND);
            confirmar.setDisable(false);
        }
    }

    private boolean validarCampos() {
        boolean camposValidos = true;

        if (txtDireccion.getText().isEmpty()) {
            txtDireccion.setPromptText("Sin texto");
            camposValidos = false;
        } else {
            txtDireccion.setPromptText("");
        }

        if (txtEmail.getText().isEmpty()) {
            txtEmail.setPromptText("Sin texto");
            camposValidos = false;
        } else {
            txtEmail.setPromptText("");
        }

        if (txtID.getText().isEmpty()) {
            txtID.setPromptText("Sin texto");
            camposValidos = false;
        } else {
            txtID.setPromptText("");
        }

        if (!txtID.getText().isEmpty() && Integer.parseInt(txtID.getText()) < 100000000) {
            txtID.setText("");
            txtID.setPromptText("Valor inválido");
            camposValidos = false;
        } else {
            txtID.setPromptText("");
        }

        if (txtNombre.getText().isEmpty()) {
            txtNombre.setPromptText("Sin texto");
            camposValidos = false;
        } else {
            txtNombre.setPromptText("");
        }

        if (txtTelefono.getText().isEmpty()) {
            txtTelefono.setPromptText("Sin texto");
            camposValidos = false;
        } else {
            txtTelefono.setPromptText("");
        }

        if (txtUsuario.getText().isEmpty()) {
            txtUsuario.setPromptText("Sin texto");
            camposValidos = false;
        } else {
            txtUsuario.setPromptText("");
        }

        if (txtContra.getText().isEmpty()) {
            txtContra.setPromptText("Sin texto");
            camposValidos = false;
        } else {
            txtContra.setPromptText("");
        }

        return camposValidos;
    }

    private boolean existeID(int id) throws ListException {
        for (int i = 1; i <= login.size(); i++) {
            Security s = (Security) login.getNode(i).data;
            if (id == s.getCustomerID()) {
                return true;
            }
        }
        return false;
    }
    private boolean existeUser(String usuario) throws ListException {
        for (int i = 1; i <= login.size(); i++) {
            Security s = (Security) login.getNode(i).data;
            if (usuario.equalsIgnoreCase(s.getUser())) {
                return true;
            }
        }
        return false;
    }
}
