package br.edu.ufu.bcc.aa.model.vo;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Random;
import java.util.Set;

public class HashTableCuckoo {
	private Integer[] tabela = null;
	private final int p = 2147483647;
	private int capacidade;
	private int a = 37, b = 17;

	public HashTableCuckoo() {
		this.capacidade = 1;
		this.tabela = new Integer[this.capacidade];
	}
	
	public int tamanho() {
		int quantidade = 0;
		for (int i = 0; i < this.tabela.length; i++)
			if ( this.tabela[i] != null )
				quantidade++;
		return quantidade;
	}
	
	public void limpar() {
		this.tabela = new Integer[this.capacidade];
	}
	
	public List<Integer> valores() {
		List<Integer> valores = new ArrayList<Integer>();
		for (int i = 0; i < tabela.length; i++)
			if ( this.tabela[i] != null )
				valores.add(this.tabela[i]);
		return valores;
	}
	
	public Set<Integer> chaves(){
		Set<Integer> chaves = new HashSet<Integer>();
		for(int i = 0; i < tabela.length; i++)
			if( this.tabela[i] != null)
				chaves.add(this.tabela[i]);
		return chaves;
	}
	
	public int hash1(Integer chave) {
		return chave % this.capacidade;
	}
	
	public int hash2(Integer chave) {
		return  ( (( this.a * chave + this.b ) % this.p) % this.capacidade );
	}
	
	public void inserir(Integer chave) {
		int indice = hash1(chave);
		int indice2 = hash2(chave);
		
		// Verificando se existe entrada na tabela
		if (this.tabela[indice] != null && this.tabela[indice].equals(chave) ) {
			return;
		}
		
		if ( this.tabela[indice2] != null && this.tabela[indice2].equals(chave) ) {
			return;
		}
		
		
		int posicao = indice;
		for ( int i = 0; i < this.capacidade; i++ ) {
			if ( this.tabela[posicao] == null ) {
				this.tabela[posicao] = chave;
				return;
			}
			chave = this.tabela[posicao];
			
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
		inserir(chave);
		selecionarA();
		selecionarB();
	}
	
	private void rehash() {
		// Antigos Valores
		Integer[] tabelaCopia = this.tabela;
		
		// Novos valores
		this.capacidade = (this.capacidade * 2);
		this.tabela = new Integer[this.capacidade];
		
		for (int i = 0; i < tabelaCopia.length; i++) {
			if ( this.tabela[i] != null )
				inserir(this.tabela[i]);
		}
		selecionarA();
		selecionarB();
	}
	
	private void selecionarA() {
		Random random = new Random();
		do {
			this.a = random.nextInt(37);
		} while (this.a == 0);	
	}
	
	private void selecionarB() {
		Random random = new Random();
		this.b = random.nextInt(17);
	}
	
	public boolean remover(Integer chave) {
		int indice = hash1(chave);
		int indice2 = hash2(chave);
		
		if ( this.tabela[indice] != null && this.tabela[indice].equals(chave) ) {
			this.tabela[indice] = null;
			return true;
		}
		
		if ( this.tabela[indice2] != null && this.tabela[indice].equals(chave) ) {
			this.tabela[indice2] = null;
			return true;
		}
		
		return false;
	}
	
	public double fatorCarga() {
		return (double)(this.tamanho()) / (double)(this.capacidade);
	}
	
	public String toString() {
		StringBuilder stringBuilder = new StringBuilder();
		for(int i = 0; i < tabela.length; i++) {
			if ( tabela[i] != null ) {
				stringBuilder.append(i + " -> " + "(" + tabela[i] + "," + tabela[i] + ")\n");
			}
		}
		stringBuilder.append("A: " + this.a + "\n");
		stringBuilder.append("B: " + this.b + "\n");
		return stringBuilder.toString();
	}
	
	public int getCapacidade() {
		return this.capacidade;
	}

}
