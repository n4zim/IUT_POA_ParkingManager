package parking.business;

import java.util.ArrayList;
import java.util.Collection;

public class Parking {
	Collection<Place> places;

	public Parking() {
		places = new ArrayList<Place>();
	}
	
	public void ajouterPlace(Place place)  {
		places.add(place);
	}
	
	public void creerPlaces() {
		for (int i = 0; i < Constante.NOMBRE_PLACES; i++) {
			Place place;
			
			if(Math.random() < 0.5) place = new Particulier();
			else place = new Transporteur();
			
			place.setNumero(i);
			places.add(place);
		}
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
