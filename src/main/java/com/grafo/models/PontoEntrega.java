package com.grafo.models;

public class PontoEntrega
{
	private int verticeOrigem	;
	private String verticeDestino;
	private int bonus;

	public int getVerticeOrigem()
	{
		return verticeOrigem;
	}

	public void setVerticeOrigem(int verticeOrigem)
	{
		this.verticeOrigem = verticeOrigem;
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
