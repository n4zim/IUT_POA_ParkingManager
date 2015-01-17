package parking.business;

import java.util.Iterator;
import java.util.Map;
import java.util.HashMap;
import java.util.Map.Entry;

import parking.exception.*;

public class Parking {
	Map<Integer, Place> places;
	double tarif; // tarif HT

	/**
	 * Construit un nouveau parking
	 * 
	 * @param tarif Le prix du parking qui sera facturé à la sortie d'un véhicule
	 */
	public Parking(double tarif) {
		this.places = new HashMap<Integer, Place>();
		this.tarif = tarif;
		creerPlaces();
	}
	
	/**
	 * Retourne le premier numéro de place du parking
	 * @return premier numéro de place
	 */
	public Integer getPremierNumeroDePlace() {
		return Constante.NUMERO_PREMIERE_PLACE;
	}
	
	/**
	 * Retourne le dernier numéro de place du parking
	 * @return dernier numéro de place
	 */
	public Integer getDernierNumeroDePlace() {
		return Constante.NUMERO_PREMIERE_PLACE + Constante.NUMERO_PREMIERE_PLACE;
	}

	/**
	 * Vérifie si un véhicule est garé dans le parking
	 * 
	 * @param v Le véhicule à chercher
	 * @return true si le véhicule à été trouvé
	 * @return false si le véhicule n'a pas été trouvé
	 */
	public boolean vehiculeExiste(Vehicule v) {
		boolean existe = false;
		for (Integer i = getPremierNumeroDePlace(); i < getDernierNumeroDePlace() && !existe; i++) {
			existe = (places.get(i).getVehiculeGare() == v);
		}
		return existe;
	}

	/**
	 * Génere les places du parking
	 */
	private void creerPlaces() {
		for (int i = getPremierNumeroDePlace(); i < getDernierNumeroDePlace(); i++) {
			Place place;

			if (Math.random() < 0.7)
				place = new Particulier();
			else
				place = new Transporteur();

			places.put(i, place);
		}
	}

	/**
	 * Retourne la première place adapté au type de véhicule Transporteur disponible
	 * 
	 * @return La place disponible
	 * @throws PlusAucunePlaceException Il n'y à plus de place disponible adaptée aux transporteurs
	 */
	private Place getFirstFreePlaceTransporteur() throws PlusAucunePlaceException {
		for (Place p : places.values())
			if (p instanceof Transporteur && p.isFree())
				return p;

		throw new PlusAucunePlaceException();
	}

	/**
	 * Retourne la première place disponible du parking
	 * 
	 * @return La place disponible
	 * @throws PlusAucunePlaceException Il n'y à plus de place disponible dans le parking
	 */
	private Place getFirstFreePlace() throws PlusAucunePlaceException {
		for (Place p : places.values())
			if (p instanceof Particulier && p.isFree())
				return p;

		return getFirstFreePlaceTransporteur();
	}
	
	/**
	 * Retourne la première place de parking disponible adaptée au véhicule donné
	 * 
	 * @param vehicule Véhicule pour lequel on cherche une place libre
	 * @return La place disponible
	 * @throws PlusAucunePlaceException Il n'y a plus de place dans le parking pour garer le véhicule
	 */
	public Place getFirstFreePlaceAuto(Vehicule vehicule) throws PlusAucunePlaceException {
		Place place;
		if (vehicule instanceof Camion) {
			place = getFirstFreePlaceTransporteur();
		} else {
			place = getFirstFreePlace();
		}
		return place;
	}

	/**
	 * Gare le véhicule dans le parking à la première place disponible
	 * 
	 * @param vehicule Le véhicule à garer
	 * @throws PlusAucunePlaceException Il n'y à plus de place, le véhicule n'a pas été garé
	 */
	public void park(Vehicule vehicule) throws PlusAucunePlaceException {
		Place place = getFirstFreePlaceAuto(vehicule);

		place.parkVehicule(vehicule);
	}

	/**
	 * Gare un véhicule dans le parking à une place spécifique
	 * 
	 * @param vehicule Le véhicule à garer
	 * @param numeroPlace Le numéro de la place souhaité
	 * @throws TypePlaceInvalideException La place souhaitée n'est pas adapté au type de véhicule que l'on souhaite garer
	 * @throws PlaceOccupeeException La place souhaitée est occupée
	 * @throws PlaceInexistanteException La place souhaitée n'existe pas
	 * @throws PlaceReserveeException La place souhaitée est réservée
	 */
	public void park(Vehicule vehicule, Integer numeroPlace)
			throws TypePlaceInvalideException, PlaceOccupeeException,
			PlaceInexistanteException, PlaceReserveeException {
		if (numeroPlace >= getDernierNumeroDePlace() || numeroPlace < getPremierNumeroDePlace())
			throw new PlaceInexistanteException();

		Place place = places.get(numeroPlace);

		if (place.getVehiculeGare() != null)
			throw new PlaceOccupeeException();
		if (place.isBooked() == true)
			throw new PlaceReserveeException();
		if (place instanceof Particulier && vehicule instanceof Camion)
			throw new TypePlaceInvalideException();

		place.parkVehicule(vehicule);
	}
	
	/**
	 * Permet de retirer un véhicule de la place de parking spécifié
	 * 
	 * @param numeroPlace Numéro de la place à libérer
	 * @return Le véhicule qui à été libéré
	 * @throws PlaceLibreException La place était déjà libre
	 * @throws PlaceInexistanteException La place n'existe pas
	 */
	public Vehicule unpark(Integer numeroPlace) throws PlaceLibreException,
			PlaceInexistanteException {
		// Si on essaye de libérer une place dont le numéro est impossible
		if (numeroPlace >= getDernierNumeroDePlace() || numeroPlace < getPremierNumeroDePlace())
			throw new PlaceInexistanteException();
		
		// On récupère l'objet Place correspondant au numéro
		Place place = places.get(numeroPlace);
		
		// Si c'est libre, impossible de libérer
		if (place.isFree())
			throw new PlaceLibreException();
		
		// Sinon on libère la place en récupérant le véhicule
		Vehicule vehiculeSortant = place.unparkVehicule();
		
		// On appelle la réorganisation du parking
		if (place instanceof Particulier)
			reorganiserPlaces();
		
		// On génère la facture
		FabriqueFacture f = new FabriqueFacture(tarif);
		f.genererFacture();
		
		return vehiculeSortant;
	}

	/**
	 * Réorganise les places du parking
	 */
	public void reorganiserPlaces() {
		Iterator<Entry<Integer, Place>> it = places.entrySet().iterator();
		while (it.hasNext()) {
			Place placeCourrante = ((Entry<Integer, Place>) it.next()) .getValue();
			if (placeCourrante instanceof Transporteur
					&& !(placeCourrante.getVehiculeGare() instanceof Camion)) {
				Vehicule vehiculeADeplacer = placeCourrante.unparkVehicule();
				
				// on ne devrait jamais arriver dans le catch puisque l'on s'assure de libérer une
				// place avant de garer le véhicule sur une nouvelle place.
				try {
					getFirstFreePlace().parkVehicule(vehiculeADeplacer);
				} catch (PlusAucunePlaceException e) {}
				break;
			}
		}
	}

	/**
	 * Affiche dans la console l'état actuel du parking
	 */
	public void EtatParking() {
		Iterator<Entry<Integer, Place>> it = places.entrySet().iterator();
		while (it.hasNext()) {
			Entry<Integer, Place> pairs = (Entry<Integer, Place>) it.next();
			System.out.println("Place " + pairs.getKey() + " : "
					+ pairs.getValue());
		}
	}

	/**
	 * Réserve une place à un véhicule
	 * 
	 * @param v Le véhicule pour lequel la place sera réservée
	 * @return La place qui à été réservée
	 * @throws PlusAucunePlaceException Il n'y à plus de place dans le parking
	 */
	public Place bookPlace(Vehicule v) throws PlusAucunePlaceException {
		Place bookedPlace;
		if (v instanceof Camion)
			bookedPlace = getFirstFreePlaceTransporteur();
		else
			bookedPlace = getFirstFreePlace();
		bookedPlace.setBooked(true);
		return bookedPlace;
	}

	/**
	 * Annulle une réservation sur une place
	 * 
	 * @param placeReservee La place qui à été resérvée
	 * @throws PlaceDisponibleException La place dont on veut annuller la réservation n'était pas réservée
	 */
	public void freePlace(Place placeReservee) throws PlaceDisponibleException {
		if (placeReservee.isBooked() == false)
			throw new PlaceDisponibleException();
		placeReservee.setBooked(true);
	}

	/**
	 * Retourne le numéro de la place occupée par le véhicule dont l'immatriculation est donnée.
	 * 
	 * @param numeroImmatriculation Numéro d'immatriculation de la voiture à chercher
	 * @return Le numéro de place occupé par le véhicule si il à été trouvé
	 * @return -1 si le véhicule n'a pas été trouvé
	 */
	public Integer getLocation(String numeroImmatriculation) {
		for (Integer i = 0; i < Constante.NOMBRE_PLACES; i++) {
			if (places.get(i).getVehiculeGare().numeroImmatriculation
					.equals(numeroImmatriculation))
				return i;
		}
		return -1;
	}

	/**
	 * Permet de retirer un véhicule du parking à partir de son numéro d'immatriculation
	 * 
	 * @param numeroImmatriculation Le numéro d'immatriculation du véhicule
	 * @return Le véhicule retiré du parking
	 * @throws PlaceLibreException La place était déjà libre
	 * @throws PlaceInexistanteException La place n'existe pas
	 */
	public Vehicule retirerVehicule(String numeroImmatriculation) throws PlaceLibreException, PlaceInexistanteException {
		Integer numeroPlace = getLocation(numeroImmatriculation);
		if (numeroPlace == -1)
			return null;
		Vehicule v = places.get(numeroPlace).getVehiculeGare();
		unpark(numeroPlace);
		return v;
	}
	
	@Override
	public String toString() {
		return "Parking [places=" + places + "]";
	}

	public static void main(String[] args) {
		Parking p = new Parking(2.5d);
		p.creerPlaces();
		Vehicule v1 = new Moto();
		Vehicule v2 = new Voiture();
		Vehicule v3 = new Camion();
		Vehicule v4 = new Voiture();
		Vehicule v5 = new Voiture();

		try {
			p.park(v1);
			p.park(v2, 3);
			p.park(v3);
			p.park(v4);
			p.bookPlace(v5);

		} catch (parking.exception.ParkingException e) {
			e.printStackTrace();
		}

		System.out.println("v2 est dans le garage : " + p.vehiculeExiste(v2));
		System.out.println("v5 est dans le garage : " + p.vehiculeExiste(v5));

		p.EtatParking();

	}

}
