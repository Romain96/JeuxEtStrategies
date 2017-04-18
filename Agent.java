import java.rmi.Remote ; 
import java.rmi.RemoteException ; 

public interface Agent extends Remote
{
	// appelé par le coordinateur pour demander à l'agent de démarrer son tour
	public void demarrerTour() throws RemoteException;
	
	// appelé par le coordinateur pour transmettre le nombre d'agents/producteurs
	public void signalerNbAgentsEtProducteurs(int nbAgents, int nbProucteurs) throws RemoteException; 
	
	// appelé par le cordinateur à la fin du jeu pour terminer l'agent
	public void terminerJeu() throws RemoteException;
	
	// permet à l'agent de tenter de voler un autre agent
	//public void voler(int idAgent, String type, int nb) throws RemoteException;
}

