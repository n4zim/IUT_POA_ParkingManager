package parking.business;

import java.util.Collection;
import java.util.Map;

import parking.exception.*;

public class TestProgramme {

	private void myAssert(boolean assertion) {
		if(assertion)
			System.out.println(" - OK - ");
		else
			System.out.println(" - ERREUR - ");
	}
	
	private void testerVehicules() {
		System.out.println("-- Test des véhicules --\n");
		
		final String numeroImmat = "ABCDEF";
		final String nomProp = "Jean Beurre-Geurre";
		
		Vehicule moto = new Moto(numeroImmat, "marquise", "j'ai une frite qui déconne", nomProp);
		Vehicule camion = new Camion(numeroImmat, "marque", "modele", nomProp);
		Vehicule voiture = new Voiture(numeroImmat, "marque", "modele", nomProp);
		
		/* MOTO */
		System.out.print("Test de getNumeroImmatriculation() sur moto");
		myAssert(moto.getNumeroImmatriculation().equals(numeroImmat));
		
		System.out.print("Test de getProprietaire() sur moto");
		myAssert(moto.getProprietaire().equals(nomProp));

		System.out.println();
		/* CAMION */
		System.out.print("Test de getNumeroImmatriculation() sur camion");
		myAssert(camion.getNumeroImmatriculation().equals(numeroImmat));
		
		System.out.print("Test de getProprietaire() sur camion");
		myAssert(camion.getProprietaire().equals(nomProp));
		
		System.out.println();
		/* VOITURE */
		System.out.print("Test de getNumeroImmatriculation() sur voiture");
		myAssert(voiture.getNumeroImmatriculation().equals(numeroImmat));
		
		System.out.print("Test de getProprietaire() sur voiture");
		myAssert(voiture.getProprietaire().equals(nomProp));
		
		System.out.println();		
	}
	
	public void testerPlace() {
		System.out.println("-- Test de place --\n");
		
	}
	
	public boolean verifierVehiculeDansParking(Parking p, Vehicule v) {
		Collection<Place> places = p.getPlacesMap().values();
		
		for (Place place : places) {
			if(place.getVehiculeGare().equals(v))
				return true;
		}

		return false;
	}
	
	
	public void testerParking() {
		Parking p = Parking.getInstance();
		
		Vehicule moto = new Moto("3D", "marquise", "j'ai une frite qui déconne", "Jean Beurre-Gueurre");
		Vehicule camion = new Camion("fqsdfas", "marque", "modele", "Jean Aimar");
		Vehicule voiture = new Voiture("acxv", "marque", "modele", "John Stone");
		Vehicule vehicule = new Vehicule("g43ewvrsd", "marque", "modele", "John Michael");
		Vehicule v5 = new Voiture("FAf90ajsd", "marque", "modele", "John D'oeuf");
		
		
		System.out.println("-- Test de park --\n");

		/* Places */
		System.out.println("1ère place place particulier ");
		
		
		
		System.out.println();
		/* Park */
		System.out.println("Garer n'importe quel type de véhicule :");
		try {
			p.park(moto);
			System.out.print("Moto");
			myAssert(verifierVehiculeDansParking(p, moto));

			p.park(camion);
			System.out.print("Camion");
			myAssert(verifierVehiculeDansParking(p, camion));

			p.park(voiture);
			System.out.print("Voiture");
			myAssert(verifierVehiculeDansParking(p, voiture));
			
			p.park(vehicule);
			System.out.print("Vehicule");
			myAssert(verifierVehiculeDansParking(p, vehicule));		
			
		} catch (PlusAucunePlaceException e) {
		}		
		
		try {
			p.park(moto);
			p.park(camion, 3);
			p.park(voiture);
			p.park(vehicule);
			p.bookPlace(v5);

		} catch (parking.exception.ParkingException e) {
			e.printStackTrace();
		}

		System.out.println("v2 est dans le parking : " + p.vehiculeExiste(camion));
		System.out.println("v5 est dans le parking : " + p.vehiculeExiste(v5));

		p.EtatParking();
		
		try {
			p.unpark(p.getLocation(camion.getNumeroImmatriculation()));
		} catch (PlaceLibreException e) {
			e.printStackTrace();
		} catch (PlaceInexistanteException e) {
			e.printStackTrace();
		}
		
	}
	
	public static void main(String[] args) {
		TestProgramme testParking = new TestProgramme();
		testParking.testerVehicules();
		testParking.testerParking();
	}
}
