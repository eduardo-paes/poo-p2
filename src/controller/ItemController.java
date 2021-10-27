package controller;

import java.util.ArrayList;

import model.Item;
import model.enums.ETipoItem;
import persistence.ItemPersistence;

public class ItemController {

	public static ItemController instance;

	private static ItemPersistence itemPersistence;
	private static ArrayList<Item> itens;
	private static ArrayList<String> tipos;

	private ItemController() {
		itemPersistence = new ItemPersistence();
		itens = itemPersistence.extraiDadosArquivo();
	}

	public static ItemController getInstance() {
		if (instance == null) {
			instance = new ItemController();
		}
		return instance;
	}

	public ArrayList<String> getTipoItems() {
		if (tipos == null) {
			tipos = new ArrayList<String>();
			tipos.add(ETipoItem.PECA.getName());
			tipos.add(ETipoItem.SERVICO.getName());
		}
		return tipos;
	}
	
	public Item encontrarItem(long codigo) {
		Item item = null;
		for (Item i : itens) {
			System.out.println(i.getCodigo());
			if (i.getCodigo() == codigo) {
				item = i;
				break;
			}
		}
		return item;
	}
	
	public Object[] listarItem(long codigo) {
		Object[] row = new Object[1];
		Item item = encontrarItem(codigo);
		if (item != null) {
			row[0] = item.getCodigo();
			row[1] = item.getTipo().getName();
			row[2] = item.getDescricao();
			row[3] = item.getPreco();
		}
		return row;
	}

	public long salvarItem(String descricao, String tipoItem, double preco, int categoriaId) {
		if (!descricao.isBlank() && !tipoItem.isBlank()) {
			ETipoItem tipo = ETipoItem.PECA.getName() == tipoItem ? ETipoItem.PECA : ETipoItem.SERVICO;
			Item item = new Item(descricao, tipo);
			item.setPreco(preco);
			itens.add(item);

			CategoriaController.getInstance().addItem(categoriaId, item);
			itemPersistence.salvaDadosArquivo(itens);
			return item.getCodigo();
		}
		return 0;
	}

	public void editarItem(long codigo, double preco, int categoriaId) {
		Item item = encontrarItem(codigo);
		
		if (item != null) {
			item.setPreco(preco);
			CategoriaController.getInstance().editarItem(categoriaId, item);
			itemPersistence.salvaDadosArquivo(itens);
		}
	}

	public void removerItem(long codigo, int categoriaId) {
		Item item = encontrarItem(codigo);

		if (item != null) {
			itens.remove(item);
			itemPersistence.salvaDadosArquivo(itens);
			CategoriaController.getInstance().removerItem(categoriaId, item);
		}
	}
}
