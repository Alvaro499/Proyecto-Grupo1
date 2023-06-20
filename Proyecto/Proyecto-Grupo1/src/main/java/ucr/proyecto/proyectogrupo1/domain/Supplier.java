package ucr.proyecto.proyectogrupo1.domain;

public class Supplier {
    private Integer id;
    private String name;
    private Integer phoneNumber;
    private String email;
    private String address;

    public Supplier() {
    }

    public Supplier(Integer ID, String name, Integer phoneNumber, String email, String address) {
        this.id = ID;
        this.name = name;
        this.phoneNumber = phoneNumber;
        this.email = email;
        this.address = address;
    }
    public Integer getID() {
        return id;
    }

    public String getName() {
        return name;
    }

    public Integer getPhoneNumber() {
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
        return "Supplier{" +
                "ID=" + id +
                "\nname='" + name +
                "\nphoneNumber=" +
                "\nemail='" + email +
                "\naddress='" + address +
                "}\n";
    }
}
