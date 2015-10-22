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
	private static final String JSON_LOGIN = "login";
	private static final String JSON_SENHA = "senha";
	private static final String JSON_ID = "id";
	private static final String JSON_FUNCIONAMENTO = "funcionamento";
	
	private String mNome;
	private String mTelefone;
	private String mEndereco;
	private String mLogin;
	private String mSenha;
	private String mId;
	private String mFuncionamento;

	private Map<UUID, Cardapio> cardapios = new LinkedHashMap<UUID, Cardapio>();
	
	public Marmitaria(String mNome, String mTelefone, String mEndereco) {
		super();
		this.mNome = mNome;
		this.mTelefone = mTelefone;
		this.mEndereco = mEndereco;
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


	public JSONObject json() throws JSONException {
		JSONObject json = new JSONObject();
		json.put(JSON_NOME, mNome);
		json.put(JSON_TELEFONE, mTelefone);
		json.put(JSON_ENDERECO, mEndereco);
		json.put(JSON_FUNCIONAMENTO, mFuncionamento);
		json.put(JSON_ID, mId);
		json.put(JSON_LOGIN, mLogin);
		json.put(JSON_SENHA, mSenha);
		//TODO enviar localizacao GPS
		return json;
	}


	public void setNome(String mNome) {
		this.mNome = mNome;
	}


	public void setTelefone(String mTelefone) {
		this.mTelefone = mTelefone;
	}


	public void setEndereco(String mEndereco) {
		this.mEndereco = mEndereco;
	}


	public String getLogin() {
		return mLogin;
	}


	public void setLogin(String mLogin) {
		this.mLogin = mLogin;
	}


	public String getSenha() {
		return mSenha;
	}


	public void setSenha(String mSenha) {
		this.mSenha = mSenha;
	}


	public String getId() {
		return mId;
	}


	public void setId(String mId) {
		this.mId = mId;
	}


	public String getFuncionamento() {
		return mFuncionamento;
	}


	public void setFuncionamento(String mFuncionamento) {
		this.mFuncionamento = mFuncionamento;
	}


	public String getNome() {
		return mNome;
	}


	public String getTelefone() {
		return mTelefone;
	}


	public String getEndereco() {
		return mEndereco;
	}


}
