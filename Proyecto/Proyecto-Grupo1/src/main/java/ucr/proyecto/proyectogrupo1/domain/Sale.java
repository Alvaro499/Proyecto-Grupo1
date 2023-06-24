package ucr.proyecto.proyectogrupo1.domain;

public class Sale {
    private Integer id;
    //@JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private String saleDate;
    private Integer customerID;
    private String annotation;

    public Sale() {
    }

    public Sale(Integer ID, String saleDate, Integer customerID, String annotation) {
        this.id = ID;
        this.saleDate = saleDate;
        this.customerID = customerID;
        this.annotation = annotation;
    }

    public void setSaleDate(String saleDate) {
        this.saleDate = saleDate;
    }

    public Integer getID() {
        return id;
    }

    public String getSaleDate() {
        return saleDate;
    }

    public Integer getCustomerID() {
        return customerID;
    }

    public String getAnnotation() {
        return annotation;
    }

    @Override
    public String toString() {
        return "Sale{" +
                "\nID=" + id +
                "\nsaleDate=" + saleDate +
                "\ncustomerID=" + customerID +
                "\nannotation='" + annotation +
                "}\n";
    }
}
