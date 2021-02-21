import java.util.ArrayList;

public class ProductorThread extends Thread
{
	private int MAX_PRODUCTOS;
	private Buzon buz;
	private boolean tipoA;
	private ArrayList<Producto> productos;
	
	public ProductorThread(int pMax, Buzon pBuz, boolean pTipo)
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
			Producto creado = new Producto(tipoA);
			while(!buz.insertar(creado))
			{
				yield();
			}
			productos.add(creado);			
		}
	}
}
