import java.rmi.server.UnicastRemoteObject ;
import java.rmi.RemoteException ;
import java.rmi.* ; 
import java.net.MalformedURLException ; 
import java.util.*;
import java.util.Timer;
import java.io.IOException;

public class ProducteurImpl extends UnicastRemoteObject implements Producteur
{  
	//==================================================================
	//							Attributs
	//==================================================================
	
	private int idProducteur;		// identifiant unique d'un producteur
	private String typeRessource;	// pour l'instant un seule ressource
	private int quantiteRessource;	// idem
	private int quantiteMax;		// le maximum qu'un agent peut obtenir
	
	//==================================================================
	//							Constructeur
	//==================================================================
	
	public ProducteurImpl(int idProducteur, String typeRessource, int quantiteRessource, int quantiteMax) throws RemoteException
	{
		this.idProducteur = idProducteur;
		this.typeRessource = typeRessource;
		this.quantiteRessource = quantiteRessource;
		this.quantiteMax = quantiteMax;
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
	
	// retourne la quantité max
	public int getQuantiteMax()
	{
		return this.quantiteMax;
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
	
	// positionne la quatité max
	public void setQuantiteMax(int quantiteMax)
	{
		this.quantiteMax = quantiteMax;
	}
	
	//==================================================================
	//							Méthodes
	//==================================================================
	
	/*
	 * Fonction 	: attribuerRessources
	 * Argument(s)	: la quantité de ressource demandée par l'agent demandeur
	 * Résultat(s)	: la quantité de ressource attribuée par le producteur (>= 0 et <= quantité demandée)
	 * Commentaires	: /
	 */
	public int attribuerRessources(String typeRessource, int quantiteDemandee) throws RemoteException
	{
		// si le type de ressource n'est pas produit par le producteur
		if (!this.typeRessource.equals(typeRessource))
		{
			return 0;
		}
		
		int quantiteAttribuee = 0;
		// si la quantité demandée est supérieure à la quantité disponible
		// l'agent ne reçoit que le maximum disponible
		if (this.quantiteRessource <= quantiteDemandee)
		{
			quantiteAttribuee = Math.min(this.quantiteRessource, this.quantiteMax);
			this.quantiteRessource = 0;
			return quantiteAttribuee;
		}
		else
		{
			quantiteAttribuee = Math.min(quantiteDemandee, this.quantiteMax);
			this.quantiteRessource -= quantiteDemandee;
			return quantiteAttribuee;
		}
	}
	
	/*
	 * Fonction 	: observerTypeRessource
	 * Argument(s)	: l'id de l'agent demandeur
	 * Résultat(s)	: la ressource produite (type)
	 * Commentaires	: /
	 */
	public String observerTypeRessource(int idAgent) throws RemoteException
	{
		System.out.println("Producteur " + getIdProducteur() + " : l'agent " + idAgent + " m'observe (type)");
		return this.typeRessource;
	}
	
	/*
	 * Fonction 	: observerQuantiteRessource
	 * Argument(s)	: l'id de l'agent demandeur
	 * Résultat(s)	: la ressource produite (quantité)
	 * Commentaires	: /
	 */
	public int observerQuantiteRessource(int idAgent) throws RemoteException
	{
		System.out.println("Producteur " + getIdProducteur() + " : l'agent " + idAgent + " m'observe (quantité)");
		return this.quantiteRessource;
	}
	
	/*
	 * Fonction 	: genererRessources
	 * Argument(s)	: /
	 * Résultat(s)	: /
	 * Commentaires	: régénère les ressources suivant une certaine politique
	 */
	public void genererRessources()
	{
		System.out.println("Producteur " + getIdProducteur() + " : je régénère mes ressources");
		// très simple pour l'instant on double ne nombre restant (si > 0)
		if (this.quantiteRessource > 0)
		{
			// augmente du maximum de 1 et de 10% des ressources
			this.quantiteRessource += Math.max(this.quantiteRessource/10, 1);
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
		try
		{
			// unbind avant la suppression
			Naming.unbind("rmi://localhost:9000/producteur" + getIdProducteur());

			// supprime du runtime RMI
			UnicastRemoteObject.unexportObject(this, true);
			
			System.out.println("Producteur " + idProducteur + " se termine" );
		} catch(Exception e){System.out.println(e);}
		System.exit(0);
	}
	
	public static void main(String [] args) throws IOException
	{
		if (args.length != 5)
		{
			System.out.println("Usage : java ObjectProducteur <port rmiregistry> <idProducteur> <typeRessource> <quantiteRessource> <quantiteMax>") ;
			System.exit(0) ;
		}
		try
		{
			System.out.println("args : " + args[0]+ "  " + args[1] + " " + args[2] + " " + args[3] + " " + args[4]);
			Coordinateur coordinateur = (Coordinateur) Naming.lookup( "rmi://localhost:" + args[0] + "/coordinateur" );
			
			int idProducteur = Integer.parseInt(args[1]);
			int quantiteRessource = Integer.parseInt(args[3]);
			int quantiteMax = Integer.parseInt(args[4]);
			final ProducteurImpl objLocal = new ProducteurImpl(idProducteur, args[2], quantiteRessource, quantiteMax );
			Naming.rebind( "rmi://localhost:" + args[0] + "/producteur" + args[1] ,objLocal) ;
			System.out.println("Producteur " + objLocal.getIdProducteur() + " pret") ;
			
			// s'enregistrer auprès du coordinateur (convention : port 9000)
			coordinateur.identifierProducteur(objLocal.getIdProducteur());

			Timer timer = new Timer();
			timer.schedule(new TimerTask() 
			{
				public void run() 
				{
					objLocal.genererRessources();
				}
			}, 0, 1000);
		}
		catch (NotBoundException re) { System.out.println(re) ; }
		catch (RemoteException re) { System.out.println(re) ; }
		catch (MalformedURLException e) { System.out.println(e) ; }
	}
}
