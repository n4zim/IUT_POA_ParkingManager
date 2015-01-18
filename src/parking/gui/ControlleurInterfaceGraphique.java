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
	
	/**
	 * Construit les interfaces
	 * @param parking le parking à gérer
	 */
	public ControlleurInterfaceGraphique(Parking parking) {
		this.parking = parking;
		
		pcv = new ParkingControlView();
		pv = new ParkingView(this);
		
		pcv.afficher();
		notifyParkingStateChanged();
	}
	
	/**
	 * Rend le parking
	 * @return
	 */
	public Parking getParking() {
		return parking;
	}
	
	public void notifyParkingStateChanged() {
		pv.parkingStateChanged(parking.getPlacesMap());
		
		System.out.println("Parking modifié : ");
		parking.EtatParking();
	}
	
	public Vehicule demanderVehicule() {
		String immat = JOptionPane.showInputDialog("Numéro d'immatriculation");
		String marque = JOptionPane.showInputDialog("Marque");
		String modele = JOptionPane.showInputDialog("Modèle");
		String proprio = JOptionPane.showInputDialog("Nom du propriétaire");
		
		return new Vehicule(immat, marque, modele, proprio);
	}
	
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

	public void libererPlace(Integer numPlace) throws PlaceLibreException {
		try {
			parking.unpark(numPlace);
		} catch (PlaceInexistanteException e) {
			e.printStackTrace();
		}
		
		notifyParkingStateChanged();
	}

	public void garerVehicule(Vehicule aGarer, Integer place) throws TypePlaceInvalideException, PlaceOccupeeException, PlaceReserveeException {
		try {
			parking.park(aGarer, place);
		} catch (PlaceInexistanteException e) {
			e.printStackTrace();
		}
		notifyParkingStateChanged();
	}
}