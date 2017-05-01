import java.rmi.Remote ; 
import java.rmi.RemoteException ; 

public interface Producteur extends Remote
{
	// appelé par le cordinateur à la fin du jeu pour terminer le producteur
	public void terminerJeu() throws RemoteException;
	
	// appelé par les agents pour acquérir des ressources
	public int attribuerRessources(String typeRessource, int quantiteDemandee) throws RemoteException;
	
	// appelé par les agents pour observer le producteur (type de ressource disponible)
	public String observerTypeRessource(int idAgent) throws RemoteException;
	
	// appelé par les agents pour observer le producteur (quantité de ressource disponible)
	public int observerQuantiteRessource(int idAgent) throws RemoteException;
}
