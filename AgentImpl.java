import java.rmi.server.UnicastRemoteObject ;
import java.rmi.RemoteException ;

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
	
	//----------------------------------------------------------------------
	//				constructeur
	//----------------------------------------------------------------------
	
	public AgentImpl(int idAgent, String typeRessource, int quantiteRessource, int objectif) throws RemoteException
	{
		this.idAgent = idAgent;
		this.typeRessource = typeRessource;
		this.quantiteRessource = quantiteRessource;
		this.objectif = objectif;
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
	
	//----------------------------------------------------------------------
	//				methodes
	//----------------------------------------------------------------------
	
	// permet de démarrer le tour de l'agent
	public void demarrerTour()
	{
		System.out.println("Agent " + idAgent + " : je commence mon tour");
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

