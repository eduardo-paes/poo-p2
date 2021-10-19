package model;

import model.interfaces.IFuncionario;

public class Funcionario extends Pessoa implements IFuncionario {

	private final int matricula;
	private static int contadorMatricula = 1;

	public Funcionario(int cpf, String nome, long telefone, Endereco endereco) {
		super(cpf, nome, telefone, endereco);
		this.matricula = contadorMatricula++;
	}

	@Override
	public int getMatricula() {
		return matricula;
	}

}
