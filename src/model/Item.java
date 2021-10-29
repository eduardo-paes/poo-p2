package model;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;

import model.enums.ETipoItem;
import model.interfaces.IItem;

public class Item implements IItem, Serializable {

	private static final long serialVersionUID = -6635864714971060407L;

	private static long contadorCodigo = 1;

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

	@SuppressWarnings("deprecation")
	private void writeObject(ObjectOutputStream obj) throws IOException {
		obj.defaultWriteObject();
		obj.writeObject(new Long(contadorCodigo));
	}

	private void readObject(ObjectInputStream obj) throws ClassNotFoundException, IOException {
		obj.defaultReadObject();
		contadorCodigo = (Long) obj.readObject();
	}

}
