package parking.business;

public class Facture {
	private int numFacture;
	private double tarifHT;
	private double tarifTTC;
	
	public Facture(int numFacture, double tarifHT) {
		this.numFacture = numFacture;
		this.tarifHT = tarifHT;
		this.tarifTTC = tarifHT * Constante.TVA; 
	}
	
	public String toString() {
		String output = "=== FACTURE " + numFacture + " ===\n";
		output += " Tarif HT  : " + tarifHT;
		output += " Tarif TTC : " + tarifTTC;
		
		return output;
	}
}
