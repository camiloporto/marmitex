package br.com.camiloporto.marmitex.android.model;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class GrupoItems implements Serializable {
	
	private Long id;
	
	private String descricao;
	
	private List<ItemCardapio> items = new ArrayList<ItemCardapio>();
	
	public void adicionaItem(ItemCardapio item) {
		items.add(item);
	}
	
	public boolean removeItem(ItemCardapio item) {
		return items.remove(item);
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

	public List<ItemCardapio> getItems() {
		return items;
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
		GrupoItems other = (GrupoItems) obj;
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
		
		JSONArray itemsJson = new JSONArray();
		for (ItemCardapio i : items) {
			itemsJson.put(i.json());
		}
		json.put("items", itemsJson);
		
		return json;
	}
	
	

}
