module ucr.proyecto.proyectogrupo1 {
    requires javafx.controls;
    requires javafx.fxml;
    requires java.logging;
    requires java.desktop;
    requires mail;
    requires javax.activation;
    requires org.apache.pdfbox;
    //requires jackson.core;
    requires com.fasterxml.jackson.databind;
    requires com.fasterxml.jackson.core;//-->Jackson JSON
    requires com.fasterxml.jackson.datatype.jsr310;//-->JacksonJSON
    exports ucr.proyecto.proyectogrupo1.domain;//-->Jackson JSON
    opens  ucr.proyecto.proyectogrupo1.domain;//-->Jackson JSON
    requires itextpdf;
    //requires jackson.annotations;
    // requires javax.mail.api;

    opens ucr.proyecto.proyectogrupo1 to javafx.fxml;
    exports ucr.proyecto.proyectogrupo1;
    exports ucr.proyecto.proyectogrupo1.controller;
    opens ucr.proyecto.proyectogrupo1.controller to javafx.fxml;
}
