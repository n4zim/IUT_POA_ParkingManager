package parking.gui;

public class VehiculesViewRecords {
	protected String immatriculation;
	protected String marque;
	protected String modele;
	protected String proprietaire;
	protected String emplacement;

	public VehiculesViewRecords() {
		immatriculation = "0";
		marque = "11";
		modele = "222";
		proprietaire = "zefzef";
		emplacement = "bb";
	}

	public String getImmatriculation() {
		return immatriculation;
	}

	public void setImmatriculation(String immatriculation) {
		this.immatriculation = immatriculation;
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

	public String getEmplacement() {
		return emplacement;
	}

	public void setEmplacement(String emplacement) {
		this.emplacement = emplacement;
	}

}