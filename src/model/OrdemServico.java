package model;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;

import model.enums.ETipoItem;
import model.interfaces.ICliente;
import model.interfaces.IFuncionario;
import model.interfaces.IItem;
import model.interfaces.IVeiculo;

public class OrdemServico implements Serializable {

	private static final long serialVersionUID = 1918577112979137404L;

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

	public int getNumero() {
		return numero;
	}

	public Date getDataEntrada() {
		return dataEntrada;
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

	public void addItem(IItem item, double qtde, double preco) {
		itens.add(new ItemOS(item, qtde, preco));
	}

	public void addItem(IItem item, double qtde) {
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
	
	public ArrayList<ItemOS> listarItensOS(){
		return itens;
	}

	public Object[] listarOS() {
		double valorServicos = this.getTotalServicos(), valorPecas = this.getTotalPecas(), desconto = 0;
		double valorTotal = valorPecas + valorServicos;
		Object[] row = new Object[11];

		String nomeCliente = "", emailCliente = "";
		long telefoneCliente = 0;
		
		if (cliente != null) {
			nomeCliente = cliente.getNome();
			telefoneCliente = cliente.getTelefone();
			emailCliente = cliente.getEmail();
			
			if (cliente.isPlatinum()) {
				desconto = valorServicos;
				valorTotal = valorPecas;
			}
		}

		row[0] = nomeCliente;
		row[1] = telefoneCliente;
		row[2] = emailCliente;
		row[3] = veiculo.getModelo().getNome();
		row[4] = veiculo.getAno();
		row[5] = veiculo.getCor();
		row[6] = veiculo.getPlaca();
		row[7] = String.format("%.2f", valorServicos);
		row[8] = String.format("%.2f", valorPecas);
		row[9] = String.format("%.2f", desconto);
		row[10] = String.format("%.2f", valorTotal);

		return row;
	}

}
