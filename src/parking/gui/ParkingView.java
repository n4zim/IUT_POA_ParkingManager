package parking.gui;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Container;
import java.awt.Dimension;
import java.awt.GridLayout;
import java.util.Random;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;

public class ParkingView extends JFrame {

	private static final long serialVersionUID = 1L;

	ParkingView() {
		super("Test GUI");
		setDefaultCloseOperation(EXIT_ON_CLOSE);
		Container contenu = getContentPane();

		Container grille = new Container();
		grille.setLayout(new GridLayout(3, 4));

		String Reserver = "data/reserver.jpg";
		String Prise = "data/prise.jpg";
		String Libre = "data/libre.jpg";
		for (int i = 0; i < 3; i++)
			for (int j = 0; j < 4; j++) {
				// test juste pour afficher différente types de place

				Random rand = new Random();
				int nombre = rand.nextInt(3);

				String alea = new String();
				if (nombre == 0) {
					alea = Libre;
				}

				if (nombre == 1) {
					alea = Reserver;
				}

				if (nombre == 2) {
					alea = Prise;
				}
				Bouton place = new Bouton(alea, alea);
				place.setText(Integer.toString(i));
				place.setPreferredSize(new Dimension(50, 50));
				grille.add(place);
				JPanel panneau = new JPanel();
				panneau.add(place);
				grille.add(panneau);
			}
		contenu.add(grille, BorderLayout.NORTH);

		JButton bouton = new JButton("12 places, 3 prises et 9 libres");
		bouton.setBackground(Color.RED);
		contenu.add(bouton);

		pack();
		setVisible(true);
	}

}
