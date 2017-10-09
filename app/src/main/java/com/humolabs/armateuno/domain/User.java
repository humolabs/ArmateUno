package com.humolabs.armateuno.domain;


import java.util.List;

public class User {
    private String userName;
    private String password;
    private List<Partido> partidosCreados;
    private List<Partido> partidosInvitado;

    public User(String userName, String password) {
        this.userName = userName;
        this.password = password;
    }

    public User (){

    }

    public List<Partido> getPartidosCreados() {
        return partidosCreados;
    }

    public void setPartidosCreados(List<Partido> partidosCreados) {
        this.partidosCreados = partidosCreados;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public List<Partido> getPartidosInvitado() {
        return partidosInvitado;
    }

    public void setPartidosInvitado(List<Partido> partidosInvitado) {
        this.partidosInvitado = partidosInvitado;
    }
}
