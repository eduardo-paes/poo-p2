package persistence;

import java.io.EOFException;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InvalidClassException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.ArrayList;

import model.Cidade;

public class CidadePersistence {
	private String arquivoDados = "cidades.bin";
	private ObjectInputStream input;
	private ObjectOutputStream output;
	
	@SuppressWarnings("unchecked")
	public ArrayList<Cidade> extraiDadosArquivo() {		
		ArrayList<Cidade> cidades = new ArrayList<Cidade>();
		
		try {
			input = new ObjectInputStream(new FileInputStream(arquivoDados));
			while (true) {
				cidades = (ArrayList<Cidade>) input.readObject();
			}
		} catch (EOFException e) {
			System.err.println("Arquivo importado.");
		} catch (ClassNotFoundException e) {
			System.err.println("Classe incompativel para desserializacao.");
		} catch (InvalidClassException e) {
			System.err.println("Classe incompativel para desserializacao.");
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
		
		if (cidades.size() == 0) {
			cidades.add(new Cidade("Rio de Janeiro", "RJ"));
			cidades.add(new Cidade("São Paulo", "SP"));
			cidades.add(new Cidade("Brasília", "DF"));
			cidades.add(new Cidade("Salvador", "BA"));
			cidades.add(new Cidade("Curitiba", "PR"));
			cidades.add(new Cidade("Porto Alegre", "RS"));
			cidades.add(new Cidade("Natal", "RN"));
			cidades.add(new Cidade("Anápolis", "GO"));
			cidades.add(new Cidade("Recife", "PE"));
			cidades.add(new Cidade("Teresina", "PI"));
			salvaDadosArquivo(cidades);
		}
		
		return cidades;
	}

	public void salvaDadosArquivo(ArrayList<Cidade> cidades) {		
		try {
			output = new ObjectOutputStream(new FileOutputStream(arquivoDados));
			output.writeObject(cidades);
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
