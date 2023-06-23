package ucr.proyecto.proyectogrupo1.domain;

import com.fasterxml.jackson.annotation.JsonFormat;

import java.time.LocalDateTime;

public class Order {
    private Integer id;
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime orderDate;
    private String orderStatus;
    private String supplierName;
    private Double totalCost;
    private String remarks;

    public Order() {
    }


    public Order(Integer ID, LocalDateTime orderDate, String orderStatus, String supplierName, Double totalCost, String remarks) {
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

    public LocalDateTime getOrderDate() {
        return orderDate;
    }

    public String getOrderStatus() {
        return orderStatus;
    }
    public void setStatus(String orderStatus){
        this.orderStatus = orderStatus;
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
    public void setRemarks(String remarks) {
        this.remarks = remarks;
    }

    public void setTotalCost(double totalCost){
        this.totalCost = totalCost;
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
