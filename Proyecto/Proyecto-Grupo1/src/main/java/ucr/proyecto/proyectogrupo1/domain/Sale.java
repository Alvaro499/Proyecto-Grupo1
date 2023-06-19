package ucr.proyecto.proyectogrupo1.domain;

import java.time.LocalDate;

public class Sale {
    private Integer ID;
    private LocalDate saleDate;
    private Integer customerID;
    private String annotation;

    public Sale(Integer ID, LocalDate saleDate, Integer customerID, String annotation) {
        this.ID = ID;
        this.saleDate = saleDate;
        this.customerID = customerID;
        this.annotation = annotation;
    }

    public void setSaleDate(LocalDate saleDate) {
        this.saleDate = saleDate;
    }

    public Integer getID() {
        return ID;
    }

    public LocalDate getSaleDate() {
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
                "\nID=" + ID +
                "\nsaleDate=" + saleDate +
                "\ncustomerID=" + customerID +
                "\nannotation='" + annotation +
                "}\n";
    }
}
