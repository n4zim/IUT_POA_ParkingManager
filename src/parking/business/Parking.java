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
