package controller;

import java.util.ArrayList;

import model.Funcionario;
import model.OrdemServico;
import model.Veiculo;
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

	public int salvarServico(String chassi, int kmAtual, int matriculaFuncionario, String descricao) {
		
		if (!chassi.isEmpty() ) {
			Veiculo veiculo = VeiculoController.getInstance().encontraVeiculo(chassi);
			
			if (veiculo != null) {
				OrdemServico os = new OrdemServico(veiculo, kmAtual);
				os.setDescricao(descricao);
				
				if (matriculaFuncionario != 0) {
					Funcionario funcionario = FuncionarioController.getInstance().encontraFuncionario(matriculaFuncionario);
					os.setConsultor(funcionario);
				}
				
				servicos.add(os);
				servicosPersistence.salvaDadosArquivo(servicos);
				
				return os.getNumero();
			}
		}
		return 0;
	}

	public void editarServico() {

	}

	public void removerServico() {

	}

	public void listarServicos() {

	}

	public void listarItensOS() {

	}

	public Object[] listarInfoOS(int index) {
		OrdemServico os = servicos.get(index);
		if (os != null) {
			return os.listarOS();
		}
		return null;
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
