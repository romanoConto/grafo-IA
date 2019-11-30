package com.grafo.models;

import com.grafo.entregas.calculoProfundidade.Rota;

import java.util.ArrayList;
import java.util.List;

public class MelhorEntrega {
    private List<Rota> entregas = new ArrayList<>();
    private int tempoAtual;
    private int bonusTotal;

    public List<Rota> getEntregas() {
        return entregas;
    }

    public void setEntregas(List<Rota> entregas) {
        this.entregas = entregas;
    }

    public void addEntregas(Rota entregas) {
        this.entregas.add(entregas);
    }

    public int getTempoAtual() {
        return tempoAtual;
    }

    public void setTempoAtual(int tempoAtual) {
        this.tempoAtual = tempoAtual;
    }

    public void addTempo(int tempo) {
        this.tempoAtual += tempo;
    }

    public int getBonusTotal() {
        return bonusTotal;
    }

    public void setBonusTotal(int bonusTotal) {
        this.bonusTotal = bonusTotal;
    }

    public void addBonus(int bonus) {
        this.bonusTotal += bonus;
    }
}
