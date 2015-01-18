package parking.business;

import java.util.Collection;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;

import parking.exception.*;

public class TestProgramme {

	private void myAssert(boolean assertion) {
		if(assertion)
			System.out.println(" - OK - ");
		else {
			System.out.println(" - ERREUR - ");
			System.exit(-1);
		}
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
	
	public void testerParkingPlaces(Parking p) {
		Set<Integer> numPlaces = p.getPlacesMap().keySet();
		
		System.out.print("1er numéro de place = "+Constante.NUMERO_PREMIERE_PLACE);
		Iterator<Integer> itNumPlaces = numPlaces.iterator();
		myAssert(itNumPlaces.next().equals(Constante.NUMERO_PREMIERE_PLACE));

		System.out.print("Numéros de places continus");
		boolean numerosContinus = true;
		Integer dernierNumPlace = null;
		Integer nombrePlaces = 0;
		for (Iterator<Integer> iterator = numPlaces.iterator(); iterator.hasNext();) {
			Integer numPlace = iterator.next();
			
			if(dernierNumPlace != null) {
				if(!numPlace.equals(dernierNumPlace+1)) {
					numerosContinus = false;
				}
			}
				
			dernierNumPlace = numPlace;
			nombrePlaces++;
		}
		myAssert(numerosContinus);

		System.out.print("Nombre de places = "+Constante.NOMBRE_PLACES);
		myAssert(nombrePlaces.equals(Constante.NOMBRE_PLACES));
		
		System.out.print("Dernier numéro de place = "+(Constante.NUMERO_PREMIERE_PLACE + Constante.NOMBRE_PLACES - 1));
		myAssert(dernierNumPlace.equals(Constante.NUMERO_PREMIERE_PLACE + Constante.NOMBRE_PLACES - 1));
		
		System.out.print("1ère place place particulier");
		myAssert(p.getPlacesMap().get(p.getPremierNumeroDePlace()).getClass().getName().equals(Particulier.class.getName()));

		System.out.print("Dernière place transporteur");
		Place dernierePlace = null;
		for (Iterator<Place> iterator = p.getPlacesMap().values().iterator(); iterator.hasNext();) {
			dernierePlace = iterator.next();
		}
		if(dernierePlace == null) myAssert(false);
		else myAssert(dernierePlace.getClass().getName().equals(Transporteur.class.getName()));
		
		System.out.println();
	}
	
	public void testerParking() {
		Parking p = Parking.getInstance();
		
		Vehicule moto = new Moto("3D", "marquise", "j'ai une frite qui déconne", "Jean Beurre-Gueurre");
		Vehicule camion = new Camion("fqsdfas", "marque", "modele", "Jean Aimar");
		Vehicule voiture = new Voiture("acxv", "marque", "modele", "John Stone");
		Vehicule vehicule = new Vehicule("g43ewvrsd", "marque", "modele", "John Michael");
		Vehicule v5 = new Voiture("FAf90ajsd", "marque", "modele", "John D'oeuf");
		
		
		System.out.println("-- Test de park --\n");
		testerParkingPlaces(p);
		
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
