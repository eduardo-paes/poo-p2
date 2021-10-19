package model;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

import model.enums.ETipoItem;
import model.interfaces.ICliente;
import model.interfaces.IFuncionario;
import model.interfaces.IItem;
import model.interfaces.IVeiculo;

public class OrdemServico {

	private static int contadorNumero = 1;

	private final int numero;
	private final Date dataEntrada;

	private int kmAtual;
	private String descricao;

	private IVeiculo veiculo;
	private ICliente cliente;
	private IFuncionario consultor;
	private ArrayList<ItemOS> itens;

	public OrdemServico(IVeiculo veiculo, int quilometragem) {
		numero = contadorNumero++;
		dataEntrada = new Date();
		itens = new ArrayList<ItemOS>();
		this.veiculo = veiculo;
		this.cliente = veiculo.getProprietario();
		this.kmAtual = quilometragem;
	}

	public String getDescricao() {
		return descricao;
	}

	public void setDescricao(String descricao) {
		this.descricao = descricao;
	}

	public int getKmAtual() {
		return kmAtual;
	}

	public void setKmAtual(int kmAtual) {
		this.kmAtual = kmAtual;
	}

	public IFuncionario getConsultor() {
		return consultor;
	}

	public void setConsultor(IFuncionario consultor) {
		this.consultor = consultor;
	}

	public IVeiculo getVeiculo() {
		return veiculo;
	}

	public ICliente getCliente() {
		return cliente;
	}

	public void addItem(IItem item, int qtde, double preco) {
		itens.add(new ItemOS(item, qtde, preco));
	}

	public void addItem(IItem item, int qtde) {
		itens.add(new ItemOS(item, qtde));
	}

	public void removeItem(int index) {
		itens.remove(index);
	}

	public double getTotalServicos() {
		double valor = 0;
		for (ItemOS item : itens) {
			if (item.getTipo() == ETipoItem.SERVICO) {
				valor += item.getPreco();
			}
		}
		return valor;
	}

	public double getTotalPecas() {
		double valor = 0;
		for (ItemOS item : itens) {
			if (item.getTipo() == ETipoItem.PECA) {
				valor += item.getPreco();
			}
		}
		return valor;
	}

	public double getTotalOS() {
		return getTotalServicos() + getTotalPecas();
	}

	public StringBuilder listarOS() {
		StringBuilder sb = new StringBuilder();
		DateFormat df = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss");

		sb.append("\n::::::: Ordem de Serviço (" + numero + "):");
		sb.append("\n\tData de Entrada: " + df.format(dataEntrada));

		sb.append("\n\t:: Dados do Cliente");
		sb.append("\n\t\tNome: " + cliente.getNome());
		sb.append("\n\t\tTelefone: " + cliente.getTelefone());
		sb.append("\n\t\tE-mail: " + cliente.getEmail());

		sb.append("\n\t:: Dados do Veículo");
		sb.append("\n\t\tModelo: " + veiculo.getModelo().getNome());
		sb.append("\n\t\tAno: " + veiculo.getAno());
		sb.append("\n\t\tCor: " + veiculo.getCor());
		sb.append("\n\t\tPlaca: " + veiculo.getPlaca());

		sb.append("\n\t:: Itens Associados");
		int i = 1;
		double valorServicos = this.getTotalServicos(), valorPecas = this.getTotalPecas(), valorTotal = 0;

		for (ItemOS item : itens) {
			sb.append("\n\t\t" + (i++) + ".\tTipo: " + item.getTipo().getName() + "\t| Cód.: TODO "
					+ "\t| Descrição: TODO \t| Quantidade: " + item.getQuantidade()
					+ "\t| Valor: R$ " + String.format("%.2f", item.getPreco()));
		}

		sb.append("\n\t:: Valores");
		sb.append("\n\t\tValor dos Serviços: R$ " + String.format("%.2f", valorServicos));
		sb.append("\n\t\tValor das Peças: R$ " + String.format("%.2f", valorPecas));

		if (cliente.isPlatinum()) {
			sb.append("\n\t\tDesconto Platinum: R$ " + String.format("%.2f", valorServicos));
			valorTotal = valorPecas;
		} else {
			valorTotal = valorPecas + valorServicos;
		}

		sb.append("\n\t\tValor Total: R$ " + String.format("%.2f", valorTotal));

		return sb;
	}

}
