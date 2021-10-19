package model;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import model.excepetions.EmailException;
import model.interfaces.IPessoa;

public class Pessoa implements IPessoa {

	private final long cpf;

	private String nome;
	private String email;
	private long telefone;
	
	private Endereco endereco;

	public Pessoa(int cpf, String nome, long telefone, Endereco endereco) {
		this.cpf = cpf;
		this.nome = nome;
		this.telefone = telefone;
		this.endereco = endereco;
	}

	public void setNome(String nome) {
		this.nome = nome;
	}	

	public void setEmail(String email) {
		try {
			Pattern pattern = Pattern.compile("^[A-Z0-9._%+-]+@[A-Z0-9.-]+\\.[A-Z]{2,6}$", Pattern.CASE_INSENSITIVE);
	        Matcher matcher = pattern.matcher(email);
	        
			if (matcher.find()) {
				this.email = email;
			}
	        else {
	        	throw new EmailException("E-mail inválido.");
	        }
			
		} catch (EmailException e) {
			System.out.println(e.getMessage());
		}
	}

	public void setTelefone(long telefone) {
		this.telefone = telefone;
	}


	@Override
	public long getCpf() {
		return this.cpf;
	}
	
	@Override
	public String getNome() {
		return nome;
	}

	@Override
	public String getEmail() {
		return this.email;
	}
	
	@Override
	public long getTelefone() {
		return telefone;
	}

	@Override
	public Endereco getEndereco() {
		return this.endereco;
	}

}