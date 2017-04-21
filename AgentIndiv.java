import java.rmi.RemoteException ;

public class AgentIndiv extends AgentImpl
{
	//----------------------------------------------------------------------
	//				attributs
	//----------------------------------------------------------------------
	
	// hérités de Agent
	
	//----------------------------------------------------------------------
	//				Constructeur
	//----------------------------------------------------------------------
	
	// initialise un agent coopératif avec un id et un numéro de port pour
	// permettre d'agir comme serveur pour les autres agents et pour le coordinateur
	// et initialise ses objectifs de ressources avec le tableau passé en argument
	public AgentIndiv(int idAgent, String typeRessource, int quantiteRessource, int objectif) throws RemoteException
	{
		super(idAgent, typeRessource, quantiteRessource, objectif);
	}
	
	//----------------------------------------------------------------------
	//				getter
	//----------------------------------------------------------------------
	
	//----------------------------------------------------------------------
	//				setters
	//----------------------------------------------------------------------
	
	//----------------------------------------------------------------------
	//				méthodes
	//----------------------------------------------------------------------
	
	/*
	 * Les fonctions suivantes n'ont pas besoin d'être redéfinies : 
	 * - demarrerTour
	 * - enregistrerCoordinateur
	 * - signalerNbAgentsEtProducteurs
	 * - enregistrerAgents
	 * - enregistrerProducteurs
	 * - surveillance
	 * - voler
	 * - terminerJeu
	 * 
	 * Les fonctions suivantes doivent être redéfinies :
	 * - choixAction
	 */
	 
	 
	 /*
	 * Fonction 	: choixAction
	 * Argument(s)	: /
	 * Résultat(s)	: /
	 * Commentaires	: choisi l'action à effectuer pour ce tour suivant la personnalité individualiste
	 * les actions possibles sont : acquérirRessources, voler, surveillance et reporterVol
	 */
	 public void choixAction()
	 {
		 /*
		  * Un agent individualiste aura tendance à tenter d'acquérir des ressources systématiquement
		  * et à défaut de pouvoir acquérir des ressources, il tentera de les voler 
		  */
		  
		// tentative de vol (on teste avec 2)
		int ressourcesVolees = producteurs[0].voler(this.idAgent, this.typeRessource, 2);
		System.out.println("Agent " + this.idAgent + " : je vole " + ressourcesVolees + " exemplaires de la ressource " + typeRessource);
		this.quantiteRessource += ressourcesVolees;
		
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
}
