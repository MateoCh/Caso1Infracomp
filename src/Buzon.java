import java.util.ArrayList;

public class Buzon 
{
	private ArrayList<Producto> productos;
	private int productosA;
	private int productosB;
	private int capacidad;
	
	public Buzon(int pCapacidad)
	{
		productos=new ArrayList<Producto>();
		productosA=0;
		productosB=0;
		capacidad=pCapacidad;
	}
	
	public boolean insertar(Producto p)
	{
		boolean rta=false;
		if(productos.size()<capacidad)
		{
			rta=true;
			productos.add(p);
			if(p.getTipo())
			{
				productosA++;
			}
			else
			{
				productosB++;
			}
		}
		return rta;
	}
	
	public Producto retirar()
	{
		if(!productos.isEmpty())
		{
			Producto actual = productos.remove(0);
			if(actual.getTipo())
				productosA--;
			else
				productosB--;
			
			return actual;
		}
			
		else
			return null;
	}
	
	public Producto retirar(boolean tipoA)
	{
		Producto rta=null;
		if(tipoA&&productosA>0)
		{
			boolean centinela=false;
			for (int i = 0; i < productos.size() && !centinela ; i++) 
			{
				if(productos.get(i).getTipo())
				{
					productosA--;
					centinela=true;
					rta=productos.remove(i);
				}
			}
		}
		else if(!tipoA&&productosB>0)
		{
			boolean centinela=false;
			for (int i = 0; i < productos.size() && !centinela ; i++) 
			{
				if(!productos.get(i).getTipo())
				{
					productosB--;
					centinela=true;
					rta=productos.remove(i);
				}
			}
		}
		return rta;
	}
	
	
	
}

