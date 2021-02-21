
public class Intermediario extends Thread
{
	private Buzon buz1;
	private Buzon buz2;
	Object lleno,vacio;
	private int totalProductos;

	public void run()
	{	
		Producto movido = retirar();
		while(movido!=null&&totalProductos>0)
		{
			almacenar(movido);
			movido = retirar();
		}
	}
	
	public Intermediario(Buzon pB1, Buzon pB2, int total)
	{
		totalProductos=total;
		buz1=pB1;
		buz2=pB2;
		lleno=new Object();
		vacio= new Object();
	}

	public void almacenar(Producto p)
	{
		boolean continuar = true ;
		while (continuar)
		{
			synchronized (this) 
			{
				if (buz2.insertar(p)) 
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
		}
	}

	public Producto retirar()
	{
		boolean continuar = true ;
		Producto rta = null ;
		while (continuar&&totalProductos>0) 
		{
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
