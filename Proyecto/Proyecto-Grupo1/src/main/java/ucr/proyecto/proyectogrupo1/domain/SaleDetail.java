package ucr.proyecto.proyectogrupo1.domain;

public class SaleDetail {
    private Integer saleID;//FK con Sale.ID
    private String productID;//FK con Product.ID
    private Integer quantity;
    private Double uniPrice;
    public SaleDetail(Integer saleID, String productID, Integer quantity, Double uniPrice) {
        this.saleID = saleID;//fk de ID de Sale
        this.productID = productID.trim(); //fk de ID de Product
        this.quantity = quantity;
        this.uniPrice = uniPrice;
    }

    public void setQuantity(Integer quantity) {
        this.quantity = quantity;
    }

    public Integer getSaleID() {
        return saleID;
    }

    public String getProductID() {
        return productID.trim();
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
                "\nsaleID=" + saleID +
                "\nproductID=" + productID +
                "\nquantity=" + quantity +
                "\nuniPrice=" + uniPrice +
                "}\n";
    }
}
