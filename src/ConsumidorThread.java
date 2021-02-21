import java.util.ArrayList;

public class ConsumidorThread extends Thread
{
	private int MAX_PRODUCTOS;
	private Buzon buz;
	private boolean tipoA;
	private ArrayList<Producto> productos;

	public ConsumidorThread(int pMax, Buzon pBuz, boolean pTipo)
	{
		productos= new ArrayList<Producto>();
		MAX_PRODUCTOS=pMax;
		buz=pBuz;
		tipoA=pTipo;
	}

	public void run()
	{
		while(MAX_PRODUCTOS-productos.size()>0)
		{
			Producto retirado=buz.retirar(tipoA);
			while(retirado==null)
			{
				yield();
				retirado=buz.retirar(tipoA);
			}
			productos.add(retirado);
		}
	}
}
