package com.humolabs.armateuno.domain;

import com.google.android.gms.location.places.Place;

public class Ubicacion {
    private String latitud;
    private String longitud;
    private Place place;

    public Ubicacion(Place place){
        this.place = place;
    }

    public Ubicacion(){

    }

    public Ubicacion(String latitud, String longitud) {
        this.latitud = latitud;
        this.longitud = longitud;
    }

    public String getLatitud() {
        return latitud;
    }
    public void setLatitud(String latitud) {
        this.latitud = latitud;
    }
    public String getLongitud() {
        return longitud;
    }
    public void setLongitud(String longitud) {
        this.longitud = longitud;
    }

    public Place getPlace() {
        return place;
    }

    public void setPlace(Place place) {
        this.place = place;
    }

}
