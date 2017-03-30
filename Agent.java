public abstract class Agent
{
	//----------------------------------------------------------------------
	//				attributs
	//----------------------------------------------------------------------
	
	private int idAgent;			// identifiant unique de l'agent 
	private Ressource ressources[];		// tableau des différentes ressources et leurs quantités possédées
	private Ressource objectifs[];		// associe à chaque ressource le nombre à obtenir pour terminer
	private int tailleTab;			// taille du tableau de ressources
	private int port;			// numéro de port pour servir de serveur
	
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
		for (int i = 0; i < tailleTab; i++)
		{
			if (objectifs[i].getType() == type)
			{
				return objectifs[i].getNb();
			}
		}
		return -1;	// une quantité négative représente une ressource non existante 
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
	public void setNbRessource(String type, int nb)
	{
		boolean trouve = false;
		for (int i = 0; i < tailleTab && !trouve; i++)
		{
			if (ressources[i].getType == type)
			{
				ressources[i].setNb(nb);
				trouve = true;	// stop
			}
		}
	}
	
	// positionne l'objectif de ressources pour le type passé en paramètre
	public void setObjectif(String type, int nb)
	{
		boolean trouve = false;
		for (int i = 0; i < tailleTab && !trouve; i++)
		{
			if (objectifs[i].getType == type)
			{
				objectifs[i].setNb(nb);
				trouve = true;	// stop
			}
		}
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