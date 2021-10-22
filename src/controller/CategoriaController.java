package controller;

public class CategoriaController {
	public static CategoriaController instance;

	private CategoriaController() {

	}

	public static CategoriaController getInstance() {
		if (instance == null)
			instance = new CategoriaController();
		return instance;
	}

	
}
