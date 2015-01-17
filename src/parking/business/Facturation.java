package parking.business;

/**
 * Informations nécessaires à la création d'une facture
 */
public class Facturation {
	private String nomClient;
	private double tarif;
	
	public Facturation(String nomClient, double tarif) {
		this.nomClient = nomClient;
		this.tarif = tarif;
	}
	
	public String getNomClient() {
		return nomClient;
	}
	
	public double getTarif() {
		return tarif;
	}
	
}
