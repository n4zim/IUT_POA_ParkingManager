package parking.business;

import java.util.Iterator;
import java.util.Map;
import java.util.HashMap;
import java.util.Map.Entry;

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
			if(p instanceof Transporteur && p.isFree())
				return p;
		
		return null;
	}	

	private Place getFirstFreePlace() {
		for (Place p : places.values())
			if(p instanceof Particulier && p.isFree())
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
	
	public void EtatParking() {
	    Iterator<Entry<Integer, Place>> it = places.entrySet().iterator();
	    while (it.hasNext()) {
	        Entry<Integer, Place> pairs = (Entry<Integer, Place>)it.next();
	        System.out.println("Place " + pairs.getKey() + " : " + pairs.getValue());
	    }
	}
	
	@Override
	public String toString() {
		return "Parking [places=" + places + "]";
	}
	
	public static void main(String[] args) {
		Parking p = new Parking();
		p.creerPlaces();
		Vehicule v = new Moto();
		Vehicule v2 = new Voiture();
		Vehicule v3 = new Camion();
		Vehicule v4 = new Voiture();

		try {
			p.park(v);
			p.park(v2, 6);
			p.park(v3);
			p.park(v4);
		} catch (PlaceOccupeeException e) {
			e.printStackTrace();
		}
		
		p.EtatParking();
	}

}
