import java.rmi.server.UnicastRemoteObject ;
import java.rmi.RemoteException ;
import java.rmi.* ; 
import java.net.MalformedURLException ; 

public abstract class AgentImpl 
  extends UnicastRemoteObject
  implements Agent  
{
	//----------------------------------------------------------------------
	//				attributs
	//----------------------------------------------------------------------
	
	private int idAgent;			// identifiant unique de l'agent 
	private String typeRessource;	// type de ressource acquise
	private int quantiteRessource;	// quantité de cette ressource acquise
	private int objectif;			// quantité de ressouce ciblée
	
	private Producteur producteurs[];	// liste des producteurs
	private int nbProducteurs;			// nombre de producteurs dans la liste
	
	private Agent agents[];		// liste des agents sauf soi
	private int nbAgents;		// nombre des agents dans la liste
	
	private Coordinateur coordinateur;
	
	//----------------------------------------------------------------------
	//				constructeur
	//----------------------------------------------------------------------
	
	public AgentImpl(int idAgent, String typeRessource, int quantiteRessource, int objectif) throws RemoteException
	{
		this.idAgent = idAgent;
		this.typeRessource = typeRessource;
		this.quantiteRessource = quantiteRessource;
		this.objectif = objectif;
		this.nbProducteurs = 0;
		this.nbAgents = 0;
		// DEBUG
		System.out.println("Agent init : " + idAgent + " " + typeRessource + " " + quantiteRessource + " " + objectif );	
	}

	//----------------------------------------------------------------------
	//				getters
	//----------------------------------------------------------------------
	
	public int getIdAgent()
	{
		return this.idAgent;
	}
	
	public int getQuantiteRessource()
	{
		return quantiteRessource;
	}
	
	public int getObjectif()
	{
		return objectif;
	}
	
	public int getNbProducteurs()
	{
		return nbProducteurs;
	}
	
	//----------------------------------------------------------------------
	//				setters
	//----------------------------------------------------------------------
	
	public void setIdAgent(int idAgent)
	{
		this.idAgent = idAgent;
	}
	
	public void setTypeRessource(String typeRessource)
	{
		this.typeRessource = typeRessource;
	}
	
	
	// positionne la ressource type avec la quantité nb
	public void setQuantiteRessource(int quantite)
	{
		this.quantiteRessource = quantite;
	}
	
	// positionne l'objectif de ressources pour le type passé en paramètre
	public void setObjectif( int nb)
	{
		this.objectif = nb;
	}
	
	public void setNbProducteurs(int nbProducteurs)
	{
		this.nbProducteurs = nbProducteurs;
	}
	
	//----------------------------------------------------------------------
	//				methodes
	//----------------------------------------------------------------------
	
	// permet de démarrer le tour de l'agent
	public void demarrerTour() throws RemoteException
	{
		System.out.println("Agent " + idAgent + " : je commence mon tour");
		coordinateur.signalerFinTour(this.idAgent);
	}
	
	// appelé par le coordinateur pour transmettre le nombre d'agents/producteurs
	public void signalerNbAgentsEtProducteurs(int nbAgents, int nbProucteurs) throws RemoteException
	{
		System.out.println("Agent " + this.idAgent + " enregistre ses agents/producteurs");
		
		this.agents = new Agent[nbAgents - 1];
		this.nbAgents = nbAgents - 1;
		this.producteurs = new Producteur[nbProducteurs];
		this.nbProducteurs = nbProducteurs;
		
		try 
		{
			Coordinateur coordinateur = (Coordinateur) Naming.lookup( "rmi://localhost:9000/coordinateur");
			this.coordinateur = coordinateur;
		}
		catch (NotBoundException re) { System.out.println(re) ; }
		catch (RemoteException re) { System.out.println(re) ; }
		catch (MalformedURLException e) { System.out.println(e) ; }
		
		enregistrerAgents(nbAgents);
		enregistrerProducteurs(nbProducteurs);
	}
	
	// permet d'enregistrer tous les agents sauf soi
	public void enregistrerAgents(int nbAgents)
	{
		try 
		{
			// agent avant soi 
			for (int i = 0; i < this.idAgent; i++)
			{
				Agent agent = (Agent) Naming.lookup( "rmi://localhost:9000/agent" + i );
			}
			// agents après soi
			for (int i = idAgent + 1; i < nbAgents; i++)
			{
				Agent agent = (Agent) Naming.lookup( "rmi://localhost:9000/agent" + i );
			}
		}
		catch (NotBoundException re) { System.out.println(re) ; }
		catch (RemoteException re) { System.out.println(re) ; }
		catch (MalformedURLException e) { System.out.println(e) ; }
	}
	
	// permet d'enregistrer tous les producteurs
	public void enregistrerProducteurs(int nbProducteurs)
	{
		try 
		{
			for (int i = 0; i < nbProducteurs; i++)
			{
				Producteur producteur = (Producteur) Naming.lookup( "rmi://localhost:9000/producteur" + i );
			}
		}
		catch (NotBoundException re) { System.out.println(re) ; }
		catch (RemoteException re) { System.out.println(re) ; }
		catch (MalformedURLException e) { System.out.println(e) ; }
	}
	
	// permet d'indiquer au coordinateur que l'agent a terminé son tour
	public void finirTour()
	{
		System.out.println("Agent " + idAgent + " : j'ai terminé mon tour");
	}
	
	// permet à l'agent d'acquérir les ressources demandées
	public void acquerirRessources(String type, int nb)
	{
		System.out.println("Agent " + idAgent + " : j'acquiers " + nb + " exemplaires de la ressource " + type);
	}
	
	// permet à l'agent de surveiller ses biens
	public void surveillance()
	{
		System.out.println("Agent " + idAgent + " : je me place en mode NSA");
	}
	
	// permet à l'agent de tenter de voler un autre agent
	//public void voler(int idAgent, String type, int nb) throws RemoteException
	//{
	//	System.out.println("Agent " + this.idAgent + " : je tente de voler " + nb + " exemplaires de la ressource " + type + " à l'agent " + idAgent);
	//}

	// appelé par le cordinateur à la fin du jeu pour terminer l'agent
	public void terminerJeu() throws RemoteException
	{
		System.out.println("Agent " + idAgent + " se termine");
		System.exit(0);
	}
}

