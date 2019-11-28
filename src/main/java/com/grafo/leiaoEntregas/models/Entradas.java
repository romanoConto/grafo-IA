package com.grafo.leiaoEntregas.models;

import java.util.List;

public class Entradas {

    private int tamanhoMatrizEntrada = 0;
    private List<Vertice> pontosEntrada;
    private int tamanhoMatrizEntrega = 0;
    private List<PontoEntrega> pontosEntrega;

    public int getTamanhoMatrizEntrada() {
        return tamanhoMatrizEntrada;
    }

    public void setTamanhoMatrizEntrada(int tamanhoMatrizEntrada) {
        this.tamanhoMatrizEntrada = tamanhoMatrizEntrada;
    }

    public List<Vertice> getPontosGrafo() {
        return pontosEntrada;
    }

    public void setPontosEntrada(List<Vertice> pontosEntrada) {
        this.pontosEntrada = pontosEntrada;
    }

    public int getTamanhoMatrizEntrega() {
        return tamanhoMatrizEntrega;
    }

    public void setTamanhoMatrizEntrega(int tamanhoMatrizEntrega) {
        this.tamanhoMatrizEntrega = tamanhoMatrizEntrega;
    }

    public List<PontoEntrega> getPontosEntrega() {
        return pontosEntrega;
    }

    public void setPontosEntrega(List<PontoEntrega> pontosEntrega) {
        this.pontosEntrega = pontosEntrega;
    }
}
