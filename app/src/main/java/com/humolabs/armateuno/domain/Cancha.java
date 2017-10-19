package com.humolabs.armateuno.domain;

public class Cancha {
    private String nombre;
    private Ubicacion ubicacion;
    private Integer capacidad;

    public Cancha(){

    }

    public Cancha(String nombre, Ubicacion ubicacion, Integer capacidad) {
        this.nombre = nombre;
        this.ubicacion = ubicacion;
        this.capacidad = capacidad;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public Integer getCapacidad() {
        return capacidad;
    }

    public Ubicacion getUbicacion() {
        return ubicacion;
    }

    public void setUbicacion(Ubicacion ubicacion) {
        this.ubicacion = ubicacion;
    }

    public void setCapacidad(Integer capacidad) {
        this.capacidad = capacidad;
    }
}
