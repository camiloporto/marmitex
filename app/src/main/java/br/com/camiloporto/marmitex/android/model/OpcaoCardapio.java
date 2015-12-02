package br.com.camiloporto.marmitex.android.model;

import java.io.Serializable;
import java.util.UUID;

public class OpcaoCardapio implements Serializable, Comparable<OpcaoCardapio> {

	private String nome;
	private String id;

	public OpcaoCardapio() {
		super();
		id = UUID.randomUUID().toString();
	}

	public String getNome() {
		return nome;
	}
	public void setNome(String nome) {
		this.nome = nome;
	}


	@Override
	public boolean equals(Object o) {
		if (this == o) return true;
		if (o == null || getClass() != o.getClass()) return false;

		OpcaoCardapio that = (OpcaoCardapio) o;

		return nome.equals(that.nome);

	}

	@Override
	public int hashCode() {
		return nome.hashCode();
	}

	@Override
	public int compareTo(OpcaoCardapio opcaoCardapio) {
		return nome.compareTo(opcaoCardapio.getNome());
	}

	public String getId() {
		return id;
	}
}
