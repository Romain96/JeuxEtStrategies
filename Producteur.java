import java.rmi.Remote ; 
import java.rmi.RemoteException ; 

public interface Producteur extends Remote
{
	// appelé par le cordinateur à la fin du jeu pour terminer le producteur
	public void terminerJeu() throws RemoteException;
	
	// appelé par les agents pour acquérir des ressources
	public int attribuerRessources(String typeRessource, int quantiteDemandee) throws RemoteException;
	
	// appelé par les agents pour observer le producteur (type et quantité de ressource disponible)
	public Ressource observer(int idAgent) throws RemoteException;
}
