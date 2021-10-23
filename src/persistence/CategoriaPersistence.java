package persistence;

import java.io.EOFException;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InvalidClassException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.ArrayList;

import model.Categoria;

public class CategoriaPersistence {
	private String arquivoDados = "categorias.bin";
	private ObjectInputStream input;
	private ObjectOutputStream output;

	@SuppressWarnings("unchecked")
	public ArrayList<Categoria> extraiDadosArquivo() {
		ArrayList<Categoria> categorias = new ArrayList<Categoria>();

		try {
			input = new ObjectInputStream(new FileInputStream(arquivoDados));
			while (true) {
				categorias = (ArrayList<Categoria>) input.readObject();
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
		return categorias;
	}

	public void salvaDadosArquivo(ArrayList<Categoria> categorias) {
		try {
			output = new ObjectOutputStream(new FileOutputStream(arquivoDados));
			output.writeObject(categorias);
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