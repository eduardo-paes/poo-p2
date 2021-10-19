package model.enums;

public enum ETipoItem {

	SERVICO("Serviço"), PECA("Peça");

	private final String name;

	ETipoItem(String name) {
		this.name = name;
	}

	public String getName() {
		return name;
	}

}
