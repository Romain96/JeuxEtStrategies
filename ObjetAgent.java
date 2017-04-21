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
			if (args[2].equals("indiv"))
			{
				System.out.println("Je suis indiv : " + args[2]);
				objLocal = new AgentIndiv(idAgent, args[4], quantiteRessource, objectif);
			}
			else if (args[2].equals("coop"))
			{
				System.out.println("Je suis coop : " + args[2]);
				objLocal = new AgentCoop(idAgent, args[4], quantiteRessource, objectif);
			}
			else
			{
				// par défaut un coop
				System.out.println("Je suis indiv par défaut : " + args[2]);
				objLocal = new AgentCoop(idAgent, args[4], quantiteRessource, objectif);
				
			}
			// enregistrement du coordinateur pour l'agent
			objLocal.enregistrerCoordinateur(args[0], Integer.parseInt(args[1]));
			
			// s'enregistrer auprès du coordinateur (convention : port 9000)
			Naming.rebind( "rmi://localhost:" + args[1] + "/agent" + args[3] ,objLocal) ;
			coordinateur.identifierAgent(objLocal.getIdAgent());
		}
		catch (NotBoundException re) { System.out.println(re) ; }
		catch (RemoteException re) { System.out.println(re) ; }
		catch (MalformedURLException e) { System.out.println(e) ; }
	}
}
