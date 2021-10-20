package controller;

import java.util.ArrayList;

import model.Cliente;
import persistence.ClientePersistence;

public class ClienteController {
	private ArrayList<Cliente> clientes;
	private ClientePersistence persistence;

	public ClienteController() {
		clientes = new ArrayList<Cliente>();
		persistence = new ClientePersistence();
	}

	public ArrayList<Object[]> listarVeiculos() {

		ArrayList<Object[]> rows = new ArrayList<Object[]>();

		return rows;
	}

	public void salvaCliente() {

	}

	public void editaCliente(int id) {

	}

	public void removeCliente(int id) {

	}

}
