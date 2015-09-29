package br.com.camiloporto.marmitex.android.model;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class Cardapio implements Serializable {
	
	private Long id;
	
	private String descricao;
	
	private boolean disponivel;
	
	private List<GrupoItems> gruposItems = new ArrayList<GrupoItems>();
	
	public void adicionaGrupo(GrupoItems grupo) {
		gruposItems.add(grupo);
	}
	
	public boolean removeGrupo(GrupoItems grupo) {
		return gruposItems.remove(grupo);
	}
	
	public void disponibilizaCardapio() {
		this.disponivel = true;
	}
	
	public void indisponibilizaCardapio() {
		this.disponivel = false;
	}
	
	public String getDescricao() {
		return descricao;
	}
	
	public void setDescricao(String descricao) {
		this.descricao = descricao;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public boolean isDisponivel() {
		return disponivel;
	}

	public List<GrupoItems> getGruposItems() {
		return gruposItems;
	}

	public JSONObject json() throws JSONException {
		
		JSONObject json = new JSONObject();
		json.put("id", id);
		json.put("disponivel", disponivel);
		
		JSONArray gruposItemsJson = new JSONArray();
		for (GrupoItems g : gruposItems) {
			gruposItemsJson.put(g.json());
		}
		json.put("gruposItems", gruposItemsJson);
		
		return json;
	}
	
	@Override
	public String toString() {
		return descricao;
	}

}
