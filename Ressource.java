import java.rmi.Remote ; 
import java.rmi.RemoteException ; 

public interface Ressource extends Remote
{	
	// transmet une ressource
	public RessourceImpl transmettreRessource() throws RemoteException;
}
