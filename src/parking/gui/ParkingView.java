package parking.gui;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Container;
import java.awt.Dimension;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;
import java.util.Random;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JMenuItem;
import javax.swing.JPanel;
import javax.swing.JPopupMenu;

import parking.business.Place;

public class ParkingView extends JFrame {
	private static final long serialVersionUID = 1L;
	
	private final JPopupMenu popup = new JPopupMenu();
	private JButton statusButton;

	private int nbCases;
	private int nbColonnes;
	private int nbLignes;

	private Map<Integer, Bouton> boutons;
	private Interface parent;
	
	ParkingView(Interface parent) {
		super("Etat du parking");
		
		this.parent = parent;
		boutons = new HashMap<>();
		
		nbColonnes = 10;
		nbLignes = (int) Math.ceil(((double) nbCases)/nbColonnes);

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

		Container contenu = getContentPane();

		Container grille = new Container();
		grille.setLayout(new GridLayout(nbLignes, nbColonnes));

		Color reserve = new Color(240, 177, 146);
		Color libre = new Color(181, 229, 29);
		Color prise = new Color(232, 60, 60);

		for (int i = 0; i < nbLignes; i++)
			for (int j = 0; j < nbColonnes; j++) {
				final Integer index = i*nbColonnes+j;
				
				Random rand = new Random();
				int nombre = rand.nextInt(3);

				Bouton place = new Bouton(index);
				place.setOpaque(true);
				
				if (nombre == 0)
					place.setBackground(libre);
				if (nombre == 1)
					place.setBackground(reserve);
				if (nombre == 2)
					place.setBackground(prise);

				place.setText(index.toString());
				place.setPreferredSize(new Dimension(50, 50));
				grille.add(place);

				boutons.put(index, place);

				place.addActionListener(new ActionListener() {
					public void actionPerformed(ActionEvent e) {
						popup.show(boutons.get(index), (getX() - 25), (getY() - 25));
					}
				});

				JPanel panneau = new JPanel();
				panneau.add(place);
				grille.add(panneau);
			}
		contenu.add(grille, BorderLayout.NORTH);

		statusButton = new JButton("12 places, 3 prises et 9 libres");
		statusButton.setBackground(Color.RED);
		contenu.add(statusButton);

		pack();
		setLocation(50, 50);

		setDefaultCloseOperation(DO_NOTHING_ON_CLOSE);
		setVisible(true);
	}

	public void notifyParkingStateChanged(Map<Integer, Place> placesMap) {
		
	}

}
