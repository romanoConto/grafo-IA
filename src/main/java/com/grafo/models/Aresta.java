package com.grafo.models;

public class Aresta {
    private String verticeDestino;
    private int comprimento;

    public String getVerticeDestino() {
        return verticeDestino;
    }

    public void setVerticeDestino(String verticeDestino) {
        this.verticeDestino = verticeDestino;
    }

    public int getComprimento() {
        return comprimento;
    }

    public void setComprimento(int comprimento) {
        this.comprimento = comprimento;
    }
}
