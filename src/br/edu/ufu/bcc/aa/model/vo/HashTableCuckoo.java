package br.edu.ufu.bcc.aa.model.vo;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Random;
import java.util.Set;

public class HashTableCuckoo<Chave, Valor> {
	private Entrada<Chave, Valor>[] tabela = null;
	private final int  p = 0xffffff;
	private int capacidade;
	private int a, b;

	
	
	public HashTableCuckoo(int capacidade) {
		if (capacidade <= 0)
			this.capacidade = 1;
		else
			this.capacidade = capacidade;
		
		this.tabela = new Entrada[this.capacidade];
		selecionarA();
		selecionarB();
	}
	
	public int tamanho() {
		int quantidade = 0;
		for (int i = 0; i < this.tabela.length; i++)
			if ( this.tabela[i] != null )
				quantidade++;
		return quantidade;
	}
	
	public void limpar() {
		this.tabela = new Entrada[this.capacidade];
	}
	
	public List<Valor> valores() {
		List<Valor> valores = new ArrayList<Valor>();
		for (int i = 0; i < tabela.length; i++)
			if ( this.tabela[i] != null )
				valores.add(this.tabela[i].getValor());
		return valores;
	}
	
	public Set<Chave> chaves(){
		Set<Chave> chaves = new HashSet<Chave>();
		for(int i = 0; i < tabela.length; i++)
			if( this.tabela[i] != null)
				chaves.add(this.tabela[i].getChave());
		return chaves;
	}
	
	public int hash1(Chave chave) {
		return chave.hashCode() % this.capacidade;
	}
	
	public int hash2(Chave chave) {
		return ( ( this.a * chave.hashCode() + this.b) % this.capacidade );
	}
	
	public void inserir(Chave chave, Valor valor) {
		int indice = hash1(chave);
		int indice2 = hash2(chave);
		
		// Verificando se existe entrada na tabela
		if (this.tabela[indice] != null && this.tabela[indice].getValor().equals(valor) ) {
			return;
		}
		
		if ( this.tabela[indice2] != null && this.tabela[indice2].getValor().equals(valor) ) {
			return;
		}
		
		
		int posicao = indice;
		for ( int i = 0; i < this.capacidade; i++ ) {
			if ( this.tabela[posicao] == null ) {
				Entrada<Chave,Valor> entrada = new Entrada<Chave,Valor>(chave, valor);
				this.tabela[posicao] = entrada;
				return;
			}
			chave = this.tabela[posicao].getChave();
			valor = this.tabela[posicao].getValor();
			
			int h1 = hash1(chave);
			int h2 = hash2(chave);
			
			if ( posicao == h1 ) {
				posicao = h2;
			}
			else {
				posicao = h1;
			}
		}
		rehash();
		inserir(chave, valor);
		selecionarA();
		selecionarB();
	}
	
	private void rehash() {
		// Antigos Valores
		Entrada<Chave, Valor>[] tabelaCopia = this.tabela;
		
		// Novos valores
		this.capacidade = (this.capacidade * 2) + 1;
		this.tabela = new Entrada[this.capacidade];
		
		for (int i = 0; i < tabelaCopia.length; i++) {
			if ( this.tabela[i] != null )
				inserir(this.tabela[i].getChave(), this.tabela[i].getValor());
		}
		selecionarA();
		selecionarB();
	}
	
	private void selecionarA() {
		do {
			Random random = new Random();
			this.a = random.nextInt(this.p);
		} while (this.a == 0);	
	}
	
	private void selecionarB() {
		Random random = new Random();
		this.b = random.nextInt(this.p);
	}
	
	public boolean remover(Chave chave, Valor valor) {
		int indice = hash1(chave);
		int indice2 = hash2(chave);
		
		if ( this.tabela[indice] != null && this.tabela[indice].getValor().equals(valor) ) {
			this.tabela[indice] = null;
			return true;
		}
		
		if ( this.tabela[indice2] != null && this.tabela[indice].getValor().equals(valor) ) {
			this.tabela[indice2] = null;
			return true;
		}
		
		return false;
	}
	

}
