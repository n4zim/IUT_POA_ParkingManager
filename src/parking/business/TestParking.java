package parking.business;

import parking.exception.PlaceInexistanteException;
import parking.exception.PlaceLibreException;

public class TestParking {
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

		System.out.println("v2 est dans le parking : " + p.vehiculeExiste(v2));
		System.out.println("v5 est dans le parking : " + p.vehiculeExiste(v5));

		p.EtatParking();
		
		try {
			p.unpark(p.getLocation(v2.getNumeroImmatriculation()));
		} catch (PlaceLibreException e) {
			e.printStackTrace();
		} catch (PlaceInexistanteException e) {
			e.printStackTrace();
		}
		
	}
}
