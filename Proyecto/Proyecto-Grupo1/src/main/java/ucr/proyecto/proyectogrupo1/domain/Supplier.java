package ucr.proyecto.proyectogrupo1.domain;

import ucr.proyecto.proyectogrupo1.util.Utility;

public class Supplier {
    private Integer id;
    private String name;
    private Integer phoneNumber;
    private String email;
    private String address;
    private int plazoEntrega;

    public Supplier() {
    }

    public Supplier(Integer ID, String name, Integer phoneNumber, String email, String address) {
        this.id = ID;
        this.name = name;
        this.phoneNumber = phoneNumber;
        this.email = email;
        this.address = address;
        this.plazoEntrega = Utility.random(25);
    }

    public int getPlazoEntrega() {
        return plazoEntrega;
    }

    public void setPlazoEntrega(Integer plazoEntrega) {
        this.plazoEntrega = plazoEntrega;
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

    public void setPhoneNumber(Integer phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public void setPlazoEntrega(int plazoEntrega) {
        this.plazoEntrega = plazoEntrega;
    }

    @Override
    public String toString() {
        return "Supplier{" +
                "ID=" + id +
                "\nname='" + name +
                "\nphoneNumber=" +
                "\nemail='" + email +
                "\naddress='" + address +
                "\nPlazo entrega=" + plazoEntrega +
                "}\n";
    }
}
