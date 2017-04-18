import java.rmi.server.UnicastRemoteObject ;
import java.rmi.RemoteException ;
import java.rmi.* ; 
import java.net.MalformedURLException ; 

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
	
	// indique si le jeu a démarré ou non
	boolean jeuEnCours;
	
	// indique l'indice du prochain agent à faire démarer (dans l'ordre d'enregistrement pas d'indice des agents)
	int prochainAgentAJouer;
	
	Ressource ressources;		// liste des ressources possédées
	
	//----------------------------------------------------------------------
	//				cosntructeur
	//----------------------------------------------------------------------
	
	public CoordinateurImpl(int nbAgents, int nbProducteurs, Ressource ressources) throws RemoteException
	{
		// initialisation du tableau d'agents
		this.agents = new Agent[nbAgents];	// les agents s'identifiront au coordinateur à l'initialisation
		this.nbAgents = nbAgents;
		this.nbAgentsEnregistres = 0;		// aucun agent enregistré pour l'instant (le coordinateur est lancé en premier)
		
		// initialisation du tableau des producteurs
		this.producteurs = new Producteur[nbProducteurs];	// les producteurs s'identifiront au coordinateur à l'initialisation
		this.nbProducteurs = nbProducteurs;
		this.nbProducteursEnregistres = 0;			// aucun producteur enregistré pour l'instant (le coordinateur est lancé en premier)
		
		// initialisation des ressources
		this.ressources = ressources;
		
		// le jeu n'a pas encore commencé (les agents/producteurs doivent d'abord s'inscrirent)
		jeuEnCours = false;
		
		// il n'y a pas d'agent à faire jouer pour l'instant donc le prochain sera le 1er enregistré
		prochainAgentAJouer = 0;
	}
	
	//----------------------------------------------------------------------
	//				getters
	//----------------------------------------------------------------------
	
	// retourne le nombre d'agents
	public int getNbAgents()
	{
		return nbAgents;
	}
	
	// retourne le nombre de producteurs
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
	
	// retourne le nombre courant de la ressouce type ou -1 si non trouvée
	public int getQuantiteRessource()
	{
		return ressources.getQuantite();
	}
	
	
	//----------------------------------------------------------------------
	//				setters
	//----------------------------------------------------------------------
	
	// positionne le nombre d'agents
	public void setNbAgents(int nbAgents)
	{
		this.nbAgents = nbAgents;
	}
	
	// positionne le nombre de producteurs
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
	
	// positionne le nombre de ressources de la ressource type
	public void setQuantiteRessource(int quantite)
	{
		ressources.setQuantite(quantite);
	}
	
	//----------------------------------------------------------------------
	//				methodes
	//----------------------------------------------------------------------
	
	/* boucle principale du coordinateur
	 * appelle chaque agent à tour de rôle avec la méthode demarrerTour 
	 * attend la fin de tour de chaque agent avant de lancer le suivant avec signalerFinTour
	 * vérifie que la quantité de ressource transmise par chaque agent est inférieure au but fixé
	 * si un agent a terminé alors demande à tous les agents et tous les producteurs de se terminer avec terminerJeu
	 */
	 public void demarrerJeu()
	 {
		 // positionne la variable jeuEnCours à vrai
		 jeuEnCours = true;
		 
		 // tramsmet à tous les agents la liste des producteurs (en réalité seulement le nombre de producteurs)
		 for (int i = 0; i < nbAgents; i++)
		 {
			 agents[i].signalerNbAgentsEtProducteurs(nbAgents, nbProducteurs);
		 }		
		  
		 // lance le 1er agent
		 agents[prochainAgentAJouer].demarrerTour();
		 System.out.println("Coordinateur : lancement de l'agent d'indice " + prochainAgentAJouer);
		 
		 prochainAgentAJouer = (prochainAgentAJouer + 1)%nbAgents;
		 System.out.println("Coordinateur : prochain agent à jouer sera d'indice " + prochainAgentAJouer);	 
	 }
	
	// vérifie si tous les agents et producteurs spécifiés sont enregistrés
	// et le cas échéant lance le jeu
	public void verificationLancementJeu()
	{
		if (nbAgentsEnregistres >= nbAgents && nbProducteursEnregistres >= nbProducteurs)
		{
			// DEBUG
			System.out.println("Coordinateur : prêt à lancer le jeu avec " + nbAgents + " agents et " + nbProducteurs + " producteurs");
			demarrerJeu();
		}
	}
	
	// verifie que l'agent a termine (ie que les ressources signalées soient <= au but visé)
	public void verifierTerminaisonJeu(String typeRessource, int quantiteRessource)
	{
		if (typeRessource == this.ressources.getType() && quantiteRessource >= this.ressources.getQuantite())
		{
			System.out.println("Coordinateur : agent terminé, jeu termine");
			for (int i = 0; i < nbAgents; i++)
			{
				agents[i].terminerJeu();	// les agents se terminent
			}
			for (int i = 0; i < nbProducteurs; i++)
			{
				producteurs[i].terminerJeu();	// les producteurs se terminent
			}
			System.exit(0);	// le coordinateur se termine
		}
	}
	
	// appelé par les agents pour s'enregistrer auprès du coordinateur
	public void identifierAgent(int idAgent) throws RemoteException
	{
		if (nbAgentsEnregistres < nbAgents )
		{
			try
			{
				System.out.println("Coordinateur : agent " + idAgent + " s'identifie" );
				// on réccupère l'agent enregistré comme étant l'agent "agent + id de l'agent"
				Agent agent = (Agent) Naming.lookup( "rmi://localhost:9000/agent" + idAgent );
				agents[nbAgentsEnregistres] = agent;
				nbAgentsEnregistres++;
				System.out.println("Coordinateur : il y a désormais " + nbAgentsEnregistres + "/" + nbAgents + " agents enregistrés" );
				verificationLancementJeu();
			}
			catch (NotBoundException re) { System.out.println(re) ; }
			catch (RemoteException re) { System.out.println(re) ; }
			catch (MalformedURLException e) { System.out.println(e) ; }	
		}
		else
		{
			System.out.println("Coordinateur : agent " + idAgent + " ne peut pas s'identifier : la liste est pleine !" );
		}
	}
	
	// appelé par les producteurs pour s'enregistrer auprès du coordinateur
	public void identifierProducteur(int idProducteur) throws RemoteException
	{
		if (nbProducteursEnregistres < nbProducteurs )
		{
			try
			{
				System.out.println("Coordinateur : producteur " + idProducteur + " s'identifie" );
				// on réccupère le producteur enregistré comme étant le producteur "producteur + id du producteur"
				Producteur producteur = (Producteur) Naming.lookup( "rmi://localhost:9000/producteur" + idProducteur );
				producteurs[nbProducteursEnregistres] = producteur;
				nbProducteursEnregistres++;
				System.out.println("Coordinateur : il y a désormais " + nbProducteursEnregistres + "/" + nbProducteurs + " producteurs enregistrés" );
				verificationLancementJeu();
			}
			catch (NotBoundException re) { System.out.println(re) ; }
			catch (RemoteException re) { System.out.println(re) ; }
			catch (MalformedURLException e) { System.out.println(e) ; }	
		}
		else
		{
			System.out.println("Coordinateur : producteur " + idProducteur + " ne peut pas s'identifier : la liste est pleine !" );
		}
	}
	
	// appelé par les agents pour signaler la fin de leur tour
	public void signalerFinTour(int idAgent) throws RemoteException
	{
		System.out.println("Coordinateur : agent " + idAgent + " signale la fin de son tour" );
		
		agents[prochainAgentAJouer].demarrerTour();
		System.out.println("Coordinateur : lancement de l'agent d'indice " + prochainAgentAJouer);
		 
		prochainAgentAJouer = (prochainAgentAJouer + 1)%nbAgents;
		System.out.println("Coordinateur : prochain agent à jouer sera d'indice " + prochainAgentAJouer);
	}
}
