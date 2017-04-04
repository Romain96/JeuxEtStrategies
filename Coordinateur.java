import java.rmi.Remote ; 
import java.rmi.RemoteException ; 

public interface Coordinateur extends Remote
{
	// appelé par les agents pour s'enregistrer auprès du coordinateur
	public void identifierAgent(int idAgent, int numeroPort) throws RemoteException;
	
	// appelé par les agents pour signaler la fin de leur tour
	public int signalerFinTour() throws RemoteException;
}
