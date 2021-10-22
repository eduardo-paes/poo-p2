package controller;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.Map;

import model.Cidade;
import model.Cliente;
import model.Endereco;
import persistence.ClientePersistence;

public class ClienteController {
	private ArrayList<Cliente> clientes;
	private ClientePersistence clientePersistence;

	public ClienteController() {
		clientes = new ArrayList<Cliente>();
		clientePersistence = new ClientePersistence();
		clientes = clientePersistence.extraiDadosArquivo();
	}

	public ArrayList<Object[]> listarClientes() {

		ArrayList<Object[]> rows = new ArrayList<Object[]>();

		for (Cliente v : clientes) {
			Object[] row = new Object[10];
			row[0] = v.getNome();
			row[1] = v.isPlatinum() ? "Sim" : "Não";
			row[2] = v.getCpf();
			row[3] = v.getTelefone();
			row[4] = v.getEmail();
			row[5] = v.getEndereco().getLogradouro();
			row[6] = v.getEndereco().getNumero();
			row[7] = v.getEndereco().getBairro();
			row[8] = v.getEndereco().getCidade().getNome();
			row[9] = v.getEndereco().getCidade().getUf();
			rows.add(row);
		}

		return rows;
	}

	public Map<Long, String> listarProprietarios() {
		Map<Long, String> proprietarios = new LinkedHashMap<Long, String>();

		for (Cliente c : clientes) {
			proprietarios.put(c.getCpf(), c.getNome());
		}

		return proprietarios;
	}

	public void salvaCliente(String nome, boolean isPlatinum, long cpf, long telefone, String email, String logradouro,
			int numero, String bairro, String nomeCidade, String uf) {

		Cidade cidade = new CidadeController().encontrarCidade(nomeCidade, uf);

		if (cidade != null) {
			Endereco endereco = new Endereco(logradouro, numero, bairro, cidade);
			Cliente cliente = new Cliente(cpf, nome, telefone, endereco);
			cliente.setPlatinum(isPlatinum);

			if (!email.isEmpty()) {
				cliente.setEmail(email);
			}

			clientes.add(cliente);
			clientePersistence.salvaDadosArquivo(clientes);
		}
	}

	public void editaCliente(int id, String nome, boolean isPlatinum, long telefone, String email, String logradouro,
			int numero, String bairro, String nomeCidade, String uf) {

		if (id >= 0 && id <= clientes.size()) {
			Cliente cliente = clientes.get(id);

			if (!nome.isEmpty()) {
				cliente.setNome(nome);
			}

			if (!email.isEmpty()) {
				cliente.setEmail(email);
			}

			if (telefone != cliente.getTelefone()) {
				cliente.setTelefone(telefone);
			}

			cliente.setPlatinum(isPlatinum);

			if (!logradouro.isEmpty()) {
				cliente.getEndereco().setLogradouro(logradouro);
			}

			if (!bairro.isEmpty()) {
				cliente.getEndereco().setLogradouro(bairro);
			}

			if (numero != cliente.getEndereco().getNumero()) {
				cliente.getEndereco().setNumero(numero);
			}

			Cidade cidade = new CidadeController().encontrarCidade(nomeCidade, uf);
			cliente.getEndereco().setCidade(cidade);

			clientePersistence.salvaDadosArquivo(clientes);
		}
	}

	public void removeCliente(int id) {
		if (id >= 0 && id <= clientes.size()) {
			clientes.remove(id);
			clientePersistence.salvaDadosArquivo(clientes);
		}
	}

}
