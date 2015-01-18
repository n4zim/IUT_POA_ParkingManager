package parking.business;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.Map;
import java.util.HashMap;
import java.util.Map.Entry;

import parking.exception.*;

public class Parking {
	/**
	 * Map de places indexées par des entiers
	 * À chaque numéro correspond une Place
	 */
	private Map<Integer, Place> places;
	
	/**
	 * Tarif hors taxe du parking
	 */
	private double tarifHT;
	
	/**
	 * Collection de factures générées
	 */
	private Collection<Facture> factures;
	
	/**
	 * Instance unique de la classe Parking
	 * Sera initialisé par getInstance()
	 */
	private static Parking instanceUniqueParking;

	/**
	 * Construit un nouveau parking
	 * Privé pour que la classe Parking soit un singleton
	 * 
	 * @param tarif Le prix du parking qui sera facturé à la sortie d'un véhicule
	 */
	private Parking(double tarif) {
		// Initialisation des collections
		this.places = new HashMap<Integer, Place>();
		this.factures = new ArrayList<Facture>();
		
		// Réglage des données membres
		this.tarifHT = tarif;
		
		// Création des places du parking
		for (int i = getPremierNumeroDePlace(); i < getDernierNumeroDePlace(); i++) {
			Place place;
			
			if(i == getPremierNumeroDePlace()) // la première place est toujours une place particulier
				place = new Particulier();
			else if(i == getDernierNumeroDePlace() - 1) // la dernière toujours une place transporteur
				place = new Transporteur();
			else { // les autres sont réparties aléatoirement
				if (Math.random() < 0.7) place = new Particulier();
				else place = new Transporteur();
			}
			
			places.put(i, place);
		}		
	}
	
	/**
	 * Retourne une instance unique de Parking
	 * 
	 * Utilise la technique de lazy loading qui crée l'instance si elle n'est pas encore définie 
	 * @return instance de Parking
	 */
	public static Parking getInstance() {
		if(instanceUniqueParking == null)
			instanceUniqueParking = new Parking(2.5d);
		
		return instanceUniqueParking;
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
	 * Retourne la première place disponible du parking pour un véhicule particulier
	 * 
	 * @return La place disponible
	 * @throws PlusAucunePlaceException Il n'y à plus de place disponible dans le parking
	 */
	private Place getFirstFreePlaceParticulier() throws PlusAucunePlaceException {
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
	public Place getFirstFreePlace(Vehicule vehicule) throws PlusAucunePlaceException {
		Place place;
		if (vehicule instanceof Camion) {
			place = getFirstFreePlaceTransporteur();
		} else {
			place = getFirstFreePlaceParticulier();
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
		Place place = getFirstFreePlace(vehicule);

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
		
		facturer(vehiculeSortant);
		
		return vehiculeSortant;
	}
	
	/**
	 * Permet de factuer un véhicule sortant
	 * 
	 * @param vehicule à facturer
	 */
	private void facturer(Vehicule vehicule) {
		// On génère la facture
		FabriqueFacture fabFacture = new FabriqueFacture();
		fabFacture.setFacturation(new Facturation(vehicule.getProprietaire(), tarifHT));
		Facture facture = fabFacture.genererFacture();
		
		// On stocke la facture
		factures.add(facture);
		
		// On affiche la facture
		System.out.println(facture);
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
					getFirstFreePlaceParticulier().parkVehicule(vehiculeADeplacer);
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
			bookedPlace = getFirstFreePlaceParticulier();
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
		for (Integer i = getPremierNumeroDePlace(); i < getDernierNumeroDePlace(); i++) {
			Vehicule vehiculeGare = places.get(i).getVehiculeGare();
			if (vehiculeGare != null && vehiculeGare.getNumeroImmatriculation()
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

	/**
	 * Récupère les factures générées
	 * @return Collection de factures
	 */
	public Collection<Facture> getFactures() {
		return factures;
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
		return Constante.NOMBRE_PLACES + Constante.NUMERO_PREMIERE_PLACE;
	}
	
	/**
	 * Retourne la map avec les places
	 * @return la map
	 **/
	protected Map<Integer, Place> getPlacesMap() {
		return places;
	}
}
