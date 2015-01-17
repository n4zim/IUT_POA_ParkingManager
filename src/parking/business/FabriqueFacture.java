package parking.business;

public class FabriqueFacture implements IFabriqueFacture {
	private static int numFacture = 0;
	private static double tarif;
	
	public FabriqueFacture(double Tarif) {
		this.tarif = tarif;
	}
	
	public Facture genererFacture() {
		return new Facture(numFacture++, tarif);
	}
}
