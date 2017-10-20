package com.humolabs.armateuno.domain;

import java.util.List;

public class Partido {
        private Cancha cancha;
        private String fechaHora;
        private String userCreador;
        private List<Jugador> jugadores;

    public Partido(){

    }

    public Partido(Cancha cancha, String horario, Ubicacion ubicacion, List<Jugador> jugadores) {
        this.cancha = cancha;
        this.fechaHora = horario;
        this.jugadores = jugadores;
    }

    public Cancha getCancha() {
        return cancha;
    }

    public void setCancha(Cancha cancha) {
        this.cancha = cancha;
    }

    public String getHorario() {
        return fechaHora;
    }

    public void setHorario(String horario) {
        this.fechaHora = horario;
    }

    public List<Jugador> getJugadores() {
        return jugadores;
    }

    public void setJugadores(List<Jugador> jugadores) {
        this.jugadores = jugadores;
    }

    public String getFechaHora() {
        return fechaHora;
    }

    public void setFechaHora(String fechaHora) {
        this.fechaHora = fechaHora;
    }

    public String getUserCreador() {
        return userCreador;
    }

    public void setUserCreador(String userCreador) {
        this.userCreador = userCreador;
    }

}
