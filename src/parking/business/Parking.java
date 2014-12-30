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
	
	public boolean vehiculeExiste(Vehicule v){
		boolean existe = false;
		for (Integer i = 0; i < Constante.NOMBRE_PLACES && !existe; i++) {
			existe = (places.get(i).vehiculeGare == v);
		}
		return existe;
	}
	
	public void creerPlaces() {
		for (int i = 0; i < Constante.NOMBRE_PLACES; i++) {
			Place place;
			
			if(Math.random() < 0.5) place = new Particulier();
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
	
	
	public void park(Vehicule vehicule) throws PlaceOccupeeException, PlusAucunePlaceException {
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
	
	public Place bookPlace () throws PlusAucunePlaceException {
		Place bookedPlace = getFirstFreePlace();
		bookedPlace.reserved = true;
		return bookedPlace;
	}
	
	public void freePlace (Place placeReservee) throws PlaceDisponibleException{
		if (placeReservee.reserved == false) 
			throw new PlaceDisponibleException();
		placeReservee.reserved = true;
	}
	
	public Integer getLocation(String numeroImmatriculation){
		for (Integer i = 0; i < Constante.NOMBRE_PLACES; i++) {
			if (places.get(i).vehiculeGare.numeroImmatriculation.equals(numeroImmatriculation))
				return i;
		}
		return -1;
	}
	
	public Vehicule retirerVehicule (String numeroImmatriculation) throws PlaceLibreException{
		Integer numeroPlace = getLocation(numeroImmatriculation);
		if (numeroPlace == -1)
			return null;
		Vehicule v = places.get(numeroPlace).vehiculeGare;
		unpark(numeroPlace);
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
			p.park(v2, 6);
			p.park(v3);
			p.park(v4);

		} catch (PlaceOccupeeException e) {
			e.printStackTrace();
		} catch (PlusAucunePlaceException e) {
			e.printStackTrace();
		}
		
		System.out.println("v2 est dans le garage : " + p.vehiculeExiste(v2));
		System.out.println("v5 est dans le garage : " + p.vehiculeExiste(v5));
		
		p.EtatParking();
	}

}
