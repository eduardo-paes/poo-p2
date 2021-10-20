package controller;

import java.util.ArrayList;

import model.Cidade;
import model.Cliente;
import model.Endereco;
import persistence.CidadePersistence;
import persistence.ClientePersistence;

public class ClienteController {
	private ArrayList<Cliente> clientes;
	private ArrayList<Cidade> cidades;
	private ClientePersistence clientePersistence;
	private CidadePersistence cidadePersistence;

	public ClienteController() {
		clientes = new ArrayList<Cliente>();
		cidades = new ArrayList<Cidade>();

		clientePersistence = new ClientePersistence();
		cidadePersistence = new CidadePersistence();

		clientes = clientePersistence.extraiDadosArquivo();
		cidades = cidadePersistence.extraiDadosArquivo();
	}

	public ArrayList<Object[]> listarVeiculos() {

		ArrayList<Object[]> rows = new ArrayList<Object[]>();

		return rows;
	}

	public void salvaCliente(String nome, boolean isPlatinum, long cpf, long telefone, String email, String logradouro,
			int numero, String bairro, String nomeCidade, String uf) {

		Cidade cidade = null;
		for (Cidade c : cidades) {
			if (c.getNome() == nomeCidade && c.getUf() == uf) {
				cidade = c;
				break;
			}
		}

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
			int numero, String bairro) {

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

			clientePersistence.salvaDadosArquivo(clientes);
		}
	}

	public void removeCliente(int id) {

	}

	public ArrayList<String> listaCidades() {
		ArrayList<String> cidadeUfs = new ArrayList<String>();

		for (Cidade c : cidades) {
			cidadeUfs.add(String.format("%s - %s", c.getNome(), c.getUf()));
		}

		return cidadeUfs;
	}

}
