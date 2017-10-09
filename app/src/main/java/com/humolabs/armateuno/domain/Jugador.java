package com.humolabs.armateuno.domain;

public class Jugador{

    private User user;
    private String name;
    private String nickName;
    private String mail;
    private String cellNumber;

    public Jugador(String nickName, String cellNumber) {
        this.nickName = nickName;
        this.cellNumber = cellNumber;
    }

    public Jugador(){ }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getNickName() {
        return nickName;
    }

    public void setNickName(String nickName) {
        this.nickName = nickName;
    }

    public String getMail() {
        return mail;
    }

    public void setMail(String mail) {
        this.mail = mail;
    }

    public String getCellNumber() {
        return cellNumber;
    }

    public void setCellNumber(String cellNumber) {
        this.cellNumber = cellNumber;
    }
}
