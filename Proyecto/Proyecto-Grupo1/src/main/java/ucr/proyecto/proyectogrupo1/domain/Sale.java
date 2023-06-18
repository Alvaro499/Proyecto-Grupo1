package ucr.proyecto.proyectogrupo1.domain;

import java.util.Date;

public class Sale {
    private Integer ID;
    private Date saleDate;
    private Integer customerID;
    private String annotation;
    public Sale(Integer ID, Date saleDate, Integer customerID, String annotation) {
        this.ID = ID;
        this.saleDate = saleDate;
        this.customerID = customerID;
        this.annotation = annotation;
    }

    public Integer getID() {
        return ID;
    }

    public Date getSaleDate() {
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
