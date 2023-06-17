package ucr.proyecto.proyectogrupo1;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import ucr.proyecto.proyectogrupo1.controller.HelloController;

import java.io.IOException;

public class HelloApplication extends Application {
    public static boolean b = true;

    @Override
    public void start(Stage stage) throws IOException {
        HelloController helloController = new HelloController();
        FXMLLoader login = new FXMLLoader(HelloApplication.class.getResource("menuAdministrador.fxml"));//login.fxml menuAdministrador.fxml

        Scene scene = new Scene(login.load());
        stage.setTitle("Laberinto de Libros");
        stage = new Stage(StageStyle.UTILITY);
        stage.setScene(scene);
        stage.sizeToScene();
        stage.setResizable(false);
        stage.show();
    }

    public static void main(String[] args) {
        launch();
    }
}