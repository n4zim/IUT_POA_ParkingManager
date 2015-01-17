package parking.business;

/**
 * Place de parking pour particuliers (véhicules de taille normale)
 */
public class Particulier extends Place {
    /**
     * @return une String qui donne le type de place,
     * la description du véhicule garé si la place est occupée,
     * ou bien si la place est réservée ou libre.
     */
	@Override
	public String toString() {
		return "Place pour particuliers " + super.toString();
	}
}
