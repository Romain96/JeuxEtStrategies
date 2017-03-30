public class AgentCoop extends Agent
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
	public AgentCoop(int id, int port, Ressource objectifs[])
	{
		this.id = id;
		this.port = port;
		this.objectifs = objectifs;	// initialisation des objectifs
		this.ressources = objectifs;	// idem il suffit simplement de mettre les quantités à 0
		for (int i = 0; i < objectifs.length(); i++)
		{
			objectifs[i].setNb(0);	// aucune ressource d'aucun type au départ
		}
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
		System.out.println("Agent " + idAgent + " : j'acquiers " + nb + " exemplaires de la ressource " + type);
		System.out.println("Je suis coopératif et altruiste aussi");
	}
	
	// surveillance n'a pas besoin d'être rédéfinie
	
	// permet à l'agent de tenter de voler un autre agent
	public void voler(int idAgent, String type, int nb)
	{
		System.out.println("Agent " + this.idAgent + " : je tente de voler " + nb + " exemplaires de la ressource " + type + " à l'agent " + idAgent);
		System.out.println("Je suis coopératif et altruiste aussi");
	}
}