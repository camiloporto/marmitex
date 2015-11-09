package br.com.camiloporto.marmitex.android.model;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

public class Marmitaria implements Serializable {

	private static final String JSON_ENDERECO = "endereco";
	private static final String JSON_TELEFONE = "telefone";
	private static final String JSON_NOME = "nome";
	private static final String JSON_UUID = "uuid";
	private static final String JSON_LOGIN = "login";
	private static final String JSON_SENHA = "senha";
	private static final String JSON_ID = "id";
	private static final String JSON_FUNCIONAMENTO = "funcionamento";
	
	private String nome;
	private String telefone;
	private String endereco;
	private String login;
	private String senha;
	private String id;
	private final UUID uuid;
	private String funcionamento;

	private Map<UUID, Cardapio> cardapios = new LinkedHashMap<UUID, Cardapio>();
	
	public Marmitaria(String mNome, String mTelefone, String endereco) {
		super();
		this.uuid = UUID.randomUUID();
		this.nome = mNome;
		this.telefone = mTelefone;
		this.endereco = endereco;
	}

	public Cardapio createCardapio(String descricao) {
		Cardapio cardapio = new Cardapio();
		cardapio.setDescricao(descricao);
		return cardapios.put(cardapio.getUuid(), cardapio);
	}

	public Cardapio saveCardapio(Cardapio cardapio) {
		return cardapios.put(cardapio.getUuid(), cardapio);
	}

	public boolean deleteCardapio(Cardapio cardapio) {
		return cardapios.remove(cardapio.getUuid()) != null;
	}

	public List<Cardapio> getCardapios() {
		return new ArrayList<Cardapio>(cardapios.values());
	}


	public void setNome(String mNome) {
		this.nome = mNome;
	}


	public void setTelefone(String mTelefone) {
		this.telefone = mTelefone;
	}


	public void setEndereco(String mEndereco) {
		this.endereco = mEndereco;
	}


	public String getLogin() {
		return login;
	}


	public void setLogin(String mLogin) {
		this.login = mLogin;
	}


	public String getSenha() {
		return senha;
	}


	public void setSenha(String mSenha) {
		this.senha = mSenha;
	}


	public String getId() {
		return id;
	}


	public void setId(String mId) {
		this.id = mId;
	}


	public String getFuncionamento() {
		return funcionamento;
	}


	public void setFuncionamento(String mFuncionamento) {
		this.funcionamento = mFuncionamento;
	}


	public String getNome() {
		return nome;
	}


	public String getTelefone() {
		return telefone;
	}


	public String getEndereco() {
		return endereco;
	}


}
