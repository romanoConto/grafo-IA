package com.grafo.models;

import java.util.ArrayList;
import java.util.List;

public class Vertice
{
    private String nome;
    private List<Aresta> arestas = new ArrayList<>();

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public List<Aresta> getArestas() {
        return arestas;
    }

    public void setArestas(List<Aresta> arestas) {
        this.arestas = arestas;
    }
}
