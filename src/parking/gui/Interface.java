package parking.gui;
import javax.swing.*;
import parking.business.*;

public class Interface extends JFrame {

	private static final long serialVersionUID = 1L;
	
	private static void addLine() {
		VehiculesView.ajouterLigne("1234", "marque", "modele", "John Stone");
	}
	
	/*private static void message(int type, String title, String msg, JFrame frame) {
		switch (type) {
		case 1:
			JOptionPane.showMessageDialog(frame, "Eggs are not supposed to be green.");
			break;
		case 2:
			JOptionPane.showMessageDialog(frame, "Eggs are not supposed to be green.", "Inane error", JOptionPane.ERROR_MESSAGE);
			break;
		case 3:
			JOptionPane.showMessageDialog(frame, "Eggs are not supposed to be green.", "Inane warning", JOptionPane.WARNING_MESSAGE);
			break;
		default:
			break;
		}
	}*/
	
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
				new ParkingView();
				VehiculesView.afficher();
				addLine();
			}
		});
	}
}