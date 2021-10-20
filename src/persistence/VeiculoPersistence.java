package persistence;

import java.io.EOFException;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InvalidClassException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.ArrayList;

import model.Veiculo;

public class VeiculoPersistence {
	private String arquivoDados = "veiculos.bin";
	private ObjectInputStream input;
	private ObjectOutputStream output;
	
	@SuppressWarnings("unchecked")
	public ArrayList<Veiculo> extraiDadosArquivo() {		
		ArrayList<Veiculo> veiculos = new ArrayList<Veiculo>();
		
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
		
		return veiculos;
	}

	public void salvaDadosArquivo(ArrayList<Veiculo> veiculos) {		
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

}
