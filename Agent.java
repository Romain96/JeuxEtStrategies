import java.rmi.Remote ; 
import java.rmi.RemoteException ; 

public interface Agent extends Remote
{
    

	
	//----------------------------------------------------------------------
	//				getters
	//----------------------------------------------------------------------
	
	public int getIdAgent() throws RemoteException;
	
	
	public int getTailleTab() throws RemoteException;
	
	
	// retourne la quantité de ressouces de la ressource passée en paramètre
	public int getNbRessource() throws RemoteException;

	
	// retourne l'objectif à atteindre pour la ressource passée en paramètre
	public int getObjectif() throws RemoteException;
	
	
	// retourne le numéro de port associé à cet agent
	public int getPort() throws RemoteException;
	
	
	
	//----------------------------------------------------------------------
	//				setters
	//----------------------------------------------------------------------
	
	public void setIdAgent(int idAgent) throws RemoteException;
	
	
	public void setTailleTab(int tailleTab) throws RemoteException;
	
	
	// positionne la ressource type avec la quantité nb
	public void setNbRessource( int nb) throws RemoteException;

	
	// positionne l'objectif de ressources pour le type passé en paramètre
	public void setObjectif( int nb) throws RemoteException;
	
	
	// positionne le numéro de port utilisé par cet agent
	public void setPort(int port) throws RemoteException;
	
	
	
	//----------------------------------------------------------------------
	//				methodes
	//----------------------------------------------------------------------
	
	// permet de démarrer le tour de l'agent
	public void demarrerTour() throws RemoteException;
	
	
	// permet d'indiquer au coordinateur que l'agent a terminé son tour
	public void finirTour() throws RemoteException;
	
	
	// permet à l'agent d'acquérir les ressources demandées
	public void acquerirRessources(String type, int nb) throws RemoteException;
	
	
	
	// permet à l'agent de surveiller ses biens
	public void surveillance() throws RemoteException;
	
	
	
	// permet à l'agent de tenter de voler un autre agent
	public void voler(int idAgent, String type, int nb) throws RemoteException;
	
	
    
    
}

