package parking.gui;

import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import javax.swing.border.*;

public class AjouterVehicule extends JDialog {

	private static final long serialVersionUID = 1L;
	private boolean succeeded;
	private JTextField textImmatriculation;
	private JTextField textMarque;
	private JTextField textModele;
	private JTextField textProprietaire;
	private JTextField textEmplacement;

	public AjouterVehicule(Frame parent) {
		super(parent, "Ajout d'un véhicule", true);
		JPanel panel = new JPanel(new GridBagLayout());
		GridBagConstraints cs = new GridBagConstraints();

		cs.fill = GridBagConstraints.HORIZONTAL;

		JLabel lblImmatriculation = new JLabel("Immatriculation : ");
		cs.gridx = 0;
		cs.gridy = 0;
		cs.gridwidth = 1;
		panel.add(lblImmatriculation, cs);
		textImmatriculation = new JTextField(20);
		cs.gridx = 1;
		cs.gridy = 0;
		cs.gridwidth = 2;
		panel.add(textImmatriculation, cs);

		JLabel lblMarque = new JLabel("Marque : ");
		cs.gridx = 0;
		cs.gridy = 1;
		cs.gridwidth = 1;
		panel.add(lblMarque, cs);
		textMarque = new JTextField(20);
		cs.gridx = 1;
		cs.gridy = 1;
		cs.gridwidth = 2;
		panel.add(textMarque, cs);
		
		JLabel lblModele = new JLabel("Modèle : ");
		cs.gridx = 0;
		cs.gridy = 2;
		cs.gridwidth = 1;
		panel.add(lblModele, cs);
		textModele = new JTextField(20);
		cs.gridx = 1;
		cs.gridy = 2;
		cs.gridwidth = 2;
		panel.add(textModele, cs);

		JLabel lblProprietaire = new JLabel("Propriétaire : ");
		cs.gridx = 0;
		cs.gridy = 3;
		cs.gridwidth = 1;
		panel.add(lblProprietaire, cs);
		textProprietaire = new JTextField(20);
		cs.gridx = 1;
		cs.gridy = 3;
		cs.gridwidth = 2;
		panel.add(textProprietaire, cs);
		
		JLabel lblEmplacement = new JLabel("Emplacement : ");
		cs.gridx = 0;
		cs.gridy = 4;
		cs.gridwidth = 1;
		panel.add(lblEmplacement, cs);
		textEmplacement = new JTextField(20);
		cs.gridx = 1;
		cs.gridy = 4;
		cs.gridwidth = 2;
		panel.add(textEmplacement, cs);
		
		panel.setBorder(new LineBorder(Color.GRAY));
		JButton btnLogin = new JButton("Créer");

		btnLogin.addActionListener(new ActionListener() {

			public void actionPerformed(ActionEvent e) {
				if (getMarque().equals("test")) {
					JOptionPane.showMessageDialog(AjouterVehicule.this,
							"C'est bon", "Test OK",
							JOptionPane.INFORMATION_MESSAGE);
					succeeded = true;
					dispose();
				} else {
					JOptionPane.showMessageDialog(AjouterVehicule.this,
							"C'est pas bon", "Test pas OK",
							JOptionPane.ERROR_MESSAGE);
					textImmatriculation.setText("");
					textMarque.setText("");
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

	public boolean isSucceeded() {
		return succeeded;
	}

	public static void main(String[] args) {
		final JFrame frame = new JFrame("Fenête à ouvrir");
		final JButton btnLogin = new JButton("AJOUTER");

		btnLogin.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				AjouterVehicule loginDlg = new AjouterVehicule(frame);
				loginDlg.setVisible(true);
				if (loginDlg.isSucceeded()) {
					btnLogin.setText("Immatriculation "
							+ loginDlg.getImmatriculation() + " OK");
				}
			}
		});

		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setSize(300, 100);
		frame.setLayout(new FlowLayout());
		frame.getContentPane().add(btnLogin);
		frame.setLocationRelativeTo(null);
		frame.setVisible(true);
	}

}