package model;

import model.enums.ETipoItem;
import model.interfaces.IItem;

public class ItemOS {

	private double quantidade;
	private double preco;
	private IItem item;
	
	public ItemOS(IItem item, double quantidade, double preco) {
		this.item = item;
		this.quantidade = quantidade;
		this.preco = preco;
	}
	
	public ItemOS(IItem item, double quantidade) {
		this(item, quantidade, item.getPreco());
	}

	public double getQuantidade() {
		return quantidade;
	}
	
	public void setQuantidade(double quantidade) {
		this.quantidade = quantidade;
	}
	
	public double getPreco() {
		return preco;
	}
	
	public void setPreco(double preco) {
		this.preco = preco;
	}
	
	public double getTotalItem() {
		return quantidade * preco;
	}
	
	public String listarItem() {
		//TODO: Implementar Listar Item
		return item.toString();
	}
	
	public ETipoItem getTipo() {
		return item.getTipo();
	}

}
