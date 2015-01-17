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
	 * Paramètres de facturation
	 */
	private static Facturation facturation;

	/**
	 * Définit les paramètres de facturation
	 * @param TarifHT Tarif hors taxe des factures
	 */
	public void setFacturation(Facturation facturation) {
		this.facturation = facturation;
	}

	public Facture genererFacture() {
		return new Facture(numFacture++, facturation);
	}
}
