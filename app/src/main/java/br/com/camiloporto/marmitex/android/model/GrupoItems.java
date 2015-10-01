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


	private UUID uuid;
	
	private String descricao;
	
	private Map<UUID, ItemCardapio> items = new LinkedHashMap<UUID, ItemCardapio>();

	public GrupoItems() {
		uuid = UUID.randomUUID();
	}

	@Deprecated
	public void newItem(ItemCardapio item) {
		items.put(item.getUUID(), item);
	}


	public ItemCardapio newItem(String descricao) {
		ItemCardapio newItem = new ItemCardapio(this);
		newItem.setDescricao(descricao);
		items.put(newItem.getUUID(), newItem);
		return newItem;
	}

	public boolean removeItem(UUID id) {
		return items.remove(id) != null;
	}

	public UUID getUuid() {
		return uuid;
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

		return uuid.equals(that.uuid);

	}

	@Override
	public int hashCode() {
		return uuid.hashCode();
	}

	public JSONObject json() throws JSONException {
		JSONObject json = new JSONObject();
		json.put("uuid", uuid);
		json.put("descricao", descricao);
		
		JSONArray itemsJson = new JSONArray();
		for (ItemCardapio i : items.values()) {
			itemsJson.put(i.json());
		}
		json.put("items", itemsJson);
		
		return json;
	}
	
	

}
