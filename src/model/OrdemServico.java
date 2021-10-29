package model;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
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

	public ArrayList<ItemOS> listarItensOS() {
		return itens;
	}

	@SuppressWarnings("deprecation")
	private void writeObject(ObjectOutputStream obj) throws IOException {
		obj.defaultWriteObject();
		obj.writeObject(new Integer(contadorNumero));
	}

	private void readObject(ObjectInputStream obj) throws ClassNotFoundException, IOException {
		obj.defaultReadObject();
		contadorNumero = (Integer) obj.readObject();
	}

}
