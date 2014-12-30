package parking.business;

import java.util.Map;
import java.util.HashMap;

public class Parking {
	Map<Integer, Place> places;

	public Parking() {
		places = new HashMap<Integer, Place>();
	}
	
	public void creerPlaces() {
		for (int i = 0; i < Constante.NOMBRE_PLACES; i++) {
			Place place;
			
			if(Math.random() < 0.5) place = new Particulier();
			else place = new Transporteur();
			
			places.put(i, place);
		}
	}
	
	private Place getFirstFreePlaceTransporteur() {
		for (Place p : places.values())
			if(p instanceof Transporteur)
				return p;
		
		return null;
	}	

	private Place getFirstFreePlace() {
		for (Place p : places.values())
			if(p instanceof Particulier)
				return p;
		
		return getFirstFreePlaceTransporteur();
	}
	
	
	public void park(Vehicule vehicule) throws PlaceOccupeeException {
		Place place;
		
		if(vehicule instanceof Camion) {
			place = getFirstFreePlaceTransporteur();
		} else {
			place = getFirstFreePlace();
		}
		
		if(place == null) throw new PlaceOccupeeException();
		
		place.parkVehicule(vehicule);
	}
	
	public void park(Vehicule vehicule, Integer numeroPlace) throws PlaceOccupeeException {
		Place place = places.get(numeroPlace);
		
		if(place instanceof Particulier && vehicule instanceof Camion)
			throw new PlaceOccupeeException();
		
		place.parkVehicule(vehicule);
	}
	
	public Vehicule unpark(Integer numeroPlace) throws PlaceLibreException { 
		Place place = places.get(numeroPlace);
		return place.unparkVehicule();
	}
	
	
	@Override
	public String toString() {
		return "Parking [places=" + places + "]";
	}
	
	public static void main(String[] args) {
		Parking p = new Parking();
		p.creerPlaces();
		System.out.println(p);
	}

}
