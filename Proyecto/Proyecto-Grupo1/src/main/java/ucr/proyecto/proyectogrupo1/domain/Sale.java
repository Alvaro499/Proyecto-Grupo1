package ucr.proyecto.proyectogrupo1.domain;

import com.fasterxml.jackson.annotation.JsonFormat;

import java.time.LocalDateTime;

public class Sale {
    private Integer id;
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime saleDate;
    private Integer customerID;
    private String annotation;

    public Sale() {
    }

    public Sale(Integer ID, LocalDateTime saleDate, Integer customerID, String annotation) {
        this.id = ID;
        this.saleDate = saleDate;
        this.customerID = customerID;
        this.annotation = annotation;
    }

    public void setSaleDate(LocalDateTime saleDate) {
        this.saleDate = saleDate;
    }

    public Integer getID() {
        return id;
    }

    public LocalDateTime getSaleDate() {
        return saleDate;
    }

    public Integer getCustomerID() {
        return customerID;
    }

    public String getAnnotation() {
        return annotation;
    }

    @Override
    public String toString() {
        return "Sale{" +
                "\nID=" + id +
                "\nsaleDate=" + saleDate +
                "\ncustomerID=" + customerID +
                "\nannotation='" + annotation +
                "}\n";
    }
}
