package bar;

import java.util.Queue;

public class ColecaoPedidos {
	private Queue<Pedido> pedidos;
	public boolean pronto;
	
	public ColecaoPedidos(Queue<Pedido> pedidos) {
		pronto = false;
	}
}
