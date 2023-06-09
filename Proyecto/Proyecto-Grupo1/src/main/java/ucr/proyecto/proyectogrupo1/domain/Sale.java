package ucr.proyecto.proyectogrupo1.domain;

import java.util.Date;

public class Sale {
    private Integer ID;
    private Date saleDate;
    private Integer customerID;
    private String annotation;
    private Double uniPrice;
    public Sale(Integer ID, Date saleDate, Integer customerID, String annotation, Double uniPrice) {
        this.ID = ID;
        this.saleDate = saleDate;
        this.customerID = customerID;
        this.annotation = annotation;
        this.uniPrice = uniPrice;
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

    public Double getUniPrice() {
        return uniPrice;
    }
}
