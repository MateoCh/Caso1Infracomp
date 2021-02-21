import java.util.ArrayList;

public class Producto 
{
	private static int cont=0;
	private boolean tipoA;
	private int id;
	private static Object asignado = new Object();
	
	public Producto(boolean pTipo)
	{
		tipoA=pTipo;
		id=asignarId();
	}
	
	public int asignarId()
	{
		synchronized(asignado){
			return cont++;
		}
		
	}
	
	public boolean getTipo()
	{
		return tipoA;
	}
	
	public int getId()
	{
		return id;
	}
}
