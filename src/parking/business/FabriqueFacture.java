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
	 * Paramètres de facturation qui seront appliqués
	 */
	private static Facturation facturation;

	public void setFacturation(Facturation facturation) {
		this.facturation = facturation;
	}

    /**
	 * @return Fabrique une facture
	 */
	public Facture genererFacture() {
		return new Facture(numFacture++, facturation);
	}
}
