import java.rmi.Remote ; 
import java.rmi.RemoteException ; 

public interface Producteur extends Remote
{
	// appelé par les agents pour acquérir des ressources
	public int attribuerRessources(int quantiteDemandee) throws RemoteException;
}
