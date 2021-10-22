package controller;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.Map;

import model.Cidade;
import model.Endereco;
import model.Funcionario;
import model.interfaces.IFuncionario;
import persistence.FuncionarioPersistence;

public class FuncionarioController {
	private ArrayList<IFuncionario> funcionarios;
	private FuncionarioPersistence funcionarioPersistence;

	public FuncionarioController() {
		funcionarioPersistence = new FuncionarioPersistence();
		funcionarios = funcionarioPersistence.extraiDadosArquivo();
	}

	public ArrayList<Object[]> listarFuncionarios() {

		ArrayList<Object[]> rows = new ArrayList<Object[]>();

		for (IFuncionario f : funcionarios) {
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

	public Map<Long, String> listarProprietarios() {
		Map<Long, String> proprietarios = new LinkedHashMap<Long, String>();

		for (IFuncionario f : funcionarios) {
			proprietarios.put(f.getCpf(), f.getNome());
		}

		return proprietarios;
	}

	public int salvaFuncionario(String nome, long cpf, long telefone, String email, String logradouro,
			int numero, String bairro, String nomeCidade, String uf) {

		Cidade cidade = new CidadeController().encontrarCidade(nomeCidade, uf);

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

	public void editaFuncionario(int id, String nome, long telefone, String email, String logradouro,
			int numero, String bairro, String nomeCidade, String uf) {

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
				funcionario.getEndereco().setLogradouro(bairro);
			}

			if (numero != funcionario.getEndereco().getNumero()) {
				funcionario.getEndereco().setNumero(numero);
			}

			Cidade cidade = new CidadeController().encontrarCidade(nomeCidade, uf);
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

}
