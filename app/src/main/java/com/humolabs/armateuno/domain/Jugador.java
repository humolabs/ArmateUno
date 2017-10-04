package com.humolabs.armateuno.domain;

public class Jugador {
    private String usuario;

    public Jugador(){

    }

    public Jugador(String usuario) {
        this.usuario = usuario;
    }

    public String getUsuario() {
        return usuario;
    }
    public void setUsuario(String usuario) {
        this.usuario = usuario;
    }
}
