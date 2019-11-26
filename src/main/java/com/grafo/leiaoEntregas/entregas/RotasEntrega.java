package com.grafo.leiaoEntregas.entregas;

import java.util.ArrayList;
import java.util.List;

public class RotasEntrega
{
	private Rota rotaMenor;
	private List<Rota> rotas = new ArrayList<>();

	public Rota getRotaMenor()
	{
		return rotaMenor;
	}

	public void setRotaMenor(Rota rotaMenor)
	{
		this.rotaMenor = rotaMenor;
	}

	public List<Rota> getRotas()
	{
		return rotas;
	}

	public void setRotas(List<Rota> rotas)
	{
		this.rotas = rotas;
	}
}
