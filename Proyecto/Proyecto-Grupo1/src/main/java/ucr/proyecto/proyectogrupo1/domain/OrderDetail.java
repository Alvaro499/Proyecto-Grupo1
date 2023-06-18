package ucr.proyecto.proyectogrupo1.domain;

public class OrderDetail {
    private Integer id;
    private Integer orderID;//FK con Order.ID
    private Integer productID;//FK con Product.ID
    private String quantity;
    private Double uniPrice;

    public OrderDetail() {
    }

    public OrderDetail(Integer ID, Integer orderID, Integer productID, String quantity, Double uniPrice) {
        this.id = ID;
        this.orderID = orderID;
        this.productID = productID;
        this.quantity = quantity;
        this.uniPrice = uniPrice;
    }

    public Integer getId() {
        return id;
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
                "id=" + id +
                ", orderID=" + orderID +
                ", productID=" + productID +
                ", quantity='" + quantity + '\'' +
                ", uniPrice=" + uniPrice +
                '}';
    }
}
