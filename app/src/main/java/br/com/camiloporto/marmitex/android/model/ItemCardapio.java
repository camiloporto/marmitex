package br.com.camiloporto.marmitex.android.model;

import java.io.Serializable;
import java.util.UUID;

import org.json.JSONException;
import org.json.JSONObject;

public class ItemCardapio implements Serializable {

	private String descricao;
	
	public ItemCardapio() {
		super();
	}

	public String getDescricao() {
		return descricao;
	}
	public void setDescricao(String descricao) {
		this.descricao = descricao;
	}


	@Override
	public boolean equals(Object o) {
		if (this == o) return true;
		if (o == null || getClass() != o.getClass()) return false;

		ItemCardapio that = (ItemCardapio) o;

		return descricao.equals(that.descricao);

	}

	@Override
	public int hashCode() {
		return descricao.hashCode();
	}
}
