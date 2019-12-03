package com.grafo.entregas.calculoProfundidade;

import com.grafo.entregas.CalculaEntregasUtils;
import com.grafo.models.*;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorCompletionService;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class Entregas {
    private Entradas entradas;
    private List<RotasEntrega> rotas = new ArrayList<>();
    private CalculaEntregasUtils calcUtils;

    public Entregas(Entradas entradas) {
        this.entradas = entradas;
        calcUtils = new CalculaEntregasUtils(entradas);
    }

    public List<RotasEntrega> processarEntregasThread() throws CloneNotSupportedException {
        List<PontoEntrega> pontoEntregas = entradas.getVerticesMatrizEntrega();

        int i = 0;
        List<CalculaEntregasProfundidadeThread> entregas = new ArrayList<>();
        for (PontoEntrega pontoEntrega : pontoEntregas) {
            i++;
            //Captura o primeiro ponto (A)
            Vertice pontoAtual = entradas.getVerticesMatrizGrafo().get(0);
            Rota rota = new Rota();
            rota.setDistancia(0);
            rota.setRecompensa(0);
            rota.setDestino(pontoEntrega.getVerticeDestino());
            rota.addPonto(pontoAtual.getNome());

            //Solicita as rotas
            CalculaEntregasProfundidadeThread cept = new CalculaEntregasProfundidadeThread(entradas, pontoAtual, rota);
            entregas.add(cept);
        }


        ExecutorService threadPool = Executors.newFixedThreadPool(i);
        ExecutorCompletionService<RotasEntrega> completionService = new ExecutorCompletionService<>(threadPool);

        //executa as tarefas
        for (CalculaEntregasProfundidadeThread entrega : entregas) {
            completionService.submit(entrega);
        }

        //aguarda e imprime o retorno de cada uma
        for (int j = 0; j < entregas.size(); j++) {
            try {
                RotasEntrega re = completionService.take().get();
                if (re != null) {
                    rotas.add(re);
                }
            } catch (InterruptedException | ExecutionException ex) {
                ex.printStackTrace();
            }
        }

        threadPool.shutdown();

        return rotas;
    }

    /**
     * Faz o gerenciamento das entregas e retorna as rotas
     */
    public List<RotasEntrega> processarEntregas() throws CloneNotSupportedException {

        //Obtem todos os pontos de entregas
        List<PontoEntrega> pontoEntregas = entradas.getVerticesMatrizEntrega();

        //Calcula a menor distancia (tempo) de cada ponto de entrega
        for (PontoEntrega pontoEntrega : pontoEntregas) {

            //Captura o primeiro ponto (A).
            Vertice pontoAtual = entradas.getVerticesMatrizGrafo().get(0);

            // Somente parametros para iniciar calculo.
            Rota rota = new Rota();
            rota.setDistancia(0);
            rota.setRecompensa(0);
            rota.setDestino(pontoEntrega.getVerticeDestino());
            rota.addPonto(pontoAtual.getNome());

            //Solicita as rotas. Retornar um objeto "RotasEntrega" com a menor rota e as rotas alternativas.
            RotasEntrega re = retornaRotas(pontoAtual, rota, null);

            if (re != null) {
                re.getRotaMenor().setPartida(pontoEntrega.getTempoSaida());
                rotas.add(re);
            }
        }

        return rotas;
    }

    /**
     * Retorna a menor rota encontrada
     */

    private RotasEntrega retornaRotas(Vertice pontoAtual, Rota rota, List<String> pontosVerificados)
            throws CloneNotSupportedException {

        //Retorna todas as possiveis rotas.
        List<Rota> possiveisRotas = getPossiveisRotas(pontoAtual, rota, pontosVerificados);

        //Faz a comparação entre as rotas e coloca a menor rota na primeira posição da lista (0)
        possiveisRotas.sort((x, y) -> Integer.compare(x.getDistancia(), y.getDistancia()));

        //Pega primeira rota após o sort realizado acima e remove da lista para que não fique duplicado
        if (possiveisRotas.size() == 0)
            return null;
        Rota rotaMenor = possiveisRotas.get(0);
        possiveisRotas.remove(rotaMenor);

        //Cria uma nova rota de entrega, sentando a menor rota e a lista de possiveis rotas
        RotasEntrega re = new RotasEntrega();
        re.setRotaMenor(rotaMenor);
        re.setRotas(possiveisRotas);

        return re;
    }

    private List<Rota> getPossiveisRotas(Vertice pontoAtual, Rota rota,
                                         List<String> pontosVerificados) throws CloneNotSupportedException {
        return getPossiveisRotas(pontoAtual, rota, pontosVerificados, null);
    }

    /**
     * Retorna as possiveis rotas encontradas
     */

    private List<Rota> getPossiveisRotas(Vertice pontoAtual, Rota rotaAnt,
                                         List<String> pontosVerificados, Aresta arestaAnterior) throws CloneNotSupportedException {
        //Cria uma nova rota com base na rota anterior (rotaAnt) (copia)
        Rota rota = new Rota();
        rota.setRecompensa(rotaAnt.getRecompensa());
        rota.setPontos(new ArrayList<>(rotaAnt.getPontos()));
        rota.setDistancia(rotaAnt.getDistancia());
        rota.setDestino(rotaAnt.getDestino());

        //adiciona o ponto na rota
        if (arestaAnterior != null && !rota.getPontos().contains(arestaAnterior.getVerticeDestino())) {
            rota.addPonto(arestaAnterior.getVerticeDestino());
            rota.addDistancia(arestaAnterior.getComprimento());
        }

        List<Rota> rotasPonto = new ArrayList<>();

        if (pontosVerificados == null) {
            pontosVerificados = new ArrayList<>();
        }

        //Varre arestas do nó
        for (Aresta aresta : pontoAtual.getArestas()) {

            //Verifica se ja passou pela nó ou se não nó na aresta
            if (pontosVerificados.stream().anyMatch(x -> x.equals(aresta.getVerticeDestino()))
                    || aresta.getComprimento() == 0) {
                continue;
            }

            //Verifica se encontrou o ponto de destino
            if (aresta.getVerticeDestino().equals(rota.getDestino())) {
                //Crio uma nova rota setando o ponto
                Rota r = new Rota();
                r.setDestino(rota.getDestino());
                r.setPontos(new ArrayList<>(rota.getPontos()));
                r.setRecompensa(rota.getRecompensa());
                r.setDistancia(rota.getDistancia());

                r.addPonto(aresta.getVerticeDestino());
                r.addDistancia(aresta.getComprimento());
                r.setRecompensa(calcUtils.getBonus(aresta.getVerticeDestino()));
                rotasPonto.add(r);

                continue;
            }

            //Adiciona os pontos verificados
            List<String> pontosVerfi = new ArrayList<>();
            for (String pontoString : rota.getPontos())
                pontosVerfi.add(pontoString);

            //obtem o proximo ponto a ser calculado
            Vertice ponto = calcUtils.getPonto(aresta.getVerticeDestino());

            //chama recursivamente a função passando o ponto atual, a rota em percurso, os pontos ja visitados, e a aresta anterior.
            //BUSCA PROFUNDA
            List<Rota> r = getPossiveisRotas(ponto, rota, pontosVerfi, aresta);

            if (r != null) {
                rotasPonto.addAll(r);
            }
        }
        return rotasPonto;
    }
}
