package com.grafo.leiaoEntregas;

import java.util.ArrayList;
import java.util.List;

public class PontoGrafo
{

    private String nome;
    private List<Distancia> distancias = new ArrayList<>();

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public List<Distancia> getDistancias() {
        return distancias;
    }

    public void setDistancias(List<Distancia> distancias) {
        this.distancias = distancias;
    }
}
