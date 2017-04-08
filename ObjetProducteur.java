import java.net.* ;
import java.rmi.* ;
import java.net.MalformedURLException ; 

public class ObjetProducteur
{
	public static void main(String [] args)
	{
		if (args.length != 5)
		{
			System.out.println("Usage : java ObjectProducteur <machine du Serveur> <port rmiregistry> <idProducteur> <typeRessource> <quantiteRessource>") ;
			System.exit(0) ;
		}
		try
		{
			Coordinateur coordinateur = (Coordinateur) Naming.lookup( "rmi://" + args[0] + ":" + args[1] + "/objLocal" );
			
			int idProducteur = Integer.parseInt(args[2]);
			int quantiteRessource = Integer.parseInt(args[4]);
			ProducteurImpl objLocal = new ProducteurImpl(idProducteur, args[3], quantiteRessource );
			Naming.rebind( "rmi://localhost:" + args[0] + "/objLocal" ,objLocal) ;
			System.out.println("Producteur " + objLocal.getIdProducteur() + " pret") ;
			
			// s'enregistrer auprès du coordinateur (convention : port 9000)
			coordinateur.identifierProducteur(objLocal.getIdProducteur(), objLocal.getNumeroPort());
		}
		catch (NotBoundException re) { System.out.println(re) ; }
		catch (RemoteException re) { System.out.println(re) ; }
		catch (MalformedURLException e) { System.out.println(e) ; }
	}
}
