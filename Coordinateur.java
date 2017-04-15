import java.rmi.Remote ; 
import java.rmi.RemoteException ; 

public interface Coordinateur extends Remote
{
	// appelé par les agents pour s'enregistrer auprès du coordinateur
	public void identifierAgent(int idAgent) throws RemoteException;
	
	// appelé par les producteurs pour s'enregistrer auprès du coordinateur
	public void identifierProducteur(int idProducteur) throws RemoteException;
	
	// appelé par les agents pour signaler la fin de leur tour
	public void signalerFinTour(int idAgent) throws RemoteException;
}
