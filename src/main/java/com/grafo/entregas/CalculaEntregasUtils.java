package com.grafo.entregas;

import com.grafo.entregas.calculoProfundidade.Rota;
import com.grafo.entregas.calculoIA.RotaIA;
import com.grafo.models.Aresta;
import com.grafo.models.Entradas;
import com.grafo.models.PontoEntrega;
import com.grafo.models.Vertice;

import java.util.ArrayList;

public class CalculaEntregasUtils {

    Entradas entradas;

    public CalculaEntregasUtils(Entradas entradas) {
        this.entradas = entradas;
    }

    /**
     * Retorna um ponto do grafo (objeto)
     */
    public Vertice getPonto(String name) {
        for (Vertice p : entradas.getVerticesMatrizGrafo()) {
            if (p.getNome().equals(name)) {
                return p;
            }
        }
        return null;
    }

    /**
     * Retorna o bonus de uma entrega
     */
    public int getBonus(String pontodist) {
        return getEntrega(pontodist).getBonus();
    }

    /**
     * Retorna o ponto de entrega
     */
    public PontoEntrega getEntrega(String name) {
        for (PontoEntrega p : entradas.getVerticesMatrizEntrega()) {
            if (p.getVerticeDestino().equals(name)) {
                return p;
            }
        }
        return null;
    }


    public Rota criaCopiaRota(Rota rota) {

        Rota r = new Rota();
        r.setDestino(rota.getDestino());
        r.setPontos(new ArrayList<>(rota.getPontos()));
        r.setRecompensa(rota.getRecompensa());
        r.setDistancia(rota.getDistancia());
        return r;
    }

    public RotaIA criaNovaRota(RotaIA rota, Aresta a) {
        RotaIA r = new RotaIA();
        r.setDestino(rota.getDestino());
        r.setPontos(new ArrayList<>(rota.getPontos()));
        r.setRecompensa(rota.getRecompensa());
        r.setDistancia(rota.getDistancia());
        r.setVerticeAtual(getPonto(a.getVerticeDestino()));
        r.addPonto(a.getVerticeDestino());
        r.addDistancia(a.getComprimento());

        return r;
    }

    public Rota criaNovaRota(Rota rota, Aresta a) {
        Rota r = criaCopiaRota(rota);
        r.addPonto(a.getVerticeDestino());
        return r;
    }
}
