package parking.gui;

import javax.swing.*;

import java.awt.*;
import java.awt.event.*;

public class ParkingControlView extends JPanel {

	private static final long serialVersionUID = 1L;

	public ParkingControlView() {
		super(new GridLayout(0, 1));
	}
	
	private static void fenetreConfirmationFermer (){
		String YesNo[] = {"Oui", "Non"};
		int PromptResult = JOptionPane.showOptionDialog(null,
				"Fermer le programme ?", "Marre des parkings ?",
				JOptionPane.DEFAULT_OPTION, JOptionPane.WARNING_MESSAGE,
				null, YesNo, YesNo[1]);
		if(PromptResult==JOptionPane.YES_OPTION) System.exit(0);
	}
	
	static void afficher() {
		JFrame frame = new JFrame("Parking");

		frame.setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
		frame.addWindowListener(new WindowAdapter() {
			public void windowClosing(WindowEvent we) {
				fenetreConfirmationFermer ();
			}
		});
		
		JMenuBar menuBar = new JMenuBar();
		frame.setJMenuBar(menuBar);
		JMenu printMenu = new JMenu("Imprimer");
        JMenu helpMenu = new JMenu("Aide");
        JMenu quitMenu = new JMenu("Quitter");
        quitMenu.addMouseListener(new MouseListener() {
        	
			@Override
			public void mouseReleased(MouseEvent e) {}
			
			@Override
			public void mousePressed(MouseEvent e) {}
			
			@Override
			public void mouseExited(MouseEvent e) {}
			
			@Override
			public void mouseEntered(MouseEvent e) {}
			
			@Override
			public void mouseClicked(MouseEvent e) {
				fenetreConfirmationFermer ();
			}
		});
        menuBar.add(printMenu);
        menuBar.add(helpMenu);
        menuBar.add(quitMenu);
        JMenuItem ProgramHelp = new JMenuItem("Documentation");
        helpMenu.add(ProgramHelp);
        JMenuItem ProgramAbout = new JMenuItem("A propos");
        helpMenu.add(ProgramAbout);
		
		ParkingControlView newContentPane = new ParkingControlView();
		newContentPane.setOpaque(true);
		frame.setContentPane(newContentPane);
		frame.pack();
		/*frame.setLocation(
				(Toolkit.getDefaultToolkit().getScreenSize().width-500)/2,
				(Toolkit.getDefaultToolkit().getScreenSize().height-500)/2
				);*/
		//frame.setLocation(200, 200);
		frame.setLocationRelativeTo(null);
		frame.setVisible(true);
	}
}