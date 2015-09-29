package br.com.camiloporto.marmitex.android.model;

import java.io.Serializable;

import org.json.JSONException;
import org.json.JSONObject;

public class ItemCardapio implements Serializable {
	
	private Long id;
	private String descricao;
	private GrupoItems parent;
	
	public ItemCardapio(GrupoItems parent) {
		super();
		this.parent = parent;
	}
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public String getDescricao() {
		return descricao;
	}
	public void setDescricao(String descricao) {
		this.descricao = descricao;
	}
	
	
	
	@Override
	public String toString() {
		return "ItemCardapio [id=" + id + ", descricao=" + descricao + "]";
	}
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((id == null) ? 0 : id.hashCode());
		return result;
	}
	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		ItemCardapio other = (ItemCardapio) obj;
		if (id == null) {
			if (other.id != null)
				return false;
		} else if (!id.equals(other.id))
			return false;
		return true;
	}
	public JSONObject json() throws JSONException {
		JSONObject json = new JSONObject();
		json.put("id", id);
		json.put("descricao", descricao);
		return json;
	}
	
	
	
}
