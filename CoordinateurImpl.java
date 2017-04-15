import java.rmi.server.UnicastRemoteObject ;
import java.rmi.RemoteException ;

public class CoordinateurImpl extends UnicastRemoteObject implements Coordinateur
{
	//----------------------------------------------------------------------
	//				attributs
	//----------------------------------------------------------------------
	
	// le coordinateur connait tous les agents
	int agents[];				// liste des id des agents
	int nbAgents;				// taille du tableau des id des agents
	int nbAgentsEnregistres;	// nombre d'agents s'étant identifiés au coordinateur
	
	// le coordinateur connait tous les producteurs
	int producteurs[];				// liste des id des producteurs
	int nbProducteurs;				// taille du tableau producteurs
	int nbProducteursEnregistres;	// nombre de producteurs s'étant identifiés au coordinateur
	
	Ressource ressources;		// liste des ressources possédées
	
	//----------------------------------------------------------------------
	//				cosntructeur
	//----------------------------------------------------------------------
	
	public CoordinateurImpl(int nbAgents, int nbProducteurs, Ressource ressources) throws RemoteException
	{
		// initialisation du tableau d'agents
		this.agents = new int[nbAgents];	// les agents s'identifiront au coordinateur à l'initialisation
		this.nbAgents = nbAgents;
		this.nbAgentsEnregistres = 0;		// aucun agent enregistré pour l'instant (le coordinateur est lancé en premier)
		
		// initialisation du tableau des producteurs
		this.producteurs = new int[nbProducteurs];	// les producteurs s'identifiront au coordinateur à l'initialisation
		this.nbProducteurs = nbProducteurs;
		this.nbProducteursEnregistres = 0;			// aucun producteur enregistré pour l'instant (le coordinateur est lancé en premier)
		
		// initialisation des ressources
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
	
	// retourne le nombre de producteurs
	public int getNbProducteurs()
	{
		return nbProducteurs;
	}
	
	// retourne le nombre d'agents enregistrés auprès du coordinateur
	public int getNbAgentsEnregistres()
	{
		return nbAgentsEnregistres;
	}
	
	// retourne le nombre de producteurs enregistrés auprès du coordinateur
	public int getNbProducteursEnregistres()
	{
		return nbProducteursEnregistres;
	}
	
	// retourne le nombre courant de la ressouce type ou -1 si non trouvée
	public int getQuantiteRessource()
	{
		return ressources.getQuantite();
	}
	
	
	//----------------------------------------------------------------------
	//				setters
	//----------------------------------------------------------------------
	
	// positionne le nombre d'agents
	public void setNbAgents(int nbAgents)
	{
		this.nbAgents = nbAgents;
	}
	
	// positionne le nombre de producteurs
	public void setNbProducteurs(int nbProducteurs)
	{
		this.nbProducteurs = nbProducteurs;
	}
	
	// positionne le nombre d'agents enregistrés
	public void setNbAgentsEnregistres(int nbAgentsEnregistres)
	{
		this.nbAgentsEnregistres = nbAgentsEnregistres;
	}
	
	// positionne le nombre de producteurs enregistrés
	public void setNbProducteursEnregistres(int nbProducteursEnregistres)
	{
		this.nbProducteursEnregistres = nbProducteursEnregistres;
	}
	
	// positionne le nombre de ressources de la ressource type
	public void setQuantiteRessource(int quantite)
	{
		ressources.setQuantite(quantite);
	}
	
	//----------------------------------------------------------------------
	//				methodes
	//----------------------------------------------------------------------
	
	// appelé par les agents pour s'enregistrer auprès du coordinateur
	public void identifierAgent(int idAgent) throws RemoteException
	{
		if (nbAgentsEnregistres < nbAgents )
		{
			System.out.println("Coordinateur : agent " + idAgent + " s'identifie" );
			agents[nbAgentsEnregistres] = idAgent;
			nbAgentsEnregistres++;
			System.out.println("Coordinateur : il y a désormais " + nbAgentsEnregistres + "/" + nbAgents + " agents enregistrés" );
		}
		else
		{
			System.out.println("Coordinateur : agent " + idAgent + " ne peut pas s'identifier : la liste est pleine !" );
		}
	}
	
	// appelé par les producteurs pour s'enregistrer auprès du coordinateur
	public void identifierProducteur(int idProducteur) throws RemoteException
	{
		if (nbProducteursEnregistres < nbProducteurs )
		{
			System.out.println("Coordinateur : producteur " + idProducteur + " s'identifie" );
			producteurs[nbProducteursEnregistres] = idProducteur;
			nbProducteursEnregistres++;
			System.out.println("Coordinateur : il y a désormais " + nbProducteursEnregistres + "/" + nbProducteurs + " producteurs enregistrés" );
		}
		else
		{
			System.out.println("Coordinateur : producteur " + idProducteur + " ne peut pas s'identifier : la liste est pleine !" );
		}
	}
	
	// appelé par les agents pour signaler la fin de leur tour
	public void signalerFinTour(int idAgent) throws RemoteException
	{
		System.out.println("Coordinateur : agent " + idAgent + " signale la fin de son tour" );
	}
}
