import java.rmi.server.UnicastRemoteObject ;
import java.rmi.RemoteException ;
import java.rmi.* ; 
import java.net.MalformedURLException ; 
import java.io.IOException;
import java.util.*;

public class CoordinateurImpl extends UnicastRemoteObject implements Coordinateur
{
	//----------------------------------------------------------------------
	//				attributs
	//----------------------------------------------------------------------
	
	// le coordinateur connait tous les agents
	Agent agents[];				// liste des agents
	int nbAgents;				// taille du tableau des id des agents
	int nbAgentsEnregistres;	// nombre d'agents s'étant identifiés au coordinateur
	
	// le coordinateur connait tous les producteurs
	Producteur producteurs[];		// liste des producteurs
	int nbProducteurs;				// taille du tableau producteurs
	int nbProducteursEnregistres;	// nombre de producteurs s'étant identifiés au coordinateur
	
	// liste des punitions des agents (nb de tours de non jeu)
	int punitionsAgents[];
	
	// liste des agents ayant terminé (si le politique de fin est que tout le monde doit finir)
	boolean terminaisonsAgents[];
	
	// classement des agents à la fin de la partie (si les agents doivent tous finir)
	ArrayList<Integer> classementsAgents;	// contient les id des agents
	
	// vrai si le jeu se finit quand le premier agent termine, faux si tous les agents doivent terminer
	boolean finPremierAgent;
	
	// indique si le jeu a démarré ou non
	boolean jeuEnCours;
	
	// indique l'indice du prochain agent à faire démarer (dans l'ordre d'enregistrement pas d'indice des agents)
	int prochainAgentAJouer;
	
	// fichier de log (voir classe Historique)
	private Historique historique;
	
	// numéro du tour
	private int numeroTour;
	
	//----------------------------------------------------------------------
	//				cosntructeur
	//----------------------------------------------------------------------
	
	public CoordinateurImpl(int nbAgents, int nbProducteurs, boolean finPremierAgent, String fichierLog) throws RemoteException, IOException
	{
		// initialisation du tableau d'agents
		this.agents = new Agent[nbAgents];	// les agents s'identifiront au coordinateur à l'initialisation
		this.nbAgents = nbAgents;
		this.nbAgentsEnregistres = 0;		// aucun agent enregistré pour l'instant (le coordinateur est lancé en premier)
		
		// initialisation du tableau des producteurs
		this.producteurs = new Producteur[nbProducteurs];	// les producteurs s'identifiront au coordinateur à l'initialisation
		this.nbProducteurs = nbProducteurs;
		this.nbProducteursEnregistres = 0;			// aucun producteur enregistré pour l'instant (le coordinateur est lancé en premier)
		
		// on initialise les punitions
		this.punitionsAgents = new int[nbAgents];
		// les terminaisons
		this.terminaisonsAgents = new boolean[nbAgents];
		// et le classement
		this.classementsAgents = new ArrayList<Integer>();
		for (int i = 0; i < nbAgents; i++)
		{
			this.punitionsAgents[i] = 0;	// pas de punition au départ
			this.terminaisonsAgents[i] = false;	// aucun agent n'a terminé
		}
		
		this.finPremierAgent = finPremierAgent;
		
		// le jeu n'a pas encore commencé (les agents/producteurs doivent d'abord s'inscrirent)
		jeuEnCours = false;
		
		// il n'y a pas d'agent à faire jouer pour l'instant donc le prochain sera le 1er enregistré
		prochainAgentAJouer = 0;
		
		// création de l'historique
		this.historique = new Historique(fichierLog);
		
		// tour numéro 1
		this.numeroTour = 1;
		
		
		// écrire que tous les agents commencent à 0
		try
		{
			for (int i = 0; i < this.nbAgents; i++)
			{
				// le tour est 0, l'id est i et les ressources sont à 0
				this.historique.ecrireLigne("0;" + i + ";0"); 
			}
		} catch (IOException ioe) { ioe.printStackTrace(); }
	}
	
	//----------------------------------------------------------------------
	//				getters
	//----------------------------------------------------------------------
	
	// retourne le nombre d'agents prévus
	public int getNbAgents()
	{
		return nbAgents;
	}
	
	// retourne le nombre de producteurs prévus
	public int getNbProducteurs()
	{
		return nbProducteurs;
	}
	
	// retourne le nombre d'agents enregistrés auprès du coordinateur
	public int getNbAgentsEnregistres()
	{
		return nbAgentsEnregistres;
	}
	
	// retourne le nombre de producteurs enregistrés auprès du coordinateur
	public int getNbProducteursEnregistres()
	{
		return nbProducteursEnregistres;
	}	
	
	//----------------------------------------------------------------------
	//				setters
	//----------------------------------------------------------------------
	
	// positionne le nombre d'agents prévus
	public void setNbAgents(int nbAgents)
	{
		this.nbAgents = nbAgents;
	}
	
	// positionne le nombre de producteurs prévus
	public void setNbProducteurs(int nbProducteurs)
	{
		this.nbProducteurs = nbProducteurs;
	}
	
	// positionne le nombre d'agents enregistrés
	public void setNbAgentsEnregistres(int nbAgentsEnregistres)
	{
		this.nbAgentsEnregistres = nbAgentsEnregistres;
	}
	
	// positionne le nombre de producteurs enregistrés
	public void setNbProducteursEnregistres(int nbProducteursEnregistres)
	{
		this.nbProducteursEnregistres = nbProducteursEnregistres;
	}
	
	//----------------------------------------------------------------------
	//				methodes
	//----------------------------------------------------------------------
	
	/*
	 * Fonction 	: demarrerJeu
	 * Argument(s)	: /
	 * Résultat(s)	: /
	 * Commentaires	: positionne la variable jeuEnCours et appelle la méthode
	 * demarrerTour du 1er agent de la liste
	 */
	 public void demarrerJeu() throws RemoteException, IOException
	 {
		 // positionne la variable jeuEnCours à vrai
		 this.jeuEnCours = true;	
		  
		 // lance le 1er agent
		 this.agents[this.prochainAgentAJouer].demarrerTour(this.numeroTour);
		 System.out.println("Coordinateur : lancement de l'agent d'indice " + this.prochainAgentAJouer);
		 
		 this.prochainAgentAJouer = (this.prochainAgentAJouer + 1)%this.nbAgents;
		 System.out.println("Coordinateur : prochain agent à jouer sera d'indice " + this.prochainAgentAJouer);	 
	 }
	
	/*
	 * Fonction 	: verificationLancementJeu
	 * Argument(s)	: /
	 * Résultat(s)	: /
	 * Commentaires	: vérifie si le bon nombre d'agents et de producteurs
	 * se sont ideitifié et le cas échéant lance le jeu en appelant le 1er agent
	 */
	public void verificationLancementJeu() throws RemoteException, IOException
	{
		if (this.nbAgentsEnregistres >= this.nbAgents && this.nbProducteursEnregistres >= this.nbProducteurs)
		{
			// DEBUG
			System.out.println("Coordinateur : prêt à lancer le jeu avec " + this.nbAgentsEnregistres + " agents et " + this.nbProducteursEnregistres + " producteurs");
			
			// tramsmet à tous les agents la liste des producteurs (en réalité seulement le nombre d'agents/producteurs)
			for (int i = 0; i < nbAgentsEnregistres; i++)
			{
				this.agents[i].signalerNbAgentsEtProducteurs(this.nbAgentsEnregistres, this.nbProducteursEnregistres);
			}	
			
			demarrerJeu();
		}
	}
	
	/*
	 * Fonction 	: terminerJeu 
	 * Argument(s)	: /
	 * Résultat(s)	: /
	 * Commentaires	: termine le jeu en appelant la fonction terminerJeu
	 * de chaque agent et de chaque producteur
	 */
	public void terminerJeu() throws RemoteException
	{
		System.out.println("Coordinateur : le jeu se termine");
		this.jeuEnCours = false;
		
		// affichage du tableau des scores
		afficherTableauScores();
		
		// fin des producteurs
		for (int i = 0; i < this.nbProducteursEnregistres; i++)
		{
			System.out.println("Coordinateur : ordonne au producteur d'indice " + i + " dans le tableau de s'arrêter");
			this.producteurs[i].terminerJeu();	// les producteurs se terminent
		}
		// fin des agents
		for (int i = 0; i < this.nbAgentsEnregistres; i++)
		{
			System.out.println("Coordinateur : ordonne à l'agent d'indice " + i + " dasns le tableau de s'arrêter");
			this.agents[i].terminerJeu();	// les agents se terminent
		}	
		// fin du coordinateur
		this.historique.fermerFichier();
		try
		{
			// unbind avant la suppression
			Naming.unbind("rmi://localhost:9000/coordinateur");
		}catch (NotBoundException re) { System.out.println(re) ; }
		catch (RemoteException re) { System.out.println(re) ; }
		catch (MalformedURLException e) { System.out.println(e) ; }
					
		// supprime du runtime RMI
		UnicastRemoteObject.unexportObject(this, true);
		System.out.println("Coordinateur se termine" );
		//System.exit(0);
		return;
	}
	
	/*
	 * Fonction 	: afficherTableauScores
	 * Argument(s)	: /
	 * Résultat(s)	: /
	 * Commentaires	: affiche le tableau des scores (ou l'agent ayant gagné)
	 */
	public void afficherTableauScores()
	{
		System.out.println("================================================================");
		System.out.println("                      La partie est terminée");
		System.out.println("================================================================");
		if(this.finPremierAgent)
		{
			// affichage de l'agent ayant terminé
			System.out.println("La victoire revient à l'agent " + this.classementsAgents.get(0) + " en " + this.numeroTour + " tours");
		}
		else
		{
			// affichage du classement des agents
			System.out.println("Classement des agents au bout du tour " + this.numeroTour + ": ");
			for (int i = 0; i < this.classementsAgents.size(); i++)
			{
				System.out.println("#" + i + " : agent " + this.classementsAgents.get(i) );
			}
		}
	}
	
	/*
	 * Fonction 	: identifierAgent 
	 * Argument(s)	: l'id de l'agent s'identifiant
	 * Résultat(s)	: /
	 * Commentaires	: l'agent est enregistrer dans la liste des agents du coordinateur
	 */
	public void identifierAgent(int idAgent) throws RemoteException, IOException
	{
		if (nbAgentsEnregistres < nbAgents )
		{
			try
			{
				System.out.println("Coordinateur : agent " + idAgent + " s'identifie" );
				// on réccupère l'agent enregistré comme étant l'agent "agent + id de l'agent"
				Agent agent = (Agent) Naming.lookup( "rmi://localhost:9000/agent" + idAgent );
				agents[idAgent] = agent;
				nbAgentsEnregistres++;
				System.out.println("Coordinateur : il y a désormais " + nbAgentsEnregistres + "/" + nbAgents + " agents enregistrés" );
			}
			catch (NotBoundException re) { System.out.println(re) ; }
			catch (RemoteException re) { System.out.println(re) ; }
			catch (MalformedURLException e) { System.out.println(e) ; }	
			
			verificationLancementJeu();
		}
		else
		{
			System.out.println("Coordinateur : agent " + idAgent + " ne peut pas s'identifier : la liste est pleine !" );
		}
	}
	
	/*
	 * Fonction 	: identifierProducteur
	 * Argument(s)	: l'id du producteur s'identifiant
	 * Résultat(s)	: /
	 * Commentaires	: le producteur est enregistrer dans la liste des producteurs du coordinateur
	 */
	public void identifierProducteur(int idProducteur) throws RemoteException, IOException
	{
		if (nbProducteursEnregistres < nbProducteurs )
		{
			try
			{
				System.out.println("Coordinateur : producteur " + idProducteur + " s'identifie" );
				// on réccupère le producteur enregistré comme étant le producteur "producteur + id du producteur"
				Producteur producteur = (Producteur) Naming.lookup( "rmi://localhost:9000/producteur" + idProducteur );
				producteurs[idProducteur] = producteur;
				nbProducteursEnregistres++;
				System.out.println("Coordinateur : il y a désormais " + nbProducteursEnregistres + "/" + nbProducteurs + " producteurs enregistrés" );
			}
			catch (NotBoundException re) { System.out.println(re) ; }
			catch (RemoteException re) { System.out.println(re) ; }
			catch (MalformedURLException e) { System.out.println(e) ; }	
			
			verificationLancementJeu();
		}
		else
		{
			System.out.println("Coordinateur : producteur " + idProducteur + " ne peut pas s'identifier : la liste est pleine !" );
		}
	}
	
	/*
	 * Fonction 	: signalerFinTour
	 * Argument(s)	: l'id de l'agent signalant la fin de son tour
	 * Résultat(s)	: /
	 * Commentaires	: lance le tour de l'agent suivant
	 */
	public void signalerFinTour(int idAgent, String log) throws RemoteException, IOException
	{
		System.out.println("Coordinateur : agent " + idAgent + " signale la fin de son tour" );
		try
		{
			this.historique.ecrireLigne(log);
		} catch (IOException ioe) { ioe.printStackTrace(); }
		
		this.prochainAgentAJouer = (this.prochainAgentAJouer + 1)%this.nbAgents;
		lancementProchainAgent();
	}
	
	/*
	 * Fonction 	: lancementProchainAgent
	 * Argument(s)	: /
	 * Résultat(s)	: /
	 * Commentaires	: vérifie que l'agent n'est pas puni et lance l'agent
	 * sinon lance l'agent suivant
	 */
	 public void lancementProchainAgent() throws RemoteException, IOException
	 {
		 if (this.punitionsAgents[this.prochainAgentAJouer] > 0)
		 {
			 System.out.println("Coordinateur : agent " + this.prochainAgentAJouer + " est puni pour " + this.punitionsAgents[this.prochainAgentAJouer] + " tours :(" );
			 this.punitionsAgents[this.prochainAgentAJouer]--;	// un tour de passé
			 if(this.prochainAgentAJouer == 0)
			 {
				 this.numeroTour++;
			 }
			 this.prochainAgentAJouer = (this.prochainAgentAJouer + 1)%this.nbAgents;
			 lancementProchainAgent();
		 }
		 else if (this.terminaisonsAgents[this.prochainAgentAJouer])
		 {
			 System.out.println("Coordinateur : agent " + this.prochainAgentAJouer + " a déjà terminé" );
			 if(this.prochainAgentAJouer == 0)
			 {
				 this.numeroTour++;
			 }
			 this.prochainAgentAJouer = (this.prochainAgentAJouer + 1)%this.nbAgents;
			 lancementProchainAgent();
		 }
		 else
		 {
			 System.out.println("Coordinateur : lancement de l'agent d'indice " + this.prochainAgentAJouer);
			 if(this.prochainAgentAJouer == 0)
			 {
				 this.numeroTour++;
			 }
			 this.agents[this.prochainAgentAJouer].demarrerTour(this.numeroTour);
		 }
	 }
	
	/*
	 * Fonction 	: signalerObjectifAtteint
	 * Argument(s)	: l'id de l'agent signalant l'objectif atteint
	 * Résultat(s)	: /
	 * Commentaires	: termine le jeu si la politique est que le premier agent 
	 * ayant terminé marque la fin et sinon attends qua tous les agents aient terminés
	 */
	public void signalerObjectifAtteint(int idAgent, String log) throws RemoteException, IOException
	{
		System.out.println("Coordinateur : agent " + idAgent + " signale l'objectif atteint :)");
		// classement de l'agent à mettre à jour
		this.classementsAgents.add(idAgent);
		this.terminaisonsAgents[idAgent] = true;
		try
		{
			this.historique.ecrireLigne(log);
		} catch (IOException ioe) { ioe.printStackTrace(); }
		
		// vérification de la fin
		if (this.finPremierAgent)
		{
			// fin quand le premier agent termine
			terminerJeu();
		}
		else
		{
			if(verifierTerminaisonTousLesAgents())
			{
				// sinon tous les agents ont fini
				terminerJeu();
			}
			else
			{
				lancementProchainAgent();
			}
		}
		
	}
	
	/*
	 * Fonction 	: verifierTerminaisonTousLesAgents
	 * Argument(s)	: /
	 * Résultat(s)	: /
	 * Commentaires	: retourne vrai si tous les agents ont fini faux sinon
	 */
	public boolean verifierTerminaisonTousLesAgents()
	{
		// si tous les agents ont fini on stoppe le jeu
		for (int i = 0; i < this.nbAgents; i++)
		{
			if (!terminaisonsAgents[i])
			{
				// au moins un agent n'a pas fini
				this.prochainAgentAJouer = (this.prochainAgentAJouer + 1)%this.nbAgents;
				return false;
			}
		}
		return true;
	}
	
	 
	/*
	 * Fonction 	: reporterTentativeVol
	 * Argument(s)	: l'id de l'agent voleur
	 * Résultat(s)	: /
	 * Commentaires	: appelé par les agents pour reporter un vol observé
	 * appelle la fonction punir sur l'agent en question
	 */
	public void reporterTentativeVol(int idAgent) throws RemoteException
	{
		System.out.println("Coordinateur : tentative de vol de l'agent " + idAgent + " reportée");
		punirAgent(idAgent);
	}
	
	/*
	 * Fonction 	: signalerObjectifAtteint
	 * Argument(s)	: l'id de l'agent signalant l'objectif atteint
	 * Résultat(s)	: /
	 * Commentaires	: termine le jeu si la politique est que le premier agent 
	 * ayant terminé marque la fin et sinon attends qua tous les agents aient terminés
	 */
	 public void punirAgent(int idAgent)
	 {
		 this.punitionsAgents[idAgent] = 3;	// 3 tours de non jeu
		 System.out.println("Coordinateur : agent d'indice " + idAgent + " passe son tour pour les " + this.punitionsAgents[idAgent] + " prochain(s) tour(s)");
	 }
	 
	 public static void main(String [] args) throws IOException
	{
		if (args.length != 5)
		{
			System.out.println("Usage : java objetCoordinateur <port du rmiregistry> <nbAgents> <nbProducteurs> <finPremierAgent> <fichierLog>");
			System.exit(0) ;
		}
		try
		{
			int nbAgents = Integer.parseInt(args[1]);
			int nbProducteurs = Integer.parseInt(args[2]);
			boolean finPremierAgent = Boolean.parseBoolean(args[3]);
			CoordinateurImpl objLocal = new CoordinateurImpl(nbAgents, nbProducteurs, finPremierAgent, args[4]);
			Naming.rebind( "rmi://localhost:" + args[0] + "/coordinateur" ,objLocal) ;
			System.out.println("Coordinateur pret") ;
		}
		catch (RemoteException re) { System.out.println(re) ; }
		catch (MalformedURLException e) { System.out.println(e) ; }
	}
}
