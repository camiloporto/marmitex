package br.com.camiloporto.marmitex.android.model;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.HashSet;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.UUID;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class GrupoItems implements Serializable {


	private String descricao;
	
	private Set<ItemCardapio> items = new HashSet<ItemCardapio>();

	public GrupoItems() {

	}

	@Deprecated
	public void newItem(ItemCardapio item) {
		items.add(item);
	}


	public ItemCardapio newItem(String descricao) {
		ItemCardapio newItem = new ItemCardapio();
		newItem.setDescricao(descricao);
		items.add(newItem);
		return newItem;
	}

	public boolean removeItem(ItemCardapio item) {
		return items.remove(item);
	}


	public String getDescricao() {
		return descricao;
	}

	public void setDescricao(String descricao) {
		this.descricao = descricao;
	}

	public Collection<ItemCardapio> getItems() {
		return items;
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
