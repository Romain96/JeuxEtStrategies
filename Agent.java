import java.rmi.Remote ; 
import java.rmi.RemoteException ; 

public interface Agent extends Remote
{
	// permet à l'agent de tenter de voler un autre agent
	//public void voler(int idAgent, String type, int nb) throws RemoteException;
}

