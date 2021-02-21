
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
			System.out.println("Inter1");

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
		lleno=new Object();
		vacio= new Object();
		id= pId;
	}

	public void almacenar(Producto p)
	{
		boolean continuar = true ;
		while (continuar)
		{
			System.out.println("Inter2");
			synchronized (this) 
			{
				boolean inserto = buz2.insertar(p);
				System.out.println(inserto);
				if (inserto) 
				{
					continuar= false ;
				}
			}
			if (continuar)
			{
				synchronized (lleno) 
				{
					try
					{ 
						lleno.wait () ; 
					}
					catch (Exception e) { }
				}
			}
		}
		synchronized (vacio) 
		{
			try
			{ 
				vacio.notify () ; 
			}
			catch (Exception e) {}		
			System.out.println("Intermediario: "+id+ " almaceno producto: "+ p.getId());

		}
	}

	public Producto retirar()
	{
		boolean continuar = true ;
		Producto rta = null ;
		while (continuar&&totalProductos>0) 
		{
			System.out.println("Inter3");
			synchronized (this)
			{
				rta=buz1.retirar();
				if (rta!=null)
				{
					continuar= false ;
				}
			}
			if (continuar)
			{
				synchronized (vacio) 
				{
					try
					{ 
						vacio.wait () ; 
					}
					catch (Exception e) { }
				}
			}
		}
		synchronized (lleno) 
		{
			try
			{ 
				lleno.notify () ; 
			}
			catch (Exception e) { }
		}
		return rta;
	}
}
