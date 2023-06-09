package ucr.proyecto.proyectogrupo1.domain;

public class Product {
    private Integer ID;
    private Integer supplierID; //FK con Supplier.ID
    private String description;
    private String name;
    private Integer currentStock;
    private Integer minimunStock;
    public Product(Integer ID, Integer supplierID, String description, String name, Integer currentStock, Integer minimunStock) {
        this.ID = ID;
        this.supplierID = supplierID;
        this.description = description;
        this.name = name;
        this.currentStock = currentStock;
        this.minimunStock = minimunStock;
    }

    public Integer getID() {
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
}
