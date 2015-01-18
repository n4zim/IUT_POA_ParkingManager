package parking.business;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
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
	
    /**
	 * Sauvegarde une facture dans un fichier
	 * @param nomFichier le nom du fichier de destination
	 */
	public void sauverDansFichier(String nomFichier) {
		File dossier = new File(Constante.DOSSIER_FACTURES);
		File fichier = new File(Constante.DOSSIER_FACTURES+File.separator+nomFichier);
		
		try {
			dossier.mkdirs();
			if(!fichier.exists()) {
			    fichier.createNewFile();
			} 
			
			PrintStream out = new PrintStream(fichier);
			out.println(toString());
			out.close();
		} catch (SecurityException e) {
			System.out.println("Erreur : impossible de créer le dossier");
			e.printStackTrace();
		} catch (FileNotFoundException e) {
			System.out.println("Erreur : impossible d'écrire dans le fichier.");
			e.printStackTrace();
		} catch (IOException e) {
			System.out.println("Erreur : I/O error");
		}
	}
	
    /**
	 * Sauvegarde une facture dans un fichier nommé automatiquement
	 */
	public void sauverDansFichier() {
		sauverDansFichier(numFacture + " - " + ((nomClient == null) ? "" : nomClient));
	}
	
    /**
     * @return une String représentant la facture
     */
	public String toString() {
		return "=== FACTURE " + numFacture + " ===\n"
               + ((nomClient == null) ? "Client non enregistré" : " Client : " + nomClient) + "\n"
               + " Tarif HT  : " + tarifHT + "\n"
               + " Tarif TTC : " + tarifTTC + "\n";
	}
}
