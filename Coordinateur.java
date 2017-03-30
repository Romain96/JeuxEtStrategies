public class Coordinateur
{
	//----------------------------------------------------------------------
	//				attributs
	//----------------------------------------------------------------------
	
	int agents[][];		// liste associant les id des agents avec leur port respectif
	int nbAgents;		// taille du tableau agents (NxN)
	Ressource ressources[];	// liste des ressources possédées
	int tailleTab;		// nombre de ressources différentes (taille du tableau ressources)
	
	//----------------------------------------------------------------------
	//				cosntructeur
	//----------------------------------------------------------------------
	
	public Coordinateur(int nbAgents, Ressource ressouces[])
	{
		this.agents = new int[nbAgents][nbAgents];	// les agents s'identifiront au coordinateur à l'initialisation
		this.nbAgents = nbAgents;
		this.ressources = ressources;
		this.tailleTab = ressources.length();
	}
	
	//----------------------------------------------------------------------
	//				getters
	//----------------------------------------------------------------------
	
	// retourne le nombre d'agents
	public int getNbAgents()
	{
		return nbAgents;
	}
	
	// retourne le nombre de ressources
	public int getNbRessources()
	{
		return tailleTab;
	}
	
	// retourne le nombre courant de la ressouce type ou -1 si non trouvée
	public int getQuantiteRessource(String type)
	{
		for (int i = 0; i < tailleTab; i++)
		{
			if (ressources[i].getType() == type)
			{
				return ressources[i].getNb();
			}
		}
		return -1;	// non trouvé
	}
	
	//----------------------------------------------------------------------
	//				setters
	//----------------------------------------------------------------------
	
	// positionne le nombre d'agents
	public void setNbAgents(int nbAgents)
	{
		this.nbAgents = nbAgents;
	}
	
	// positionne le nombre de ressources
	public void setNbResources(int nbRessources)
	{
		this.tailleTab = nbRessources;
	}
	
	// positionne le nombre de ressources de la ressource type
	public void setQuantiteRessource(String type, int nb)
	{
		for (int i = 0; i < tailleTab; i++)
		{
			if(ressources[i].getType() == type)
			{
				ressources[i].setNb(nb);
			}
		}
	}
	
	//----------------------------------------------------------------------
	//				methodes
	//----------------------------------------------------------------------
	
	
}