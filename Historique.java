import java.io.File;
import java.io.FileOutputStream;
import java.io.BufferedWriter;
import java.io.IOException;

public class Historique
{
	//----------------------------------------------------------------------
	//				attributs
	//----------------------------------------------------------------------
	
	private final String nom = "historique.csv";	// nom du fichier à écrire
	private FileOutputStream fos;
	private BufferedWriter bw;
	
	//----------------------------------------------------------------------
	//				constructeur
	//----------------------------------------------------------------------
	
	public Historique()
	{
		System.out.println("Création du fichier " + this.nom + " pour stocker l'historique");
		File fout = new File(this.nom);
		this.fos = new FileOutputStream(fout);
		this.bw = new BufferedWriter(new OutputStreamWriter(this.fos));
	}
	
	//----------------------------------------------------------------------
	//				getters
	//----------------------------------------------------------------------
	
	public File getFile()
	{
		return this.fout;
	}
	
	public FileOutputStream getFileOutputStream()
	{
		return this.fos;
	}
	
	public BufferedWriter getBufferedWriter()
	{
		return this.bw;
	}
	
	//----------------------------------------------------------------------
	//				setters
	//----------------------------------------------------------------------
	
	public void setFile(File fout)
	{
		this.fout = fout;
	}
	
	public setFileOutputStream(FileOutputStream fos)
	{
		this.fos = fos;
	}
	
	public setBufferedWriter(BufferedWriter bw)
	{
		this.bw = bw;
	}
	
	//----------------------------------------------------------------------
	//				méthodes
	//----------------------------------------------------------------------
	
	public static void ecrireLigne(int idAgent, int temps, String typeRessource, int quantiteRessource) throws IOException 
	{ 
		String ligneAEcrire = "" + idAgent + "," + temps + "," + typeRessource + "," + quantiteRessource;
		bw.write(ligneAEcrire);
		bw.newLine();
	}
	
	public void fermerFichier()
	{
		this.bw.close();
	}
	
	// pour les tests
	public static void main(String[] args)
	{
		Historique his = new Historique();
		his.ecrireLigne(0,0,"gold",0);
		his.ecrireLigne(0,1,"gold",5);
		his.ecrireLigne(0,2,"gold",10);
		his.ecrireLigne(0,3,"gold",15);
		his.fermerFichier();
	}
}
