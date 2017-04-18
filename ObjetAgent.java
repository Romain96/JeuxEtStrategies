import java.rmi.* ; 
import java.net.MalformedURLException ; 

public class ObjetAgent
{
	public static void main(String [] args)
	{
		if (args.length != 7)
		{
			System.out.println("Usage : java ObjetAgent <machine du Serveur> <port du rmiregistry> <personnalite> <idAgent> <typeRessource> <quantiteRessource> <objectif>") ;
			System.exit(0) ;
		}
		try
		{
			Coordinateur coordinateur = (Coordinateur) Naming.lookup( "rmi://" + args[0] + ":" + args[1] + "/coordinateur" );
			
			int idAgent = Integer.parseInt(args[3]);
			int quantiteRessource = Integer.parseInt(args[5]);
			int objectif = Integer.parseInt(args[6]);
			AgentImpl objLocal;
			
			// créer et initialiser l'agent selon la personnalité 
			if (args[3] == "indiv")
			{
				objLocal = new AgentIndiv(idAgent, args[4], quantiteRessource, objectif);
			}
			else if (args[3] == "coop")
			{
				objLocal = new AgentCoop(idAgent, args[4], quantiteRessource, objectif);
			}
			else
			{
				// par défaut un coop
				objLocal = new AgentCoop(idAgent, args[4], quantiteRessource, objectif);
				
			}
			
			// s'enregistrer auprès du coordinateur (convention : port 9000)
			Naming.rebind( "rmi://localhost:" + args[1] + "/agent" + args[3] ,objLocal) ;
			coordinateur.identifierAgent(objLocal.getIdAgent());
		}
		catch (NotBoundException re) { System.out.println(re) ; }
		catch (RemoteException re) { System.out.println(re) ; }
		catch (MalformedURLException e) { System.out.println(e) ; }
	}
}
