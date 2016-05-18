package br.com.camiloporto.marmitex.android.model;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Set;
import java.util.TreeSet;

public class Seller implements Serializable {

	private String name;
	private String phone;
	private String address;
	private String profileId;
	private String commentsForClients;

	private List<Menu> menus = new LinkedList<Menu>();

	public Seller(String name, String phone, String address) {
		super();
		this.name = name;
		this.phone = phone;
		this.address = address;
	}

	public Seller() {

	}

	public Menu createCardapio(String name) {
		Menu cardapio = new Menu(name);
		menus.add(cardapio);
		return cardapio;
	}

	public void saveCardapio(Menu cardapio) {
		menus.remove(cardapio);
		menus.add(cardapio);
	}

	public boolean deleteCardapio(Menu cardapio) {
		return menus.remove(cardapio);
	}

	public List<Menu> getMenus() {
		return new ArrayList<Menu>(menus);
	}


	public void setName(String mNome) {
		this.name = mNome;
	}


	public void setPhone(String mTelefone) {
		this.phone = mTelefone;
	}


	public void setAddress(String mEndereco) {
		this.address = mEndereco;
	}


	public String getProfileId() {
		return profileId;
	}


	public void setProfileId(String mLogin) {
		this.profileId = mLogin;
	}


	public String getName() {
		return name;
	}


	public String getPhone() {
		return phone;
	}


	public String getAddress() {
		return address;
	}

	public String getCommentsForClients() {
		return commentsForClients;
	}

	public void setCommentsForClients(String commentsForClients) {
		this.commentsForClients = commentsForClients;
	}

	public void setMenus(List<Menu> menus) {
		this.menus = menus;
	}

	public Menu findCardapioPeloId(String name) {
		for (Menu c  : menus) {
			if(c.getName().equals(name)) return c;
		}
		return null;
	}
}
