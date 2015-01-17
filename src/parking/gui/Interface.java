package parking.gui;

import javax.swing.JFrame;

public class Interface extends JFrame {

	private static final long serialVersionUID = 1L;
	
	public static void main(String[] args) {
		javax.swing.SwingUtilities.invokeLater(new Runnable() {
			public void run() {
				VehiculesView.afficher();
				new ParkingView();
			}
		});
	}
}