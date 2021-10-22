package model;

import model.interfaces.IFuncionario;

public class Funcionario extends Pessoa implements IFuncionario {

	private static final long serialVersionUID = -6136271179513794180L;
	private final int matricula;
	private static int contadorMatricula = 1;

	public Funcionario(long cpf, String nome, long telefone, Endereco endereco) {
		super(cpf, nome, telefone, endereco);
		this.matricula = contadorMatricula++;
	}

	@Override
	public int getMatricula() {
		return matricula;
	}

}
