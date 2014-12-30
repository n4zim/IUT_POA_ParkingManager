package parking.business;

public class Place {
	Vehicule vehiculeGare;
	boolean reserved = false;
	
	public Place() {
		vehiculeGare = null;
	}
	
	public void parkVehicule(Vehicule v) throws PlaceOccupeeException {
		if(!isFree()) throw new PlaceOccupeeException();
		vehiculeGare = v;
	}
	
	public Vehicule unparkVehicule() throws PlaceLibreException {
		if(isFree()) throw new PlaceLibreException();
		Vehicule unparked = vehiculeGare;
		vehiculeGare = null;
		return unparked;
	}
	
	public boolean isFree() {
		return (vehiculeGare == null || reserved == false);			
	}

	@Override
	public String toString() {
		String description = "Place " + getClass();
		
		if(!isFree()) description += " occuppée : " + vehiculeGare;
			
		return description;
	}
}
