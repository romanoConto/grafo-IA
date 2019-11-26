package com.grafo.leiaoEntregas.entregas;

import java.util.ArrayList;
import java.util.List;

public class Rota implements Cloneable {

    private int distancia;
    private int recompensa;
    private String destino;
    private List<String> pontos = new ArrayList<>();

    public String getDestino()
    {
        return destino;
    }

    public void setDestino(String destino)
    {
        this.destino = destino;
    }

    public int getDistancia() {
        return distancia;
    }

    public void setDistancia(int distancia) {
        this.distancia = distancia;
    }

    public void addDistancia(int distancia) {
        this.distancia += distancia;
    }

    public int getRecompensa() {
        return recompensa;
    }

    public void setRecompensa(int recompensa) {
        this.recompensa = recompensa;
    }

    public List<String> getPontos() {
        return pontos;
    }

    public void setPontos(List<String> pontos) {
        this.pontos = pontos;
    }

    public void addPonto(String ponto)
    {
        this.pontos.add(ponto);
    }

    @Override
    public Rota clone() throws CloneNotSupportedException {
        return (Rota) super.clone();
    }
}
