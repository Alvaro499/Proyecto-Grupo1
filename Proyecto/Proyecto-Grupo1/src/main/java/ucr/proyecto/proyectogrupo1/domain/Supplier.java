package ucr.proyecto.proyectogrupo1.domain;

public class Supplier {
    private Integer id;
    private String name;
    private Integer phoneNumber;
    private String email;
    private String address;

    private Integer PLAZO_ENTREGA;

    public Supplier() {
    }

    public Supplier(Integer ID, String name, Integer phoneNumber, String email, String address) {
        this.id = ID;
        this.name = name;
        this.phoneNumber = phoneNumber;
        this.email = email;
        this.address = address;
    }

    public Supplier(Integer id, String name, Integer phoneNumber, String email, String address, Integer PLAZO_ENTREGA) {
        this.id = id;
        this.name = name;
        this.phoneNumber = phoneNumber;
        this.email = email;
        this.address = address;
        this.PLAZO_ENTREGA = PLAZO_ENTREGA;
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

    public Integer getPLAZO_ENTREGA() {
        return PLAZO_ENTREGA;
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
