package com.grafo.leiaoEntregas.entregas.calculoIA;

import com.grafo.leiaoEntregas.entregas.calculoProfundidade.Rota;
import com.grafo.leiaoEntregas.models.Vertice;

public class RotaIA extends Rota {
    private Vertice verticeAtual;

    public Vertice getVerticeAtual() {
        return verticeAtual;
    }

    public void setVerticeAtual(Vertice verticeAtual) {
        this.verticeAtual = verticeAtual;
    }
}
