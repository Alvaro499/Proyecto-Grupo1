package ucr.proyecto.proyectogrupo1.domain;

import java.util.Date;

public class Sale {
    private Integer id;
    private Date saleDate;
    private Integer customerID;
    private String annotation;
    private Double uniPrice;

    public Sale() {
    }

    public Sale(Integer ID, Date saleDate, Integer customerID, String annotation, Double uniPrice) {
        this.id = ID;
        this.saleDate = saleDate;
        this.customerID = customerID;
        this.annotation = annotation;
        this.uniPrice = uniPrice;
    }

    public Integer getId() {
        return id;
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

    @Override
    public String toString() {
        return "Sale{" +
                "id=" + id +
                ", saleDate=" + saleDate +
                ", customerID=" + customerID +
                ", annotation='" + annotation + '\'' +
                ", uniPrice=" + uniPrice +
                '}';
    }
}
