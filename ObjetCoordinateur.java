import java.net.* ;
import java.rmi.* ;

public class ObjetCoordinateur
{
	public static void main(String [] args)
	{
		if (args.length != 4)
		{
			System.out.println("Usage : java ObjectProducteur <port rmiregistry> <nbAgents> <typeRessource> <quantiteRessource>") ;
			System.exit(0) ;
		}
		try
		{
			int nbAgents = Integer.parseInt(args[1]);
			int quantiteRessource = Integer.parseInt(args[3]);
			CoordinateurImpl objLocal = new CoordinateurImpl(nbAgents, new Ressource(args[2], quantiteRessource));
			Naming.rebind( "rmi://localhost:" + args[0] + "/objLocal" ,objLocal) ;
			System.out.println("Coordinateur pret") ;
		}
		catch (RemoteException re) { System.out.println(re) ; }
		catch (MalformedURLException e) { System.out.println(e) ; }
	}
}
