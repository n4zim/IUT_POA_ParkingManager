package parking.gui;

import javax.swing.*;
import javax.swing.event.*;

import java.awt.*;
import java.awt.event.*;
import java.io.File;
import java.io.IOException;
import java.net.URI;

public class ParkingControlView extends JPanel {

	private static final long serialVersionUID = 1L;

	public ParkingControlView() {
		super(new GridLayout(0, 1));
	}

	private void fenetreConfirmationFermer() {
		String YesNo[] = { "Oui", "Non" };
		int PromptResult = JOptionPane.showOptionDialog(null,
				"Fermer le programme ?", "Marre des parkings ?",
				JOptionPane.DEFAULT_OPTION, JOptionPane.WARNING_MESSAGE, null,
				YesNo, YesNo[1]);
		if (PromptResult == JOptionPane.YES_OPTION)
			System.exit(0);
	}

	public void afficher() {
		JFrame frame = new JFrame("Parking");

		frame.setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
		frame.addWindowListener(new WindowAdapter() {
			public void windowClosing(WindowEvent we) {
				fenetreConfirmationFermer();
			}
		});

		JMenuBar menuBar = new JMenuBar();
		frame.setJMenuBar(menuBar);
		JMenu printMenu = new JMenu("Imprimer");
		printMenu.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				/* ACTION DU BOUTON */
			}
		});

		JMenu helpMenu = new JMenu("Aide");

		JMenu quitMenu = new JMenu("Quitter");
		quitMenu.addMouseListener(new MouseListener() {
			public void mouseClicked(MouseEvent e) {
				fenetreConfirmationFermer();
			}

			public void mouseEntered(MouseEvent arg0) {
			}

			public void mouseExited(MouseEvent arg0) {
			}

			public void mousePressed(MouseEvent arg0) {
			}

			public void mouseReleased(MouseEvent arg0) {
			}
		});
		menuBar.add(printMenu);
		menuBar.add(helpMenu);
		menuBar.add(quitMenu);

		JMenuItem ProgramHelp = new JMenuItem("Documentation");
		ProgramHelp.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				try {
					java.awt.Desktop
							.getDesktop()
							.browse(java.net.URI
									.create("http://sereth.nerdbox.fr/parking-doc"));
				} catch (IOException e1) {
					e1.printStackTrace();
				}

			}
		});
		helpMenu.add(ProgramHelp);

		JMenuItem ProgramAbout = new JMenuItem("A propos");
		helpMenu.add(ProgramAbout);
		ProgramAbout.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				JOptionPane.showMessageDialog(frame,
						"Gestion de parking\nProjet CPOA 2015\nIUT Aix-en-Provence\n\n"
								+ "Team :\n" + "Josua Gonzalez\n"
								+ "Carole Lai\n" + "Nazim Lachter\n"
								+ "Alexis Delieux\n", "A propos...",
						JOptionPane.INFORMATION_MESSAGE);
			}
		});

		ParkingControlView newContentPane = new ParkingControlView();
		newContentPane.setOpaque(true);
		frame.setContentPane(newContentPane);
		frame.pack();
		/*
		 * frame.setLocation(
		 * (Toolkit.getDefaultToolkit().getScreenSize().width-500)/2,
		 * (Toolkit.getDefaultToolkit().getScreenSize().height-500)/2 );
		 */
		// frame.setLocation(200, 200);
		frame.setLocationRelativeTo(null);
		frame.setVisible(true);
	}
}