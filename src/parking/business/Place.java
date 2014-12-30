package parking.business;

public class Place {
	Vehicule vehiculeGare;
	
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
		return vehiculeGare == null;			
	}
}
