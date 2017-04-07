import java.rmi.server.UnicastRemoteObject ;
import java.rmi.RemoteException ;

public class CoordinateurImpl extends UnicastRemoteObject implements Coordinateur
{
	//----------------------------------------------------------------------
	//				attributs
	//----------------------------------------------------------------------
	
	int agents[][];				// liste associant les id des agents avec leur port respectif
	int nbAgents;				// taille du tableau agents (N lignes contenant 2 colonnes qui sont l'id et le port)
	int nbAgentsEnregistres;	// nombre d'agents s'étant identifiés au coordinateur
	Ressource ressources;		// liste des ressources possédées
	
	//----------------------------------------------------------------------
	//				cosntructeur
	//----------------------------------------------------------------------
	
	public CoordinateurImpl(int nbAgents, Ressource ressources) throws RemoteException
	{
		this.agents = new int[nbAgents][2];	// les agents s'identifiront au coordinateur à l'initialisation
		this.nbAgents = nbAgents;
		this.nbAgentsEnregistres = 0;	// aucun agent enregistré pour l'instant (le coordinateur est lancé en premier)
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
	
	// retourne le nombre d'agents enregistrés auprès du coordinateur
	public int getNbAgentsEnregistres()
	{
		return nbAgentsEnregistres;
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
	
	// positionne le nombre d'agents enregistrés
	public void setNbAgentsEnregistres(int nbAgentsEnregistres)
	{
		this.nbAgentsEnregistres = nbAgentsEnregistres;
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
	public void identifierAgent(int idAgent, int numeroPort) throws RemoteException
	{
		System.out.println("Coordinateur : agent " + idAgent + "de port " + numeroPort + " s'identifie" );
		agents[nbAgentsEnregistres][0] = idAgent;
		agents[nbAgentsEnregistres][1] = numeroPort;
		nbAgentsEnregistres++;
		System.out.println("Coordinateur : il y a désormais " + nbAgentsEnregistres + "/" + nbAgents + " agents enregistrés" );
	}
	
	// appelé par les agents pour signaler la fin de leur tour
	public void signalerFinTour(int idAgent, int numeroPort) throws RemoteException
	{
		System.out.println("Coordinateur : agent " + idAgent + "de port " + numeroPort + " signale la fin de son tour" );
	}
}
