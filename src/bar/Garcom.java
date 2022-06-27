package bar;

import java.util.LinkedList;
import java.util.Queue;
import java.util.UUID;

public class Garcom extends Thread {

	private Queue<Garcom> _garcomsDisponiveis;
	
	private int _capacidade;
	private int _rodadasRestantes;
	
	private Bar _bar;
	
	private Queue<Pedido> _pedidos;
	
	public String nome;
	
	public Garcom(
			Queue<Garcom> garcomsDisponiveis, 
			int capacidade, 
			int totalRodadas,
			Bar bar) 
	{
		_garcomsDisponiveis = garcomsDisponiveis;
		_capacidade = capacidade;
		_rodadasRestantes = totalRodadas;
		_bar = bar;
		
		_pedidos = new LinkedList<Pedido>();
		
		nome = UUID.randomUUID().toString();
	}
	
	@Override
	public void run() {
		System.out.println("Garçom " + nome + " chegou ao bar");
		
		novaRodada();
		esperarPedidos();
		
	}
	
	private void novaRodada() {
		_rodadasRestantes--;
	}
	
	private void esperarPedidos() {
		synchronized (_garcomsDisponiveis) {
			_garcomsDisponiveis.add(this);
			_garcomsDisponiveis.notifyAll();
		}
	}
	
	public void receberPedido(Pedido pedido) {
		_pedidos.add(pedido);
		
		System.out.println("O garçom " + nome + " recebeu o pedido de " + pedido.cliente.nome);
		
		if(_pedidos.size() == _capacidade) {
			levaPedidosAoBar();
		}
		else {
			esperarPedidos();
		}
	}
	
	private void levaPedidosAoBar() {
		synchronized (_bar) {
			ColecaoPedidos colecaoPedidos = new ColecaoPedidos(_pedidos, this);

			_bar.preparaPedidos(colecaoPedidos);
			
			synchronized (colecaoPedidos) {
				while(!colecaoPedidos.pronto) {
					try {
						colecaoPedidos.wait();
					} catch (InterruptedException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
				}
			}
			
			System.out.println("Os pedidos do garcom " + nome + " estão prontos");
			entregarPedidos();
		}
	}
	
	private void entregarPedidos() {
		Pedido pedido = _pedidos.poll();
		
		while(pedido != null) {
			
			synchronized (pedido) {
				pedido.entregue = true;
				System.out.println("O garcom " + nome + " entregou o pedido de " + pedido.cliente.nome);
				pedido.notifyAll();
			}
			
			pedido = _pedidos.poll();
		}
		
		if(_rodadasRestantes > 0) {
			novaRodada();
			esperarPedidos();
		}
		else {
			synchronized (_bar) {
				_bar.garcomFinalizou();
			}
		}
	}
}
