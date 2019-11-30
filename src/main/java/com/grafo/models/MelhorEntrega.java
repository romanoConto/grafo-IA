package com.grafo.models;

import com.grafo.entregas.calculoProfundidade.Rota;

import java.util.ArrayList;
import java.util.List;

public class MelhorEntrega {
    private List<Rota> entregas = new ArrayList<>();
    private int tempo;
    private int bonus;

    public List<Rota> getEntregas() {
        return entregas;
    }

    public void setEntregas(List<Rota> entregas) {
        this.entregas = entregas;
    }

    public void addEntregas(Rota entregas) {
        this.entregas.add(entregas);
    }

    public int getTempo() {
        return tempo;
    }

    public void setTempo(int tempo) {
        this.tempo = tempo;
    }

    public void addTempo(int tempo) {
        this.tempo += tempo;
    }

    public int getBonus() {
        return bonus;
    }

    public void setBonus(int bonus) {
        this.bonus = bonus;
    }

    public void addBonus(int bonus) {
        this.bonus += bonus;
    }
}
