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
	
	// Retourne l'id de l'agent
	public int getIdAgent()
	{
		return this.idAgent;
	}
	
	// retourne le type de la ressource
	public String getTypeRessource()
	{
		return this.typeRessource;
	}
	
	// retourne la quantite de ressource de l'agent
	public int getQuantiteRessource()
	{
		return quantiteRessource;
	}
	
	// Retourne l'objectif de ressource actuel
	public int getObjectif()
	{
		return objectif;
	}
	
	// retourne le nombre de prducteurs connus de cet agent
	public int getNbProducteurs()
	{
		return nbProducteurs;
	}
	
	// retourne vrai si l'agent est en surveillance, faux sinon
	public boolean getEnSurveillance()
	{
		return this.enSurveillance;
	}
	
	// retourne le coordinateur
	public Coordinateur getCoordinateur()
	{
		return this.coordinateur;
	}
	
	// retourne le ième agent du tableau d'agent
	public Agent getAgentAtPos(int pos)
	{
		if (pos < 0)
		{
			return agents[0];	// le premier
		}
		else if (pos >= nbAgents)
		{
			return agents[nbAgents-1];	// le dernier
		}
		else 
		{
			return agents[pos];
		}
	}
	
	// retourne le ième producteur du tableau de producteur
	public Producteur getProducteurAtPos(int pos)
	{
		if (pos < 0)
		{
			return producteurs[0];	// le premier
		}
		else if (pos >= nbProducteurs)
		{
			return producteurs[nbProducteurs-1];	// le dernier
		}
		else 
		{
			return producteurs[pos];
		}
	}
	
	//----------------------------------------------------------------------
	//				setters
	//----------------------------------------------------------------------
	
	// positionne l'id de l'agent
	public void setIdAgent(int idAgent)
	{
		this.idAgent = idAgent;
	}
	
	// positionne le type de ressource
	public void setTypeRessource(String typeRessource)
	{
		this.typeRessource = typeRessource;
	}
	
	// positionne la quantité de ressource possédée
	public void setQuantiteRessource(int quantite)
	{
		this.quantiteRessource = quantite;
	}
	
	// positionne l'objectif de ressources
	public void setObjectif(int nb)
	{
		this.objectif = nb;
	}
	
	// positionne le nombre de producteurs connus de cet agent
	public void setNbProducteurs(int nbProducteurs)
	{
		this.nbProducteurs = nbProducteurs;
	}
	
	// positionne le mode surveillance
	public void setEnSurveillance(boolean enSurveillance)
	{
		this.enSurveillance = enSurveillance;
	}
	
	// positionne le coordinateur
	public void setCoordinateur(Coordinateur coordinateur)
	{
		this.coordinateur = coordinateur;
	}
	
	//----------------------------------------------------------------------
	//				methodes
	//----------------------------------------------------------------------
	
	/*
	 * Fonction 	: demarrerTour
	 * Argument(s)	: /
	 * Résultat(s)	: /
	 * Commentaires	: appelée par le coordinateur pour commencer le tour
	 * de l'agent, appelle la fonction choixAction qui est différente selon
	 * la personnalité de l'agent
	 */
	public void demarrerTour() throws RemoteException
	{
		System.out.println("Agent " + idAgent + " : je commence mon tour");
		choixAction();
	}
	
	/*
	 * Fonction 	: choixAction
	 * Argument(s)	: /
	 * Résultat(s)	: /
	 * Commentaires	: choisi l'action à effectuer selon la personnalité de l'agent
	 */
	 public void choixAction() throws RemoteException
	 {
		 System.out.println("Agent " + this.idAgent + " : je fais mon choix");
		 // doit être redéfinie pour chaque personnalité d'agent
	 }
	
	/*
	 * Fonction 	: enregistrerCoordinateur
	 * Argument(s)	: le nom de la machine du serveur et le port utilisé
	 * Résultat(s)	: /
	 * Commentaires	: enregistre le coordinateur dans la variable coordinateur
	 */
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
	
	/*
	 * Fonction 	: signalerNbAgentsEtProducteurs
	 * Argument(s)	: le nombre d'agents et le nombre de producteurs
	 * Résultat(s)	: /
	 * Commentaires	: initialise les tableaux d'agents et de producteurs
	 * ainsi que leurs tailles et appelle les fonctions enregistrerAgents
	 * et enregistrerProducteurs
	 */
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
	
	/*
	 * Fonction 	: enregistrerAgents
	 * Argument(s)	: le nombre d'agents
	 * Résultat(s)	: /
	 * Commentaires	: enregistre dans le tableau agents tous les agents
	 * d'id 0 à nbAgent sauf soi
	 */
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
	
	/*
	 * Fonction 	: enregistrerProducteur
	 * Argument(s)	: le nombre de producteurs
	 * Résultat(s)	: /
	 * Commentaires	: enregistre dans le tableau producteurs
	 * tous les producteurs d'id 0 à idProducteur
	 */
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
	
	/*
	 * Fonction 	: surveillance
	 * Argument(s)	: /
	 * Résultat(s)	: /
	 * Commentaires	: positionne la variable enSurveillance
	 * et permet à l'agent de protéger ses biens pour le tour à venir
	 */
	public void surveillance()
	{
		System.out.println("Agent " + idAgent + " : je me place en mode NSA");
		setEnSurveillance(true);
	}
	
	/*
	 * Fonction 	: voler
	 * Argument(s)	: l'id de l'agent (voleur), le type de ressource à voler et la quantité à voler
	 * Résultat(s)	: le nombre de cette ressource volée (>= 0)
	 * Commentaires	: /
	 */
	public int voler(int idAgent, String type, int nb) throws RemoteException
	{
		System.out.println("Agent " + this.idAgent + " : tentative de vol de " + nb + " exemplaires de la ressource " + type + " (voleur : agent " + idAgent + ")");
		if (getEnSurveillance())
		{
			System.out.println("Agent " + this.idAgent + " : je suis en mode NSA ! J'ai tout vu !");
			// il faudra garder en mémoire l'id de l'agent voleur
			return 0;
		}
		else
		{
			System.out.println("Agent " + this.idAgent + " : Je n'ai rien vu !");
			if (this.quantiteRessource >= nb)
			{
				int quantiteVolee = nb;
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

	/*
	 * Fonction 	: terminerJeu 
	 * Argument(s)	: /
	 * Résultat(s)	: /
	 * Commentaires	: appelé par le coordinateur pour terminer l'agent
	 */
	public void terminerJeu() throws RemoteException
	{
		System.out.println("Agent " + idAgent + " se termine");
		System.exit(0);
	}
}

