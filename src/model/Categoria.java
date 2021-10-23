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

	public ArrayList<Object[]> listarItens() {
		ArrayList<Object[]> rows = new ArrayList<Object[]>();

		for (IItem item : itens) {
			Object[] row = new Object[4];
			row[0] = item.getCodigo();
			row[1] = item.getTipo().getName();
			row[2] = item.getDescricao();
			row[3] = String.format("%.2f", item.getPreco());
			rows.add(row);
		}
		return rows;
	}

}
