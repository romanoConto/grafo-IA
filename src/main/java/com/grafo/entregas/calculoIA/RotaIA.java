package com.grafo.entregas.calculoIA;

import com.grafo.entregas.calculoProfundidade.Rota;
import com.grafo.models.Vertice;

public class RotaIA extends Rota
{
    private Vertice verticeAtual;

    public Vertice getVerticeAtual() {
        return verticeAtual;
    }

    public void setVerticeAtual(Vertice verticeAtual) {
        this.verticeAtual = verticeAtual;
    }
}
