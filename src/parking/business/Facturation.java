package parking.business;

/**
 * Informations nécessaires à la création d'une facture
 */
public class Facturation {
    
    /**
     * Nom du client
     */
	private String nomClient;
    
    /**
     * Tarif appliqué
     */
	private double tarif;
	
    /**
     * Construit une facturation
     */
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
