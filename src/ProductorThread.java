import java.util.ArrayList;

public class ProductorThread extends Thread
{
	private int MAX_PRODUCTOS;
	private Buzon buz;
	private boolean tipoA;
	private ArrayList<Producto> productos;
	private int id;
	private static Object salida = new Object();

	public ProductorThread(int pMax, Buzon pBuz, boolean pTipo, int pId)
	{
		productos= new ArrayList<Producto>();
		MAX_PRODUCTOS=pMax;
		buz=pBuz;
		tipoA=pTipo;
		id = pId;
	}

	public void run()
	{
		while((MAX_PRODUCTOS-productos.size())>0)
		{
			//System.out.println("Productor");

			Producto creado = new Producto(tipoA);
			while(!buz.insertar(creado) && (MAX_PRODUCTOS-productos.size())>0)
			{
				//System.out.println("Productor2");
				yield();
			}
			synchronized(buz)
			{
				buz.notify();				
			}
			productos.add(creado);	
			synchronized(salida) {
				System.out.println("Productor: " + id + " de tipo: " + (tipoA? "A":"B") + " creo producto: " + creado.getId() + " de tipo: " + (creado.getTipo()? "A":"B"));
			}

		}
	}
}
