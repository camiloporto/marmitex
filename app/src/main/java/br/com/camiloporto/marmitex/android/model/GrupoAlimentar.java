package br.com.camiloporto.marmitex.android.model;

import java.io.Serializable;
import java.util.Collection;
import java.util.HashSet;
import java.util.Set;

public class GrupoAlimentar implements Serializable {


	private String descricao;
	
	private Set<ItemCardapio> opcoes = new HashSet<ItemCardapio>();

	public GrupoAlimentar() {

	}

	@Deprecated
	public void adicioneOpcao(ItemCardapio item) {

		opcoes.add(item);
	}

	public ItemCardapio adicioneOpcao(String descricao) {
		ItemCardapio newItem = new ItemCardapio();
		newItem.setDescricao(descricao);
		opcoes.add(newItem);
		return newItem;
	}

	public boolean removeItem(ItemCardapio item) {
		return opcoes.remove(item);
	}


	public String getDescricao() {
		return descricao;
	}

	public void setDescricao(String descricao) {
		this.descricao = descricao;
	}

	public Collection<ItemCardapio> getOpcoes() {
		return opcoes;
	}

	@Override
	public boolean equals(Object o) {
		if (this == o) return true;
		if (o == null || getClass() != o.getClass()) return false;

		GrupoAlimentar that = (GrupoAlimentar) o;

		return descricao.equals(that.descricao);

	}

	@Override
	public int hashCode() {
		return descricao.hashCode();
	}

}
