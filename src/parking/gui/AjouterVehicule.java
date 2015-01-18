package parking.gui;

import java.awt.*;
import java.awt.event.*;

import javax.swing.*;
import javax.swing.border.*;

import parking.business.*;
import parking.exception.PlaceInexistanteException;

public class AjouterVehicule extends JDialog {

	private static final long serialVersionUID = 1L;
	private boolean succeeded;
	private JTextField textImmatriculation;
	private JTextField textMarque;
	private JTextField textModele;
	private JTextField textProprietaire;
	private JTextField textEmplacement;

	private void setGridBagConstraints (GridBagConstraints cs, boolean isLabel, int rowNumber){
		cs.gridy = rowNumber;
		if (isLabel){
			cs.gridx = 0;
			cs.gridwidth = 1;
		}
		else {
			cs.gridx = 1;
			cs.gridwidth = 2;
		}
	}
	
	public AjouterVehicule(Frame parent) {
		super(parent, "Ajout d'un véhicule", true);
		JPanel panel = new JPanel(new GridBagLayout());
		GridBagConstraints cs = new GridBagConstraints();

		cs.fill = GridBagConstraints.HORIZONTAL;

		int rowNumber = 0;
		
		JLabel lblImmatriculation = new JLabel("Immatriculation : ");
		setGridBagConstraints (cs, true, rowNumber);
		panel.add(lblImmatriculation, cs);
		textImmatriculation = new JTextField(20);
		setGridBagConstraints (cs, false, rowNumber++);
		panel.add(textImmatriculation, cs);

		JLabel lblMarque = new JLabel("Marque : ");
		setGridBagConstraints (cs, true, rowNumber);
		panel.add(lblMarque, cs);
		textMarque = new JTextField(20);
		setGridBagConstraints (cs, false, rowNumber++);
		panel.add(textMarque, cs);
		
		JLabel lblModele = new JLabel("Modèle : ");
		setGridBagConstraints (cs, true, rowNumber);
		panel.add(lblModele, cs);
		textModele = new JTextField(20);
		setGridBagConstraints (cs, false, rowNumber++);
		panel.add(textModele, cs);

		JLabel lblProprietaire = new JLabel("Propriétaire : ");
		setGridBagConstraints (cs, true, rowNumber);
		panel.add(lblProprietaire, cs);
		textProprietaire = new JTextField(20);
		setGridBagConstraints (cs, false, rowNumber++);
		panel.add(textProprietaire, cs);
		
		JLabel lblEmplacement = new JLabel("Emplacement numéro : ");
		setGridBagConstraints (cs, true, rowNumber);
		panel.add(lblEmplacement, cs);
		textEmplacement = new JTextField(20);
		setGridBagConstraints (cs, false, rowNumber);
		panel.add(textEmplacement, cs);
		
		panel.setBorder(new LineBorder(Color.GRAY));
		JButton btnLogin = new JButton("Créer");

		btnLogin.addActionListener(new ActionListener() {

			public void actionPerformed(ActionEvent e) {
				if (tousChampsRemplisEtValides()) {
					JFrame frameDemandeTypeVehicule = new JFrame("Type de véhicule");
					JPanel panelDemandeTypeVehicule = new JPanel(new FlowLayout());
					JButton boutonCamion = new JButton("Camion");
					JButton boutonMoto = new JButton("Moto");
					JButton boutonVoiture = new JButton("Voiture");
					panelDemandeTypeVehicule.add(boutonCamion);
					panelDemandeTypeVehicule.add(boutonMoto);
					panelDemandeTypeVehicule.add(boutonVoiture);
					frameDemandeTypeVehicule.add(panelDemandeTypeVehicule);
					/*JOptionPane.showMessageDialog(AjouterVehicule.this,
							"C'est bon", "Véhicule garé",
							JOptionPane.INFORMATION_MESSAGE);*/
					succeeded = true;
					
					dispose();
				} else {
					JOptionPane.showMessageDialog(AjouterVehicule.this,
							"C'est pas bon", "Test pas OK",
							JOptionPane.ERROR_MESSAGE);
					succeeded = false;
				}
			}
		});
		JButton btnCancel = new JButton("Annuler");
		btnCancel.addActionListener(new ActionListener() {

			public void actionPerformed(ActionEvent e) {
				dispose();
			}
		});
		JPanel bp = new JPanel();
		bp.add(btnLogin);
		bp.add(btnCancel);

		getContentPane().add(panel, BorderLayout.CENTER);
		getContentPane().add(bp, BorderLayout.PAGE_END);

		pack();
		setResizable(false);
		setLocationRelativeTo(parent);
	}

	public String getImmatriculation() {
		return textImmatriculation.getText().trim();
	}

	public String getMarque() {
		return textMarque.getText().trim();
	}
	
	public String getModele() {
		return textModele.getText().trim();
	}

	public String getProprietaire() {
		return textProprietaire.getText().trim();
	}

	public Integer getEmplacement() {
		Integer numero = -1;
		try{
			numero = Integer.parseInt(textEmplacement.getText().trim());
			if (numero < Constante.NUMERO_PREMIERE_PLACE
				|| numero >= Constante.NUMERO_PREMIERE_PLACE + Constante.NOMBRE_PLACES)
				throw new PlaceInexistanteException();
		}
		catch (NumberFormatException e){
			String msg = "Entrer un nombre entre " + Constante.NUMERO_PREMIERE_PLACE + " et " 
						+ (Constante.NUMERO_PREMIERE_PLACE + Constante.NOMBRE_PLACES - 1);
			JOptionPane.showMessageDialog(AjouterVehicule.this, msg, "C'est pas bon", JOptionPane.ERROR_MESSAGE);
		} catch (PlaceInexistanteException e) {
			String msg = "Entrer un nombre entre " + Constante.NUMERO_PREMIERE_PLACE + " et " 
					+ (Constante.NUMERO_PREMIERE_PLACE + Constante.NOMBRE_PLACES - 1);
			JOptionPane.showMessageDialog(AjouterVehicule.this, msg, "C'est pas bon", JOptionPane.ERROR_MESSAGE);
		}
		return numero;
	}
	
	public boolean tousChampsRemplisEtValides(){
		
		return !(getImmatriculation().equals("")
				|| getMarque().equals("")
				|| getModele().equals("")
				|| getProprietaire().equals(""));
	}

	public boolean hasSucceeded() {
		return succeeded;
	}

	public static void main(String[] args) {
		final JFrame frame = new JFrame("Fenête à ouvrir");
		final JButton addButton = new JButton("AJOUTER");

		addButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				AjouterVehicule buttonAdd = new AjouterVehicule(frame);
				buttonAdd.setVisible(true);
				if (buttonAdd.hasSucceeded()) {
					addButton.setText("Immatriculation "
							+ buttonAdd.getEmplacement() + " OK");
				}
			}
		});

		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setSize(300, 100);
		frame.setLayout(new FlowLayout());
		frame.getContentPane().add(addButton);
		frame.setLocationRelativeTo(null);
		frame.setVisible(true);
	}

}