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
		return "[numeroImmatriculation=" + numeroImmatriculation
				+ ", marque=" + marque + ", modele=" + modele
				+ ", proprietaire=" + proprietaire + "]";
	}
	
	public String getNumeroImmatriculation() {
		return numeroImmatriculation;
	}

	public void setNumeroImmatriculation(String numeroImmatriculation) {
		this.numeroImmatriculation = numeroImmatriculation;
	}

	public String getMarque() {
		return marque;
	}

	public void setMarque(String marque) {
		this.marque = marque;
	}

	public String getModele() {
		return modele;
	}

	public void setModele(String modele) {
		this.modele = modele;
	}

	public String getProprietaire() {
		return proprietaire;
	}

	public void setProprietaire(String proprietaire) {
		this.proprietaire = proprietaire;
	}
	
}
