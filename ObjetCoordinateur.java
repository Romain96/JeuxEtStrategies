import java.net.* ;
import java.rmi.* ;

public class ObjetCoordinateur
{
	public static void main(String [] args)
	{
		if (args.length != 3)
		{
			System.out.println("Usage : java ObjectProducteur <port rmiregistry> <nbAgents> <nbProducteurs>") ;
			System.exit(0) ;
		}
		try
		{
			int nbAgents = Integer.parseInt(args[1]);
			int nbProducteurs = Integer.parseInt(args[2]);
			CoordinateurImpl objLocal = new CoordinateurImpl(nbAgents, nbProducteurs);
			Naming.rebind( "rmi://localhost:" + args[0] + "/coordinateur" ,objLocal) ;
			System.out.println("Coordinateur pret") ;
		}
		catch (RemoteException re) { System.out.println(re) ; }
		catch (MalformedURLException e) { System.out.println(e) ; }
	}
}
