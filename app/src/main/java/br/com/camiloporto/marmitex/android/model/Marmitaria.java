package br.com.camiloporto.marmitex.android.model;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.TreeSet;
import java.util.UUID;

public class Marmitaria implements Serializable {

	private String nome;
	private String telefone;
	private String endereco;
	private String login;
	private String senha;

	@SerializedName("_id")
	private String id;

	@SerializedName("_rev")
	private String revision;

	private String funcionamento;

	private Set<Cardapio> cardapios = new TreeSet<Cardapio>();
	
	public Marmitaria(String mNome, String mTelefone, String endereco) {
		super();

		id = UUID.randomUUID().toString();
		this.nome = mNome;
		this.telefone = mTelefone;
		this.endereco = endereco;
	}

	public Cardapio createCardapio(String descricao) {
		Cardapio cardapio = new Cardapio();
		cardapio.setNome(descricao);
		cardapios.add(cardapio);
		return cardapio;
	}

	public void saveCardapio(Cardapio cardapio) {
		cardapios.remove(cardapio);
		cardapios.add(cardapio);
	}

	public boolean deleteCardapio(Cardapio cardapio) {
		return cardapios.remove(cardapio);
	}

	public List<Cardapio> getCardapios() {
		return new ArrayList<Cardapio>(cardapios);
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

	public String getRevision() {
		return revision;
	}

	public void setRevision(String revision) {
		this.revision = revision;
	}

	public Cardapio findCardapioPeloId(String id) {
		for (Cardapio c  : cardapios) {
			if(c.getId().equals(id)) return c;
		}
		return null;
	}
}
