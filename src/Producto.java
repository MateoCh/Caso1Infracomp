import java.util.ArrayList;

public class Producto 
{
	private static int cont=0;
	private boolean tipoA;
	private int id;
	
	public Producto(boolean pTipo)
	{
		tipoA=pTipo;
		id=asignarId();
	}
	
	public synchronized int asignarId()
	{
		return cont++;
	}
	
	public boolean getTipo()
	{
		return tipoA;
	}
	
}
