package bar;

import java.util.LinkedList;
import java.util.Queue;

public class Garcom extends Thread {

	private Queue<Garcom> _garcomsDisponiveis;
	
	private int _capacidade;
	private int _rodadasRestantes;
	
	private Bar _bar;
	
	private Queue<Pedido> _pedidos;
	
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
	}
	
	@Override
	public void run() {
		while(_rodadasRestantes > 0) {
			novaRodada();
			esperarPedidos();
		}
		
		synchronized (_bar) {
			_bar.garcomFinalizou();
		}
	}
	
	private void novaRodada() {
		_rodadasRestantes--;
	}
	
	private void esperarPedidos() {
		synchronized (_garcomsDisponiveis) {
			_garcomsDisponiveis.add(this);
		}
	}
	
	public void receberPedido(Pedido pedido) {
		_pedidos.add(pedido);
		if(_pedidos.size() == _capacidade) {
			levaPedidosAoBar();
		}
		else {
			esperarPedidos();
		}
	}
	
	private void levaPedidosAoBar() {
		synchronized (_bar) {
			ColecaoPedidos colecaoPedidos = new ColecaoPedidos(_pedidos);
			
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
			
		}
	}
}
