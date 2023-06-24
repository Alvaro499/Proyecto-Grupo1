package ucr.proyecto.proyectogrupo1.domain;

import java.io.Serializable;

public class Binnacle implements Serializable {

    //@JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private String fecha;
    private Integer usario;
    private String accion;

    public Binnacle(String fecha, Integer usario, String accion) {
        this.fecha = fecha;
        this.usario = usario;
        this.accion = accion;
    }

    public Binnacle() {
    }

    @Override
    public String toString() {
        return "Binnacle{" +
                "fecha='" + fecha + '\'' +
                ", usario=" + usario +
                ", accion='" + accion + '\'' +
                '}';
    }

    public String getFecha() {
        return fecha;
    }

    public void setFecha(String fecha) {
        this.fecha = fecha;
    }

    public Integer getUsario() {
        return usario;
    }

    public void setUsario(Integer usario) {
        this.usario = usario;
    }

    public String getAccion() {
        return accion;
    }

    public void setAccion(String accion) {
        this.accion = accion;
    }
}
