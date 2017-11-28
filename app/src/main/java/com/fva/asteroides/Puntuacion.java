package com.fva.asteroides;

public class Puntuacion {
    private long fecha;
    private String nombre;
    private int puntos;

    public Puntuacion(int puntos, String nombre, long fecha) {
        this.puntos = puntos;
        this.nombre = nombre;
        this.fecha = fecha;
    }

    public int getPuntos() {
        return this.puntos;
    }

    public void setPuntos(int puntos) {
        this.puntos = puntos;
    }

    public String getNombre() {
        return this.nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public long getFecha() {
        return this.fecha;
    }

    public void setFecha(long fecha) {
        this.fecha = fecha;
    }
}
