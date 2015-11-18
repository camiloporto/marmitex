package br.com.camiloporto.marmitex.android.model;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.UUID;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class Cardapio implements Serializable {

	private String descricao;
	
	private boolean disponivel;
	
	private Set<GrupoItems> gruposItems = new HashSet<GrupoItems>();


	public Cardapio() {

	}

	public GrupoItems addGrupo(String descricao) {
		GrupoItems grupoItems = new GrupoItems();
		grupoItems.setDescricao(descricao);
		gruposItems.add(grupoItems);
		return grupoItems;
	}
	
	public GrupoItems adicionaGrupo(GrupoItems grupo) {
		gruposItems.add(grupo);
		return grupo;
	}
	
	public boolean removeGrupo(GrupoItems grupoItems) {
		return gruposItems.remove(grupoItems);
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


	public boolean isDisponivel() {
		return disponivel;
	}

	public List<GrupoItems> getGruposItems() {
		return new ArrayList<GrupoItems>(gruposItems);
	}

	@Override
	public boolean equals(Object o) {
		if (this == o) return true;
		if (o == null || getClass() != o.getClass()) return false;

		Cardapio cardapio = (Cardapio) o;

		return descricao.equals(cardapio.descricao);

	}

	@Override
	public int hashCode() {
		return descricao.hashCode();
	}

}

