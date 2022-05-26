package bar;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.Queue;
import java.util.Random;

public class Main {
	public static void main(String[] args) {
		int numeroClientes = 3;
		int numeroGarcoms = 2;
		int numeroRodadas = 2;
		int capacidadeGarcom = 5;
	
		Queue<Garcom> garcomsDisponiveis = new LinkedList<Garcom>();
		
		Garcom garcom;
		for(int i = 0; i < numeroGarcoms; i++) {
			garcom = new Garcom(garcomsDisponiveis, capacidadeGarcom, numeroRodadas);
			garcom.start();
		}
		
		Cliente cliente;
		for(int i = 0; i < numeroClientes; i++) {
			cliente = new Cliente(garcomsDisponiveis);
		}
		
	}
}
