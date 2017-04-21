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
		
		try
		{
		  
			// tentative d'acquisition de ressources (on teste avec 5)
			int ressourcesAcquises = getProducteurAtPos(0).attribuerRessources(5);
			System.out.println("Agent " + getIdAgent() + " : j'acquiers " + getQuantiteRessource() + " exemplaires de la ressource " + getTypeRessource());
			setQuantiteRessource(getQuantiteRessource() + ressourcesAcquises);
			
			if (getQuantiteRessource() >= getObjectif() )
			{
				System.out.println("Agent " + getIdAgent() + " : je possède " + getQuantiteRessource() + " exemplaires de la ressource " + getTypeRessource() + "/" + getObjectif());
				getCoordinateur().signalerObjectifAtteint(getIdAgent());
			}
			else
			{
				System.out.println("Agent " + getIdAgent() + " : je possède " + getQuantiteRessource() + " exemplaires de la ressource " + getTypeRessource() + "/" + getObjectif());
				getCoordinateur().signalerFinTour(getObjectif());
			}
		}
		catch (NotBoundException re) { System.out.println(re) ; }
		catch (RemoteException re) { System.out.println(re) ; }
		catch (MalformedURLException e) { System.out.println(e) ; }
	 }
}
