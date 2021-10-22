package controller;

import java.util.ArrayList;

import model.enums.ETipoItem;

public class ItemController {
	
	public static ItemController instance;
	
	private ItemController() {
		
	}
	
	public static ItemController getInstance() {
		if (instance == null) {
			instance = new ItemController();
		}
		return instance;
	}

	public ArrayList<String> getTipoItems() {
		ArrayList<String> tipos = new ArrayList<String>();
		
		tipos.add(ETipoItem.PECA.getName());
		tipos.add(ETipoItem.SERVICO.getName());
		
		return tipos;
	}
}
