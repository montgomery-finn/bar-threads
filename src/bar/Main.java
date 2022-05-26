package bar;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.Queue;
import java.util.Random;

public class Main {
	public static void main(String[] args) {
		int numeroClientes = 2;
		int numeroGarcoms = 1;
		int numeroRodadas = 1;
		int capacidadeGarcom = 1;
	
		Queue<Garcom> garcomsDisponiveis = new LinkedList<Garcom>();
		Bar bar = new Bar(numeroGarcoms);
		
		Garcom garcom;
		for(int i = 0; i < numeroGarcoms; i++) {
			garcom = new Garcom(
					garcomsDisponiveis, 
					capacidadeGarcom, 
					numeroRodadas,
					bar);
			garcom.start();
		}
		
		Cliente cliente;
		for(int i = 0; i < numeroClientes; i++) {
			cliente = new Cliente(garcomsDisponiveis, bar);
			cliente.start();
		}
		
	}
}
