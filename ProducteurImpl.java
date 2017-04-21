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
	
	// retourne l'id du producteur
	public int getIdProducteur()
	{
		return this.idProducteur;
	}
	
	// retourne la ressource produite par le producteur
	public String getTypeRessource()
	{
		return typeRessource;
	}
	
	// retourne la quantité courante de cette ressource possédée
	public int getQuantiteRessource()
	{
		return quantiteRessource;
	}
	
	//==================================================================
	//							Setters
	//==================================================================
	
	// positionne l'id du producteur
	public void setIdProducteur(int idProducteur)
	{
		this.idProducteur = idProducteur;
	}
	
	// positionne le type de ressource produite par le producteur
	public void setTypeRessource(String typeRessource)
	{
		this.typeRessource = typeRessource;
	}
	
	// positionne la quantité de ressources possédée par le producteur
	public void setQuantiteRessource(int quantiteRessource)
	{
		this.quantiteRessource = quantiteRessource;
	}
	
	//==================================================================
	//							Méthodes
	//==================================================================
	
	/*
	 * Fonction 	: atribuerRessources
	 * Argument(s)	: la quantité de ressource demandée par l'agent demandeur
	 * Résultat(s)	: la quantité de ressource attribuée par le producteur (>= 0 et <= quantité demandée)
	 * Commentaires	: /
	 */
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
	
	/*
	 * Fonction 	: genererRessources
	 * Argument(s)	: /
	 * Résultat(s)	: /
	 * Commentaires	: régénère les ressources suivant une certaine politique
	 */
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
	
	/*
	 * Fonction 	: terminerJeu
	 * Argument(s)	: /
	 * Résultat(s)	: /
	 * Commentaires	: appelé par le coordinateur pour terminer le producteur
	 */
	public void terminerJeu() throws RemoteException
	{
		System.out.println("Producteur " + idProducteur + " se termine" );
		System.exit(0);
	}
}
