package br.com.camiloporto.marmitex.android.model;

import java.io.Serializable;
import java.util.UUID;

import org.json.JSONException;
import org.json.JSONObject;

public class ItemCardapio implements Serializable {

	@Deprecated
	private Long id;

	private UUID uuid;
	private String descricao;
//	private GrupoItems parent;
	
	public ItemCardapio() {
		super();
//		this.parent = parent;
		uuid = UUID.randomUUID();
	}

	@Deprecated
	public void setId(Long id) {
		this.id = id;
	}
	public String getDescricao() {
		return descricao;
	}
	public void setDescricao(String descricao) {
		this.descricao = descricao;
	}

	public UUID getUUID() {
		return uuid;
	}

	@Override
	public boolean equals(Object o) {
		if (this == o) return true;
		if (o == null || getClass() != o.getClass()) return false;

		ItemCardapio that = (ItemCardapio) o;

		return uuid.equals(that.uuid);

	}

	@Override
	public int hashCode() {
		return uuid.hashCode();
	}

	@Override
	public String toString() {
		return "ItemCardapio{" +
				"uuid=" + uuid +
				", descricao='" + descricao + '\'' +
				'}';
	}

	public JSONObject json() throws JSONException {
		JSONObject json = new JSONObject();
		json.put("descricao", descricao);
		json.put("uuid", uuid.toString());
		return json;
	}
	
	
	
}
