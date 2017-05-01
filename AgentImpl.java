import java.rmi.server.UnicastRemoteObject ;
import java.rmi.RemoteException ;
import java.rmi.* ; 
import java.net.MalformedURLException ; 
import java.util.*;
import java.io.IOException;
import java.lang.Thread;

public abstract class AgentImpl 
  extends UnicastRemoteObject
  implements Agent  
{
	//----------------------------------------------------------------------
	//				attributs
	//----------------------------------------------------------------------
	
	private int idAgent;			// identifiant unique de l'agent 
	private ArrayList<Ressource> ressources;	// tableau de : typeRessource,QuantiteRessource,ObjectiRessource
	private boolean enSurveillance;	// indique si l'agent est actuellement en surveillance
	
	private Producteur producteurs[];	// liste des producteurs
	private int nbProducteurs;			// nombre de producteurs dans la liste
	
	private Agent agents[];		// liste des agents sauf soi
	private int nbAgents;		// nombre des agents dans la liste
	
	private Coordinateur coordinateur;
	
	//----------------------------------------------------------------------
	//				constructeur
	//----------------------------------------------------------------------
	
	public AgentImpl(int idAgent, ArrayList<Ressource> ressources) throws RemoteException
	{
		this.idAgent = idAgent;
		this.ressources = ressources;
		this.nbProducteurs = 0;
		this.nbAgents = 0;
		this.enSurveillance = false;	// pas au début	
	}

	//----------------------------------------------------------------------
	//				getters
	//----------------------------------------------------------------------
	
	// Retourne l'id de l'agent
	public int getIdAgent()
	{
		return this.idAgent;
	}
	
	// retourne le nombre d'agents connus de cet agent
	public int getNbAgents()
	{
		return this.nbAgents;
	}
	
	// retourne le nombre de producteurs connus de cet agent
	public int getNbProducteurs()
	{
		return this.nbProducteurs;
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
		else if (pos >= getIdAgent())	// id supérieurs à soi (agent id+1 = id)
		{
			return agents[pos-1];
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
	
	// retourne la ième ressource (précondition : 0 <= i < size)
	public Ressource getRessourceAtPos(int i)
	{
		return this.ressources.get(i);
	}
	
	// retourne la liste des ressources possédées
	public ArrayList<Ressource> getRessources()
	{
		return this.ressources;
	}
	
	// retourne la quantité de la ressource typeRessource
	public Ressource getRessourceByType(String typeRessource)
	{
		// recherche de la position
		int pos = -1;
		for (int i = 0; i < this.ressources.size() && pos < 0; i++)
		{
			if (this.ressources.get(i).getTypeRessource().equals(typeRessource))
			{
				return this.ressources.get(i);
			}
		}
		return null;
	}
	
	//----------------------------------------------------------------------
	//				setters
	//----------------------------------------------------------------------
	
	// positionne l'id de l'agent
	public void setIdAgent(int idAgent)
	{
		this.idAgent = idAgent;
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
	
	// positionne la ressource à la position i (précondition : 0 <= i < size)
	public void setRessourceAtPos(int i, Ressource ressource)
	{
		this.ressources.set(i,ressource);
	}
	
	// positionne la quantité de la ressource typeRessource
	public void setRessourceByType(Ressource ressource)
	{
		// recherche de la position
		int pos = -1;
		for (int i = 0; i < this.ressources.size() && pos < 0; i++)
		{
			if (this.ressources.get(i).getTypeRessource().equals(ressource.getTypeRessource()))
			{
				pos = i;
			}
		}
		// mise à jour
		this.ressources.set(pos,ressource);
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
	public void demarrerTour(int numeroTour) throws RemoteException, IOException
	{
		System.out.println("Agent " + idAgent + " : je commence mon tour");
		choixAction();
		boolean fin = this.verifierObjectifAtteint();
		System.out.println("fin = " + fin);
		
		// encodage du log
		String log = "" + numeroTour + ";" + getIdAgent() + ";";
		ArrayList<Ressource> copie = getRessources();
		for (int i = 0; i < copie.size(); i++)
		{
			log = log + copie.get(i).getTypeRessource() + ";" + copie.get(i).getQuantiteRessource();
		}
		System.out.println("Agent " + getIdAgent() + " : mon log est : " + log);
		
		// signalement de la fin du tour / de l'objectif atteint
		if (fin)
		{
			System.out.println("Agent " + getIdAgent() + " : objectif atteint !");
			getCoordinateur().signalerObjectifAtteint(getIdAgent(), log);
		}
		else
		{
			System.out.println("Agent " + getIdAgent() + " : objectif non atteint");
			getCoordinateur().signalerFinTour(getIdAgent(), log);
		}
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
	 * Commentaires	: on ne peut pas voler plus de 10% de ce qu'on possède (min 1)
	 */
	public int voler(int idAgent, String typeRessource, int quantiteRessource) throws RemoteException
	{
		System.out.println("Agent " + this.idAgent + " : tentative de vol de " + quantiteRessource + 
		" exemplaires de la ressource " + typeRessource + " (voleur : agent " + idAgent + ")");
		if (getEnSurveillance())
		{
			System.out.println("Agent " + this.idAgent + " : je suis en mode NSA ! J'ai tout vu !");
			// il faudra garder en mémoire l'id de l'agent voleur
			return 0;
		}
		else
		{
			System.out.println("Agent " + this.idAgent + " : Je n'ai rien vu !");
			
			// recherche de la ressource contenant le typeRessource demandé
			for (int i = 0; i < this.ressources.size(); i++)
			{
				// le type de ressource correspond
				if (this.ressources.get(i).getTypeRessource().equals(typeRessource))
				{
					Ressource modif = this.ressources.get(i);	// récupération de la ressource
					if (this.ressources.get(i).getQuantiteRessource() >= quantiteRessource)
					{
						// on prend le plus petit entre 10% + 1 et la quantité demandée
						int quantiteVolee = Math.min(modif.getQuantiteRessource()/10 + 1, quantiteRessource);
						// on retire la quantité volée à la copie locale
						modif.setQuantiteRessource(modif.getQuantiteRessource() - quantiteVolee);
						this.ressources.set(i,modif);	// on met à jour la liste des ressources
						return quantiteVolee;
					}
					else
					{
						// on prend le plus petit entre 10% + 1 et la quantité possédée
						int quantiteVolee = Math.min(modif.getQuantiteRessource()/10 + 1, modif.getQuantiteRessource());
						modif.setQuantiteRessource(modif.getQuantiteRessource() - quantiteVolee);
						this.ressources.set(i,modif);	// on met à jour la liste des ressources
						return quantiteVolee;
					}
				}
			}
			return 0;	// non trouvé
		}
	}
	
	/*
	 * Fonction 	: observer
	 * Argument(s)	: l'id de l'agent demandeur
	 * Résultat(s)	: les ressources produites (type, quantité et objectif)
	 * Commentaires	: /
	 */
	public int observerRessourceParType(String typeRessource) throws RemoteException
	{
		System.out.println("Agent " + getIdAgent() + " : l'agent " + idAgent + " m'observe (ressource " + typeRessource + ")");
		
		// si on est en surveillance, on ne divulge rien
		if (getEnSurveillance())
		{
			System.out.println("Agent " + getIdAgent() + " : je suis en mode NSA, je ne dirai rien !");
			return 0;	// l'agent pensera que je n'ai pas la ressource
		}
		// sinon on ne voit rien de cet espionnage et on divulge la quantité possédée
		else
		{
			System.out.println("Agent " + getIdAgent() + " : je n'ai rien vu");
			Ressource ressourceObservee = getRessourceByType(typeRessource);
			return ressourceObservee.getQuantiteRessource();
		}
	}
	
	/*
	 * Fonction 	: verifierObjectifAtteint 
	 * Argument(s)	: aucun
	 * Résultat(s)	: vrai si tous les abjectifs sont atteints, faux sinon
	 * Commentaires	: permet de lancer signalerObjectifAtteint quand le résultat est vrai
	 */
	public boolean verifierObjectifAtteint()
	{
		for( int i = 0; i < this.ressources.size(); i++)
		{
			// debug
			System.out.println("ressource " + this.ressources.get(i).getTypeRessource() + " : " + this.ressources.get(i).getQuantiteRessource() + " / " + this.ressources.get(i).getObjectifRessource());
			if (this.ressources.get(i).getQuantiteRessource() < this.ressources.get(i).getObjectifRessource())
			{
				return false;
			}
		}
		return true;
	}

	/*
	 * Fonction 	: terminerJeu 
	 * Argument(s)	: /
	 * Résultat(s)	: /
	 * Commentaires	: appelé par le coordinateur pour terminer l'agent
	 */
	public void terminerJeu() throws RemoteException
	{			
		new Thread(new Runnable() 
		{
			public void run() 
			{
				try 
				{
					Thread.sleep(100);
				} catch (InterruptedException e) {/* rien */}
				
				// unbind avant la suppression
				Naming.unbind("rmi://localhost:9000/agent" + getIdAgent());
				
				// supprime du runtime RMI
				//UnicastRemoteObject.unexportObject(this, true);
				System.out.println("Agent " + getIdAgent() + " se termine" );
				System.exit(0);
			}
		});
		return;
	}
}
