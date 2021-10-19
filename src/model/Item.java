package model;

import model.enums.ETipoItem;
import model.interfaces.IItem;

public class Item implements IItem {

	private static int contadorCodigo = 1;
	
	private final ETipoItem tipo;
	private final long codigo;
	private final String descricao;

	private double preco;

	public Item(String descricao, ETipoItem tipo) {
		this.codigo = contadorCodigo++;
		this.descricao = descricao;
		this.tipo = tipo;
	}

	public void setPreco(double preco) {
		this.preco = preco;
	}

	@Override
	public ETipoItem getTipo() {
		return tipo;
	}

	@Override
	public long getCodigo() {
		return codigo;
	}

	@Override
	public String getDescricao() {
		return descricao;
	}

	@Override
	public double getPreco() {
		return preco;
	}

}
