package com.matheus.simuladordeprocessos.simuladordeprocessos.Classes;

/**
 * Created by Matheus on 13/09/2016.
 */

public class Processo {
    private String estado;
    private int id;
    private int numeroCiclosExecutando;
    private String recursoSolicitado;
    private boolean solicitouHD;
    private boolean solicitouImpressora;
    private boolean solicitouVideo;
    private int tempoExecutando;
    private int tempoExecutandoTotalES;
    private int tempoTotal;
    private int tempoTotalES;
    private long tempoEspera;

    public long getTempoEspera() {
        return tempoEspera;
    }

    public void setTempoEspera(long tempoEspera) {
        this.tempoEspera = tempoEspera;
    }

    public Processo() {
        this.solicitouHD = false;
        this.solicitouImpressora = false;
        this.solicitouVideo = false;
    }

    public boolean isSolicitouImpressora() {
        return this.solicitouImpressora;
    }

    public void setSolicitouImpressora(boolean solicitouImpressora) {
        this.solicitouImpressora = solicitouImpressora;
    }

    public boolean isSolicitouVideo() {
        return this.solicitouVideo;
    }

    public void setSolicitouVideo(boolean solicitouVideo) {
        this.solicitouVideo = solicitouVideo;
    }

    public boolean isSolicitouHD() {
        return this.solicitouHD;
    }

    public void setSolicitouHD(boolean solicitouHD) {
        this.solicitouHD = solicitouHD;
    }

    public int getTempoExecutandoTotalES() {
        return this.tempoExecutandoTotalES;
    }

    public void setTempoExecutandoTotalES(int tempoExecutandoTotalES) {
        this.tempoExecutandoTotalES = tempoExecutandoTotalES;
    }

    public String getRecursoSolicitado() {
        return this.recursoSolicitado;
    }

    public void setRecursoSolicitado(String recursoSolicitado) {
        this.recursoSolicitado = recursoSolicitado;
    }

    public int getTempoTotalES() {
        return this.tempoTotalES;
    }

    public void setTempoTotalES(int tempoTotalES) {
        this.tempoTotalES = tempoTotalES;
    }

    public int getId() {
        return this.id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getTempoTotal() {
        return this.tempoTotal;
    }

    public void setTempoTotal(int tempoTotal) {
        this.tempoTotal = tempoTotal;
    }

    public int getTempoExecutando() {
        return this.tempoExecutando;
    }

    public void setTempoExecutando(int tempoExecutando) {
        this.tempoExecutando = tempoExecutando;
    }

    public String getEstado() {
        return this.estado;
    }

    public void setEstado(String estado) {
        this.estado = estado;
    }

    public int getNumeroCiclosExecutando() {
        return this.numeroCiclosExecutando;
    }

    public void setNumeroCiclosExecutando(int numeroCiclosExecutando) {
        this.numeroCiclosExecutando = numeroCiclosExecutando;
    }
}
