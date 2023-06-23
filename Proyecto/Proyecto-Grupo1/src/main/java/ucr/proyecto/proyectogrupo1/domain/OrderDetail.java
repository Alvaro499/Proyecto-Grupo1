package ucr.proyecto.proyectogrupo1.domain;

import ucr.proyecto.proyectogrupo1.util.Utility;

public class OrderDetail {
    //private Integer id;
    private Integer orderID;//PK con Order.ID
    private Integer productID;//FK con Product.ID
    private String quantity;
    private Double uniPrice;

    private int plazoEntrega;

    public OrderDetail() {
    }

    public OrderDetail(Integer orderID, Integer productID, String quantity, Double uniPrice) {
        this.orderID = orderID;
        this.productID = productID;
        this.quantity = quantity;
        this.uniPrice = uniPrice;
        this.plazoEntrega = Utility.random(20);
    }
    public OrderDetail(Integer orderID, Integer productID, String quantity, Double uniPrice, int plazoEntrega) {
        this.orderID = orderID;
        this.productID = productID;
        this.quantity = quantity;
        this.uniPrice = uniPrice;
        this.plazoEntrega = plazoEntrega;
    }

    public int getPlazoEntrega() {
        return plazoEntrega;
    }

    public Integer getOrderID() {
        return orderID;
    }

    public Integer getProductID() {
        return productID;
    }

    public String getQuantity() {
        return quantity;
    }

    public Double getUniPrice() {
        return uniPrice;
    }

    @Override
    public String toString() {
        return "OrderDetail{" +
                "orderID=" + orderID +
                ", productID=" + productID +
                ", quantity='" + quantity + '\'' +
                ", uniPrice=" + uniPrice +
                '}';
    }
}