package com.humolabs.armateuno.domain;

public class Ubicacion {
    private String latLong;
    private String direccion;

    public Ubicacion(String latLong, String direccion) {
        this.latLong = latLong;
        this.direccion = direccion;
    }

    public Ubicacion(){

    }

    public String getLatLong() {
        return latLong;
    }

    public void setLatLong(String latLong) {
        this.latLong = latLong;
    }

    public String getDireccion() {
        return direccion;
    }

    public void setDireccion(String direccion) {
        this.direccion = direccion;
    }
}
