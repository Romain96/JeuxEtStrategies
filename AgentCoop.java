import java.rmi.RemoteException ;

public class AgentCoop extends AgentImpl
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
	public AgentCoop(int idAgent, String typeRessource, int quantiteRessource, int objectif) throws RemoteException
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
	 * Commentaires	: choisi l'action à effectuer pour ce tour suivant la personnalité coopératif
	 * les actions possibles sont : acquérirRessources, voler, surveillance et reporterVol
	 * doit terminer en appelant soit signalerFinTour soit signalerObjectifAtteint
	 */
	 public void choixAction()
	 {
		 /*
		  * Un agent coopératif aura tendance à tenter d'acquérir des ressources
		  * et à défaut de pouvoir acquérir des ressources, se mettra en mode surveillance (pas de vol)
		  */
		  
		// tentative d'acquisition de ressources (on teste avec 5)
		int ressourcesAcquises = producteurs[0].attribuerRessources(5);
		System.out.println("Agent " + this.idAgent + " : j'acquiers " + ressourcesAcquises + " exemplaires de la ressource " + typeRessource);
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
}
