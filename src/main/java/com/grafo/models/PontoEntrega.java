package com.grafo.models;

public class PontoEntrega
{
	private int tempoSaida;
	private String verticeDestino;
	private int bonus;

	public int getTempoSaida()
	{
		return tempoSaida;
	}

	public void setTempoSaida(int tempoSaida)
	{
		this.tempoSaida = tempoSaida;
	}

	public String getVerticeDestino()
	{
		return verticeDestino;
	}

	public void setVerticeDestino(String verticeDestino)
	{
		this.verticeDestino = verticeDestino;
	}

	public int getBonus()
	{
		return bonus;
	}

	public void setBonus(int bonus)
	{
		this.bonus = bonus;
	}
}
