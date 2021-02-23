import java.util.ArrayList;

public class ConsumidorThread extends Thread
{
	private int MAX_PRODUCTOS;
	private Buzon buz;
	private boolean tipoA;
	private ArrayList<Producto> productos;
	private int id;
	private static Object salida = new Object();
	
	public ConsumidorThread(int pMax, Buzon pBuz, boolean pTipo, int pId)
	{
		productos= new ArrayList<Producto>();
		MAX_PRODUCTOS=pMax;
		buz=pBuz;
		tipoA=pTipo;
		id=pId;
	}

	public void run()
	{
		while((MAX_PRODUCTOS-productos.size())>0)
		{
			//System.out.println("Consumidor");
//			synchronized(salida)
//			{
				Producto retirado=buz.retirar(tipoA);
				while(retirado==null)
				{
					//System.out.println("Consumidor2");
					yield();
					retirado=buz.retirar(tipoA);
				}
				synchronized(buz)
				{
					buz.notify();
				}
				productos.add(retirado);
				System.out.println("Consumidor: " + id + " de tipo: " + (tipoA? "A":"B") + " consumio producto: " + retirado.getId()+ " de tipo: " + (retirado.getTipo()? "A":"B"));
			

		}
	}
}
