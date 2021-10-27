package controller;

import java.util.ArrayList;

import model.Categoria;
import model.Item;
import model.interfaces.IItem;
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

	public void salvaCategoria(String nome) {
		if (!nome.isBlank()) {
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
			categoria.removeItem(item);
			categoriaPersistence.salvaDadosArquivo(categorias);
		}
	}

	public void editarItem(int categoriaId, Item item) {
		if (categoriaId >= 0 && categoriaId <= categorias.size()) {
			Categoria categoria = (Categoria) categorias.get(categoriaId);
			categoria.removeItem(item);
			categoria.addItem(item);
			categoriaPersistence.salvaDadosArquivo(categorias);
		}
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
	
	public ArrayList<Object[]> listarItensCategoria(int index) {
		ArrayList<Object[]> rows = new ArrayList<Object[]>();
		Categoria categoria = categorias.get(index);

		if (categoria != null) {
			for (IItem item : categoria.listarItens()) {
				Object[] row = new Object[4];
				row[0] = item.getCodigo();
				row[1] = item.getTipo().getName();
				row[2] = item.getDescricao();
				row[3] = String.format("%.2f", item.getPreco());
				rows.add(row);
			}
		}

		return rows;
	}

	public Item encontraCategoriaItem(int categoriaId, int itemId) {
		if (categoriaId >= 0 && itemId >= 0) {
			return (Item)categorias.get(categoriaId).listarItens().get(itemId);
		}
		return null;
	}
	
	public int encontraCategoriaPeloItem(long codigoItem) {
		int index = -1;
		if (codigoItem >= 1) {
			for (Categoria categoria : categorias) {
				index++;
				for (IItem item : categoria.listarItens()) {
					if (item.getCodigo() == codigoItem) {
						return index;
					}
				}
			}
		}
		return -1;
	}

}
