package com.grafo.leiaoEntregas.carregarDados;

import com.grafo.leiaoEntregas.models.Aresta;
import com.grafo.leiaoEntregas.models.Entradas;
import com.grafo.leiaoEntregas.models.PontoEntrega;
import com.grafo.leiaoEntregas.models.Vertice;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class GerarMatrizNxN {
    private static Entradas matriz;

    public static Entradas getMatriz(int tamanho, Float f) {
        matriz = new Entradas();
        List<PontoEntrega> pontosEntrega = new ArrayList<>();
        List<Vertice> pontosGrafo = new ArrayList<>();
        List<String> nomesVertices = new ArrayList<>();

        int a1 = 0;
        int a2 = 0;
        int a3 = 0;
        int a4 = 0;

        for (int i = 0; i < tamanho; i++) {
            a1++;
            if (a1 == 26) {
                a2++;
                a1 = 0;
            }
            if (a2 == 26) {
                a3++;
                a2 = 0;
            }
            if (a3 == 26) {
                a4++;
                a3 = 0;
            }
            nomesVertices.add(getName(a1, a2, a3, a4));
        }

        for (int i = 0; i < tamanho; i++) {
            Vertice v = new Vertice();
            v.setNome(nomesVertices.get(i));
            List<Aresta> arestas = new ArrayList<>();

            for (int j = 0; j < tamanho; j++) {
                Aresta a = new Aresta();

                double complexidade = f > 75 ? 37 : f/2;
                if (tamanho > 500 && f > 37)
                    complexidade = f + (100 - (f)) - (15000 / tamanho);
                complexidade = 1 - (complexidade / 100);

                int maxArestas = 0;
                if(tamanho < 50)
                 maxArestas = 10;
                else if(tamanho < 100)
                    maxArestas = 7;
                else if(tamanho < 500)
                    maxArestas = 6;
                else if(tamanho < 1000)
                    maxArestas = 5;
                else
                    maxArestas = 3;
                if (i == j || getRandomBoolean(complexidade) || Math.abs(i - j) > maxArestas) {
                    a.setComprimento(0);
                } else {
                    Random r = new Random();
                    a.setComprimento(r.nextInt(20));
                }
                a.setVerticeDestino(nomesVertices.get(j));
                arestas.add(a);
            }
            v.setArestas(arestas);
            pontosGrafo.add(v);
        }

        for (int i = 1; i < tamanho; i += 2) {
            PontoEntrega pontoEntrega = new PontoEntrega();
            pontoEntrega.setBonus((i + 100) / 100);
            pontoEntrega.setVerticeDestino(nomesVertices.get(i));
            pontoEntrega.setTempoSaida((i + 10) / 2);
            pontosEntrega.add(pontoEntrega);
        }

        matriz.setTamanhoMatrizGrafo(tamanho);
        matriz.setTamanhoMatrizEntrega(tamanho);
        matriz.setVerticesMatrizEntrega(pontosEntrega);
        matriz.setVerticesMatrizGrafo(pontosGrafo);

        return matriz;

    }

    private static String getName(int a1, int a2, int a3, int a4) {
        List<Integer> ints = new ArrayList<>();
        ints.add(a1);
        ints.add(a2);
        ints.add(a3);
        ints.add(a4);
        ints.add(4);
        String name = "";
        for (Integer i : ints) {
            char aux = 'A';
            for (int j = 0; j < i; j++)
                aux++;
            name += aux;
        }
        return name;
    }

    public static boolean getRandomBoolean(double f) {
        return Math.random() < f;
    }
}

