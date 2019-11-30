package com.grafo.carregarDados;

import com.grafo.models.Aresta;
import com.grafo.models.Entradas;
import com.grafo.models.PontoEntrega;
import com.grafo.models.Vertice;

import java.io.BufferedReader;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class LerEntradas {
    private static Entradas arquivoTxt;

    public Entradas lerArquivoTxt(String diretorio) throws Exception {

        arquivoTxt = new Entradas();

        FileReader arquivo = new FileReader(diretorio);
        BufferedReader leituraDoBuffer = new BufferedReader(arquivo);

        String linhaTxt = null;
        List<Vertice> vertices = new ArrayList<>();
        List<PontoEntrega> pontosEntregas = new ArrayList<>();
        int linhaMatriz = 0;
        boolean matrizElaborada = false;

        while ((linhaTxt = leituraDoBuffer.readLine()) != null) {

            int colunaMatriz = 0;

            List<String> colunas = Arrays.asList(linhaTxt.split(","));

            //Verifica se é o tamanho da matrizElaborada distancia
            if (colunas.size() == 1 && arquivoTxt.getTamanhoMatrizGrafo() == 0) {
                arquivoTxt.setTamanhoMatrizGrafo(Integer.parseInt(colunas.get(0)));
                continue;
            }

            //Verifica se é o tamanho da matrizElaborada de entregas
            if (colunas.size() == 1 && arquivoTxt.getTamanhoMatrizEntrega() == 0) {
                arquivoTxt.setTamanhoMatrizEntrega(Integer.parseInt(colunas.get(0)));
                continue;
            }

            if (!linhaTxt.contains("'") && verificaSeLetra(colunas)) {
                PontoEntrega rota = new PontoEntrega();

                rota.setTempoSaida(Integer.parseInt(colunas.get(0)));
                rota.setVerticeDestino(colunas.get(1));
                rota.setBonus(Integer.parseInt(colunas.get(2)));
                pontosEntregas.add(rota);
                continue;
            }

            for (String coluna : colunas) {

                //Verifica se é o cabeçalho da matrizElaborada com o nome dos pontos
                if (coluna.contains("'")) {
                    if (colunas.size() != arquivoTxt.getTamanhoMatrizGrafo())
                        throw new Exception();

                    Vertice ponto = new Vertice();
                    ponto.setNome(coluna.replaceAll("'", ""));

                    vertices.add(ponto);
                    continue;
                }

                //Verifica se é uma linhaTxt da matrizElaborada distancia
                if (!verificaSeLetra(colunas)) {
                    Vertice ponto = vertices.get(colunaMatriz);
                    List<Aresta> arestas = ponto.getArestas();

                    Integer distancia = Integer.valueOf(coluna);

                    Aresta novaAresta = new Aresta();
                    try {
                        if (linhaMatriz == colunaMatriz && distancia != 0) {
                            continue;
                        }
                    } catch (Exception e) {
                        System.out.println("A distancia do ponto " + ponto.getNome() + " é diferente de zero!");
                    }

                    novaAresta.setVerticeDestino(vertices.get(linhaMatriz).getNome());
                    novaAresta.setComprimento(distancia);

                    arestas.add(novaAresta);

                    ponto.setArestas(arestas);

                    matrizElaborada = true;
                    colunaMatriz++;
                    continue;
                }
            }
            if (matrizElaborada)
                linhaMatriz++;
        }
        if (arquivoTxt.getTamanhoMatrizGrafo() != linhaMatriz)
            throw new Exception();

        arquivoTxt.setVerticesMatrizGrafo(vertices);
        arquivoTxt.setVerticesMatrizEntrega(pontosEntregas);

        return arquivoTxt;
    }

    private static boolean verificaSeLetra(List<String> colunas) {
        for (String coluna : colunas) {
            char[] posicao = coluna.toCharArray();
            for (int i = 0; i < posicao.length; i++) {
                if (Character.isAlphabetic(posicao[i])) {
                    return true;
                }
            }
        }
        return false;
    }
}
