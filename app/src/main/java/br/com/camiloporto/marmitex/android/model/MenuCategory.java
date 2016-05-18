package br.com.camiloporto.marmitex.android.model;

import java.io.Serializable;
import java.util.Collection;
import java.util.LinkedList;
import java.util.List;
import java.util.Set;
import java.util.TreeSet;

public class MenuCategory implements Serializable, Comparable<MenuCategory> {


	private String name;
	private String comments;
	
	private List<MenuOption> options = new LinkedList<MenuOption>();

	public MenuCategory(String name) {

		this.name = name;
	}

	public MenuOption adicioneOpcao(String name) {
		MenuOption newItem = new MenuOption(name);
		options.add(newItem);
		return newItem;
	}

	public boolean removaOpcao(MenuOption item) {
		return options.remove(item);
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

	public Collection<MenuOption> getOptions() {
		return options;
	}

	@Override
	public boolean equals(Object o) {
		if (this == o) return true;
		if (o == null || getClass() != o.getClass()) return false;

		MenuCategory that = (MenuCategory) o;

		return name.equals(that.name);

	}

	public void setOptions(List<MenuOption> options) {
		this.options = options;
	}

	@Override
	public int hashCode() {
		return name.hashCode();
	}

	@Override
	public int compareTo(MenuCategory grupoAlimentar) {
		return name.compareTo(grupoAlimentar.getName());
	}

	public void limpa() {
		options.clear();
	}

	public MenuOption findOpcaoCardapioPeloId(String name) {
		for (MenuOption o : options) {
			if(o.getName().equals(name)) return o;
		}
		return null;
	}
}
