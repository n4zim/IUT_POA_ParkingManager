package parking.business;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Collection;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;

import parking.exception.*;

public class TestProgramme {
	/**
	 * Arrête l'éxécution du programme si l'assertion est fausse.
	 * Affiche OK si l'assertion est juste, ERREUR sinon.
	 * @param assertion expresssion à vérifier
	 */
	private void myAssert(boolean assertion) {
		if(assertion)
			System.out.println(" - OK - ");
		else {
			System.out.println(" - ERREUR - ");
			System.exit(-1);
		}
	}
	
	/**
	 * Teste la classe Vehicule
	 */
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
	
	/**
	 * Vérifie la présence d'un véhicule dans le parking
	 * @param p Parking
	 * @param v Véhicule à tester
	 * @return true si le véhicule à été trouvé
	 * @return false sinon
	 */
	public boolean verifierVehiculeDansParking(Parking p, Vehicule v) {
		Collection<Place> places = p.getPlacesMap().values();
		
		for (Place place : places) {
			if(!place.isFree() && place.getVehiculeGare().equals(v))
				return true;
		}

		return false;
	}
	
	/**
	 * Teste les places d'un parking
	 * @param p Le parking
	 */
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
	
	/**
	 * Teste la classe Parking
	 */
	public void testerParking() {
		Parking p = Parking.getInstance();
		
		Vehicule moto = new Moto("3D", "marquise", "j'ai une frite qui déconne", "Jean Beurre-Gueurre");
		Vehicule camion = new Camion("IMMATRICULATION", "marque", "modele", "Jean Aimar");
		Vehicule voiture = new Voiture("PLAQUE", "marque", "modele", "John Stone");
		Vehicule vehicule = new Vehicule("BONJOUR", "marque", "modele", "John Michael");
		Vehicule voiture2 = new Voiture("DEVIL", "marque", "modele", "John Duff");
		
		System.out.println("-- Test de park --\n");
		testerParkingPlaces(p);
		
		/* Park */
		System.out.println("Garer n'importe quel type de véhicule :");
		try {
			System.out.print("Moto");
			p.park(moto);
			myAssert(verifierVehiculeDansParking(p, moto));

			System.out.print("Camion");
			p.park(camion);
			myAssert(verifierVehiculeDansParking(p, camion));

			System.out.print("Voiture");
			p.park(voiture);
			myAssert(verifierVehiculeDansParking(p, voiture));

			System.out.print("Vehicule");
			p.park(vehicule);
			myAssert(verifierVehiculeDansParking(p, vehicule));		
		} catch (PlusAucunePlaceException e) {
		}		
		
		System.out.println("Libérer les véhicules par plaque d'immatricuation :");
		try {
			System.out.print("Moto");
			Vehicule v = p.retirerVehicule(moto.getNumeroImmatriculation());
			myAssert(v.equals(moto));

			System.out.print("Camion");
			v = p.retirerVehicule(camion.getNumeroImmatriculation());
			myAssert(v.equals(camion));

			System.out.print("Voiture");
			v = p.retirerVehicule(voiture.getNumeroImmatriculation());
			myAssert(v.equals(voiture));

			System.out.print("Vehicule");
			v = p.retirerVehicule(vehicule.getNumeroImmatriculation());
			myAssert(v.equals(vehicule));	
		} catch (PlaceLibreException e) {
			myAssert(false);
		}
		
		System.out.print("Il y à 4 factures enregistrés");
		myAssert(p.getFactures().size() == 4);
		
		Facture facture = p.getDerniereFacture();
		System.out.print("Test d'enregistrement de facture");
		facture.sauverDansFichier();
		String str = null;
		try {
			str = new String(Files.readAllBytes(Paths.get(Constante.DOSSIER_FACTURES+"/"+facture.getNomFichier()+".txt")));
		} catch (IOException e) {
			System.out.print("Impossible d'ouvrir le fichier");
			myAssert(false);
		}
		myAssert(str.trim().equals(facture.toString().trim()));
		
		// remise à zéro du parking
		Parking.resetInstance();
		p = Parking.getInstance();

		System.out.print("Ne pas pouvoir garer si le parking est plein");
		try {
			// on remplit le parking
			for(int i = p.getPremierNumeroDePlace(); i < p.getDernierNumeroDePlace(); i++) {
				p.park(new Vehicule("voit"+i, "marque", "modèle", "être humain"));
			}
		} catch (PlusAucunePlaceException e) {
		}
		boolean parkImpossible = false;
		try {
			// on essaye de garer quelqu'un
			p.park(vehicule);
		} catch (PlusAucunePlaceException e) {
			parkImpossible = true;
		}
		myAssert(parkImpossible);

		// remise à zéro du parking
		Parking.resetInstance();
		p = Parking.getInstance();

		System.out.println("Garer un véhicule à une place précise :");
		
		try {
			for(int i = p.getPremierNumeroDePlace(); i < p.getDernierNumeroDePlace(); i++) {
				System.out.print(" -> Place " + i + "\t");
				Vehicule aGarer = new Vehicule("voit"+i, "marque", "modèle", "être humain");
				p.park(aGarer, i);
				Vehicule vehiculeGare = p.getPlacesMap().get(i).getVehiculeGare();
				
				if(vehiculeGare == null) {
					System.out.println(" le véhicule n'a pas été garé");
					myAssert(false);
				}
				
				myAssert(aGarer.equals(vehiculeGare));
			}
		} catch (TypePlaceInvalideException | PlaceOccupeeException
				| PlaceInexistanteException | PlaceReserveeException e) {
			System.out.println(" le véhicule n'a pu être garé ");
			e.printStackTrace();
			myAssert(false);
		}

		// remise à zéro du parking
		Parking.resetInstance();
		p = Parking.getInstance();
		
		System.out.println("\nRetirer un véhicule à une place précise :");
		try {
			for(int i = p.getPremierNumeroDePlace(); i < p.getDernierNumeroDePlace(); i++) {
				System.out.print(" -> Place " + i + "\t");
				Vehicule aGarer = new Vehicule("voit"+i, "marque", "modèle", "être humain");
				p.park(aGarer, i);
				Vehicule vehiculeGare = p.unpark(i);
				
				if(vehiculeGare == null) {
					System.out.print(" aucun véhicule n'a été récupéré");
					myAssert(false);
				}
				
				myAssert(aGarer.equals(vehiculeGare));
			}
		} catch (PlaceLibreException | PlaceInexistanteException | TypePlaceInvalideException | PlaceOccupeeException
				| PlaceReserveeException e) {
			e.printStackTrace();
			assert(false);
		}
		

		// remise à zéro du parking
		Parking.resetInstance();
		p = Parking.getInstance();
		
		System.out.println("\nTest de vehiculeExiste :");
		try {
			p.park(moto);
		} catch (PlusAucunePlaceException e) {
			e.printStackTrace();
			myAssert(false);
		}
		myAssert(p.vehiculeExiste(moto));
		
	}
	
	/**
	 * Programme de test principal
	 * @param args Aucun argument n'est utilisé
	 */
	public static void main(String[] args) {
		TestProgramme testParking = new TestProgramme();
		testParking.testerVehicules();
		testParking.testerParking();
	}
}
