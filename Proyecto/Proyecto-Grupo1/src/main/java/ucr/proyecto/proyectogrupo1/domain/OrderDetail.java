package ucr.proyecto.proyectogrupo1.domain;

public class OrderDetail {
    private Integer ID;
    private Integer orderID;//FK con Order.ID
    private Integer productID;//FK con Product.ID
    private String quantity;
    private Double uniPrice;

    public OrderDetail(Integer ID, Integer orderID, Integer productID, String quantity, Double uniPrice) {
        this.ID = ID;
        this.orderID = orderID;
        this.productID = productID;
        this.quantity = quantity;
        this.uniPrice = uniPrice;
    }

    public Integer getID() {
        return ID;
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
}
