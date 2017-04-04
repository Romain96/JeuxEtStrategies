public class Ressource
{
	//----------------------------------------------------------------------
	//				attributs
	//----------------------------------------------------------------------
	
	private String type;	// type de ressource
	private int quantite;	// nombre de cette ressource
	
	//----------------------------------------------------------------------
	//				constructeur
	//----------------------------------------------------------------------
	
	// constructeur standard
	public Ressource(String type, int quantite)
	{
		this.type = type;
		this.quantite = quantite;
	}
	
	//----------------------------------------------------------------------
	//				getters
	//----------------------------------------------------------------------
	
	public String getType()
	{
		return this.type;
	}
	
	public int getQuantite()
	{
		return this.quantite;
	}
	
	//----------------------------------------------------------------------
	//				setters
	//----------------------------------------------------------------------
	
	public void setType(String type)
	{
		this.type = type;
	}
	
	public void setQuantite(int quantite)
	{
		this.quantite = quantite;
	}
	
	//----------------------------------------------------------------------
	//				méthodes
	//----------------------------------------------------------------------
	
	// rien
}
