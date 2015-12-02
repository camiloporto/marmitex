package br.com.camiloporto.marmitex.android.model;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.TreeSet;
import java.util.UUID;

public class Cardapio implements Serializable, Comparable<Cardapio> {

	private String id;

	private String nome;
	
	private boolean disponivel;
	
	private Set<GrupoAlimentar> gruposDeOpcoes = new TreeSet<GrupoAlimentar>();


	public Cardapio() {
		id = UUID.randomUUID().toString();
	}

	public GrupoAlimentar adicioneGrupoDeOpcoes(String descricao) {
		GrupoAlimentar grupoItems = new GrupoAlimentar();
		grupoItems.setNome(descricao);
		gruposDeOpcoes.add(grupoItems);
		return grupoItems;
	}
	
	public GrupoAlimentar adicionaGrupo(GrupoAlimentar grupo) {
		gruposDeOpcoes.remove(grupo);
		gruposDeOpcoes.add(grupo);
		return grupo;
	}
	
	public boolean removeGrupo(GrupoAlimentar grupoItems) {
		return gruposDeOpcoes.remove(grupoItems);
	}
	
	public void disponibilizaCardapio() {
		this.disponivel = true;
	}
	
	public void indisponibilizaCardapio() {
		this.disponivel = false;
	}
	
	public String getNome() {
		return nome;
	}
	
	public void setNome(String nome) {
		this.nome = nome;
	}


	public boolean isDisponivel() {
		return disponivel;
	}

	public List<GrupoAlimentar> getGruposDeOpcoes() {
		return new ArrayList<GrupoAlimentar>(gruposDeOpcoes);
	}

	public String getId() {
		return id;
	}

	@Override
	public boolean equals(Object o) {
		if (this == o) return true;
		if (o == null || getClass() != o.getClass()) return false;

		Cardapio cardapio = (Cardapio) o;

		return nome.equals(cardapio.nome);

	}

	@Override
	public int hashCode() {
		return nome.hashCode();
	}

	public GrupoAlimentar findGrupoDeOpcoes(String id) {
		for (GrupoAlimentar g : gruposDeOpcoes) {
			if(g.getId().equals(id)) return g;
		}
		return null;
	}

	@Override
	public int compareTo(Cardapio cardapio) {
		return nome.compareTo(cardapio.getNome());
	}
}

