package com.grafo.leiaoEntregas.carregarDados;

import com.grafo.leiaoEntregas.models.Aresta;
import com.grafo.leiaoEntregas.models.Entradas;
import com.grafo.leiaoEntregas.models.PontoEntrega;
import com.grafo.leiaoEntregas.models.Vertice;

import java.io.BufferedReader;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class LerEntradas {
    private static Entradas arquivoTxt;

    /**
     * Faz a leitura do arquivo, quebrando em linhas e colunas
     */

    public Entradas lerArquivoTxt(String diretorio) throws Exception {

        arquivoTxt = new Entradas();

        FileReader arquivo = new FileReader(diretorio);
        BufferedReader leituraDoBuffer = new BufferedReader(arquivo);
        String linhaTxt = null;

        List<Vertice> vertices = new ArrayList<>();
        List<PontoEntrega> pontosEntregas = new ArrayList<>();

        int tamanhoLinhaMatriz = 0;
        boolean matrizElaborada = false;

        while ((linhaTxt = leituraDoBuffer.readLine()) != null) {

            int tamanhoColunaMatriz = 0;

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


            if (!linhaTxt.contains("'") && isRoute(colunas)) {
                PontoEntrega rota = new PontoEntrega();

                rota.setVerticeOrigem(Integer.parseInt(colunas.get(0)));
                rota.setVerticeDestino(colunas.get(1));
                rota.setBonus(Integer.parseInt(colunas.get(2)));

                pontosEntregas.add(rota);
                continue;
            }

            for (String col : colunas) {

                //Verifica se é o cabeçalho da matrizElaborada com o nome dos pontos
                if (col.contains("'")) {
                    if (colunas.size() != arquivoTxt.getTamanhoMatrizGrafo())
                        throw new Exception();

                    Vertice ponto = new Vertice();
                    ponto.setNome(col.replaceAll("'", ""));

                    vertices.add(ponto);
                    continue;
                }

                //Verifica se é uma linhaTxt da matrizElaborada distancia
                if (!isRoute(colunas)) {
                    Vertice ponto = vertices.get(tamanhoColunaMatriz);
                    List<Aresta> arestas = ponto.getArestas();

                    Integer distancia = Integer.valueOf(col);

                    Aresta novaAresta = new Aresta();
                    try {
                        if (tamanhoLinhaMatriz == tamanhoColunaMatriz && distancia != 0) {
                            continue;
                        }
                    } catch (Exception e) {
                        System.out.println("A distancia do ponto " + ponto.getNome() + " é diferente de zero!");
                    }

                    novaAresta.setVerticeDestino(vertices.get(tamanhoLinhaMatriz).getNome());
                    novaAresta.setComprimento(distancia);

                    arestas.add(novaAresta);

                    ponto.setArestas(arestas);

                    matrizElaborada = true;
                    tamanhoColunaMatriz++;
                    continue;
                }

                //Verifica se é uma linhaTxt da matrizElaborada entregas
            }

            if (matrizElaborada) {
                tamanhoLinhaMatriz++;
            }
        }
        if (arquivoTxt.getTamanhoMatrizGrafo() != tamanhoLinhaMatriz)
            throw new Exception();

        arquivoTxt.setVerticesMatrizGrafo(vertices);
        arquivoTxt.setVerticesMatrizEntrega(pontosEntregas);

        return arquivoTxt;
    }

    /**
     * Verifica se parte analisada é um caracter alfabetico,
     * caso seja, sinifica que é um ponto do grafo
     */

    private static boolean isRoute(List<String> partes) {
        for (String parte : partes) {
            char[] ps = parte.toCharArray();
            for (int i = 0; i < ps.length; i++) {
                if (Character.isAlphabetic(ps[i])) {
                    return true;
                }
            }
        }
        return false;
    }
}
