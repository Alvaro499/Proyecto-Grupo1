module ucr.proyecto.proyectogrupo1 {
    requires javafx.controls;
    requires javafx.fxml;


    opens ucr.proyecto.proyectogrupo1 to javafx.fxml;
    exports ucr.proyecto.proyectogrupo1;
    exports ucr.proyecto.proyectogrupo1.controller;
    opens ucr.proyecto.proyectogrupo1.controller to javafx.fxml;
}
