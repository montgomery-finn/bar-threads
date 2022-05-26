package bar;

public class Bar extends Thread {
	boolean aberto;
	
	private int _garcomsTrabalhando;
	private int _garcomsFinalizados;
	
	public Bar(int garcomsTrabalhando) {
		aberto = true;
		
		_garcomsTrabalhando = garcomsTrabalhando;
	}
	
	public void garcomFinalizou() {
		_garcomsFinalizados++;
		
		if(_garcomsFinalizados == _garcomsTrabalhando) {
			aberto = false;
		}
	}
	
	public void preparaPedidos(ColecaoPedidos colecaoPedidos) {
		try {
			sleep(2000); //tempo de preparo
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		synchronized (colecaoPedidos) {
			colecaoPedidos.pronto = true;
			colecaoPedidos.notifyAll();
		}
	}
}
