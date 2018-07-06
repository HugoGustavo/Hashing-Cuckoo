import java.util.Random;

import br.edu.ufu.bcc.aa.model.vo.HashTableCuckoo;

public class Principal {
	public static void main(String[] args) {
		HashTableCuckoo hashTable = new HashTableCuckoo();
		
		Random random = new Random();
		
		for (int i = 0; i < 1000; i++) {
			int chave = random.nextInt(1000000);
			hashTable.inserir(chave);			
		}		
		System.out.println(hashTable);
		System.out.println("Quantidade de Elementos: " + hashTable.tamanho());
		System.out.println("Capacidade da Tabela   : " + hashTable.getCapacidade());
		System.out.println("Fator de Carga         : " + hashTable.fatorCarga());
	}
}
