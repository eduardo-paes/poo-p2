package model;

import java.io.Serializable;

import model.enums.ETipoItem;
import model.interfaces.IItem;

public class ItemOS implements Serializable {

	private static final long serialVersionUID = -7332009090446944410L;
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

	public ETipoItem getTipo() {
		return item.getTipo();
	}
	
	//--Metodos Suplementares
	public long getCodigo() {
		return item.getCodigo();
	}
	
	public String getDescricao() {
		return item.getDescricao();
	}

}
