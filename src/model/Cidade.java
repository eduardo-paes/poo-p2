package model;

import java.io.Serializable;

public class Cidade implements Serializable {

	private static final long serialVersionUID = -3715085183524788898L;
	private final String nome;
	private final String uf;

	public Cidade(String nome, String uf) {
		this.nome = nome;
		this.uf = uf;
	}

	public String getNome() {
		return nome;
	}

	public String getUf() {
		return uf;
	}

}
