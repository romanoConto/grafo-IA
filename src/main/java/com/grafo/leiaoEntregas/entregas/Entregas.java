package com.grafo.leiaoEntregas.entregas;

import com.grafo.leiaoEntregas.models.Distancia;
import com.grafo.leiaoEntregas.models.Entradas;
import com.grafo.leiaoEntregas.models.PontoEntrega;
import com.grafo.leiaoEntregas.models.PontoGrafo;
import java.util.ArrayList;
import java.util.List;

public class Entregas
{
	private Entradas entradas;
	private List<RotasEntrega> rotas = new ArrayList<>();

	public Entregas(Entradas entradas)
	{
		this.entradas = entradas;
	}

	/**
	 * Faz o gerenciamento das entregas e retorna as rotas
	 */

	public List<RotasEntrega> processarEntregas() throws CloneNotSupportedException
	{
		List<PontoEntrega> pontoEntregas = entradas.getPontosEntrega();

		for (PontoEntrega pontoEntrega : pontoEntregas)
		{
			//Captura o primeiro ponto (A)
			PontoGrafo pontoAtual = entradas.getPontosGrafo().get(0);
			Rota rota = new Rota();
			rota.setDistancia(0);
			rota.setRecompensa(0);
			rota.setDestino(pontoEntrega.getDestino());
			rota.addPonto(pontoAtual.getNome());

			//Solicita as rotas
			RotasEntrega re = retornaRotas(pontoAtual, rota, null);

			if (re != null)
			{
				rotas.add(re);
			}
		}

		return rotas;
	}

	/**
	 * Retorna a menor rota encontrada
	 */

	private RotasEntrega retornaRotas(PontoGrafo pontoAtual, Rota rota, List<String> pontosVerificados)
		throws CloneNotSupportedException
	{
		List<Rota> possiveisRotas = getPossiveisRotas(pontoAtual, rota, pontosVerificados);

		//Faz a comparação entre as rotas e coloca a menor rota na primeira posição da lista (0)
		possiveisRotas.sort((x, y) -> Integer.compare(x.getDistancia(), y.getDistancia()));

		//Pega primeira rota após o sort realizado acima e remove da lista para que não fique duplicado
		Rota rotaMenor = possiveisRotas.get(0);
		possiveisRotas.remove(rotaMenor);

		//Cria uma nova rota de entrega, sentando a menor rota e a lista de possiveis rotas
		RotasEntrega re = new RotasEntrega();
		re.setRotaMenor(rotaMenor);
		re.setRotas(possiveisRotas);

		return re;
	}

	private List<Rota> getPossiveisRotas(PontoGrafo pontoAtual, Rota rota,
		List<String> pontosVerificados) throws CloneNotSupportedException
	{
		return getPossiveisRotas(pontoAtual, rota, pontosVerificados, null);
	}

	/**
	 * Retorna as possiveis rotas encontradas
	 */

	private List<Rota> getPossiveisRotas(PontoGrafo pontoAtual, Rota rotaAnt,
		List<String> pontosVerificados, Distancia distAnt) throws CloneNotSupportedException
	{
		//Cria uma nova rota com base na rota anterior (rotaAnt)
		Rota rota = new Rota();
		rota.setRecompensa(rotaAnt.getRecompensa());
		rota.setPontos(new ArrayList<>(rotaAnt.getPontos()));
		rota.setDistancia(rotaAnt.getDistancia());
		rota.setDestino(rotaAnt.getDestino());

		if (distAnt != null && !rota.getPontos().contains(distAnt.getNome()))
		{
			rota.addPonto(distAnt.getNome());
			rota.addDistancia(distAnt.getDistancia());
		}

		List<Rota> rotasPonto = new ArrayList<>();

		if (pontosVerificados == null)
		{
			pontosVerificados = new ArrayList<>();
		}

		for (Distancia dist : pontoAtual.getDistancias())
		{
			//Verifica se ja passou pela rota ou seja alcançavel
			if (pontosVerificados.stream().anyMatch(x -> x.equals(dist.getNome()))
				|| dist.getDistancia() == 0)
			{
				continue;
			}

			//Encontrei meu ponto de destino
			if (dist.getNome().equals(rota.getDestino()))
			{
				//Crio uma nova rota setando o ponto
				Rota r = new Rota();
				r.setDestino(rota.getDestino());
				r.setPontos(new ArrayList<>(rota.getPontos()));
				r.setRecompensa(rota.getRecompensa());
				r.setDistancia(rota.getDistancia());

				r.addPonto(dist.getNome());
				r.addDistancia(dist.getDistancia());
				r.setRecompensa(getBonus(dist.getNome()));
				rotasPonto.add(r);

				continue;
			}

			//Adiciona os pontos verificados
			List<String> pontosVerfi = new ArrayList<>();
			for (String p : rota.getPontos())
				pontosVerfi.add(p);

			PontoGrafo ponto = getPonto(dist.getNome());
			List<Rota> r = getPossiveisRotas(ponto, rota, pontosVerfi, dist);
			if (r != null)
			{
				rotasPonto.addAll(r);
			}
		}
		return rotasPonto;
	}

	/**
	 * Retorna um ponto do grafo (objeto)
	 */

	private PontoGrafo getPonto(String name)
	{
		for (PontoGrafo p : entradas.getPontosGrafo())
		{
			if (p.getNome().equals(name))
			{
				return p;
			}
		}
		return null;
	}

	/**
	 * Retorna o bonus de uma entrega
	*/

	private int getBonus(String pontodist)
	{
		return getEntrega(pontodist).getBonus();
	}

	/**
	 * Retorna o ponto de entrega
	 */

	private PontoEntrega getEntrega(String name)
	{
		for (PontoEntrega p : entradas.getPontosEntrega())
		{
			if (p.getDestino().equals(name))
			{
				return p;
			}
		}
		return null;
	}
}
