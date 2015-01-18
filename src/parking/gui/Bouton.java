package parking.gui;

import java.awt.Color;
import javax.swing.JButton;
import javax.swing.SwingConstants;

public class Bouton extends JButton {
	 
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
}