package br.com.camiloporto.marmitex.android.model;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class GrupoItems implements Serializable {


	private String descricao;
	
	private Map<UUID, ItemCardapio> items = new LinkedHashMap<UUID, ItemCardapio>();

	public GrupoItems() {

	}

	@Deprecated
	public void newItem(ItemCardapio item) {
		items.put(item.getUUID(), item);
	}


	public ItemCardapio newItem(String descricao) {
		ItemCardapio newItem = new ItemCardapio();
		newItem.setDescricao(descricao);
		items.put(newItem.getUUID(), newItem);
		return newItem;
	}

	public boolean removeItem(UUID id) {
		return items.remove(id) != null;
	}


	@Deprecated
	public void setId(Long id) {

	}

	public String getDescricao() {
		return descricao;
	}

	public void setDescricao(String descricao) {
		this.descricao = descricao;
	}

	public Collection<ItemCardapio> getItems() {
		return items.values();
	}

	@Override
	public boolean equals(Object o) {
		if (this == o) return true;
		if (o == null || getClass() != o.getClass()) return false;

		GrupoItems that = (GrupoItems) o;

		return descricao.equals(that.descricao);

	}

	@Override
	public int hashCode() {
		return descricao.hashCode();
	}
}
