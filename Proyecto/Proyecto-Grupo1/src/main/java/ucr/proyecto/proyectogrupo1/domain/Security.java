package ucr.proyecto.proyectogrupo1.domain;

public class Security {
    private Integer customerID; //FK con customer.ID
    private String user;
    private String password;
    public Security(Integer customerID, String user, String password) {
        this.customerID = customerID;
        this.user = user;
        this.password = password;
    }

    public Integer getCustomerID() {
        return customerID;
    }

    public String getUser() {
        return user;
    }

    public String getPassword() {
        return password;
    }
}
