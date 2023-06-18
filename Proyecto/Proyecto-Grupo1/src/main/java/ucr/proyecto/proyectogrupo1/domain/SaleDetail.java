package ucr.proyecto.proyectogrupo1.domain;

public class SaleDetail {
    private Integer id;
    private Integer saleID;//FK con Sale.ID
    private Integer productID;//FK con Product.ID
    private Integer quantity;
    private Double uniPrice;

    public SaleDetail() {
    }

    public SaleDetail(Integer ID, Integer saleID, Integer productID, Integer quantity, Double uniPrice) {
        this.id = ID;
        this.saleID = saleID;
        this.productID = productID;
        this.quantity = quantity;
        this.uniPrice = uniPrice;
    }

    public Integer getId() {
        return id;
    }

    public Integer getSaleID() {
        return saleID;
    }

    public Integer getProductID() {
        return productID;
    }

    public Integer getQuantity() {
        return quantity;
    }

    public Double getUniPrice() {
        return uniPrice;
    }


    @Override
    public String toString() {
        return "SaleDetail{" +
                "id=" + id +
                ", saleID=" + saleID +
                ", productID=" + productID +
                ", quantity=" + quantity +
                ", uniPrice=" + uniPrice +
                '}';
    }
}
