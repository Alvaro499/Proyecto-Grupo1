package ucr.proyecto.proyectogrupo1.domain;

import javafx.scene.control.CheckBox;
import javafx.scene.image.Image;
import com.fasterxml.jackson.annotation.JsonIgnore;
import ucr.proyecto.proyectogrupo1.util.Utility;


public class Product {
    private String id;
    private Integer supplierID; //FK con Supplier.ID
    private String description;
    private String name;
    private Integer currentStock;
    private Integer minimunStock;
    private Double price;
    private String url_img;

    private int ventaAlDia;


    //Ignorar estos atributos al momento de la serializacion con Jackson JSON
    @JsonIgnore
    private Image image;

    @JsonIgnore
    private CheckBox checkBox;

    public Product() {
    }

    public Product(String ID, Integer supplierID, String name, Double price, Integer currentStock, Integer minimunStock) {
        this.id = ID;
        this.supplierID = supplierID;
        this.name = name;
        this.currentStock = currentStock;
        this.minimunStock = minimunStock;
        this.price = price;
        checkBox = new CheckBox();
        ventaAlDia = Utility.random(10);
    }

    public Product(String ID, Integer supplierID, String description, String name, Double price, Integer currentStock, Integer minimunStock, String url_img) {
        this.id = ID;
        this.supplierID = supplierID;
        this.description = description;
        this.name = name;
        this.currentStock = currentStock;
        this.minimunStock = minimunStock;
        this.price = price;
        this.url_img = url_img;
    }
    public int getVentaAlDia() {
        return ventaAlDia;
    }

    public CheckBox getCheckBox() {
        return checkBox;
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
        return id;
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

    public void setCurrentStock(Integer currentStock) {
        this.currentStock = currentStock;
    }

    public Integer getMinimunStock() {
        return minimunStock;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public void setMinimunStock(Integer minimunStock) {
        this.minimunStock = minimunStock;
    }

    @JsonIgnore
    public void setPreloadedImage(Image image) {
        this.image = image;
    }

    public Object getPreloadedImage() {
        return image;
    }

/*    @Override
    public String toString() {
        return "Product{" +
                "id=" + id +
                ", supplierID=" + supplierID +
                ", description='" + description + '\'' +
                ", name='" + name + '\'' +
                ", currentStock=" + currentStock +
                ", minimunStock=" + minimunStock +
                ", price=" + price +
                ", url_img='" + url_img + '\'' +
                '}';
    }
    */

    @Override
    public String toString() {
        return "Product{" +
                "id='" + id + '\'' +
                ", supplierID=" + supplierID +
                ", description='" + description + '\'' +
                ", name='" + name + '\'' +
                ", currentStock=" + currentStock +
                ", minimunStock=" + minimunStock +
                ", price=" + price +
                ", url_img='" + url_img + '\'' +
                ", ventaAlDia=" + ventaAlDia +
                '}';
    }

    public String toStringChoiceBox(){
        return name;
    }
}