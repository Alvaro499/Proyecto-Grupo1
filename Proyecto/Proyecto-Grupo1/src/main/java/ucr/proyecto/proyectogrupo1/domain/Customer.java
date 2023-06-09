package ucr.proyecto.proyectogrupo1.domain;

public class Customer {
    private Integer ID;//cedula
    private String name;
    private String phoneNumber;
    private String email;
    private String address;

    public Customer(Integer ID, String name, String phoneNumber, String email, String address) {
        this.ID = ID;
        this.name = name;
        this.phoneNumber = phoneNumber;
        this.email = email;
        this.address = address;
    }

    public Integer getID() {
        return ID;
    }

    public String getName() {
        return name;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public String getEmail() {
        return email;
    }

    public String getAddress() {
        return address;
    }
}
