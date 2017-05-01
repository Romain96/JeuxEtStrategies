import java.io.File;
import java.io.FileOutputStream;
import java.io.BufferedWriter;
import java.io.OutputStreamWriter;
import java.io.IOException;
import java.io.FileNotFoundException;

public class Historique
{
	//----------------------------------------------------------------------
	//				attributs
	//----------------------------------------------------------------------
	
	private String nom = "historique.csv";	// nom du fichier à écrire
	private FileOutputStream fos;
	private BufferedWriter bw;
	
	//----------------------------------------------------------------------
	//				constructeur
	//----------------------------------------------------------------------
	
	public Historique(String nom)
	{
		setNom(nom);
		System.out.println("Création du fichier " + this.nom + " pour stocker l'historique");
		try 
		{
			File fout = new File(this.nom);
			this.fos = new FileOutputStream(fout);
			this.bw = new BufferedWriter(new OutputStreamWriter(this.fos));
		} catch (FileNotFoundException fnfe) { fnfe.printStackTrace(); }
	}
	
	//----------------------------------------------------------------------
	//				getters
	//----------------------------------------------------------------------
	
	public String getNom()
	{
		return this.nom;
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
	
	public void setNom(String nom)
	{
		this.nom = nom;
	}
	
	public void setFileOutputStream(FileOutputStream fos)
	{
		this.fos = fos;
	}
	
	public void setBufferedWriter(BufferedWriter bw)
	{
		this.bw = bw;
	}
	
	//----------------------------------------------------------------------
	//				méthodes
	//----------------------------------------------------------------------
	
	public void ecrireLigne(String log) throws IOException 
	{ 
		try
		{
			bw.write(log);
			bw.newLine();
		} catch (IOException ioe) { ioe.printStackTrace(); }
	}
	
	public void fermerFichier()
	{
		try
		{
			this.bw.close();
		} catch (IOException ioe) { ioe.printStackTrace(); }
	}
}
