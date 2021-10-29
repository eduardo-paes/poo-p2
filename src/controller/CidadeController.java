package controller;

import java.util.ArrayList;

import model.Cidade;
import persistence.CidadePersistence;

public class CidadeController {
	private ArrayList<Cidade> cidades;
	private CidadePersistence cidadePersistence;
	private static CidadeController instance;
	
	private CidadeController() {
		cidades = new ArrayList<Cidade>();
		cidadePersistence = new CidadePersistence();
		cidades = cidadePersistence.extraiDadosArquivo();
	}
	
	public static CidadeController getInstance() {
		if (instance == null)
			instance = new CidadeController();
		return instance;
	}

	public ArrayList<String> listaCidades() {
		ArrayList<String> cidadeUfs = new ArrayList<String>();

		for (Cidade c : cidades) {
			cidadeUfs.add(String.format("%s - %s", c.getNome(), c.getUf()));
		}

		return cidadeUfs;
	}

	public Cidade encontrarCidade(String nomeCidade, String uf) {
		Cidade cidade = null;
		for (Cidade c : cidades) {
			if (c.getNome().contains(nomeCidade) && c.getUf().contains(uf)) {
				cidade = c;
				break;
			}
		}
		return cidade;
	}
}
