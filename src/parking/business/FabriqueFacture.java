package parking.business;

/**
 * Fabrique de facture simple
 * Implémente l'interface IFabriqueFacture
 */
public class FabriqueFacture implements IFabriqueFacture {
	/**
	 * Numéro de facture actuel, s'incrémente à chaque génération de facture
	 */
	private static int numFacture = 0;
	
	/**
	 * Tarif à appliquer aux factures
	 */
	private static double tarif;
	
	/**
	 * Construit une fabrique de factures avec un tarif spécifié
	 * @param TarifHT Tarif hors taxe des factures
	 */
	public FabriqueFacture(double TarifHT) {
		this.tarif = tarif;
	}

	public Facture genererFacture() {
		return new Facture(numFacture++, tarif);
	}
}
