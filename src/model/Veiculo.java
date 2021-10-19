package model;

import java.util.ArrayList;

import model.interfaces.ICliente;
import model.interfaces.IVeiculo;

public class Veiculo implements IVeiculo {

	private final String chassi;
	private final int ano;

	private String cor;
	private String placa;

	private ICliente proprietario;
	private Modelo modelo;
	private ArrayList<OrdemServico> servicos;

	public Veiculo(String chassi, Modelo modelo, int ano, String cor) {
		this.servicos = new ArrayList<OrdemServico>();
		this.chassi = chassi;
		this.modelo = modelo;
		this.ano = ano;
		this.cor = cor;
	}

	public Veiculo(String chassi, Modelo modelo, int ano, String cor, String placa) {
		this(chassi, modelo, ano, cor);
		this.placa = placa;
	}

	public void setPlaca(String placa) {
		this.placa = placa;
	}

	public void setCor(String cor) {
		this.cor = cor;
	}

	public void setProprietario(ICliente proprietario) {
		this.proprietario = proprietario;
	}

	public StringBuilder listarServicos() {
		StringBuilder sb = new StringBuilder();
	
		sb.append("\n ::::::::: Histórico de Serviços:");
		sb.append("\n\t:: Dados do Veículo");
		sb.append("\n\t\tModelo: " + modelo.getNome());
		sb.append("\n\t\tAno: " + ano);
		sb.append("\n\t\tCor: " + cor);

		sb.append("\n\t:: Ordens de Serviço");

		double total = 0;
		int count = 1;
		for (OrdemServico os : servicos) {
			sb.append("\n\t\t- Número: " + count++);
			sb.append("\n\t\tQuilometragem de Entrada: " + os.getKmAtual() + " KM");
			sb.append("\n\t\tDescrição: " + os.getDescricao());
			sb.append(
					"\n\t\tConsultor: " + os.getConsultor().getNome() + " (" + os.getConsultor().getMatricula() + ")");
			sb.append("\n\t\tValor dos Serviços: R$ " + String.format("%.2f", os.getTotalServicos()));
			sb.append("\n\t\tValor das Peças: R$ " + String.format("%.2f", os.getTotalPecas()));

			if (proprietario.isPlatinum()) {
				sb.append("\n\t\tValor Desconto: R$ " + String.format("%.2f", os.getTotalServicos()));
				total = os.getTotalPecas();
			} else {
				total = os.getTotalPecas() + os.getTotalServicos();
			}

			sb.append("\n\t\tValor Total: R$ " + String.format("%.2f", total) + "\n");
		}

		return sb;
	}

	public void addServico(OrdemServico os) {
		servicos.add(os);
	}

	public void removeServico(OrdemServico os) {
		servicos.remove(os);
	}

	@Override
	public Modelo getModelo() {
		return this.modelo;
	}

	@Override
	public String getChassi() {
		return this.chassi;
	}

	@Override
	public int getAno() {
		return this.ano;
	}

	@Override
	public String getCor() {
		return this.cor;
	}

	@Override
	public String getPlaca() {
		return this.placa;
	}

	@Override
	public ICliente getProprietario() {
		return this.proprietario;
	}

}
