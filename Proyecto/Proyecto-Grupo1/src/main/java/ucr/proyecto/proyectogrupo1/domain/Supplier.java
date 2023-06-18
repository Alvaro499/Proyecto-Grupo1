package ucr.proyecto.proyectogrupo1.domain;

public class Supplier {
    private Integer ID;
    private String name;
    private Integer phoneNumber;
    private String email;
    private String address;

    public Supplier(Integer ID, String name, Integer phoneNumber, String email, String address) {
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
                "ID=" + ID +
                "\nname='" + name +
                "\nphoneNumber=" +
                "\nemail='" + email +
                "\naddress='" + address +
                "}\n";
    }
}
