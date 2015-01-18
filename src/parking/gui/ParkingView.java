package parking.gui;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Container;
import java.awt.Dimension;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Map.Entry;
import javax.swing.*;
import parking.business.*;
import parking.exception.*;

/**
 * Affiche et gère la fenêtre qui montre l'état du parking
 */
public class ParkingView extends JFrame {

	private static final long serialVersionUID = 1L;

	/**
	 * Bouton qui fait office d'état
	 */
	private JButton statusButton;

	/**
	 * Les cases correspondant aux places de parking de la fenêtre
	 */
	private Map<Integer, Bouton> boutons;
	/**
	 * Permet d'obtenir les informations sur le parking
	 */
	private ControlleurInterfaceGraphique parent;
	
	/**
	 * Construit cette fenêtre
	 * @param parent Instance de ControlleurInterfaceGraphique
	 */
	ParkingView(ControlleurInterfaceGraphique parent) {
		super("Etat du parking");
		
		this.parent = parent;
		boutons = new HashMap<>();
		
		int nbCases = parent.getParking().getNombrePlaces();
		int nbColonnes = 10;
		int nbLignes = (int) Math.ceil(((double) nbCases)/nbColonnes);

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
				
				boutonPlace.getMenuOccuper().addActionListener(new ActionListener() {
					public void actionPerformed(ActionEvent e) {
						garer(index);
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
	
	/**
	 * Permet de libérer une place
	 * @param numPlace le numéro de la place
	 */
	public void liberer(Integer numPlace) {
		try {
			parent.libererPlace(numPlace);
		} catch (PlaceLibreException e) {
			JOptionPane.showMessageDialog(this, "Cette place est déjà libre.", "Erreur", JOptionPane.ERROR_MESSAGE);
		}
	}
	
	/**
	 * Demande de garer un véhicule sur une place
	 * @param numPlace le numéro de la place
	 */
	public void garer(Integer numPlace) {
		Vehicule aGarer = parent.demanderVehicule();
		try {
			parent.garerVehicule(aGarer, numPlace);
		} catch (TypePlaceInvalideException e) {
			JOptionPane.showMessageDialog(this, "Cette place est inadaptée à ce véhicule.", "Erreur", JOptionPane.ERROR_MESSAGE);
		} catch (PlaceOccupeeException e) {
			JOptionPane.showMessageDialog(this, "Cette place est occupée", "Erreur", JOptionPane.ERROR_MESSAGE);
		} catch (PlaceReserveeException e) {
			JOptionPane.showMessageDialog(this, "Cette place est réservée par un autre véhicule.", "Erreur", JOptionPane.ERROR_MESSAGE);
		}
	}
	
	/**
	 * Doit être appellé quand l'état du parking change
	 * Met à jour la grille ainsi que le message de status
	 * @param placesMap L'état des places de parking
	 */
	public void parkingStateChanged(Map<Integer, Place> placesMap) {
		Integer nbPlaces = parent.getParking().getNombrePlaces();
		Integer nbLibres = 0;
		Integer nbPrises = 0;
		Integer nbResa = 0;

	    Iterator<Map.Entry<Integer, Place>> it = placesMap.entrySet().iterator();
	    while (it.hasNext()) {
	        Map.Entry<Integer, Place> pairs = (Entry<Integer, Place>) it.next();
	        System.out.println(pairs.getKey() + " = " + pairs.getValue());
	        
	        Bouton b = boutons.get(pairs.getKey());
	        
	        if(pairs.getValue().isBooked()) {
	        	b.setEtat(Bouton.ETAT_RESERVE);
	        	nbResa++;
	        } else if(!pairs.getValue().isFree()) {
	        	b.setEtat(Bouton.ETAT_PRIS);
	        	nbPrises++;
	        } else {
	        	b.setEtat(Bouton.ETAT_LIBRE);
	        	nbLibres++;
	        }
	    }
		
		statusButton.setText(nbPlaces + " places, " + nbPrises + " prises et " + nbLibres + " libres, " + nbResa + " réservées"); 
	}

}
