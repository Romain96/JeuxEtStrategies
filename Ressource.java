public class Ressource
{
	//----------------------------------------------------------------------
	//				attributs
	//----------------------------------------------------------------------
	
	private String typeRessource;	// type de ressource
	private int quantiteRessource;	// nombre de cette ressource
	
	//----------------------------------------------------------------------
	//				constructeur
	//----------------------------------------------------------------------
	
	// constructeur standard
	public Ressource(String typeRessource, int quantiteRessource)
	{
		this.typeRessource = typeRessource;
		this.quantiteRessource = quantiteRessource;
	}
	
	//----------------------------------------------------------------------
	//				getters
	//----------------------------------------------------------------------
	
	public String getTypeRessource()
	{
		return this.typeRessource;
	}
	
	public int getQuantiteRessource()
	{
		return this.quantiteRessource;
	}
	
	//----------------------------------------------------------------------
	//				setters
	//----------------------------------------------------------------------
	
	public void setTypeRessoure(String typeRessource)
	{
		this.typeRessource = typeRessource;
	}
	
	public void setQuantiteRessource(int quantiteRessource)
	{
		this.quantiteRessource = quantiteRessource;
	}
	
	//----------------------------------------------------------------------
	//				méthodes
	//----------------------------------------------------------------------
	
	// rien
}
