package parking.business;

public class Camion extends Vehicule {

	/**
	 * Constructeur
	 * 
	 * @param numeroImmatriculation Numéro d'immatriculation
	 * @param marque Marque
	 * @param modele Modèle
	 * @param proprietaire Nom du propriétaire
	 */
	public Camion(String numeroImmatriculation, String marque, String modele,
			String proprietaire) {
		super(numeroImmatriculation, marque, modele, proprietaire);
	}
	
	@Override
	public String toString() {
		return "Camion : " + super.toString();
	}
}
