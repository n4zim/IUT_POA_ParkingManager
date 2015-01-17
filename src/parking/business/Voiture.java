package parking.business;

/**
 * Décrit une voiture
 */
public class Voiture extends Vehicule {

	/**
	 * Constructeur
	 * 
	 * @param numeroImmatriculation Numéro d'immatriculation
	 * @param marque Marque
	 * @param modele Modèle
	 * @param proprietaire Nom du propriétaire
	 */
	public Voiture(String numeroImmatriculation, String marque, String modele,
			String proprietaire) {
		super(numeroImmatriculation, marque, modele, proprietaire);
	}
	
    /**
     * @return une String qui donne le type de véhicule et ses informations
     */
	@Override
	public String toString() {
		return "Voiture : " + super.toString();
	}

}
