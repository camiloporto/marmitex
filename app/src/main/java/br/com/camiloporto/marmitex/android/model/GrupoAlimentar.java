package br.com.camiloporto.marmitex.android.model;

import java.io.Serializable;
import java.util.Collection;
import java.util.Set;
import java.util.TreeSet;
import java.util.UUID;

public class GrupoAlimentar implements Serializable, Comparable<GrupoAlimentar> {


	private String nome;
	
	private Set<OpcaoCardapio> opcoes = new TreeSet<OpcaoCardapio>();
	private String id;

	public GrupoAlimentar() {
		id = UUID.randomUUID().toString();
	}

	public OpcaoCardapio adicioneOpcao(String descricao) {
		OpcaoCardapio newItem = new OpcaoCardapio();
		newItem.setNome(descricao);
		opcoes.add(newItem);
		return newItem;
	}

	public boolean removaOpcao(OpcaoCardapio item) {
		return opcoes.remove(item);
	}


	public String getNome() {
		return nome;
	}

	public void setNome(String nome) {
		this.nome = nome;
	}

	public Collection<OpcaoCardapio> getOpcoes() {
		return opcoes;
	}

	@Override
	public boolean equals(Object o) {
		if (this == o) return true;
		if (o == null || getClass() != o.getClass()) return false;

		GrupoAlimentar that = (GrupoAlimentar) o;

		return nome.equals(that.nome);

	}

	@Override
	public int hashCode() {
		return nome.hashCode();
	}

	@Override
	public int compareTo(GrupoAlimentar grupoAlimentar) {
		return nome.compareTo(grupoAlimentar.getNome());
	}

	public void limpa() {
		opcoes.clear();
	}

	public String getId() {
		return id;
	}

	public OpcaoCardapio findOpcaoCardapioPeloId(String id) {
		for (OpcaoCardapio o : opcoes) {
			if(o.getId().equals(id)) return o;
		}
		return null;
	}
}
