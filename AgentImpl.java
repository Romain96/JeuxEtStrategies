import java.rmi.server.UnicastRemoteObject ;
import java.rmi.RemoteException ;
import java.rmi.* ; 
import java.net.MalformedURLException ; 

public abstract class AgentImpl 
  extends UnicastRemoteObject
  implements Agent  
{
	//----------------------------------------------------------------------
	//				attributs
	//----------------------------------------------------------------------
	
	private int idAgent;			// identifiant unique de l'agent 
	private String typeRessource;	// type de ressource acquise
	private int quantiteRessource;	// quantité de cette ressource acquise
	private int objectif;			// quantité de ressouce ciblée
	private boolean enSurveillance;	// indique si l'agent est actuellement en surveillance
	
	private Producteur producteurs[];	// liste des producteurs
	private int nbProducteurs;			// nombre de producteurs dans la liste
	
	private Agent agents[];		// liste des agents sauf soi
	private int nbAgents;		// nombre des agents dans la liste
	
	private Coordinateur coordinateur;
	
	//----------------------------------------------------------------------
	//				constructeur
	//----------------------------------------------------------------------
	
	public AgentImpl(int idAgent, String typeRessource, int quantiteRessource, int objectif) throws RemoteException
	{
		this.idAgent = idAgent;
		this.typeRessource = typeRessource;
		this.quantiteRessource = quantiteRessource;
		this.objectif = objectif;
		this.nbProducteurs = 0;
		this.nbAgents = 0;
		this.enSurveillance = false;	// pas au début
		// DEBUG
		System.out.println("Agent init : " + idAgent + " " + typeRessource + " " + quantiteRessource + " " + objectif );	
	}

	//----------------------------------------------------------------------
	//				getters
	//----------------------------------------------------------------------
	
	public int getIdAgent()
	{
		return this.idAgent;
	}
	
	public int getQuantiteRessource()
	{
		return quantiteRessource;
	}
	
	public int getObjectif()
	{
		return objectif;
	}
	
	public int getNbProducteurs()
	{
		return nbProducteurs;
	}
	
	public boolean getEnSurveillance()
	{
		return this.enSurveillance;
	}
	
	//----------------------------------------------------------------------
	//				setters
	//----------------------------------------------------------------------
	
	public void setIdAgent(int idAgent)
	{
		this.idAgent = idAgent;
	}
	
	public void setTypeRessource(String typeRessource)
	{
		this.typeRessource = typeRessource;
	}
	
	
	// positionne la ressource type avec la quantité nb
	public void setQuantiteRessource(int quantite)
	{
		this.quantiteRessource = quantite;
	}
	
	// positionne l'objectif de ressources pour le type passé en paramètre
	public void setObjectif( int nb)
	{
		this.objectif = nb;
	}
	
	public void setNbProducteurs(int nbProducteurs)
	{
		this.nbProducteurs = nbProducteurs;
	}
	
	public void setEnSurveillance(boolean enSurveillance)
	{
		this.enSurveillance = enSurveillance;
	}
	
	//----------------------------------------------------------------------
	//				methodes
	//----------------------------------------------------------------------
	
	// permet de démarrer le tour de l'agent
	public void demarrerTour() throws RemoteException
	{
		System.out.println("Agent " + idAgent + " : je commence mon tour");
		
		// tentative d'acquisition de ressources (on teste avec 5 le but étant 10 donc 2 passes)
		int ressourcesAcquises = producteurs[0].attribuerRessources(5);
		System.out.println("Agent " + idAgent + " : j'acquiers " + ressourcesAcquises + " exemplaires de la ressource " + typeRessource);
		this.quantiteRessource += ressourcesAcquises;
		
		if (this.quantiteRessource >= this.objectif )
		{
			System.out.println("Agent " + idAgent + " : je possède " + quantiteRessource + " exemplaires de la ressource " + typeRessource + "/" + objectif);
			coordinateur.signalerObjectifAtteint(this.idAgent);
		}
		else
		{
			System.out.println("Agent " + idAgent + " : je possède " + quantiteRessource + " exemplaires de la ressource " + typeRessource + "/" + objectif);
			coordinateur.signalerFinTour(this.idAgent);
		}
	}
	
	// permet d'identifier le cordinateur
	public void enregistrerCoordinateur(String machineServeur, int numeroPort)
	{
		try 
		{
			Coordinateur coordinateur = (Coordinateur) Naming.lookup( "rmi://" + machineServeur + ":" + numeroPort + "/coordinateur");
			System.out.println("Agent " + this.idAgent + " : enregistre le coordinateur : rmi://" + machineServeur + ":" + numeroPort + "/coordinateur" );
			this.coordinateur = coordinateur;
		}
		catch (NotBoundException re) { System.out.println(re) ; }
		catch (RemoteException re) { System.out.println(re) ; }
		catch (MalformedURLException e) { System.out.println(e) ; }
	}
	
	// appelé par le coordinateur pour transmettre le nombre d'agents/producteurs
	public void signalerNbAgentsEtProducteurs(int nbAgents, int nbProducteurs) throws RemoteException
	{
		System.out.println("Agent " + this.idAgent + " enregistre ses agents(" + nbAgents + ") et producteurs(" + nbProducteurs + ")" );
		
		this.agents = new Agent[nbAgents - 1];
		this.nbAgents = nbAgents - 1;
		this.producteurs = new Producteur[nbProducteurs];
		this.nbProducteurs = nbProducteurs;
		
		enregistrerAgents(nbAgents);
		enregistrerProducteurs(nbProducteurs);
	}
	
	// permet d'enregistrer tous les agents sauf soi
	public void enregistrerAgents(int nbAgents)
	{
		try 
		{
			// agent avant soi 
			for (int i = 0; i < this.idAgent; i++)
			{
				System.out.println("Agent " + this.idAgent + " : enregistre l'agent (avant) " + i);
				Agent agent = (Agent) Naming.lookup( "rmi://localhost:9000/agent" + i );
				this.agents[i] = agent;
			}
			// agents après soi
			for (int i = this.idAgent + 1; i < nbAgents; i++)
			{
				System.out.println("Agent " + this.idAgent + " : enregistre l'agent (après) " + i);
				Agent agent = (Agent) Naming.lookup( "rmi://localhost:9000/agent" + i );
				this.agents[i-1] = agent;
			}
		}
		catch (NotBoundException re) { System.out.println(re) ; }
		catch (RemoteException re) { System.out.println(re) ; }
		catch (MalformedURLException e) { System.out.println(e) ; }
	}
	
	// permet d'enregistrer tous les producteurs
	public void enregistrerProducteurs(int nbProducteurs)
	{
		try 
		{
			for (int i = 0; i < nbProducteurs; i++)
			{
				Producteur producteur = (Producteur) Naming.lookup( "rmi://localhost:9000/producteur" + i );
				producteurs[i] = producteur;
			}
		}
		catch (NotBoundException re) { System.out.println(re) ; }
		catch (RemoteException re) { System.out.println(re) ; }
		catch (MalformedURLException e) { System.out.println(e) ; }
	}
	
	// permet à l'agent de surveiller ses biens
	public void surveillance()
	{
		System.out.println("Agent " + idAgent + " : je me place en mode NSA");
		setEnSurveillance(true);
	}
	
	// permet à l'agent de tenter de voler un autre agent
	public int voler(int idAgent, String type, int nb) throws RemoteException
	{
		System.out.println("Agent " + this.idAgent + " : tentative de vol de " + nb + " exemplaires de la ressource " + type + " (voleur : agent " + idAgent + ")");
		if (getEnSurveillance())
		{
			System.out.println("Agent " + this.idAgent + " : je suis en mode NSA ! J'ai tout vu !");
			// il faudra garder en mémoire l'id de l'agent voleur
		}
		else
		{
			System.out.println("Agent " + this.idAgent + " : Je n'ai rien vu !");
			if (this.quantiteRessource >= nb)
			{
				int quantiteVolee = this.quantiteRessource - nb;
				this.quantiteRessource -= nb;
				return quantiteVolee;
			}
			else
			{
				int quantiteVolee = this.quantiteRessource;
				this.quantiteRessource = 0;
				return quantiteVolee;
			}
		}
	}

	// appelé par le cordinateur à la fin du jeu pour terminer l'agent
	public void terminerJeu() throws RemoteException
	{
		System.out.println("Agent " + idAgent + " se termine");
		System.exit(0);
	}
}

