package ucr.proyecto.proyectogrupo1.domain;

import com.fasterxml.jackson.annotation.JsonFormat;

import java.time.LocalDateTime;

public class Binnacle {

    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime fecha;
    private Integer usario;
    private String accion;

    public Binnacle(LocalDateTime fecha, Integer usario, String accion) {
        this.fecha = fecha;
        this.usario = usario;
        this.accion = accion;
    }
}
