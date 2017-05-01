import java.rmi.server.UnicastRemoteObject ;
import java.rmi.RemoteException ;
public class RessourceImpl extends UnicastRemoteObject implements Ressource
{
	//----------------------------------------------------------------------
	//				attributs
	//----------------------------------------------------------------------
	
	private String typeRessource;	// type de ressource
	private int quantiteRessource;	// nombre de cette ressource
	private int objectifRessource;	// objectif de quantité à atteindre
	
	//----------------------------------------------------------------------
	//				constructeur
	//----------------------------------------------------------------------
	
	// constructeur standard
	public RessourceImpl(String typeRessource, int quantiteRessource, int objectifRessource) throws RemoteException
	{
		this.typeRessource = typeRessource;
		this.quantiteRessource = quantiteRessource;
		this.objectifRessource = objectifRessource;
	}
	
	//----------------------------------------------------------------------
	//				getters
	//----------------------------------------------------------------------
	
	public String getTypeRessource()
	{
		return this.typeRessource;
	}
	
	public int getQuantiteRessource()
	{
		return this.quantiteRessource;
	}
	
	public int getObjectifRessource()
	{
		return this.objectifRessource;
	}
	
	//----------------------------------------------------------------------
	//				setters
	//----------------------------------------------------------------------
	
	public void setTypeRessoure(String typeRessource)
	{
		this.typeRessource = typeRessource;
	}
	
	public void setQuantiteRessource(int quantiteRessource)
	{
		this.quantiteRessource = quantiteRessource;
	}
	
	public void setObjectifRessource(int objectifRessource)
	{
		this.objectifRessource = objectifRessource;
	}
	
	//----------------------------------------------------------------------
	//				méthodes
	//----------------------------------------------------------------------
	
	// transmet une ressource
	public RessourceImpl transmettreRessource() throws RemoteException
	{
		return new RessourceImpl(this.typeRessource, this.quantiteRessource, 0);
	}
}
