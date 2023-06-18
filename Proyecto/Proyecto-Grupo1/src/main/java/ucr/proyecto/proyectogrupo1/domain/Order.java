package ucr.proyecto.proyectogrupo1.domain;

public class Order {
    private Integer id;
    private String orderDate;
    private String orderStatus;
    private String supplierName;
    private Double totalCost;
    private String remarks;

    public Order() {
    }

    public Order(Integer ID, String orderDate, String orderStatus, String supplierName, Double totalCost, String remarks) {
        this.id = ID;
        this.orderDate = orderDate;
        this.orderStatus = orderStatus;
        this.supplierName = supplierName;
        this.totalCost = totalCost;
        this.remarks = remarks;
    }

    public Integer getId() {
        return id;
    }

    public String getOrderDate() {
        return orderDate;
    }

    public String getOrderStatus() {
        return orderStatus;
    }

    public String getSupplierName() {
        return supplierName;
    }

    public Double getTotalCost() {
        return totalCost;
    }

    public String getRemarks() {
        return remarks;
    }

    @Override
    public String toString() {
        return "Order{" +
                "id=" + id +
                ", orderDate='" + orderDate + '\'' +
                ", orderStatus='" + orderStatus + '\'' +
                ", supplierName='" + supplierName + '\'' +
                ", totalCost=" + totalCost +
                ", remarks='" + remarks + '\'' +
                '}';
    }
}
