package model.interfaces;

import model.enums.ETipoItem;

public interface IItem {
	
	public ETipoItem getTipo();
	public long getCodigo();
	public String getDescricao();
	public double getPreco();
	
}
