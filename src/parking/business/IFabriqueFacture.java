package parking.business;

/**
 * Interface pour une fabrique de factures
 */
public interface IFabriqueFacture {
	/**
	 * Génère une nouvelle facture
	 * @return Facture généree
	 */
	public Facture genererFacture();
}
