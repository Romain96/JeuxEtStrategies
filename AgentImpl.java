import java.rmi.server.UnicastRemoteObject ;
import java.rmi.RemoteException ;

public class AgentImpl 
  extends UnicastRemoteObject
  implements Agent
{  
	    //----------------------------------------------------------------------
	//				attributs
	//----------------------------------------------------------------------
	
	private int idAgent;			// identifiant unique de l'agent 
	private int tailleTab;			// taille du tableau de ressources
	private int port;				// numéro de port pour servir de serveur
	private Ressource ressources;	// nb de ressource aquises
	private Ressource objectifs;	// nb de ressource necessaires
	
	/*	Pour apres
	private Ressource ressources[];		// tableau des différentes ressources et leurs quantités possédées
	private Ressource objectifs[];		// associe à chaque ressource le nombre à obtenir pour terminer
	*/
	
	
	//----------------------------------------------------------------------
	//				getters
	//----------------------------------------------------------------------
	
	public int getIdAgent()
	{
		return this.idAgent;
	}
	
	public int getTailleTab()
	{
		return this.tailleTab;
	}
	
	/* pour apres
	// retourne la quantité de ressouces de la ressource passée en paramètre
	public int getNbRessource(String type)
	{
		for (int i = 0; i < tailleTab; i++)
		{
			if (ressources[i].getType() == type)
			{
				return ressources[i].getNb();
			}
		}
		return -1;	// une quantité négative représente une ressource non existante 
	}
	
	// retourne l'objectif à atteindre pour la ressource passée en paramètre
	public int getObjectif(String type)
	{
		
	}
	*/ 
	
	public int getNbRessource()
	{
		return ressources;
	}
	
	public int getObjectif(String type)
	{
		return objectifs;
	}
	
	// retourne le numéro de port associé à cet agent
	public int getPort()
	{
		return port;
	}
	
	//----------------------------------------------------------------------
	//				setters
	//----------------------------------------------------------------------
	
	public void setIdAgent(int idAgent)
	{
		this.idAgent = idAgent;
	}
	
	public void setTailleTab(int tailleTab)
	{
		this.tailleTab = tailleTab;
	}
	
	// positionne la ressource type avec la quantité nb
	public void setNbRessource( int nb)
	{
		ressources.setNb(nb);
	}
	
	// positionne l'objectif de ressources pour le type passé en paramètre
	public void setObjectif( int nb)
	{
		objectifs.setNb(nb);
	}
	
	// positionne le numéro de port utilisé par cet agent
	public void setPort(int port)
	{
		this.port = port;
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
	public void voler(int idAgent, String type, int nb)
	{
		System.out.println("Agent " + this.idAgent + " : je tente de voler " + nb + " exemplaires de la ressource " + type + " à l'agent " + idAgent);
	}
  
  
}
