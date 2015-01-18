package parking.gui;

import java.awt.Color;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.SwingConstants;

public class Bouton extends JButton {
	public static final int ETAT_LIBRE = 1;
	public static final int ETAT_RESERVE = 2;
	public static final int ETAT_PRIS = 3;
	 
    private static final long serialVersionUID = 1L;
 
    public Bouton(int id) {
        setForeground(Color.WHITE);
         
        setOpaque(false);
        setContentAreaFilled(false); // On met à false pour empêcher le composant de peindre l'intérieur du JButton.
        setBorderPainted(true); // De même, on ne veut pas afficher les bordures.
        setFocusPainted(false); // On n'affiche pas l'effet de focus.
         
        setHorizontalAlignment(SwingConstants.CENTER);
        setHorizontalTextPosition(SwingConstants.CENTER);
    }
    
	public void setEtat(int etat) {
		if(etat == ETAT_PRIS)
			setBackground(new Color(232, 60, 60));
		else if(etat == ETAT_RESERVE)
			setBackground(new Color(240, 177, 146));
		else
			setBackground(new Color(181, 229, 29));
	}
}