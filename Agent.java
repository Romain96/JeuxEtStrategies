import java.rmi.Remote ; 
import java.rmi.RemoteException ; 
import java.util.*;

public interface Agent extends Remote
{
	// appelé par le coordinateur pour demander à l'agent de démarrer son tour
	public void demarrerTour() throws RemoteException;
	
	// appelé par le coordinateur pour transmettre le nombre d'agents/producteurs
	public void signalerNbAgentsEtProducteurs(int nbAgents, int nbProducteurs) throws RemoteException; 
	
	// appelé par le cordinateur à la fin du jeu pour terminer l'agent
	public void terminerJeu() throws RemoteException;
	
	// permet à l'agent de tenter de voler un autre agent
	public int voler(int idAgent, String typeRessource, int quantiteRessource) throws RemoteException;
	
	// permet à un agent de connaître les ressources possédées par un autre agent
	public int observerRessourceParType(String typeRessource) throws RemoteException;
}

