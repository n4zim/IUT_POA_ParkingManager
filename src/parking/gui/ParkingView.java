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
	final JPopupMenu popup = new JPopupMenu();

	private int nbCases = 40;
	
	ParkingView() {
		super("Etat du parking");
		
		int Colonnes = 10;
		int Lignes = (int) Math.ceil(((double) nbCases)/Colonnes);

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

		//setUndecorated(true);
		Container contenu = getContentPane();

		Container grille = new Container();
		grille.setLayout(new GridLayout(Lignes, Colonnes));

		Color reserve = new Color(240, 177, 146);
		Color libre = new Color(181, 229, 29);
		Color prise = new Color(232, 60, 60);

		for (int i = 0; i < Lignes; i++)
			for (int j = 0; j < Colonnes; j++) {

				Random rand = new Random();
				int nombre = rand.nextInt(3);

				Bouton place = new Bouton(i+j);
				place.setOpaque(true);
				
				if (nombre == 0)
					place.setBackground(libre);
				if (nombre == 1)
					place.setBackground(reserve);
				if (nombre == 2)
					place.setBackground(prise);

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
