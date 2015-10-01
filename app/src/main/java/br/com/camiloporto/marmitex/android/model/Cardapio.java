package br.com.camiloporto.marmitex.android.model;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class Cardapio implements Serializable {

	private final UUID uuid;

	private String descricao;
	
	private boolean disponivel;
	
	private Map<UUID, GrupoItems> gruposItems = new LinkedHashMap<UUID, GrupoItems>();

	public Cardapio() {
		this.uuid = UUID.randomUUID();
	}

	public GrupoItems createGrupo(String descricao) {
		GrupoItems grupoItems = new GrupoItems();
		grupoItems.setDescricao(descricao);
		return grupoItems;
	}
	
	public GrupoItems adicionaGrupo(GrupoItems grupo) {
		return gruposItems.put(grupo.getUuid(), grupo);
	}
	
	public boolean removeGrupo(UUID id) {

		return gruposItems.remove(id) != null;
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


	@Deprecated
	public void setId(Long id) {

	}

	public boolean isDisponivel() {
		return disponivel;
	}

	public List<GrupoItems> getGruposItems() {

		return new ArrayList<GrupoItems>(gruposItems.values());
	}

	public JSONObject json() throws JSONException {
		
		JSONObject json = new JSONObject();
		json.put("uuid", uuid);
		json.put("disponivel", disponivel);
		
		JSONArray gruposItemsJson = new JSONArray();
		for (GrupoItems g : gruposItems.values()) {
			gruposItemsJson.put(g.json());
		}
		json.put("gruposItems", gruposItemsJson);
		
		return json;
	}

	@Override
	public boolean equals(Object o) {
		if (this == o) return true;
		if (o == null || getClass() != o.getClass()) return false;

		Cardapio cardapio = (Cardapio) o;

		return uuid.equals(cardapio.uuid);

	}

	@Override
	public int hashCode() {
		return uuid.hashCode();
	}

	@Override
	public String toString() {
		return "Cardapio{" +
				"uuid=" + uuid +
				", descricao='" + descricao + '\'' +
				", disponivel=" + disponivel +
				'}';
	}
}

