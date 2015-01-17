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
	 * Nom du client pour qui les factures sont générées
	 */
	private String nomClient;
	
	/**
	 * Construit une facture
	 * 
	 * @param numFacture Le numéro de la facture
	 * @param tarifHT Le tarif hors taxe
	 */
	public Facture(int numFacture, Facturation facturation) {
		this.numFacture = numFacture;
		this.tarifHT = facturation.getTarif();
		this.tarifTTC = this.tarifHT * Constante.TVA;
		this.nomClient = facturation.getNomClient();
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
		output += " Client : " + ((nomClient == null) ? "Annonymous" : "") + "\n";
		output += " Tarif HT  : " + tarifHT + "\n";
		output += " Tarif TTC : " + tarifTTC + "\n";
		
		return output;
	}
}
