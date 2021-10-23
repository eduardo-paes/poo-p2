package controller;

import java.util.ArrayList;

import model.Categoria;
import model.Item;
import persistence.CategoriaPersistence;

public class CategoriaController {
	public static CategoriaController instance;
	private static ArrayList<Categoria> categorias;
	private static CategoriaPersistence categoriaPersistence;

	private CategoriaController() {
		categoriaPersistence = new CategoriaPersistence();
		categorias = categoriaPersistence.extraiDadosArquivo();
	}

	public static CategoriaController getInstance() {
		if (instance == null)
			instance = new CategoriaController();
		return instance;
	}

	public ArrayList<Object[]> listarCategorias() {
		ArrayList<Object[]> rows = new ArrayList<Object[]>();
		for (Categoria c : categorias) {
			Object[] row = new Object[1];
			row[0] = c.getNome();
			rows.add(row);
		}
		return rows;
	}

	public ArrayList<Object[]> listarItensCategoria(int id) {
		ArrayList<Object[]> rows = new ArrayList<Object[]>();
		if (id >= 0) {
			Categoria categoria = (Categoria) categorias.get(id);
			rows = categoria.listarItens();
		}
		return rows;
	}

	public void salvaCategoria(String nome) {
		if (!nome.isBlank() ) {
			Categoria categoria = new Categoria(nome);
			categorias.add(categoria);
		}
	}
	
	public void removeCategoria(int id) {
		if (id >= 0 && id <= categorias.size()) {
			categorias.remove(id);
			categoriaPersistence.salvaDadosArquivo(categorias);
		}
	}	

	public void addItem(int categoriaId, Item item) {

		if (categoriaId >= 0 && categoriaId <= categorias.size()) {
			Categoria categoria = (Categoria) categorias.get(categoriaId);
			categoria.addItem(item);
			categoriaPersistence.salvaDadosArquivo(categorias);
		}
	}

	public void removerItem(int categoriaId, Item item) {

		if (categoriaId >= 0 && categoriaId <= categorias.size()) {
			Categoria categoria = (Categoria) categorias.get(categoriaId);
			System.out.println(item);
			categoria.removeItem(item);
			categoriaPersistence.salvaDadosArquivo(categorias);
			System.out.println("Item removido da categoria.");
		}
	}
	
	public void editarItem(int categoriaId, Item item) {
		if (categoriaId >= 0 && categoriaId <= categorias.size()) {
			Categoria categoria = (Categoria) categorias.get(categoriaId);
			categoria.removeItem(item);
			categoria.addItem(item);
			categoriaPersistence.salvaDadosArquivo(categorias);
			System.out.println("Item editado na categoria.");
		}
	}
		
}
