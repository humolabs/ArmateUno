package com.humolabs.armateuno.domain;

import java.util.List;

public class Partido {
        private Cancha cancha;

        private String fechaHora;
        private Place place;
        private List<Jugador> jugadores;
        private Jugador owner;

    public Partido(){

    }

    public Partido(Cancha cancha, String horario, Place place, List<Jugador> jugadores, Jugador owner) {
        this.cancha = cancha;
        this.fechaHora = horario;
        this.place = place;
        this.jugadores = jugadores;
        this.owner = owner;
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

    public Place getPlace() {
        return place;
    }

    public void setPlace(Place place) {
        this.place = place;
    }

    public List<Jugador> getJugadores() {
        return jugadores;
    }

    public void setJugadores(List<Jugador> jugadores) {
        this.jugadores = jugadores;
    }

    public Jugador getOwner() { return owner; }

    public void setOwner (Jugador owner) { this.owner = owner; }
}
