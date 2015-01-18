package parking.business;

/**
 * Informations nécessaires à la création d'une facture
 * Sert principalement au stockage d'informations.
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
	
	/**
	 * Retourne le nom du client
	 * @return nom du client
	 */
	public String getNomClient() {
		return nomClient;
	}
	
	/**
	 * Retourne le tarif associé à la facture
	 * @return le tarfif HT
	 */
	public double getTarif() {
		return tarif;
	}
	
}
