package parking.business;

/**
 * Place de parking
 */
public class Place {
	/**
	 * Véhicule garé
	 * Est nul si aucun véhicule n'est garé
	 */
	private Vehicule vehiculeGare;
	
	/**
	 * Place réservée
	 */
	private boolean booked = false;
	
	/**
	 * Constructeur par défaut
	 */
	public Place() {
		vehiculeGare = null;
	}
	
	/**
	 * Gare un véhicule
	 * @param v le véhicule à garer
	 */
	public void parkVehicule(Vehicule v) {
		vehiculeGare = v;
	}
	
	/**
	 * Retire un véhicule
	 * @return le véhicule retiré
	 */
	public Vehicule unparkVehicule() {
		Vehicule unparked = vehiculeGare;
		vehiculeGare = null;
		return unparked;
	}

	public boolean isFree() {
		return (vehiculeGare == null && booked == false);			
	}
	
	public Vehicule getVehiculeGare() {
		return vehiculeGare;
	}

	public boolean isBooked() {
		return booked;
	}

	public void setBooked(boolean booked) {
		this.booked = booked;
	}


	@Override
	public String toString() {
		String description = getClass().toString();
		
		if (vehiculeGare != null) description += " occuppée : " + vehiculeGare;
		if (booked == true) description += " réservée";
		else if (vehiculeGare == null) description += " libre";
		
		return description;
	}
}
