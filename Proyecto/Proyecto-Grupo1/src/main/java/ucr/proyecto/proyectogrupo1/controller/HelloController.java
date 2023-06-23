package ucr.proyecto.proyectogrupo1.controller;

import javafx.animation.ScaleTransition;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import javafx.util.Duration;
import ucr.proyecto.proyectogrupo1.HelloApplication;
import ucr.proyecto.proyectogrupo1.TDA.CircularLinkedList;
import ucr.proyecto.proyectogrupo1.domain.Security;
import ucr.proyecto.proyectogrupo1.util.Utility;

import java.io.IOException;

public class HelloController {
    @FXML
    private TextField txtNombreUsuario;
    @FXML
    private PasswordField txtContraseña;
    @FXML
    private BorderPane bp;
    @FXML
    private Button entrar;

    private CircularLinkedList loginCircularLinkedList;

    @FXML
    public void initialize() {
        loginCircularLinkedList = Utility.getLoginCircularLinkedList();
    }

    private void loadPage(String page) {
        FXMLLoader fxmlLoader = new FXMLLoader(HelloApplication.class.getResource(page));
        try {
            this.bp.setCenter(fxmlLoader.load());
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    @FXML
    void btnEntrar(ActionEvent event) {
        new HelloApplication();
        // Agregar animación al botón
        playButtonAnimation(entrar);
        try {
            for (int i = 1; i <= loginCircularLinkedList.size(); i++) {
                //buscamos que coincida el user y password en cada nodo del CircularLinkedList
                Security s = (Security) loginCircularLinkedList.getNode(i).data;
                String user = s.getUser();
                String password = s.getPassword();
                String fieldUser = txtNombreUsuario.getText().trim();
                String fieldPassword = txtContraseña.getText().trim();
                Integer ID = s.getCustomerID();
                if (password.equals(fieldPassword) && user.equals(fieldUser)) {
                    if (ID < 1000) {//admin
                        stage("menuAdministrador.fxml");
                    } else if (ID < 2000) {//consulta
                        stage("menuConsulta.fxml");
                    } else {//cliente
                        Utility.setIDClient(ID);
                        stage("cliente.fxml");
                    }
                }
            }
        } catch (Exception e) {
            System.out.println("Missing information");
        }
    }

    private void playButtonAnimation(Button button) {
        ScaleTransition scaleTransition = new ScaleTransition(Duration.seconds(0.1), button);
        scaleTransition.setToX(0.9);
        scaleTransition.setToY(0.9);
        scaleTransition.setAutoReverse(true);
        scaleTransition.setCycleCount(2);
        scaleTransition.play();
    }

    private void stage(String s) {
        FXMLLoader fxmlLoader = new FXMLLoader(HelloApplication.class.getResource(s));
        try {
            Parent root = fxmlLoader.load();
            Stage registerStage = new Stage();
            registerStage.initStyle(StageStyle.UTILITY);
            registerStage.setScene(new Scene(root));
            registerStage.sizeToScene();
            registerStage.setResizable(false);
            registerStage.show();
        } catch (IOException e) {
            e.printStackTrace();
            e.getCause();
        }
    }

    @FXML
    void show(KeyEvent event) {
        if (event.getCode() == KeyCode.ENTER) {
            btnEntrar(new ActionEvent());
        }
    }
}