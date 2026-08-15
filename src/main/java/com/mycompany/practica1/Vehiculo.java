package com.mycompany.practica1;

/**
 * Representa un vehículo estacionado: su placa y la posición interna
 * (fila, columna) que ocupa dentro del estacionamiento de 8x8.
 */
public class Vehiculo {

    private String placa;
    private int fila;
    private int columna;

    public Vehiculo(String placa, int fila, int columna) {
        this.placa = placa;
        this.fila = fila;
        this.columna = columna;
    }

    public String getPlaca() {
        return placa;
    }

    public int getFila() {
        return fila;
    }

    public int getColumna() {
        return columna;
    }
}
