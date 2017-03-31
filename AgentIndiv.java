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
	
	// demarrerTour n'a pas besoin d'être redéfinie	
	// idem pour finir tour
	
	// permet à l'agent d'acquérir les ressources demandées
	public void acquerirRessources(String type, int nb)
	{
		System.out.println("Agent " + getIdAgent() + " : j'acquiers " + nb + " exemplaires de la ressource " + type);
		System.out.println("Je suis égoïste et avare aussi");
	}
	
	// surveillance n'a pas besoin d'être rédéfinie
	
	// permet à l'agent de tenter de voler un autre agent
	public void voler(int idAgent, String type, int nb)
	{
		System.out.println("Agent " + getIdAgent() + " : je tente de voler " + nb + " exemplaires de la ressource " + type + " à l'agent " + idAgent);
		System.out.println("Je suis égoïste et avare aussi");
	}
}
