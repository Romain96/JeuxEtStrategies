import java.rmi.server.UnicastRemoteObject ;
import java.rmi.RemoteException ;
import java.rmi.* ; 
import java.net.MalformedURLException ; 
import java.util.Scanner; 
import java.util.*;

public class AgentHumain extends AgentImpl
{
	//----------------------------------------------------------------------
	//				attributs
	//----------------------------------------------------------------------
	
	// hérités de Agent
	private Scanner scanner;	// scanner pour la lecture des input utilisateur
	
	//----------------------------------------------------------------------
	//				Constructeur
	//----------------------------------------------------------------------
	
	// initialise un agent humain avec un id et un numéro de port pour
	// permettre d'agir comme serveur pour les autres agents et pour le coordinateur
	// et initialise ses objectifs de ressources avec le tableau passé en argument
	public AgentHumain(int idAgent, ArrayList<Ressource> ressources) throws RemoteException
	{
		super(idAgent, ressources);
		scanner = new Scanner(System.in);
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
	 * Commentaires	: agit comme un interpréteur pour le joueur humain
	 */
	public void choixAction() throws RemoteException
	{
		/*
		 * un agent humain n'a pas de comportement programmé, il appartient au joueur
		 * seul de choisir l'action à réaliser
		 */
		
		String input = "dummy";
		// boucle tant que la commande est incorrecte
		
		while (!verificationInput(input))
		{
			System.out.println("Choisir une action\n" + "Commandes :\n" + 
			"a [idProducteur] [typeRessource] [quantiteRessource] : acquérir quantiteRessource ressources de la ressource typeRessource chez le producteur idProducteur\n" + 
			"v [idAgent] [typeRessource] [quantiteRessource] : voler quantiteRessource ressources de la ressource typeRessource à l'agent idAgent\n" +
			"o a [idAgent] : observer l'agent idAgent\n" +
			"o p [idProducteur] : observer le producteur idProducteur\n" +
			"s se mettre en surveillance jusqu'au tour suivant\n");
			input = this.scanner.nextLine();	// ligne de commande
			System.out.println("input : " + input);
		}
	}
	
	 /*
	 * Fonction 	: verificationSyntaxiqueInput
	 * Argument(s)	: la chaine entrée par le joueur humain
	 * Résultat(s)	: vrai si la chaine est correcte, faux sinon
	 * Commentaires	: vérifie la syntaxe de la commande ainsi que 
	 * la validité des arguments donnés
	 */
	public boolean verificationInput(String input)
	{
		String mots[] = input.split(" ");	// sépare les mots pour les compter et le analyser
		
		switch (mots.length)
		{
			case 1:	
			// seul cas autorisé : "s" (surveillance)
			if (mots[0].equals("s"))
			{
				return true;	// syntaxe valide et pas d'arguments
			}
			else
			{
				return false;
			}
			
			case 3:
			// deux cas autorisés : "o a [idAgent]" (observer un agent) 
			// ou "o p [idProducteur]" (observer un producteur)
			if (mots[0].equals("o"))
			{
				// vérifiaction avec l'argument agent
				if (mots[1].equals("a"))
				{
					// syntaxe correcte, vérification de la validité de l'argument
					int nbAgentsArg = Integer.parseInt(mots[2]);
					if (nbAgentsArg < getNbAgents() && nbAgentsArg >= 0)
					{
						// syntaxe correcte et argument valide
						return true;
					}
					else
					{
						// syntaxe correcte et argument invalide
						return false;
					}
				}
				// vérification avec l'argument producteur
				else if (mots[1].equals("p"))
				{
					// syntaxe correcte, vérification de la validité de l'argument
					int nbProducteurArg = Integer.parseInt(mots[2]);
					if (nbProducteurArg < getNbProducteurs() && nbProducteurArg >= 0)
					{
						// syntaxe correcte et argument valide
						return true;
					}
					else
					{
						// syntaxe correcte et argument invalide
						return false;
					}
				}
				else
				{
					// syntaxe fausse (2e mot)
					return false;
				}
			}
			else
			{
				// syntaxe fausse (1er mot)
				return false;
			}
			
			case 4:
			// cas autorisés : "a [idProducteur] [typeRessource] [quantiteRessource]" (acquérirRessource) 
			// et "v [idProducteur/idAgent] [typeRessource] [quantiteRessource]" (voler)
			if (mots[0].equals("a"))
			{
				// vérification de la commande d'acquisition de ressources (sauf le typeRessource)
				int idProducteurArg = Integer.parseInt(mots[1]);
				int quantiteRessourceArg = Integer.parseInt(mots[3]);
				if (idProducteurArg >= 0 && idProducteurArg <= getNbProducteurs() && quantiteRessourceArg >= 0)
				{
					// syntaxe correcte et arguments valides
					return true;
				}
				else
				{
					// syntaxe correcte et arguments invalides
					return false;
				}
			}
			else if (mots[0].equals("v"))
			{
				// véridication de la commande de vol de ressources
				int idAgentArg = Integer.parseInt(mots[1]);
				int quantiteRessourceArg = Integer.parseInt(mots[3]);
				if (idAgentArg > 0 && idAgentArg <= getNbAgents() && idAgentArg != getIdAgent() && quantiteRessourceArg >= 0)
				{
					// syntaxe correcte et arguments valides
					return true;
				}
				else
				{
					// syntaxe correcte et arguments invalides
					return false;
				}
			}
			else
			{
				// syntaxe fausse (1er mot)
				return false;
			}
			default:
				return false;
		}
	}
	
	// tests
	public static void main(String[] args) throws RemoteException
	{	
		// au moins un triplet type quantite objectif en plus des deux arguments obligatoires
		if (args.length >= 5 && args.length%3 == 0)
		{
			System.out.println("Usage : java AgentHumain <port du rmiregistry> <idAgent> <typeRessource> <quantiteRessource> <objectif> (répéter le triplet)") ;
			System.exit(0);
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


			System.out.println("Je suis humain");
			objLocal = new AgentHumain(idAgent, ressources);

			// enregistrement du coordinateur pour l'agent
			objLocal.enregistrerCoordinateur("localhost", Integer.parseInt(args[0]));
			
			// s'enregistrer auprès du coordinateur (convention : port 9000)
			Naming.rebind( "rmi://localhost:" + args[0] + "/agent" + args[1] ,objLocal) ;
			coordinateur.identifierAgent(objLocal.getIdAgent());
			
			// test
			objLocal.choixAction();
		}
		catch (NotBoundException re) { System.out.println(re) ; }
		catch (RemoteException re) { System.out.println(re) ; }
		catch (MalformedURLException e) { System.out.println(e) ; }
	}
}
