import java.rmi.server.UnicastRemoteObject ;
import java.rmi.RemoteException ;

public class ProducteurImpl extends UnicastRemoteObject implements Producteur
{  
	//==================================================================
	//							Attributs
	//==================================================================
	
	private int idProducteur;		// identifiant unique d'un producteur
	private String typeRessource;	// pour l'instant un seule ressource
	private int quantiteRessource;	// idem
	
	//==================================================================
	//							Constructeur
	//==================================================================
	
	public ProducteurImpl(int idProducteur, String typeRessource, int quantiteRessource) throws RemoteException
	{
		this.idProducteur = idProducteur;
		this.typeRessource = typeRessource;
		this.quantiteRessource = quantiteRessource;
		// DEBUG
		System.out.println("Producteur init : " + idProducteur  + " " + typeRessource + " " + quantiteRessource );
	}
	
	//==================================================================
	//							Getters
	//==================================================================
	
	public int getIdProducteur()
	{
		return this.idProducteur;
	}
	
	public String getTypeRessource()
	{
		return typeRessource;
	}
	
	public int getQuantiteRessource()
	{
		return quantiteRessource;
	}
	
	//==================================================================
	//							Setters
	//==================================================================
	
	public void setIdProducteur(int idProducteur)
	{
		this.idProducteur = idProducteur;
	}
	
	public void setTypeRessource(String typeRessource)
	{
		this.typeRessource = typeRessource;
	}
	
	public void setQuantiteRessource(int quantiteRessource)
	{
		this.quantiteRessource = quantiteRessource;
	}
	
	//==================================================================
	//							Méthodes
	//==================================================================
	
	// méthode appelée par un agent pour récupérer des ressources
	public int attribuerRessources(int quantiteDemandee) throws RemoteException
	{
		int quantiteAttribuee = 0;
		// si la quantité demandée est supérieure à la quantité disponible
		// l'agent ne reçoit que le maximum disponible
		if (this.quantiteRessource <= quantiteDemandee)
		{
			quantiteAttribuee = this.quantiteRessource;
			this.quantiteRessource -= quantiteDemandee;
			return quantiteAttribuee;
		}
		else
		{
			quantiteAttribuee = quantiteDemandee;
			this.quantiteRessource -= quantiteDemandee;
			return quantiteAttribuee;
		}
	}
	
	// génération des ressources
	public void genererRessources()
	{
		// très simple pour l'instant on double ne nombre restant (si > 0)
		if (this.quantiteRessource > 0)
		{
			this.quantiteRessource *= 2;
		}
		else
		{
			this.quantiteRessource = 100;	// nombre arbitraire
		}
	}
	
	// appelé par le cordinateur à la fin du jeu pour terminer le producteur
	public void terminerJeu() throws RemoteException
	{
		System.out.println("Producteur " + idProducteur + " se termine" );
		System.exit(0);
	}
}
