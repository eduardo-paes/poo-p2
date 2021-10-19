package model.enums;

public enum ETipoItem {

	SERVICO("Servi�o"), PECA("Pe�a");

	private final String name;

	ETipoItem(String name) {
		this.name = name;
	}

	public String getName() {
		return name;
	}

}
