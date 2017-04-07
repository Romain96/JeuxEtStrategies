import java.net.* ;
import java.rmi.* ;

public class ObjetProducteur
{
	public static void main(String [] args)
	{
		if (args.length != 4)
		{
			System.out.println("Usage : java ObjectProducteur <port rmiregistry> <idProducteur> <typeRessource> <quantiteRessource>") ;
			System.exit(0) ;
		}
		try
		{
			int idProducteur = Integer.parseInt(args[1]);
			int quantiteRessource = Integer.parseInt(args[3]);
			ProducteurImpl objLocal = new ProducteurImpl(idProducteur, args[2], quantiteRessource );
			Naming.rebind( "rmi://localhost:" + args[0] + "/objLocal" ,objLocal) ;
			System.out.println("Producteur " + objLocal.getIdProducteur() + " pret") ;
		}
		catch (RemoteException re) { System.out.println(re) ; }
		catch (MalformedURLException e) { System.out.println(e) ; }
	}
}
