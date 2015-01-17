package parking.business;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintStream;

public class Facture {
	/**
	 * Numéro de cette facture
	 */
	private int numFacture;
	
	/**
	 * Tarif hors taxe
	 */
	private double tarifHT;
	
	/**
	 * Tarif toutes taxes comprises
	 */
	private double tarifTTC;
	
	/**
	 * Construit une facture
	 * 
	 * @param numFacture Le numéro de la facture
	 * @param tarifHT Le tarif hors taxe
	 */
	public Facture(int numFacture, double tarifHT) {
		this.numFacture = numFacture;
		this.tarifHT = tarifHT;
		this.tarifTTC = tarifHT * Constante.TVA; 
	}
	
	public void sauverDansFichier(String nomFichier) {
		File fichier = new File(Constante.DOSSIER_FACTURES+File.pathSeparator+nomFichier);
		
		try {
			fichier.mkdirs();
			PrintStream out = new PrintStream(fichier);
			out.println(toString());		
			out.close();
		} catch (SecurityException e) {
			System.out.println("Erreur : impossible de créer le dossier");
			e.printStackTrace();
		} catch (FileNotFoundException e) {
			System.out.println("Erreur : impossible d'écrire dans le fichier.");
			e.printStackTrace();
		}
	}
	
	public String toString() {
		String output = "=== FACTURE " + numFacture + " ===\n";
		output += " Tarif HT  : " + tarifHT;
		output += " Tarif TTC : " + tarifTTC;
		
		return output;
	}
}
