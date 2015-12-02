package br.com.camiloporto.marmitex.android.model;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.TreeSet;

public class Cardapio implements Serializable {

	private String descricao;
	
	private boolean disponivel;
	
	private Set<GrupoAlimentar> gruposItems = new TreeSet<GrupoAlimentar>();


	public Cardapio() {

	}

	public GrupoAlimentar adicioneGrupoDeOpcoes(String descricao) {
		GrupoAlimentar grupoItems = new GrupoAlimentar();
		grupoItems.setDescricao(descricao);
		gruposItems.add(grupoItems);
		return grupoItems;
	}
	
	public GrupoAlimentar adicionaGrupo(GrupoAlimentar grupo) {
		gruposItems.remove(grupo);
		gruposItems.add(grupo);
		return grupo;
	}
	
	public boolean removeGrupo(GrupoAlimentar grupoItems) {
		return gruposItems.remove(grupoItems);
	}
	
	public void disponibilizaCardapio() {
		this.disponivel = true;
	}
	
	public void indisponibilizaCardapio() {
		this.disponivel = false;
	}
	
	public String getDescricao() {
		return descricao;
	}
	
	public void setDescricao(String descricao) {
		this.descricao = descricao;
	}


	public boolean isDisponivel() {
		return disponivel;
	}

	public List<GrupoAlimentar> getGruposDeOpcoes() {
		return new ArrayList<GrupoAlimentar>(gruposItems);
	}

	@Override
	public boolean equals(Object o) {
		if (this == o) return true;
		if (o == null || getClass() != o.getClass()) return false;

		Cardapio cardapio = (Cardapio) o;

		return descricao.equals(cardapio.descricao);

	}

	@Override
	public int hashCode() {
		return descricao.hashCode();
	}

}

