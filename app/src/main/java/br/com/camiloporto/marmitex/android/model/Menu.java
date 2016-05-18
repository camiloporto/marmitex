package br.com.camiloporto.marmitex.android.model;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Set;
import java.util.TreeSet;

public class Menu implements Serializable, Comparable<Menu> {

	private String name;
	
	private boolean disponivel;

	private String comments;
	
	private List<MenuCategory> categories = new LinkedList<MenuCategory>();


	public Menu(String name) {
		this.name = name;
	}

	public MenuCategory adicioneGrupoDeOpcoes(String name) {
		MenuCategory grupoItems = new MenuCategory(name);
		categories.add(grupoItems);
		return grupoItems;
	}
	
	public MenuCategory adicionaGrupo(MenuCategory grupo) {
		categories.remove(grupo);
		categories.add(grupo);
		return grupo;
	}
	
	public boolean removeGrupo(MenuCategory grupoItems) {
		return categories.remove(grupoItems);
	}
	
	public void disponibilizaCardapio() {
		this.disponivel = true;
	}
	
	public void indisponibilizaCardapio() {
		this.disponivel = false;
	}
	
	public String getName() {
		return name;
	}
	
	public void setName(String name) {
		this.name = name;
	}

	public String getComments() {
		return comments;
	}

	public void setComments(String comments) {
		this.comments = comments;
	}

	public boolean isDisponivel() {
		return disponivel;
	}

	public List<MenuCategory> getCategories() {
		return new ArrayList<MenuCategory>(categories);
	}

	public void setCategories(List<MenuCategory> categories) {
		this.categories = categories;
	}

	@Override
	public boolean equals(Object o) {
		if (this == o) return true;
		if (o == null || getClass() != o.getClass()) return false;

		Menu cardapio = (Menu) o;

		return name.equals(cardapio.name);

	}

	@Override
	public int hashCode() {
		return name.hashCode();
	}

	public MenuCategory findGrupoDeOpcoes(String name) {
		for (MenuCategory g : categories) {
			if(g.getName().equals(name)) return g;
		}
		return null;
	}

	@Override
	public int compareTo(Menu cardapio) {
		return name.compareTo(cardapio.getName());
	}
}

