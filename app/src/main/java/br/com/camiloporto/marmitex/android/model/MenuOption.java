package br.com.camiloporto.marmitex.android.model;

import java.io.Serializable;

public class MenuOption implements Serializable, Comparable<MenuOption> {

	private String name;
	private String comments;

	public MenuOption(String name) {
		this.name = name;
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

	@Override
	public boolean equals(Object o) {
		if (this == o) return true;
		if (o == null || getClass() != o.getClass()) return false;

		MenuOption that = (MenuOption) o;

		return name.equals(that.name);

	}

	@Override
	public int hashCode() {
		return name.hashCode();
	}

	@Override
	public int compareTo(MenuOption opcaoCardapio) {
		return name.compareTo(opcaoCardapio.getName());
	}

}
