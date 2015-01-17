package parking.business;

/**
 * Place de parking pour les transporteurs (véhicules de grande taille)
 */
public class Transporteur extends Place {
    /**
     * @return une String qui donne le type de place,
     * la description du véhicule garé si la place est occupée,
     * ou bien si la place est réservée ou libre.
     */
	@Override
	public String toString() {
		return "Place pour transporteurs " + super.toString();
	}
}
