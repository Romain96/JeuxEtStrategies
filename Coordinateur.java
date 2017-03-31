public class Coordinateur
{
	//----------------------------------------------------------------------
	//				attributs
	//----------------------------------------------------------------------
	
	int agents[][];		// liste associant les id des agents avec leur port respectif
	int nbAgents;		// taille du tableau agents (NxN)
	Ressource ressources;	// liste des ressources possédées
	
	//----------------------------------------------------------------------
	//				cosntructeur
	//----------------------------------------------------------------------
	
	public Coordinateur(int nbAgents, Ressource ressources)
	{
		this.agents = new int[nbAgents][nbAgents];	// les agents s'identifiront au coordinateur à l'initialisation
		this.nbAgents = nbAgents;
		this.ressources = ressources;
	}
	
	//----------------------------------------------------------------------
	//				getters
	//----------------------------------------------------------------------
	
	// retourne le nombre d'agents
	public int getNbAgents()
	{
		return nbAgents;
	}
	
	
	// retourne le nombre courant de la ressouce type ou -1 si non trouvée
	public int getQuantiteRessource()
	{
		return ressources.getNb();
	}
	
	//----------------------------------------------------------------------
	//				setters
	//----------------------------------------------------------------------
	
	// positionne le nombre d'agents
	public void setNbAgents(int nbAgents)
	{
		this.nbAgents = nbAgents;
	}
	
	// positionne le nombre de ressources de la ressource type
	public void setQuantiteRessource(int nb)
	{
		ressources.setNb(nb);
	}
	
	//----------------------------------------------------------------------
	//				methodes
	//----------------------------------------------------------------------
	
	
}
