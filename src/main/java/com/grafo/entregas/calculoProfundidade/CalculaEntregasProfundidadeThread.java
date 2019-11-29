package com.grafo.entregas.calculoProfundidade;

import com.grafo.entregas.CalculaEntregasUtils;
import com.grafo.models.Aresta;
import com.grafo.models.Entradas;
import com.grafo.models.RotasEntrega;
import com.grafo.models.Vertice;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Callable;

public class CalculaEntregasProfundidadeThread implements Callable<RotasEntrega> {
    private Entradas entradas;
    private List<RotasEntrega> rotas = new ArrayList<>();
    private CalculaEntregasUtils calcUtils;
    private Vertice pontoAtual;
    private Rota rota;

    public CalculaEntregasProfundidadeThread(Entradas entradas, Vertice pontoAtual, Rota rota) {
        this.entradas = entradas;
        calcUtils = new CalculaEntregasUtils(entradas);
        this.pontoAtual = pontoAtual;
        this.rota = rota;
    }

    /**
     * Retorna a menor rota encontrada
     */

    private RotasEntrega retornaRotas()
            throws CloneNotSupportedException {
        List<Rota> possiveisRotas = getPossiveisRotas(pontoAtual, rota);

        //Faz a comparação entre as rotas e coloca a menor rota na primeira posição da lista (0)
        possiveisRotas.sort((x, y) -> Integer.compare(x.getDistancia(), y.getDistancia()));

        //Pega primeira rota após o sort realizado acima e remove da lista para que não fique duplicado
        if (possiveisRotas.isEmpty())
            return null;
        Rota rotaMenor = possiveisRotas.get(0);
        possiveisRotas.remove(rotaMenor);

        //Cria uma nova rota de entrega, sentando a menor rota e a lista de possiveis rotas
        RotasEntrega re = new RotasEntrega();
        re.setRotaMenor(rotaMenor);
        re.setRotas(possiveisRotas);

        return re;
    }

    private List<Rota> getPossiveisRotas(Vertice pontoAtual, Rota rota) throws CloneNotSupportedException {
        return getPossiveisRotas(pontoAtual, rota, null, null);
    }

    /**
     * Retorna as possiveis rotas encontradas
     */

    private List<Rota> getPossiveisRotas(Vertice pontoAtual, Rota rotaAnt,
                                         List<String> pontosVerificados, Aresta distAnt) throws CloneNotSupportedException {
        //Cria uma nova rota com base na rota anterior (rotaAnt)
        Rota rota = new Rota();
        rota.setRecompensa(rotaAnt.getRecompensa());
        rota.setPontos(new ArrayList<>(rotaAnt.getPontos()));
        rota.setDistancia(rotaAnt.getDistancia());
        rota.setDestino(rotaAnt.getDestino());

        if (distAnt != null && !rota.getPontos().contains(distAnt.getVerticeDestino())) {
            rota.addPonto(distAnt.getVerticeDestino());
            rota.addDistancia(distAnt.getComprimento());
        }

        List<Rota> rotasPonto = new ArrayList<>();

        if (pontosVerificados == null) {
            pontosVerificados = new ArrayList<>();
        }

        for (Aresta aresta : pontoAtual.getArestas()) {
            //Verifica se ja passou pela rota ou seja alcançavel
            if (pontosVerificados.stream().anyMatch(x -> x.equals(aresta.getVerticeDestino()))
                    || aresta.getComprimento() == 0) {
                continue;
            }

            //Encontrei meu ponto de destino
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
            for (String p : rota.getPontos())
                pontosVerfi.add(p);

            Vertice ponto = calcUtils.getPonto(aresta.getVerticeDestino());
            List<Rota> r = getPossiveisRotas(ponto, rota, pontosVerfi, aresta);
            if (r != null) {
                rotasPonto.addAll(r);
            }
        }
        return rotasPonto;
    }

    @Override
    public RotasEntrega call() throws Exception {
        return retornaRotas();
    }
}
