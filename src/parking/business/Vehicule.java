package parking.business;

public class Vehicule {
	String numeroImmatriculation;
	String marque;
	String modele;
	String proprietaire;
	
	public Vehicule() {
		
	}
	
	public Vehicule(String numeroImmatriculation, String marque, String modele,
			String proprietaire) {
		this.numeroImmatriculation = numeroImmatriculation;
		this.marque = marque;
		this.modele = modele;
		this.proprietaire = proprietaire;
	}

	@Override
	public String toString() {
		return "Vehicule [numeroImmatriculation=" + numeroImmatriculation
				+ ", marque=" + marque + ", modele=" + modele
				+ ", proprietaire=" + proprietaire + "]";
	}
		
}
