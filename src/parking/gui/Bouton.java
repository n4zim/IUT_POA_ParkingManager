package parking.gui;

import java.awt.Color;
import java.awt.Component;
import java.awt.event.*;
import java.io.Externalizable;

import javax.swing.JButton;
import javax.swing.JMenuItem;
import javax.swing.JPopupMenu;
import javax.swing.SwingConstants;

/**
 * Extention de JButton qui représente une case de parking
 */
public class Bouton extends JButton {
	/**
	 * La place est libre
	 */
	public static final int ETAT_LIBRE = 1;
	/**
	 * La place est réservée
	 */
	public static final int ETAT_RESERVE = 2;
	/**
	 * La place est occupée
	 */
	public static final int ETAT_PRIS = 3;
	
	/**
	 * Menu affiché au clic sur le bouton
	 */
	private final JPopupMenu popup = new JPopupMenu();
	/**
	 * Menu libérer
	 */
    private JMenuItem menuLiberer;
    /**
     * Menu réserver
     */
    private JMenuItem menuReserver;
    /**
     * Menu occuper
     */
    private JMenuItem menuOccuper;
    
	/**
	 * Index de la place correspondant au bouton
	 */
	Integer index;
    
	/**
	 * Crée un nouveau bouton
	 * @param id son index
	 */
    public Bouton(int id) {
    	index = id;
    	
    	setForeground(Color.WHITE); // texte en blanc
        setContentAreaFilled(false); // On met à false pour empêcher le composant de peindre l'intérieur du JButton.
        setBorderPainted(true); // De même, on ne veut pas afficher les bordures.
        setFocusPainted(false); // On n'affiche pas l'effet de focus.
        setOpaque(false); // permet d'afficher la couleur de fond
         
        // alignement
        setHorizontalAlignment(SwingConstants.CENTER);
        setHorizontalTextPosition(SwingConstants.CENTER);
        
        initPopupMenu();

		addMouseListener(new MouseAdapter() {
			public void mousePressed(MouseEvent e) {
				showPopup(e);
			}

			public void mouseReleased(MouseEvent e) {
				showPopup(e);
			}

			private void showPopup(MouseEvent e) {
				if (e.isPopupTrigger()) {
					popup.show((Component) e.getSource(), (getX() + 25),
							(getY() + 25));
				}
			}
		});

    }
    
    /**
     * Crée le popup menu
     */
    private void initPopupMenu() {
    	menuLiberer = new JMenuItem("Libérer");
		menuReserver = new JMenuItem("Réserver");
		menuOccuper = new JMenuItem("Occuper");
		
		popup.add(menuReserver);
		popup.add(menuLiberer);
		popup.add(menuOccuper);

		
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
    }
    
    /**
     * Règle l'état du bouton actuel
     * @param etat une des trois constantes ETAT_RESERVE, ETAT_LIBRE OU ETAT_PRIS
     */
	public void setEtat(int etat) {
		if(etat == ETAT_PRIS)
			setBackground(new Color(232, 60, 60));
		else if(etat == ETAT_RESERVE) {
			setBackground(new Color(240, 177, 146));
		} else
			setBackground(new Color(181, 229, 29));
	}

	/**
	 * Retourne le menu libérer
	 * @return le menu en question
	 */
	public JMenuItem getMenuLiberer() {
		return menuLiberer;
	}

	/**
	 * Retourne le menu réserver
	 * @return le menu en question
	 */
	public JMenuItem getMenuReserver() {
		return menuReserver;
	}

	/**
	 * Retourne le menu occuper
	 * @return le menu en question
	 */
	public JMenuItem getMenuOccuper() {
		return menuOccuper;
	}
}