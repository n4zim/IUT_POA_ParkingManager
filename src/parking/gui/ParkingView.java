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
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JPopupMenu;

import parking.business.Place;
import parking.exception.PlaceDisponibleException;

public class ParkingView extends JFrame {
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


		Container contenu = getContentPane();

		Container grille = new Container();
		grille.setLayout(new GridLayout(nbLignes, nbColonnes));

		// on génere les cases pour chauqe place de parking
		for (int i = 0; i < nbLignes; i++)
			for (int j = 0; j < nbColonnes; j++) {
				final Integer index = (i * nbColonnes) + j + parent.getParking().getPremierNumeroDePlace();

				Bouton boutonPlace = new Bouton(index);
				boutonPlace.setOpaque(true);
				boutonPlace.setText(index.toString());
				boutonPlace.setPreferredSize(new Dimension(50, 50));
				grille.add(boutonPlace);

				boutonPlace.getMenuLiberer().addActionListener(new ActionListener() {
					public void actionPerformed(ActionEvent e) {
						liberer(index);
					}
				});
				
				boutons.put(index, boutonPlace);

				JPanel panneau = new JPanel();
				panneau.add(boutonPlace);
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
	
	public void liberer(Integer numPlace) {
		Place placeALibere = parent.getParking().getPlacesMap().get(numPlace);
		try {
			parent.getParking().freePlace(placeALibere);
		} catch (PlaceDisponibleException e) {
			JOptionPane.showMessageDialog(this, "Cette place est déjà libre.", "Erreur", JOptionPane.ERROR_MESSAGE);
		}
	}
	
	public void parkingStateChanged(Map<Integer, Place> placesMap) {
		Integer nbPlaces = parent.getParking().getNombrePlaces();
		Integer nbLibres = 0;
		Integer nbPrises = 0;

	    Iterator<Map.Entry<Integer, Place>> it = placesMap.entrySet().iterator();
	    while (it.hasNext()) {
	        Map.Entry<Integer, Place> pairs = (Entry<Integer, Place>) it.next();
	        System.out.println(pairs.getKey() + " = " + pairs.getValue());
	        
	        Bouton b = boutons.get(pairs.getKey());
	        
	        if(pairs.getValue().isBooked())
	        	b.setEtat(Bouton.ETAT_RESERVE);
	        else if(!pairs.getValue().isFree()) {
	        	b.setEtat(Bouton.ETAT_PRIS);
	        	nbPrises++;
	        } else {
	        	b.setEtat(Bouton.ETAT_LIBRE);
	        	nbLibres++;
	        }
	    }
		
		statusButton.setText(nbPlaces + " places, " + nbPrises + " prises et " + nbLibres + " libres"); 
	}

}
