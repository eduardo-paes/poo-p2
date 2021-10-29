package controller;

import java.util.ArrayList;

import model.Modelo;
import model.Veiculo;
import model.interfaces.IVeiculo;
import persistence.VeiculoPersistence;

public class VeiculoController {

	private ArrayList<Veiculo> veiculos;
	private VeiculoPersistence persistence;
	private static VeiculoController instance;

	public static VeiculoController getInstance() {
		if (instance == null)
			instance = new VeiculoController();
		return instance;
	}

	private VeiculoController() {
		persistence = new VeiculoPersistence();
		veiculos = persistence.extraiDadosArquivo();
	}

	public ArrayList<Object[]> listarVeiculos() {

		ArrayList<Object[]> rows = new ArrayList<Object[]>();

		for (Veiculo v : veiculos) {
			Object[] row = new Object[6];
			row[0] = v.getModelo().getNome();
			row[1] = v.getChassi();
			row[2] = v.getCor();
			row[3] = v.getPlaca();
			row[4] = v.getAno();

			String proprietario = "";
			if (v.getProprietario() != null) {
				proprietario = v.getProprietario().getNome();
			}
			row[5] = proprietario;
			rows.add(row);
		}

		return rows;
	}

	public void salvaVeiculo(String chassi, int ano, String cor, String placa, String nomeModelo, long cpfCliente) {
		Modelo modelo = new Modelo(nomeModelo);
		Veiculo veiculo;

		if (placa.length() == 0) {
			veiculo = new Veiculo(modelo, chassi, ano, cor);
		} else {
			veiculo = new Veiculo(modelo, chassi, ano, cor, placa);
		}

		veiculo.setProprietario(ClienteController.getInstance().encontraCliente(cpfCliente));
		veiculos.add(veiculo);
		persistence.salvaDadosArquivo(veiculos);
	}

	public void editaVeiculo(int id, String cor, String placa, long cpfCliente) {

		if (id >= 0 && id <= veiculos.size()) {
			Veiculo veiculo = veiculos.get(id);

			if (placa.length() > 0) {
				veiculo.setPlaca(placa);
			}
			if (cor.length() > 0) {
				veiculo.setCor(cor);
			}

			veiculo.setProprietario(ClienteController.getInstance().encontraCliente(cpfCliente));
			persistence.salvaDadosArquivo(veiculos);
		}
	}

	public void removerVeiculo(int id) {
		if (id >= 0 && id <= veiculos.size()) {
			veiculos.remove(id);
			persistence.salvaDadosArquivo(veiculos);
		}
	}

	public IVeiculo encontraVeiculo(String chassi) {
		for (IVeiculo veiculo : veiculos) {
			if (veiculo.getChassi().equals(chassi)) {
				return veiculo;
			}
		}
		return null;
	}

	public String encontraProprietario(String chassi) {
		IVeiculo veiculo = encontraVeiculo(chassi);
		if (veiculo != null && veiculo.getProprietario() != null) {
			return veiculo.getProprietario().getNome();
		}
		return null;
	}

}
