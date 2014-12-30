package parking.business;

import java.util.Iterator;
import java.util.Map;
import java.util.HashMap;
import java.util.Map.Entry;

import parking.exception.PlaceDisponibleException;
import parking.exception.PlaceInexistanteException;
import parking.exception.PlaceLibreException;
import parking.exception.PlaceOccupeeException;
import parking.exception.PlaceReserveeException;
import parking.exception.PlusAucunePlaceException;
import parking.exception.TypePlaceInvalideException;

public class Parking {
	Map<Integer, Place> places;

	public Parking() {
		places = new HashMap<Integer, Place>();
	}
	
	public boolean vehiculeExiste(Vehicule v){
		boolean existe = false;
		for (Integer i = 0; i < Constante.NOMBRE_PLACES && !existe; i++) {
			existe = (places.get(i).getVehiculeGare() == v);
		}
		return existe;
	}
	
	public void creerPlaces() {
		for (int i = 0; i < Constante.NOMBRE_PLACES; i++) {
			Place place;
			
			if(Math.random() < 0.7) place = new Particulier();
			else place = new Transporteur();
			
			places.put(i, place);
		}
	}
	
	private Place getFirstFreePlaceTransporteur() throws PlusAucunePlaceException {
		for (Place p : places.values())
			if(p instanceof Transporteur && p.isFree())
				return p;
		
		throw new PlusAucunePlaceException();
	}

	private Place getFirstFreePlace() throws PlusAucunePlaceException {
		for (Place p : places.values())
			if(p instanceof Particulier && p.isFree())
				return p;
		
		return getFirstFreePlaceTransporteur();
	}
	
	public void park(Vehicule vehicule) throws PlusAucunePlaceException {
		Place place;
		
		if (vehicule instanceof Camion) {
			place = getFirstFreePlaceTransporteur();
		} else {
			place = getFirstFreePlace();
		}
		
		place.parkVehicule(vehicule);
	}
	
	public void park(Vehicule vehicule, Integer numeroPlace) throws TypePlaceInvalideException, PlaceOccupeeException, PlaceInexistanteException, PlaceReserveeException {
		if (numeroPlace >= Constante.NOMBRE_PLACES)
			throw new PlaceInexistanteException();
		
		Place place = places.get(numeroPlace);
		
		if (place.getVehiculeGare() != null)
			throw new PlaceOccupeeException();
		if (place.isBooked() == true)
			throw new PlaceReserveeException();
		if (place instanceof Particulier && vehicule instanceof Camion)
			throw new TypePlaceInvalideException();
		
		place.parkVehicule(vehicule);
	}
	
	public Vehicule unpark(Integer numeroPlace) throws PlaceLibreException, PlaceInexistanteException, TypePlaceInvalideException, PlaceOccupeeException, PlaceReserveeException { 
		if (numeroPlace >= Constante.NOMBRE_PLACES)
			throw new PlaceInexistanteException();
		Place place = places.get(numeroPlace);
		if (place.isFree())
			throw new PlaceLibreException();
		if (place instanceof Particulier) return reorganiserPlaces(numeroPlace);
		return place.unparkVehicule();
	}
	// genre ya un mec qui va venir te bouger ta voiture comme ça, en plus ce code est dégueulasse
	public Vehicule reorganiserPlaces (Integer numeroPlacePourParticulierAReorganiser){
		Place placePourParticulierAReorganiser = places.get(numeroPlacePourParticulierAReorganiser);
		Vehicule vehiculeSortant = placePourParticulierAReorganiser.getVehiculeGare();
		placePourParticulierAReorganiser.setVehiculeGare(null);
		
		Iterator<Entry<Integer, Place>> it = places.entrySet().iterator();
	    while (it.hasNext()) {
	    	Place placeCourrante = ((Entry<Integer, Place>)it.next()).getValue();
			if (placeCourrante instanceof Transporteur && !(placeCourrante.getVehiculeGare() instanceof Camion)){
				placePourParticulierAReorganiser.setVehiculeGare(placeCourrante.getVehiculeGare());
				placeCourrante.setVehiculeGare(null);
				break;
			}
	    }
	    return vehiculeSortant;
	}
	
	public void EtatParking() {
	    Iterator<Entry<Integer, Place>> it = places.entrySet().iterator();
	    while (it.hasNext()) {
	        Entry<Integer, Place> pairs = (Entry<Integer, Place>)it.next();
	        System.out.println("Place " + pairs.getKey() + " : " + pairs.getValue());
	    }
	}
	
	public Place bookPlace (Vehicule v) throws PlusAucunePlaceException {
		Place bookedPlace;
		if (v instanceof Camion)
			bookedPlace = getFirstFreePlaceTransporteur();
		else 
			bookedPlace = getFirstFreePlace();
		bookedPlace.setBooked(true);
		return bookedPlace;
	}
	
	public void freePlace (Place placeReservee) throws PlaceDisponibleException{
		if (placeReservee.isBooked() == false) 
			throw new PlaceDisponibleException();
		placeReservee.setBooked(true);
	}
	
	public Integer getLocation(String numeroImmatriculation){
		for (Integer i = 0; i < Constante.NOMBRE_PLACES; i++) {
			if (places.get(i).getVehiculeGare().numeroImmatriculation.equals(numeroImmatriculation))
				return i;
		}
		return -1;
	}
	
	public Vehicule retirerVehicule (String numeroImmatriculation) throws PlaceLibreException, PlaceInexistanteException, TypePlaceInvalideException, PlaceOccupeeException, PlaceReserveeException{
		Integer numeroPlace = getLocation(numeroImmatriculation);
		if (numeroPlace == -1)
			return null;
		Vehicule v = places.get(numeroPlace).getVehiculeGare();
		unpark(numeroPlace); // PlaceInexistanteException ne devrait pas avoir lieu ici mais il fait chier avec donc je le déclare
		return v;
	}
	
	@Override
	public String toString() {
		return "Parking [places=" + places + "]";
	}
	
	public static void main(String[] args) {
		Parking p = new Parking();
		p.creerPlaces();
		Vehicule v1 = new Moto();
		Vehicule v2 = new Voiture();
		Vehicule v3 = new Camion();
		Vehicule v4 = new Voiture();
		Vehicule v5 = new Voiture();

		try {
			p.park(v1);
			p.park(v2, 3);
			p.park(v3);
			p.park(v4);
			p.bookPlace(v5);

		} catch (parking.exception.ParkingException e) {
			e.printStackTrace();
		}
		
		System.out.println("v2 est dans le garage : " + p.vehiculeExiste(v2));
		System.out.println("v5 est dans le garage : " + p.vehiculeExiste(v5));
		
		p.EtatParking();
		
	}

}
