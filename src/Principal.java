import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.FileReader;
import java.io.IOException;
import java.util.Properties;


public class Principal 
{
	private static Properties datos;
	private static int numProdCons;
	private static int numProductos;
	private static int buzonesProd;
	private static int buzonesCons;
	private static Buzon buzProd;
	private static Buzon buzInter;
	private static Buzon buzCons;
	
	public static void main(String[] args) 
	{
		try 
		{
			cargar();
			inicializarCaso();
			buzProd=new Buzon(buzonesProd);
			buzInter= new Buzon(1);
			buzCons=new Buzon(buzonesCons);
			for (int i = 0; i < numProdCons; i++) 
			{
				new ProductorThread(numProductos, buzProd, i%2==0).start();
				new ConsumidorThread(numProductos, buzCons, i%2==0).start();		
			}
			int total= numProdCons*numProductos;
			new Intermediario(buzProd, buzInter, total).start();
			new Intermediario(buzInter, buzCons, total).start();
			
		}
		catch (Exception e) 
		{
			e.printStackTrace();
		}
	}
	
	private static void cargar( ) throws Exception
	{
		datos = new Properties( );
		FileInputStream in = new FileInputStream( "caso1.properties" );
		try
		{
			datos.load( in );
			in.close( );

		}
		catch( IOException e )
		{
			throw new Exception( "Error al cargar el archivo, archivo no válido." );
		}
	}
	
	private  static void inicializarCaso( ) throws Exception
	{

		try
		{
			numProdCons=Integer.parseInt(datos.getProperty( "concurrencia.numProdCons"));
			numProductos=Integer.parseInt(datos.getProperty( "concurrencia.numProductos"));
			buzonesProd=Integer.parseInt(datos.getProperty( "concurrencia.buzonesProd"));
			buzonesCons=Integer.parseInt(datos.getProperty( "concurrencia.buzonesCons"));
			
		}
		catch(Exception e)
		{
			throw new Exception("Error al leer el formato del archivo.");
		}
	}
}
