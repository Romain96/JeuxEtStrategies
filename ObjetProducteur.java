import java.net.* ;
import java.rmi.* ;

public class ObjetProducteur
{
	public static void main(String [] args)
	{
		if (args.length != 1)
		{
			System.out.println("Usage : java ObjectProducteur <idProducteur> <typeRessource> <quantiteRessource>") ;
			System.exit(0) ;
		}
		try
		{
			ProducteurImpl objLocal = new ProducteurImpl( args[1], args[2], args[3] ),
			Naming.rebind( "rmi://localhost:" + args[0] + "/objLocal" ,objLocal) ;
			System.out.println("Producteur " + objLocal.getIdProducteur() + " pret") ;
		}
		catch (RemoteException re) { System.out.println(re) ; }
		catch (MalformedURLException e) { System.out.println(e) ; }
	}
}
