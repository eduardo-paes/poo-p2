package controller;

import java.util.ArrayList;

import model.OrdemServico;
import persistence.ServicosPersistence;

public class ServicosController {
	public static ServicosController instance;
	private static ArrayList<OrdemServico> servicos;
	private static ServicosPersistence servicosPersistence;

	private ServicosController() {
		servicosPersistence = new ServicosPersistence();
		servicos = servicosPersistence.extraiDadosArquivo();
	}

	public static ServicosController getInstance() {
		if (instance == null)
			instance = new ServicosController();
		return instance;
	}
	
	public void salvarServico() {
		
	}
	
	public void editarServico() {
		
	}
	
	public void removerServico() {
		
	}
	
	public void listarServicos() {
		
	}
	
	public void listarItensOS() {
		
	}
	
	public void listarInfoOS() {
		
	}
	
	public void salvarItemOS() {
		
	}
	
	public void editarItemOS() {
		
	}
	
	public void removerItemOS() {
		
	}
	
	public void listarItemOS() {
		
	}
}
