package com.grafo.leiaoEntregas.entregas.calculoIA;

import com.grafo.leiaoEntregas.entregas.calculoProfundidade.Rota;
import com.grafo.leiaoEntregas.models.Entradas;
import com.grafo.leiaoEntregas.models.PontoEntrega;
import com.grafo.leiaoEntregas.models.RotasEntrega;
import com.grafo.leiaoEntregas.models.Vertice;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorCompletionService;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class EntregasIA {

    private Entradas entradas;
    private List<RotasEntrega> rotas = new ArrayList<>();

    public EntregasIA(Entradas entradas) {
        this.entradas = entradas;
    }

    /**
     * Faz o gerenciamento das entregas e retorna as rotas
     */

    public List<RotasEntrega> processarEntregas() throws CloneNotSupportedException {
        List<PontoEntrega> pontoEntregas = entradas.getVerticesMatrizEntrega();

        for (PontoEntrega pontoEntrega : pontoEntregas) {
            //Captura o primeiro ponto (A)
            Vertice verticeAtual = entradas.getVerticesMatrizGrafo().get(0);
            RotaIA rota = new RotaIA();
            rota.setDistancia(0);
            rota.setRecompensa(pontoEntrega.getBonus());
            rota.setDestino(pontoEntrega.getVerticeDestino());
            rota.addPonto(verticeAtual.getNome());
            rota.setVerticeAtual(verticeAtual);

            //Solicita as rotas
            CalculaRotaEntregaIA calculaRotasIA = new CalculaRotaEntregaIA(entradas);
            Rota r = calculaRotasIA.calculaRota(rota);

            if (r != null) {
                RotasEntrega re = new RotasEntrega();
                re.setRotaMenor(r);
                rotas.add(re);
            }
        }

        return rotas;
    }


    public List<RotasEntrega> processaEntregasThread() {
        List<PontoEntrega> pontoEntregas = entradas.getVerticesMatrizEntrega();

        int i = 0;
        List<CalculaRotaEntregaIAThread> entregas = new ArrayList<>();
        for (PontoEntrega pontoEntrega : pontoEntregas) {
            i++;
            //Captura o primeiro ponto (A)
            Vertice verticeAtual = entradas.getVerticesMatrizGrafo().get(0);
            RotaIA rota = new RotaIA();
            rota.setDistancia(0);
            rota.setRecompensa(pontoEntrega.getBonus());
            rota.setDestino(pontoEntrega.getVerticeDestino());
            rota.addPonto(verticeAtual.getNome());
            rota.setVerticeAtual(verticeAtual);

            //Solicita as rotas
            CalculaRotaEntregaIAThread calculaRotasIAThread = new CalculaRotaEntregaIAThread(entradas, rota);
            entregas.add(calculaRotasIAThread);
        }

        ExecutorService threadPool = Executors.newFixedThreadPool(i);
        ExecutorCompletionService<RotaIA> completionService = new ExecutorCompletionService<>(threadPool);

        //executa as tarefas
        for (CalculaRotaEntregaIAThread entrega : entregas) {
            completionService.submit(entrega);
        }

        //aguarda e imprime o retorno de cada uma
        for (int j = 0; j < entregas.size(); j++) {
            try {
                Rota r = completionService.take().get();
                if (r != null) {
                    RotasEntrega re = new RotasEntrega();
                    re.setRotaMenor(r);
                    rotas.add(re);
                }
            } catch (InterruptedException | ExecutionException ex) {
                ex.printStackTrace();
            }
        }

        threadPool.shutdown();

        return rotas;
    }
}
