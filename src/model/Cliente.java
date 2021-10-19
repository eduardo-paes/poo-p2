package model;

import model.interfaces.ICliente;

public class Cliente extends Pessoa implements ICliente {

	private boolean platinum;

	public Cliente(int cpf, String nome, long telefone, Endereco endereco) {
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
