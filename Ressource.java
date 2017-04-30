public class Ressource
{
	//----------------------------------------------------------------------
	//				attributs
	//----------------------------------------------------------------------
	
	private String typeRessource;	// type de ressource
	private int quantiteRessource;	// nombre de cette ressource
	private int objectifRessource;	// objectif de quantité à atteindre
	
	//----------------------------------------------------------------------
	//				constructeur
	//----------------------------------------------------------------------
	
	// constructeur standard
	public Ressource(String typeRessource, int quantiteRessource, int ObjectifRessource)
	{
		this.typeRessource = typeRessource;
		this.quantiteRessource = quantiteRessource;
		this.objectifRessource = objectifRessource;
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
	
	public int getObjectifRessource()
	{
		return this.objectifRessource;
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
	
	public void setObjectifRessource(int objectifRessource)
	{
		this.objectifRessource = objectifRessource;
	}
	
	//----------------------------------------------------------------------
	//				méthodes
	//----------------------------------------------------------------------
	
	// rien
}
