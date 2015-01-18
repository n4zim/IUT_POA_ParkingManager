package parking.gui;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Container;
import java.awt.Dimension;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
//import java.awt.Toolkit;
import java.util.Random;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JMenuItem;
import javax.swing.JPanel;
import javax.swing.JPopupMenu;

public class ParkingView extends JFrame {

	private static final long serialVersionUID = 1L;

	private static final int Ligne = 0;

	private static final int Col = 0;

	final JPopupMenu popup = new JPopupMenu();

	ParkingView() {
		super("Etat du parking");
		
		int nbCases = 15;
		for (int i = 0; i < nbCases; i++) {
			if (nbCases%i == 0) {
				
				 Col = i;
				 Ligne = nbCases/Col;
				
				break;
			}
		}
		
		JMenuItem menuLiberer = new JMenuItem("Libérer");
		popup.add(menuLiberer);
		JMenuItem menuReserver = new JMenuItem("Réserver");
		popup.add(menuReserver);
		JMenuItem menuOccuper = new JMenuItem("Occuper");
		popup.add(menuOccuper);
		
		menuLiberer.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				System.out.println("Libérée");
			}
		});
		menuReserver.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				System.out.println("Réservée");
			}
		});
		menuOccuper.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				System.out.println("Occupée");
			}
		});

		setUndecorated(true);
		Container contenu = getContentPane();

		Container grille = new Container();
		grille.setLayout(new GridLayout(Ligne, Col));

		String Reserver = "data/reserver.jpg";
		String Prise = "data/prise.jpg";
		String Libre = "data/libre.jpg";
		for (int i = 0; i < Ligne; i++)
			for (int j = 0; j < Col; j++) {

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
				Bouton place = new Bouton(1, alea, alea);
				place.setText(Integer.toString(i));
				place.setPreferredSize(new Dimension(50, 50));
				grille.add(place);
				
				place.addActionListener(new ActionListener() {
		            public void actionPerformed(ActionEvent e) {
		            	popup.show(place, (getX() - 25), (getY() - 25));
		            }
				});
				
				JPanel panneau = new JPanel();
				panneau.add(place);
				grille.add(panneau);
			}
		contenu.add(grille, BorderLayout.NORTH);

		JButton bouton = new JButton("12 places, 3 prises et 9 libres");
		bouton.setBackground(Color.RED);
		contenu.add(bouton);

		pack();
		/*
		 * setLocation((Toolkit.getDefaultToolkit().getScreenSize().width+600)/3,
		 * (Toolkit.getDefaultToolkit().getScreenSize().height-600)/2);
		 */
		// setLocation(800, 200);
		setLocation(50, 50);

		setVisible(true);
	}

}
