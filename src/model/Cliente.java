package model;

import java.io.Serializable;

import model.interfaces.ICliente;

public class Cliente extends Pessoa implements ICliente, Serializable {

	private static final long serialVersionUID = -8910909612926666907L;
	private boolean platinum;

	public Cliente(long cpf, String nome, long telefone, Endereco endereco) {
		super(cpf, nome, telefone, endereco);
	}

	public void setPlatinum(boolean platinum) {
		this.platinum = platinum;
	}

	@Override
	public boolean isPlatinum() {
		return platinum;
	}

}
