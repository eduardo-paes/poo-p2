package controller;

import java.util.ArrayList;

import model.Cidade;
import model.Endereco;
import model.Funcionario;
import model.interfaces.IFuncionario;
import persistence.FuncionarioPersistence;

public class FuncionarioController {
	private ArrayList<Funcionario> funcionarios;
	private FuncionarioPersistence funcionarioPersistence;
	private static FuncionarioController instance;

	private FuncionarioController() {
		funcionarioPersistence = new FuncionarioPersistence();
		funcionarios = funcionarioPersistence.extraiDadosArquivo();
	}

	public static FuncionarioController getInstance() {
		if (instance == null)
			instance = new FuncionarioController();
		return instance;
	}

	public ArrayList<Object[]> listarFuncionarios() {

		ArrayList<Object[]> rows = new ArrayList<Object[]>();

		for (Funcionario f : funcionarios) {
			Object[] row = new Object[10];
			row[0] = f.getMatricula();
			row[1] = f.getNome();
			row[2] = f.getCpf();
			row[3] = f.getTelefone();
			row[4] = f.getEmail();
			row[5] = f.getEndereco().getLogradouro();
			row[6] = f.getEndereco().getNumero();
			row[7] = f.getEndereco().getBairro();
			row[8] = f.getEndereco().getCidade().getNome();
			row[9] = f.getEndereco().getCidade().getUf();
			rows.add(row);
		}

		return rows;
	}

	public int salvaFuncionario(String nome, long cpf, long telefone, String email, String logradouro, int numero,
			String bairro, String nomeCidade, String uf) {

		Cidade cidade = CidadeController.getInstance().encontrarCidade(nomeCidade, uf);

		if (cidade != null) {
			Endereco endereco = new Endereco(logradouro, numero, bairro, cidade);
			Funcionario funcionario = new Funcionario(cpf, nome, telefone, endereco);

			if (!email.isEmpty()) {
				funcionario.setEmail(email);
			}

			funcionarios.add(funcionario);
			funcionarioPersistence.salvaDadosArquivo(funcionarios);
			return funcionario.getMatricula();
		}
		return 0;
	}

	public void editaFuncionario(int id, String nome, long telefone, String email, String logradouro, int numero,
			String bairro, String nomeCidade, String uf) {

		if (id >= 0 && id <= funcionarios.size()) {
			Funcionario funcionario = (Funcionario) funcionarios.get(id);

			if (!nome.isEmpty()) {
				funcionario.setNome(nome);
			}

			if (!email.isEmpty()) {
				funcionario.setEmail(email);
			}

			if (telefone != funcionario.getTelefone()) {
				funcionario.setTelefone(telefone);
			}

			if (!logradouro.isEmpty()) {
				funcionario.getEndereco().setLogradouro(logradouro);
			}

			if (!bairro.isEmpty()) {
				funcionario.getEndereco().setBairro(bairro);
			}

			if (numero != funcionario.getEndereco().getNumero()) {
				funcionario.getEndereco().setNumero(numero);
			}

			Cidade cidade = CidadeController.getInstance().encontrarCidade(nomeCidade, uf);
			funcionario.getEndereco().setCidade(cidade);

			funcionarioPersistence.salvaDadosArquivo(funcionarios);
		}
	}

	public void removeFuncionario(int id) {
		if (id >= 0 && id <= funcionarios.size()) {
			funcionarios.remove(id);
			funcionarioPersistence.salvaDadosArquivo(funcionarios);
		}
	}

	public IFuncionario encontraFuncionario(int matricula) {
		for (IFuncionario funcionario : funcionarios) {
			if (funcionario.getMatricula() == matricula) {
				return funcionario;
			}
		}
		return null;
	}

}
