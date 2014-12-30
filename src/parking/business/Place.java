package parking.business;

public class Place {
	Vehicule vehiculeGare;
	boolean booked = false;
	
	public Place() {
		vehiculeGare = null;
	}
	
	public void parkVehicule(Vehicule v) {
		vehiculeGare = v;
	}
	
	public Vehicule unparkVehicule() {
		Vehicule unparked = vehiculeGare;
		vehiculeGare = null;
		return unparked;
	}
	
	public boolean isFree() {
		return (vehiculeGare == null && booked == false);			
	}

	@Override
	public String toString() {
		String description = getClass().toString();
		
		if (vehiculeGare != null) description += " occupp�e : " + vehiculeGare;
		if (booked == true) description += " r�serv�e";
		else if (vehiculeGare == null) description += " libre";
		
		return description;
	}
}
