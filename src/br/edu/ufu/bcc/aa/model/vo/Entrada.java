package br.edu.ufu.bcc.aa.model.vo;

public class Entrada<Chave, Valor> {
	private Chave chave = null;
	private Valor valor = null;
	
	public Entrada(Chave chave, Valor valor) {
		this.chave = chave;
		this.valor = valor;
	}
	
	public Chave getChave() {
		return chave;
	}
	public void setChave(Chave chave) {
		this.chave = chave;
	}
	public Valor getValor() {
		return valor;
	}
	public void setValor(Valor valor) {
		this.valor = valor;
	}
}
