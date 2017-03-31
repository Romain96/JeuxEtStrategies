public class Ressource
{
	//----------------------------------------------------------------------
	//				attributs
	//----------------------------------------------------------------------
	
	private String type;	// type de ressource
	private int nb;		// nombre de cette ressource
	
	//----------------------------------------------------------------------
	//				constructeur
	//----------------------------------------------------------------------
	
	// constructeur par défaut, n'a pas beaucoup de sens
	public Ressource()
	{
		this.type = "defaut";
		this.nb = 0;
	}
	
	// constructeur sans type de ressource 
	// (pour les tests avec un seul type de ressources)
	public Ressource(int nb)
	{
		this.type = "defaut";
		this.nb = nb;
	}
	
	// constructeur standard
	public Ressource(String type, int nb)
	{
		this.type = type;
		this.nb = nb;
	}
	
	//----------------------------------------------------------------------
	//				getters
	//----------------------------------------------------------------------
	
	public String getType()
	{
		return this.type;
	}
	
	public int getNb()
	{
		return this.nb;
	}
	
	//----------------------------------------------------------------------
	//				setters
	//----------------------------------------------------------------------
	
	public void setType(String type)
	{
		this.type = type;
	}
	
	public void setNb(int nb)
	{
		this.nb = nb;
	}
	
	//----------------------------------------------------------------------
	//				méthodes
	//----------------------------------------------------------------------
	
	// rien
}