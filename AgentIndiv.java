import java.rmi.server.UnicastRemoteObject ;
import java.rmi.RemoteException ;
import java.rmi.* ; 
import java.net.MalformedURLException ; 
import java.util.*;
import java.io.IOException;

public class AgentIndiv extends AgentImpl
{
	//----------------------------------------------------------------------
	//				attributs
	//----------------------------------------------------------------------
	
	// hérités de Agent
	
	//----------------------------------------------------------------------
	//				Constructeur
	//----------------------------------------------------------------------
	
	// initialise un agent coopératif avec un id et un numéro de port pour
	// permettre d'agir comme serveur pour les autres agents et pour le coordinateur
	// et initialise ses objectifs de ressources avec le tableau passé en argument
	public AgentIndiv(int idAgent, ArrayList<Ressource> ressources) throws RemoteException
	{
		super(idAgent, ressources);
	}
	
	//----------------------------------------------------------------------
	//				getter
	//----------------------------------------------------------------------
	
	//----------------------------------------------------------------------
	//				setters
	//----------------------------------------------------------------------
	
	//----------------------------------------------------------------------
	//				méthodes
	//----------------------------------------------------------------------
	
	/*
	 * Les fonctions suivantes n'ont pas besoin d'être redéfinies : 
	 * - demarrerTour
	 * - enregistrerCoordinateur
	 * - signalerNbAgentsEtProducteurs
	 * - enregistrerAgents
	 * - enregistrerProducteurs
	 * - surveillance
	 * - voler
	 * - terminerJeu
	 * 
	 * Les fonctions suivantes doivent être redéfinies :
	 * - choixAction
	 */
	 
	 
	 /*
	 * Fonction 	: choixAction
	 * Argument(s)	: /
	 * Résultat(s)	: /
	 * Commentaires	: choisi l'action à effectuer pour ce tour suivant la personnalité individualiste
	 * les actions possibles sont : acquérirRessources, voler, surveillance et reporterVol
	 */
	 public void choixAction() throws RemoteException
	 {
		/*
		 * Un agent individualiste aura tendance à tenter d'acquérir des ressources
		 * et à défaut de pouvoir acquérir des ressources, se mettra en quête de les voler (pas de surveillance)
		 */
		
		try
		{
			ArrayList<Ressource> copie = getRessources();	// copie des ressources de l'agent
			// parcours des ressources jusqu'à trouver une ressource dont l'objectif de quantité n'est pas atteint
			for (int i = 0; i < copie.size(); i++)
			{
				if (copie.get(i).getQuantiteRessource() < copie.get(i).getObjectifRessource())
				{
					System.out.println("Agent " + getIdAgent() + " : je choisi la ressource " + copie.get(i).getTypeRessource());
					// parcours des producteurs jusqu'à en trouver un qui produit la ressource recherchée
					for (int j = 0; j < getNbProducteurs(); j++)
					{
						// demander au producteur la ressource qu'il produit
						String typeProduit = getProducteurAtPos(j).observerTypeRessource(getIdAgent());
						int quantiteProduite = getProducteurAtPos(j).observerQuantiteRessource(getIdAgent());
						System.out.println("Agent " + getIdAgent() + " : le producteur " + j + " produit " + quantiteProduite + " de " + typeProduit);
						if (typeProduit.equals(copie.get(i).getTypeRessource()))
						{
							System.out.println("Agent " + getIdAgent() + " : je demande au producteur " + j);
							// acquérir cette ressource
							Ressource aAcquerir = copie.get(i);
							// on en demande autant que ce qui est disponible
							int ressourcesAcquises = getProducteurAtPos(j).attribuerRessources(aAcquerir.getTypeRessource(), quantiteProduite);
							System.out.println("Agent " + getIdAgent() + " : j'acquiers " + ressourcesAcquises + 
							" exemplaires de la ressource " + aAcquerir.getTypeRessource());
							// mettre à jour
							setRessourceByType(new Ressource(aAcquerir.getTypeRessource(), aAcquerir.getQuantiteRessource() + 
							ressourcesAcquises, aAcquerir.getObjectifRessource()));
							return;
						}
					}
					// sinon aucun producteur ne produit cette resource, on tente de voler la ressource au premier agent en ayant plus que 0
					for (int j = 0; j < getNbAgents(); j++)
					{
						int quantitePossedee = getAgentAtPos(j).observerRessourceParType(copie.get(i).getTypeRessource());
						System.out.println("Agent " + getIdAgent() + " : j'ai observé l'agent " + j + " et il possède " +
						quantitePossedee + " exemplaires de la resource " + copie.get(i).getTypeRessource());
						// si cette quantité est supérieure à 0 on vole ce qu'on peut
						if (quantitePossedee > 0)
						{
							Ressource aVoler = copie.get(i);
							// voler la ressource (autant que l'agent possède)
							int ressourcesVolees = getAgentAtPos(j).voler(getIdAgent(), aVoler.getTypeRessource(), quantitePossedee);
							System.out.println("Agent " + getIdAgent() + " : je vole " + ressourcesVolees + 
							" exemplaires de la ressource " + aVoler.getTypeRessource());
							// mettre à jour
							setRessourceByType(new Ressource(aVoler.getTypeRessource(), aVoler.getQuantiteRessource() + 
							ressourcesVolees, aVoler.getObjectifRessource()));
							return;
						}
					}
				}
				// si ce cas est atteint alors tous les objectifs sont atteints
				// on ne devrait pas être ici !!
				System.out.println("Pas de ressources à acquérir :(");
			}	
		}
		catch (RemoteException re) { System.out.println(re) ; }
	 }
	
	public static void main(String[] args) throws IOException
	{
		// au moins un triplet type quantite objectif en plus des deux arguments obligatoires
		if (args.length >= 5 && args.length%3 == 0)
		{
			System.out.println("Usage : java AgentHumain <port du rmiregistry> <idAgent> <typeRessource> <quantiteRessource> <objectif> (répéter le triplet)") ;
			System.exit(0) ;
		}
		try
		{
			Coordinateur coordinateur = (Coordinateur) Naming.lookup( "rmi://localhost:" + args[0] + "/coordinateur" );
			
			int idAgent = Integer.parseInt(args[1]);
			ArrayList<Ressource> ressources = new ArrayList<Ressource>();
			for (int i = 2; i < args.length; i+=3)
			{
				int quantiteRessource = Integer.parseInt(args[i+1]);
				int objectifRessource = Integer.parseInt(args[i+2]);
				Ressource ressource = new Ressource(args[i], quantiteRessource, objectifRessource);
				ressources.add(ressource);
			}
			AgentImpl objLocal;


			System.out.println("Je suis individuel");
			objLocal = new AgentIndiv(idAgent, ressources);

			// enregistrement du coordinateur pour l'agent
			objLocal.enregistrerCoordinateur("localhost", Integer.parseInt(args[0]));
			
			// s'enregistrer auprès du coordinateur (convention : port 9000)
			Naming.rebind( "rmi://localhost:" + args[0] + "/agent" + args[1] ,objLocal) ;
			coordinateur.identifierAgent(objLocal.getIdAgent());
		}
		catch (NotBoundException re) { System.out.println(re) ; }
		catch (RemoteException re) { System.out.println(re) ; }
		catch (MalformedURLException e) { System.out.println(e) ; }
	}
}
