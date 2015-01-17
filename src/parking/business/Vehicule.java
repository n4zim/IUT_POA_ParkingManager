package parking.business;

/**
 * Décrit un véhicule
 */
public class Vehicule {
	/**
	 * Numéro d'immatriculation du véhicule
	 */
	private String numeroImmatriculation;
	
	/**
	 * Marque du véhicule
	 */
	private String marque;
	
	/**
	 * Modèle du véhicule
	 */
	private String modele;
	
	/**
	 * Nom du propriétaire du véhicule
	 */
	private String proprietaire;
	
	/**
	 * Constructeur par défaut
	 */
	public Vehicule() {}
	
	/**
	 * Construit un véhicule
	 * 
	 * @param numeroImmatriculation Numéro d'immatriculation du véhicule
	 * @param marque Marque du véhicule
	 * @param modele Modèle du véhicule
	 * @param proprietaire Nom du propriétaire du véhicule
	 */
	public Vehicule(String numeroImmatriculation, String marque, String modele,
			String proprietaire) {
		this.numeroImmatriculation = numeroImmatriculation;
		this.marque = marque;
		this.modele = modele;
		this.proprietaire = proprietaire;
	}
	
	public String getNumeroImmatriculation() {
		return numeroImmatriculation;
	}

	public String getProprietaire() {
		return proprietaire;
	}
    
    /**
     * @return une String qui donne les informations sur le véhicule
     */
	@Override
	public String toString() {
		return "[numero d'mmatriculation: " + numeroImmatriculation
				+ ", marque: " + marque + ", modele: " + modele
				+ ", proprietaire: " + proprietaire + "]";
	}
}
