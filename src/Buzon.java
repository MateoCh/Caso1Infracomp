import java.util.ArrayList;

public class Buzon 
{
	private static int numProductos=0;
	private int tamanho;
	Object lleno,vacio;
	
	public Buzon(int n)
	{
		tamanho=n;
		lleno= new Object();
		vacio= new Object();
	}
	
	public void almacenar(int x) throws InterruptedException
	{
		boolean continuar=true;
		while(continuar)
		{
			//(x+numProductos)>=tamanho
			synchronized(this)
			{
				if((x+numProductos)<=tamanho)
				{
					numProductos+=x;
					continuar=false;
				}
			}
			if(continuar)
			{
				synchronized(lleno)
				{
					lleno.wait();				
				}
			}
			
		}
		
		synchronized(vacio)
		{
			vacio.notify();
		}
	}
	
	public int retirar(int y) throws InterruptedException
	{
		boolean continuar=true;
		int rta=0;
		while(continuar)
		{
			synchronized(this)
			{
				if((numProductos-y)>=0)
				{
					rta=y;
					numProductos-=y;
					continuar=false;
				}
			}
			if(continuar)
			{
				synchronized(vacio)
				{
					vacio.wait();				
				}
			}
		}
		synchronized(lleno)
		{
			lleno.notify();			
		}
		return rta;
	}
}
