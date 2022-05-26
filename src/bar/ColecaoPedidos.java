package bar;

import java.util.Queue;

public class ColecaoPedidos {
	private Queue<Pedido> pedidos;
	public Garcom garcom;
	public boolean pronto;
	
	public ColecaoPedidos(Queue<Pedido> pedidos, Garcom garcom) {
		pronto = false;
		this.garcom = garcom;
	}
}
