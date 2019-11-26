package com.grafo.leiaoEntregas;

import java.util.List;

public class Entradas {

    private int tamanhoMatrizEntrada = 0;
    private List<PontoGrafo> pontosEntrada;
    private int tamanhoMatrizEntrega = 0;
    private List<PontoEntrega> pontosEntrega;

    public int getTamanhoMatrizEntrada() {
        return tamanhoMatrizEntrada;
    }

    public void setTamanhoMatrizEntrada(int tamanhoMatrizEntrada) {
        this.tamanhoMatrizEntrada = tamanhoMatrizEntrada;
    }

    public List<PontoGrafo> getPontosGrafo() {
        return pontosEntrada;
    }

    public void setPontosEntrada(List<PontoGrafo> pontosEntrada) {
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
