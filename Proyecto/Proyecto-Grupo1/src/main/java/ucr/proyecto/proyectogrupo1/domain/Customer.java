package ucr.proyecto.proyectogrupo1.domain;

public class Customer {
    private Integer id;//cedula
    private String name;
    private String phoneNumber;
    private String email;
    private String address;

    public Customer() {
    }

    public Customer(Integer ID, String name, String phoneNumber, String email, String address) {
        this.id = ID;
        this.name = name;
        this.phoneNumber = phoneNumber;
        this.email = email;
        this.address = address;
    }

    public Integer getId() {
        return id;
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

    @Override
    public String toString() {
        return "Customer{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", phoneNumber='" + phoneNumber + '\'' +
                ", email='" + email + '\'' +
                ", address='" + address + '\'' +
                '}';
    }
}
