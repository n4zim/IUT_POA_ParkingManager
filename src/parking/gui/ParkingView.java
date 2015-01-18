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
import java.util.Iterator;
import java.util.Map;
import java.util.Map.Entry;
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
		
		nbCases = parent.getParking().getNombrePlaces();
		nbColonnes = 10;
		nbLignes = (int) Math.ceil(((double) nbCases)/nbColonnes);

		JMenuItem menuLiberer = new JMenuItem("Libérer");
		JMenuItem menuReserver = new JMenuItem("Réserver");
		JMenuItem menuOccuper = new JMenuItem("Occuper");
		
		popup.add(menuReserver);
		popup.add(menuLiberer);
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

		// on génere les cases pour chauqe place de parking
		for (int i = 0; i < nbLignes; i++)
			for (int j = 0; j < nbColonnes; j++) {
				final Integer index = (i * nbColonnes) + j + parent.getParking().getPremierNumeroDePlace();

				Bouton place = new Bouton(index);
				place.setOpaque(true);
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

		statusButton = new JButton("status");
		statusButton.setBackground(Color.RED);
		contenu.add(statusButton);

		pack();
		setLocation(50, 50);

		setDefaultCloseOperation(DO_NOTHING_ON_CLOSE);
		setVisible(true);
	}
	
	public void notifyParkingStateChanged(Map<Integer, Place> placesMap) {
		Integer nbPlaces = parent.getParking().getNombrePlaces();
		Integer nbLibres = 0;
		Integer nbPrises = 0;

	    Iterator<Map.Entry<Integer, Place>> it = placesMap.entrySet().iterator();
	    while (it.hasNext()) {
	        Map.Entry<Integer, Place> pairs = (Entry<Integer, Place>) it.next();
	        System.out.println(pairs.getKey() + " = " + pairs.getValue());
	        
	        Bouton b = boutons.get(pairs.getKey());
	        
	        if(!pairs.getValue().isFree()) {
	        	b.setEtat(Bouton.ETAT_PRIS);
	        	nbPrises++;
	        } else if(pairs.getValue().isBooked())
	        	b.setEtat(Bouton.ETAT_RESERVE);
	        else {
	        	b.setEtat(Bouton.ETAT_LIBRE);
	        	nbLibres++;
	        }
	    }
		
		statusButton.setText(nbPlaces + " places, " + nbPrises + " prises et " + nbLibres + " libres"); 
	}

}
