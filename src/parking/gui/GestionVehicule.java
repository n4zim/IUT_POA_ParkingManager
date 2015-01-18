package parking.gui;

import java.awt.FlowLayout;
import java.awt.List;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JOptionPane;

import parking.business.Vehicule;

/**
 * Gère les véhicules
 */
public class GestionVehicule extends JDialog {
	private static final long serialVersionUID = 1L;
	/**
	 * Véhicules enregistrés
	 */
	java.util.List<Vehicule> vehicules;
	
	List liste;
	
	public GestionVehicule() {
		vehicules = new ArrayList<Vehicule>();
		liste = new List();

		JButton ok = new JButton("ok");
		JButton nv = new JButton("nouvelle voiture");
		JButton nv2 = new JButton("nouvelle moto");
		JButton nv3 = new JButton("nouveau camion");
		ok.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				valier();
			}
		});
		nv.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				creerNouveau();
			}
		});
		nv2.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				creerNouveau();
			}
		});
		nv3.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				creerNouveau();
			}
		});
		
		setLayout(new FlowLayout());
		add(liste);
		add(ok);
		add(nv);
		add(nv2);
		add(nv3);
		
		pack();
		
		mettreAJour();
	}

	boolean wait = true;
	Vehicule vChoisi = null;
	
	private void valier() {
		wait = false;
		
		vChoisi = vehicules.get(liste.getSelectedIndex());
	}
	
	public Vehicule demanderVehicule() {
		setVisible(true);

		
		return null;
	}
	
	public boolean getWait() {
		return wait;
	}
	
	public void mettreAJour() {
		liste.removeAll();
		for (Vehicule vehicule : vehicules) {
			liste.add(vehicule.getClass().getName() + " : " + vehicule.getNumeroImmatriculation());
		}
	}
	
	public void creerNouveau() {
		String immat = JOptionPane.showInputDialog("Numéro d'immatriculation");
		String marque = JOptionPane.showInputDialog("Marque");
		String modele = JOptionPane.showInputDialog("Modèle");
		String proprio = JOptionPane.showInputDialog("Nom du propriétaire");
		
		vehicules.add(new Vehicule(immat, marque, modele, proprio));
		mettreAJour();
	}

	public Vehicule getVehiculeSelectione() {
		return vChoisi;
	}
}
