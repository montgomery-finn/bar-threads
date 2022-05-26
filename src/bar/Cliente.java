package bar;

import java.util.Queue;
import java.util.Random;
import java.util.UUID;

public class Cliente extends Thread{
	private Random random;
	private Queue<Garcom> _garcomsDisponiveis;
	private Bar _bar;
	
	public String nome;

	public Cliente(Queue<Garcom> garcomsDisponiveis, Bar bar) {
		random = new Random();
		_garcomsDisponiveis = garcomsDisponiveis;
		_bar = bar;
		nome = UUID.randomUUID().toString();
	}
	
	@Override
	public void run() {
		System.out.println("Cliente " + nome + " chegou ao bar");

		
		while(barEstaAberto()) {	
		
			Garcom garcom;
		
			synchronized (_garcomsDisponiveis) {
				garcom = _garcomsDisponiveis.poll();
				
				while(garcom == null) {
					try {
						System.out.println("Cliente " + nome + " não conseguiu garçom e ira esperar");
						_garcomsDisponiveis.wait();
						garcom = _garcomsDisponiveis.poll();
					} catch (InterruptedException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
				}
			}
			
			System.out.println("Cliente " + nome + " chamou o garcom " + garcom.nome);
			
			Pedido pedido = new Pedido();
			pedido.cliente = this;
			garcom.receberPedido(pedido);
			esperarPedido(pedido);
		}

	}	
	
	private void esperarPedido(Pedido pedido) {
		synchronized (pedido) {
			while(!pedido.entregue) {
				try {
					pedido.wait();
				} catch (InterruptedException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		}
		
		receberPedido();
	}
	
	private void receberPedido() {
		System.out.println("O cliente " + nome + " recebeu o pedido");
		consumirPedido();
	}
	
	private void consumirPedido() {
		System.out.println("Consumindo pedido");
		int tempoConsumo = random.nextInt(11);
		
		try {
			sleep(tempoConsumo * 1000);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	private boolean barEstaAberto() {
		synchronized (_bar) {
			return _bar.aberto;
		}

	}
}