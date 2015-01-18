package parking.gui;
import javax.swing.*;

import parking.business.*;
import parking.exception.*;

/**
 * Orchestre la GUI en créeant et syncrhonisant les différentes fenêtres
 */
public class ControlleurInterfaceGraphique extends JFrame {
	private static final long serialVersionUID = 1L;

	/**
	 * Fenêtre avec les menus
	 */
	ParkingControlView pcv;
	/**
	 * Fenêtre affichant l'état du parking
	 */
	ParkingView pv;
	/**
	 * Parking à gérer
	 */
	Parking parking;
	
	GestionVehicule gVehicules;
	
	
	/**
	 * Construit les interfaces
	 * @param parking le parking à gérer
	 */
	public ControlleurInterfaceGraphique(Parking parking) {
		this.parking = parking;
		
		pcv = new ParkingControlView();
		pv = new ParkingView(this);
		gVehicules = new GestionVehicule();
		
		pcv.afficher();
		notifyParkingStateChanged();
	}
	
	/**
	 * Retourne l'état du parking
	 * @return parking
	 */
	public Parking getParking() {
		return parking;
	}
	
	/**
	 * Doit être appellée quand l'état du parking change. Se charge de mettre à jour les fenêtres qui ont besoin de l'état du parking.
	 **/
	public void notifyParkingStateChanged() {
		pv.parkingStateChanged(parking.getPlacesMap());
		
		System.out.println("Parking modifié : ");
		parking.EtatParking();
	}
	
	/**
	 * Affiche la fenêtre pour sélectionner un véhicule
	 */
	public void demanderVehicule() {
		gVehicules.afficher();
	}

	/**
	 * Se cherge de libérer la place de parking indiquée
	 * @param numPlace Numéro de la place à libérer
	 * @throws PlaceLibreException La place est déjà libre
	 */
	public void libererPlace(Integer numPlace) throws PlaceLibreException {
		try {
			parking.unpark(numPlace);
		} catch (PlaceInexistanteException e) {
			e.printStackTrace();
		}
		
		notifyParkingStateChanged();
	}

	/**
	 * Se charge de garer un véhicule à la place indiquée
	 * @param aGarer le véhicule
	 * @param place le numéro de place
	 * @throws TypePlaceInvalideException La place n'est pas adaptée à ce type de véhicule
	 * @throws PlaceOccupeeException La place est déjà occupée
	 * @throws PlaceReserveeException La place est réservée
	 */
	public void garerVehicule(Vehicule aGarer, Integer place) throws TypePlaceInvalideException, PlaceOccupeeException, PlaceReserveeException {
		try {
			parking.park(aGarer, place);
		} catch (PlaceInexistanteException e) {
			e.printStackTrace();
		}
		notifyParkingStateChanged();
	}
	
	public GestionVehicule getGestionVechicule() {
		return gVehicules;
	}
	
	/**
	 * Programme principal
	 * @param args
	 */
	public static void main(String[] args) {
		
		Parking p = Parking.getInstance();
		Vehicule v1 = new Moto("1234", "marque", "modele", "John Stone");
		Vehicule v2 = new Voiture("acxv", "marque", "modele", "John Stoned");
		Vehicule v3 = new Camion("fqsdfas", "marque", "modele", "John Aimar");
		Vehicule v4 = new Voiture("g43ewvrsd", "marque", "modele", "John Michael");
		Vehicule v5 = new Voiture("FAf90ajsd", "marque", "modele", "John D'oeuf");
		
		try {
			p.park(v1);
			p.park(v2, 3);
			p.park(v3);
			p.park(v4);
			p.bookPlace(v5);
		} catch (parking.exception.ParkingException e) {
			e.printStackTrace();
		}
		
		new ControlleurInterfaceGraphique(p);
	}
}