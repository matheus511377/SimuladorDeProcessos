package com.matheus.simuladordeprocessos.simuladordeprocessos.Classes;

/**
 * Created by Matheus on 13/09/2016.
 */
public class Fila {
    private int id;
    private int numeroFila;

    public Fila(int id, int numeroFila) {
        this.id = id;
        this.numeroFila = numeroFila;
    }

    public int getId() {
        return this.id;
    }

    public int getNumeroFila() {
        return this.numeroFila;
    }
}
