package model;

import java.io.Serializable;
import java.util.ArrayList;

import model.interfaces.IItem;

public class Categoria implements Serializable {

	private static final long serialVersionUID = 8107502357582336083L;

	private final String nome;

	private ArrayList<IItem> itens;

	public Categoria(String nome) {
		this.nome = nome;
		itens = new ArrayList<IItem>();
	}

	public String getNome() {
		return nome;
	}

	public void addItem(IItem item) {
		itens.add(item);
	}

	public void removeItem(IItem item) {
		if (itens.remove(item)) {
			System.out.println("Item removido");
		}
	}

	public ArrayList<IItem> listarItens() {
		return itens;
	}

}
