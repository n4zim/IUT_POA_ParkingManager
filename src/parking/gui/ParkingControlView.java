package parking.gui;

import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.awt.print.PrinterException;
import java.awt.print.PrinterJob;
import java.io.IOException;

import javax.print.attribute.HashPrintRequestAttributeSet;
import javax.swing.JFrame;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JPanel;

/**
 * Fenêtre principale du programme, affiche les menus
 */
public class ParkingControlView extends JPanel {

	private static final long serialVersionUID = 1L;

	/**
	 * Constructeur
	 */
	public ParkingControlView() {
		super(new GridLayout(0, 1));
		setOpaque(true);
	}

	/**
	 * Demande à l'utilisateur si il est certain de vouloir quitter le programme.
	 */
	private void fenetreConfirmationFermer() {
		String YesNo[] = { "Oui", "Non" };
		int PromptResult = JOptionPane.showOptionDialog(null,
				"Fermer le programme ?", "Marre des parkings ?",
				JOptionPane.DEFAULT_OPTION, JOptionPane.WARNING_MESSAGE, null,
				YesNo, YesNo[1]);
		if (PromptResult == JOptionPane.YES_OPTION)
			System.exit(0);
	}
	
	private void fenetreImpression() {
	      PrinterJob pj = PrinterJob.getPrinterJob();
	      HashPrintRequestAttributeSet printAttr = new HashPrintRequestAttributeSet();
	      if(pj.printDialog(printAttr))
	      {
	    	  try
	    	  {
	    		  pj.print(printAttr);
	    	  }
	    	  catch(PrinterException e)
	    	  {
	    		  JOptionPane.showMessageDialog(this,"Echec lors de l'impression "+e,"Error",JOptionPane.ERROR_MESSAGE);
	    	  }
	      }
	}
	

	/**
	 * Crée et affiche la fenêtre
	 */
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
		
		JMenu addVh = new JMenu("AJOUTER");
		addVh.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				AjouterVehicule av = new AjouterVehicule(frame);
			}
		});
		
		JMenu delVh = new JMenu("SUPPRIMER");
		delVh.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {

			}
		});
		
		JMenu printMenu = new JMenu("Imprimer");
		printMenu.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				fenetreImpression();
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
		menuBar.add(addVh);
		menuBar.add(delVh);
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
				JOptionPane.showMessageDialog(new JFrame(),
						"Gestion de parking\nProjet CPOA 2015\nIUT Aix-en-Provence\n\n"
								+ "Team :\n" + "Josua Gonzalez\n"
								+ "Carole Lai Tong\n" + "Nazim Lachter\n"
								+ "Alexis Delieux\n", "A propos...",
						JOptionPane.INFORMATION_MESSAGE);
			}
		});

		frame.getContentPane().add(new VehiculesView());
		frame.pack();
		frame.setLocationRelativeTo(null);
		frame.setVisible(true);
	}
}