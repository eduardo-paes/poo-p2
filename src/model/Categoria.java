package model;

import java.util.ArrayList;

import model.interfaces.IItem;

public class Categoria {

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
		itens.remove(item);
	}
	
	public StringBuilder listarItens() {
		StringBuilder sb = new StringBuilder();

		sb.append("\nItens da Categoria - " + nome + ":");
		int i = 1;
		for (IItem item : itens) {

			sb.append(
					"\n\t" + (i++) + "." + "\tTipo: " + item.getTipo().getName() + "\t| Cód.: " + item.getCodigo()
							+ "\t| Descrição: " + item.getDescricao() + "\t| Valor: R$ " + String.format("%.2f", item.getPreco()));
		}

		return sb;
	}

}
