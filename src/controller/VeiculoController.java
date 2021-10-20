package controller;

import java.util.ArrayList;

import model.Modelo;
import model.Veiculo;
import persistence.VeiculoPersistence;

public class VeiculoController {

	private ArrayList<Veiculo> veiculos;
	private VeiculoPersistence persistence;

	public VeiculoController() {
		veiculos = new ArrayList<Veiculo>();
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

	public void salvaVeiculo(String chassi, int ano, String cor, String placa, String nomeModelo, String cpfCliente) {
		Modelo modelo = new Modelo(nomeModelo);
		Veiculo veiculo;
		
		if (placa.length() == 0) {
			veiculo = new Veiculo(modelo, chassi, ano, cor);
		} else {
			veiculo = new Veiculo(modelo, chassi, ano, cor, placa);
		}

		if (cpfCliente.length() > 0) {
			veiculo.setProprietario(null);
		}
		veiculos.add(veiculo);
		persistence.salvaDadosArquivo(veiculos);
	}

	public void editaVeiculo(int id, String cor, String placa, String cpfCliente) {

		if (id >= 0 && id <= veiculos.size()) {
			Veiculo veiculo = veiculos.get(id);

			if (placa.length() > 0) {
				veiculo.setPlaca(placa);
			}
			if (cor.length() > 0) {
				veiculo.setCor(cor);
			}
			if (cpfCliente.length() > 0) {
				veiculo.setProprietario(null);
			}
			persistence.salvaDadosArquivo(veiculos);
		}
	}

	public void removerVeiculo(int id) {
		if (id >= 0 && id <= veiculos.size()) {
			veiculos.remove(id);
			persistence.salvaDadosArquivo(veiculos);
		}
	}

}
