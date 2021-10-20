package controller;

import java.io.EOFException;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InvalidClassException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.ArrayList;

import model.Modelo;
import model.Veiculo;

public class VeiculoController {
	
	private ArrayList<Veiculo> veiculos;
	private String arquivoDados = "veiculos.bin";
	
	public VeiculoController() {
		veiculos = new ArrayList<Veiculo>();
		extraiDadosArquivo();
	}
	
	@SuppressWarnings("unchecked")
	private void extraiDadosArquivo() {
		ObjectInputStream input = null;

        try {
            input = new ObjectInputStream(new FileInputStream(arquivoDados));
            while (true) {
            	veiculos = (ArrayList<Veiculo>) input.readObject();
            }
        } catch (EOFException e) {
            System.err.println("Arquivo importado.");
        } catch (ClassNotFoundException e) {
            System.err.println("Classe incompatÄ±Ì�vel para desserializacao.");
        } catch (InvalidClassException e) {
            System.err.println("Classe incompatÄ±Ì�vel para desserializacao.");
        } catch (IOException e) {
            System.err.println("Erro de leitura do arquivo: " + e.getMessage().toString());
        } finally {
            try {
                if (input != null)
                    input.close();
            } catch (IOException e) {
                System.err.println("Erro ao fechar arquivo:");
            }
        }
	}
	
	private void salvaDadosArquivo() {
		ObjectOutputStream output = null;
        try {
            output = new ObjectOutputStream(new FileOutputStream(arquivoDados));
            output.writeObject(veiculos);
        } catch (IOException e) {
            System.err.println("Erro ao escrever no arquivo: " + e.getMessage().toString());
        } finally {
            try {
                if (output != null)
                    output.close();
            } catch (IOException e) {
                System.err.println("Erro ao fechar o arquivo:");
            }
        }
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
		}
		else {
			veiculo = new Veiculo(modelo, chassi, ano, cor, placa);
		}
		
		if (cpfCliente.length() > 0) {
			veiculo.setProprietario(null);
		}
		veiculos.add(veiculo);
		salvaDadosArquivo();
	}
	
	public void editaVeiculo(int id, String cor, String placa, String cpfCliente) {
		
		if (id >= 0 && id <= veiculos.size()) {
			Veiculo veiculo = veiculos.get(id);
			
			if (placa.length() > 0 ) {
				veiculo.setPlaca(placa);
			}
			if (cor.length() > 0) {
				veiculo.setCor(cor);
			}
			if (cpfCliente.length() > 0) {
				veiculo.setProprietario(null);
			}
			salvaDadosArquivo();
		}
	}
	
	public void removerVeiculo(int id) {
		if (id >= 0 && id <= veiculos.size()) {
			veiculos.remove(id);
			salvaDadosArquivo();
		}
	}
}
