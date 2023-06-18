package ucr.proyecto.proyectogrupo1.domain;

import javafx.scene.image.Image;

public class Product {
    private String ID;
    private Integer supplierID; //FK con Supplier.ID
    private String description;
    private String name;
    private Integer currentStock;
    private Integer minimunStock;
    private Double price;
    private String url_img;
    private Image image;

    public Product(String ID, Integer supplierID, String description, String name, Double price, Integer currentStock, Integer minimunStock, String url_img) {
        this.ID = ID;
        this.supplierID = supplierID;
        this.description = description;
        this.name = name;
        this.currentStock = currentStock;
        this.minimunStock = minimunStock;
        this.price = price;
        this.url_img = url_img;
    }

    public String getUrl_img() {
        return url_img;
    }

    public void setUrl_img(String url_img) {
        this.url_img = url_img;
    }

    public Double getPrice() {
        return price;
    }

    public void setPrice(Double price) {
        this.price = price;
    }

    public String getID() {
        return ID;
    }

    public Integer getSupplierID() {
        return supplierID;
    }

    public String getDescription() {
        return description;
    }

    public String getName() {
        return name;
    }

    public Integer getCurrentStock() {
        return currentStock;
    }

    public Integer getMinimunStock() {
        return minimunStock;
    }

    public void setPreloadedImage(Image image) {
        this.image = image;
    }

    public Object getPreloadedImage() {
        return image;
    }
}
