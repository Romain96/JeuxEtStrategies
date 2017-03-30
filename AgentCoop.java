public class AgentCoop extends Agent
{
	//----------------------------------------------------------------------
	//				attributs
	//----------------------------------------------------------------------
	
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