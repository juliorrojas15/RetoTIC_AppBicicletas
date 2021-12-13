package com.app.retotic.modelos;

public class Sucursal {
    private int id;
    private String name;
    private String description;
    private String address;
    private byte[] image;

    public Sucursal(int id, String name, String description, String address, byte[] image) {
        this.id = id;
        this.name = name;
        this.description = description;
        this.address = address;
        this.image = image;
    }

    public Sucursal() {
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public byte[] getImage() {
        return image;
    }

    public void setImage(byte[] image) {
        this.image = image;
    }
}
