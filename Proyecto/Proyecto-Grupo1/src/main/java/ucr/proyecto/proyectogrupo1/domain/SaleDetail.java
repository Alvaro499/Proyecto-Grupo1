package ucr.proyecto.proyectogrupo1.domain;

public class SaleDetail {
    private Integer ID;
    private Integer saleID;//FK con Sale.ID
    private Integer productID;//FK con Product.ID
    private Integer quantity;
    private Double uniPrice;
    public SaleDetail(Integer ID, Integer saleID, Integer productID, Integer quantity, Double uniPrice) {
        this.ID = ID;
        this.saleID = saleID;
        this.productID = productID;
        this.quantity = quantity;
        this.uniPrice = uniPrice;
    }

    public Integer getID() {
        return ID;
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
}
