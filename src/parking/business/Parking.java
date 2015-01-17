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
	 * @param tarif Le prix du parking qui sera factur� � la sortie d'un v�hicule
	 */
	public Parking(double tarif) {
		this.places = new HashMap<Integer, Place>();
		this.tarif = tarif;
		creerPlaces();
	}
	
	/**
	 * Retourne le premier num�ro de place du parking
	 * @return premier num�ro de place
	 */
	public Integer getPremierNumeroDePlace() {
		return Constante.NUMERO_PREMIERE_PLACE;
	}
	
	/**
	 * Retourne le dernier num�ro de place du parking
	 * @return dernier num�ro de place
	 */
	public Integer getDernierNumeroDePlace() {
		return Constante.NUMERO_PREMIERE_PLACE + Constante.NUMERO_PREMIERE_PLACE;
	}

	/**
	 * V�rifie si un v�hicule est gar� dans le parking
	 * 
	 * @param v Le v�hicule � chercher
	 * @return true si le v�hicule � �t� trouv�
	 * @return false si le v�hicule n'a pas �t� trouv�
	 */
	public boolean vehiculeExiste(Vehicule v) {
		boolean existe = false;
		for (Integer i = getPremierNumeroDePlace(); i < getDernierNumeroDePlace() && !existe; i++) {
			existe = (places.get(i).getVehiculeGare() == v);
		}
		return existe;
	}

	/**
	 * G�nere les places du parking
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
	 * Retourne la premi�re place adapt� au type de v�hicule Transporteur disponible
	 * 
	 * @return La place disponible
	 * @throws PlusAucunePlaceException Il n'y � plus de place disponible adapt�e aux transporteurs
	 */
	private Place getFirstFreePlaceTransporteur() throws PlusAucunePlaceException {
		for (Place p : places.values())
			if (p instanceof Transporteur && p.isFree())
				return p;

		throw new PlusAucunePlaceException();
	}

	/**
	 * Retourne la premi�re place disponible du parking
	 * 
	 * @return La place disponible
	 * @throws PlusAucunePlaceException Il n'y � plus de place disponible dans le parking
	 */
	private Place getFirstFreePlace() throws PlusAucunePlaceException {
		for (Place p : places.values())
			if (p instanceof Particulier && p.isFree())
				return p;

		return getFirstFreePlaceTransporteur();
	}
	
	/**
	 * Retourne la premi�re place de parking disponible adapt�e au v�hicule donn�
	 * 
	 * @param vehicule V�hicule pour lequel on cherche une place libre
	 * @return La place disponible
	 * @throws PlusAucunePlaceException Il n'y a plus de place dans le parking pour garer le v�hicule
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
	 * Gare le v�hicule dans le parking � la premi�re place disponible
	 * 
	 * @param vehicule Le v�hicule � garer
	 * @throws PlusAucunePlaceException Il n'y � plus de place, le v�hicule n'a pas �t� gar�
	 */
	public void park(Vehicule vehicule) throws PlusAucunePlaceException {
		Place place = getFirstFreePlaceAuto(vehicule);

		place.parkVehicule(vehicule);
	}

	/**
	 * Gare un v�hicule dans le parking � une place sp�cifique
	 * 
	 * @param vehicule Le v�hicule � garer
	 * @param numeroPlace Le num�ro de la place souhait�
	 * @throws TypePlaceInvalideException La place souhait�e n'est pas adapt� au type de v�hicule que l'on souhaite garer
	 * @throws PlaceOccupeeException La place souhait�e est occup�e
	 * @throws PlaceInexistanteException La place souhait�e n'existe pas
	 * @throws PlaceReserveeException La place souhait�e est r�serv�e
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
	 * Permet de retirer un v�hicule de la place de parking sp�cifi�
	 * 
	 * @param numeroPlace Num�ro de la place � lib�rer
	 * @return Le v�hicule qui � �t� lib�r�
	 * @throws PlaceLibreException La place �tait d�j� libre
	 * @throws PlaceInexistanteException La place n'existe pas
	 */
	public Vehicule unpark(Integer numeroPlace) throws PlaceLibreException,
			PlaceInexistanteException {
		// Si on essaye de lib�rer une place dont le num�ro est impossible
		if (numeroPlace >= getDernierNumeroDePlace() || numeroPlace < getPremierNumeroDePlace())
			throw new PlaceInexistanteException();
		
		// On r�cup�re l'objet Place correspondant au num�ro
		Place place = places.get(numeroPlace);
		
		// Si c'est libre, impossible de lib�rer
		if (place.isFree())
			throw new PlaceLibreException();
		
		// Sinon on lib�re la place en r�cup�rant le v�hicule
		Vehicule vehiculeSortant = place.unparkVehicule();
		
		// On appelle la r�organisation du parking
		if (place instanceof Particulier)
			reorganiserPlaces();
		
		// On g�n�re la facture
		FabriqueFacture f = new FabriqueFacture(tarif);
		f.genererFacture();
		
		return vehiculeSortant;
	}

	/**
	 * R�organise les places du parking
	 */
	public void reorganiserPlaces() {
		Iterator<Entry<Integer, Place>> it = places.entrySet().iterator();
		while (it.hasNext()) {
			Place placeCourrante = ((Entry<Integer, Place>) it.next()) .getValue();
			if (placeCourrante instanceof Transporteur
					&& !(placeCourrante.getVehiculeGare() instanceof Camion)) {
				Vehicule vehiculeADeplacer = placeCourrante.unparkVehicule();
				
				// on ne devrait jamais arriver dans le catch puisque l'on s'assure de lib�rer une
				// place avant de garer le v�hicule sur une nouvelle place.
				try {
					getFirstFreePlace().parkVehicule(vehiculeADeplacer);
				} catch (PlusAucunePlaceException e) {}
				break;
			}
		}
	}

	/**
	 * Affiche dans la console l'�tat actuel du parking
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
	 * R�serve une place � un v�hicule
	 * 
	 * @param v Le v�hicule pour lequel la place sera r�serv�e
	 * @return La place qui � �t� r�serv�e
	 * @throws PlusAucunePlaceException Il n'y � plus de place dans le parking
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
	 * Annulle une r�servation sur une place
	 * 
	 * @param placeReservee La place qui � �t� res�rv�e
	 * @throws PlaceDisponibleException La place dont on veut annuller la r�servation n'�tait pas r�serv�e
	 */
	public void freePlace(Place placeReservee) throws PlaceDisponibleException {
		if (placeReservee.isBooked() == false)
			throw new PlaceDisponibleException();
		placeReservee.setBooked(true);
	}

	/**
	 * Retourne le num�ro de la place occup�e par le v�hicule dont l'immatriculation est donn�e.
	 * 
	 * @param numeroImmatriculation Num�ro d'immatriculation de la voiture � chercher
	 * @return Le num�ro de place occup� par le v�hicule si il � �t� trouv�
	 * @return -1 si le v�hicule n'a pas �t� trouv�
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
	 * Permet de retirer un v�hicule du parking � partir de son num�ro d'immatriculation
	 * 
	 * @param numeroImmatriculation Le num�ro d'immatriculation du v�hicule
	 * @return Le v�hicule retir� du parking
	 * @throws PlaceLibreException La place �tait d�j� libre
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
