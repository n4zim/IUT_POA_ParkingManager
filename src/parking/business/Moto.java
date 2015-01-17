package parking.business;

/**
 * Décrit une moto
 */
public class Moto extends Vehicule {
	
	/**
	 * Constructeur
	 * 
	 * @param numeroImmatriculation Numéro d'immatriculation
	 * @param marque Marque
	 * @param modele Modèle
	 * @param proprietaire Nom du propriétaire
	 */
	public Moto(String numeroImmatriculation, String marque, String modele,
			String proprietaire) {
		super(numeroImmatriculation, marque, modele, proprietaire);
	}
	
	@Override
	public String toString() {
		return "Moto : " + super.toString();
	}
}
