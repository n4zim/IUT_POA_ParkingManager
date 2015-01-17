package parking.business;

/**
 * Décrit une place de parking
 */
public class Place {
	/**
	 * Véhicule garé sur la place.
	 * Est nul si aucun véhicule n'est garé.
	 */
	private Vehicule vehiculeGare;
	
	/**
	 * Place réservée ou non. Non par défaut.
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

    /**
     * @return si la place est libre et non-réservée ou pas
     */
	public boolean isFree() {
		return (vehiculeGare == null && booked == false);
	}
	
    /**
     * @return le véhicule garé à cette place
     */
	public Vehicule getVehiculeGare() {
		return vehiculeGare;
	}

    /**
     * @return si la place est réservée ou non
     */
	public boolean isBooked() {
		return booked;
	}

    /**
     * Réserve ou libère la place
     * @param booked vrai si on réserve la place, faux si on la libère
     */
	public void setBooked(boolean booked) {
		this.booked = booked;
	}

    /**
     * @return une String qui donne
     * la description du véhicule garé si la place est occupée,
     * ou bien si la place est réservée ou libre.
     */
	public String toString() {
		if (vehiculeGare != null) return "occuppée par : " + vehiculeGare.toString();
		if (booked == true) return "réservée";
		return "libre";
	}
}
