package com.grafo.leiaoEntregas.models;

import java.util.List;

public class Entradas {

    private int tamanhoMatrizGrafo = 0;
    private List<Vertice> verticesMatrizGrafo;
    private int tamanhoMatrizEntrega = 0;
    private List<PontoEntrega> verticesMatrizEntrega;

    public int getTamanhoMatrizGrafo() {
        return tamanhoMatrizGrafo;
    }

    public void setTamanhoMatrizGrafo(int tamanhoMatrizGrafo) {
        this.tamanhoMatrizGrafo = tamanhoMatrizGrafo;
    }

    public List<Vertice> getVerticesMatrizGrafo() {
        return verticesMatrizGrafo;
    }

    public void setVerticesMatrizGrafo(List<Vertice> verticesMatrizGrafo) {
        this.verticesMatrizGrafo = verticesMatrizGrafo;
    }

    public int getTamanhoMatrizEntrega() {
        return tamanhoMatrizEntrega;
    }

    public void setTamanhoMatrizEntrega(int tamanhoMatrizEntrega) {
        this.tamanhoMatrizEntrega = tamanhoMatrizEntrega;
    }

    public List<PontoEntrega> getVerticesMatrizEntrega() {
        return verticesMatrizEntrega;
    }

    public void setVerticesMatrizEntrega(List<PontoEntrega> verticesMatrizEntrega) {
        this.verticesMatrizEntrega = verticesMatrizEntrega;
    }
}
