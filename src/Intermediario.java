
public class Intermediario extends Thread
{
	private Buzon buz1;
	private Buzon buz2;
	Object lleno,vacio;
	private int totalProductos;
	private int id;

	public void run()
	{	
		Producto movido = retirar();
		while(totalProductos>0)
		{	
//			System.out.println("Inter1");

			if(movido!=null)
			{
				almacenar(movido);
				totalProductos--;
			}
			movido = retirar();
		}
	}

	public Intermediario(Buzon pB1, Buzon pB2, int total, int pId)
	{
		totalProductos=total;
		buz1=pB1;
		buz2=pB2;
//		lleno=new Object();
//		vacio= new Object();
		id= pId;
	}

	public void almacenar(Producto p)
	{
		boolean continuar = true ;
		while (continuar)
		{
//			System.out.println("Inter2");
			synchronized (buz2) 
			{
				boolean inserto = buz2.insertar(p);
//				System.out.println(inserto);
				if (inserto) 
				{
					continuar= false ;
				}
			}
			if (continuar)
			{
				synchronized (buz2) 
				{
					try
					{ 
						buz2.wait () ; 
					}
					catch (Exception e) { e.printStackTrace();}
				}
			}
		}
		synchronized (buz1) 
		{
			try
			{ 
				buz1.notify () ; 
			}
			catch (Exception e) {e.printStackTrace();}		
			System.out.println("Intermediario: "+id+ " almaceno producto: "+ p.getId());

		}
	}

	public Producto retirar()
	{
		boolean continuar = true ;
		Producto rta = null ;
		while (continuar&&totalProductos>0) 
		{
//			System.out.println("Inter3");
			synchronized (buz1)
			{
				rta=buz1.retirar();
				if (rta!=null)
				{
					continuar= false ;
				}
			}
			if (continuar)
			{
				synchronized (buz1) 
				{
					try
					{ 
						buz1.wait () ; 
					}
					catch (Exception e) {e.printStackTrace(); }
				}
			}
		}
		synchronized (buz2) 
		{
			try
			{ 
				buz2.notify () ; 
			}
			catch (Exception e) { e.printStackTrace();}
		}
		return rta;
	}
}
