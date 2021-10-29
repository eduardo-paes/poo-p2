package controller;

import java.text.SimpleDateFormat;
import java.util.ArrayList;

import model.ItemOS;
import model.OrdemServico;
import model.interfaces.IFuncionario;
import model.interfaces.IItem;
import model.interfaces.IVeiculo;
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
			IVeiculo veiculo = VeiculoController.getInstance().encontraVeiculo(chassi);

			if (veiculo != null) {
				OrdemServico os = new OrdemServico(veiculo, kmAtual);
				os.setDescricao(descricao);

				if (matriculaFuncionario != 0) {
					IFuncionario funcionario = FuncionarioController.getInstance()
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
				IFuncionario funcionario = FuncionarioController.getInstance()
						.encontraFuncionario(matriculaFuncionario);
				os.setConsultor(funcionario);
			}

			servicosPersistence.salvaDadosArquivo(servicos);
		}
	}

	public void removerServico(int index) {
		if (index >= 0 && servicos.size() >= index) {
			OrdemServico os = servicos.get(index);
			for (int i = 0; i < servicos.get(index).listarItensOS().size(); i++) {
				os.removeItem(i);
			}

			servicos.remove(index);
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

	public ArrayList<Object[]> listarItensOS(int osId) {
		ArrayList<Object[]> rows = new ArrayList<Object[]>();
		if (osId >= 0) {
			for (ItemOS item : servicos.get(osId).listarItensOS()) {
				if (item != null) {

					Object[] row = new Object[5];
					row[0] = item.getCodigo();
					row[1] = item.getTipo().getName();
					row[2] = item.getDescricao();
					row[3] = item.getQuantidade();
					row[4] = item.getPreco();
					rows.add(row);
				}
			}
		}
		return rows;
	}

	public Object[] listarInfoOS(int index) {
		OrdemServico os = servicos.get(index);
		if (os != null) {

			double valorServicos = os.getTotalServicos(), valorPecas = os.getTotalPecas(), desconto = 0;
			double valorTotal = valorPecas + valorServicos;
			Object[] row = new Object[11];

			String nomeCliente = "", emailCliente = "";
			long telefoneCliente = 0;

			if (os.getCliente() != null) {
				nomeCliente = os.getCliente().getNome();
				telefoneCliente = os.getCliente().getTelefone();
				emailCliente = os.getCliente().getEmail();

				if (os.getCliente().isPlatinum()) {
					desconto = valorServicos;
					valorTotal = valorPecas;
				}
			}

			row[0] = nomeCliente;
			row[1] = telefoneCliente;
			row[2] = emailCliente;
			row[3] = os.getVeiculo().getModelo().getNome();
			row[4] = os.getVeiculo().getAno();
			row[5] = os.getVeiculo().getCor();
			row[6] = os.getVeiculo().getPlaca();
			row[7] = String.format("%.2f", valorServicos);
			row[8] = String.format("%.2f", valorPecas);
			row[9] = String.format("%.2f", desconto);
			row[10] = String.format("%.2f", valorTotal);

			return row;
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
