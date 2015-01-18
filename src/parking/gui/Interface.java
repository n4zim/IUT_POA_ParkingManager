package parking.gui;
import javax.swing.JFrame;

import parking.business.Camion;
import parking.business.Moto;
import parking.business.Parking;
import parking.business.Vehicule;
import parking.business.Voiture;

public class Interface extends JFrame {

	private static final long serialVersionUID = 1L;
	
	public static void main(String[] args) {
		
		Parking p = Parking.getInstance();
		Vehicule v1 = new Moto("1234", "marque", "modele", "John Stone");
		Vehicule v2 = new Voiture("acxv", "marque", "modele", "John Stoned");
		Vehicule v3 = new Camion("fqsdfas", "marque", "modele", "John Aimar");
		Vehicule v4 = new Voiture("g43ewvrsd", "marque", "modele", "John Michael");
		Vehicule v5 = new Voiture("FAf90ajsd", "marque", "modele", "John D'oeuf");
		
		try {
			p.park(v1);
			p.park(v2, 3);
			p.park(v3);
			p.park(v4);
			p.bookPlace(v5);
		} catch (parking.exception.ParkingException e) {
			e.printStackTrace();
		}
		
		javax.swing.SwingUtilities.invokeLater(new Runnable() {
			public void run() {
				VehiculesView.afficher();
				new ParkingView();
			}
		});
	}
}