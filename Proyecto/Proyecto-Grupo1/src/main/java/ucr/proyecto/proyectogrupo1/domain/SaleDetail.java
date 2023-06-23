package ucr.proyecto.proyectogrupo1.domain;

public class SaleDetail {
    private Integer saleID;//FK con Sale.ID
    private String productID;//FK con Product.ID
    private Integer quantity;
    private Double uniPrice;
    private Boolean order_canceled;

    public SaleDetail() {
    }

    public SaleDetail(Integer saleID, String productID, Integer quantity, Double uniPrice) {
        this.saleID = saleID;//fk de ID de Sale
        this.productID = productID.trim(); //fk de ID de Product
        this.quantity = quantity;
        this.uniPrice = uniPrice;
        order_canceled = false;
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

    public Boolean getOrder_canceled() {
        return order_canceled;
    }

    public void setOrder_canceled(Boolean order_canceled) {
        this.order_canceled = order_canceled;
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
