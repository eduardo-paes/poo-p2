package controller;

import java.text.SimpleDateFormat;
import java.util.ArrayList;

import model.Funcionario;
import model.ItemOS;
import model.OrdemServico;
import model.Veiculo;
import model.interfaces.IItem;
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

		if (!chassi.isEmpty()) {
			Veiculo veiculo = VeiculoController.getInstance().encontraVeiculo(chassi);

			if (veiculo != null) {
				OrdemServico os = new OrdemServico(veiculo, kmAtual);
				os.setDescricao(descricao);

				if (matriculaFuncionario != 0) {
					Funcionario funcionario = FuncionarioController.getInstance()
							.encontraFuncionario(matriculaFuncionario);
					os.setConsultor(funcionario);
				}

				servicos.add(os);
				servicosPersistence.salvaDadosArquivo(servicos);

				return os.getNumero();
			}
		}
		return 0;
	}

	public void editarServico(int categoriaId, int kmAtual, int matriculaFuncionario, String descricao) {
		if (categoriaId >= 0 && servicos.size() >= categoriaId) {
			OrdemServico os = servicos.get(categoriaId);

			os.setDescricao(descricao);

			if (matriculaFuncionario != 0) {
				Funcionario funcionario = FuncionarioController.getInstance().encontraFuncionario(matriculaFuncionario);
				os.setConsultor(funcionario);
			}

			servicosPersistence.salvaDadosArquivo(servicos);
		}
	}

	public void removerServico(int index) {
		if (index >= 0 && servicos.size() >= index) {
			servicos.remove(index);

			// TODO: Remover itemOS

			servicosPersistence.salvaDadosArquivo(servicos);
		}
	}

	public ArrayList<Object[]> listarServicos() {
		ArrayList<Object[]> rows = new ArrayList<Object[]>();

		for (OrdemServico os : servicos) {

			String proprietario = "";
			if (os.getCliente() != null) {
				proprietario = os.getCliente().getNome();
			}

			String consultor = "";
			if (os.getConsultor() != null) {
				consultor = String.format("%s - %s", os.getConsultor().getMatricula(), os.getConsultor().getNome());
			}

			String veiculo = "";
			if (os.getVeiculo() != null) {
				veiculo = String.format("%s %s (%s) - %s", os.getVeiculo().getModelo().getNome(),
						os.getVeiculo().getCor(), os.getVeiculo().getAno(), os.getVeiculo().getChassi());
			}

			Object[] row = new Object[7];
			row[0] = os.getNumero();
			row[1] = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss").format(os.getDataEntrada());
			row[2] = veiculo;
			row[3] = proprietario;
			row[4] = consultor;
			row[5] = os.getKmAtual();
			row[6] = os.getDescricao();

			rows.add(row);
		}

		return rows;
	}

	public ArrayList<ItemOS> listarItensOS(int osId) {
		if (osId >= 0) {
			// TODO: Ajustar retorno
			return servicos.get(osId).listarItensOS();
		}
		return null;
	}

	public Object[] listarInfoOS(int index) {
		OrdemServico os = servicos.get(index);
		if (os != null) {
			return os.listarOS();
		}
		return null;
	}

	public void salvarItemOS(int osId, IItem item, double quantidade, double preco) {
		if (osId >= 0 && servicos.size() >= osId) {
			OrdemServico os = servicos.get(osId);
			if (preco != 0) {
				os.addItem(item, quantidade, preco);
			} else {
				os.addItem(item, quantidade);
			}
			servicosPersistence.salvaDadosArquivo(servicos);
		}
	}

	public void editarItemOS(int osId, int itemId, double quantidade, double preco) {
		if (osId >= 0 && servicos.size() >= osId) {
			OrdemServico os = servicos.get(osId);
			ItemOS itemOS = os.listarItensOS().get(itemId);

			if (itemOS != null) {
				itemOS.setPreco(preco);
				if (preco != 0) {
					itemOS.setQuantidade(quantidade);
				} else {
					itemOS.setQuantidade(quantidade);
				}
				servicosPersistence.salvaDadosArquivo(servicos);
			}

		}
	}

	public void removerItemOS(int osId, int itemId) {
		if (osId >= 0 && servicos.size() >= osId) {
			OrdemServico os = servicos.get(osId);
			os.removeItem(itemId);
			servicosPersistence.salvaDadosArquivo(servicos);
		}
	}

}
