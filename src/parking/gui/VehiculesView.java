package parking.gui;

import javax.swing.*;

import java.awt.*;
import java.awt.event.*;

public class VehiculesView extends JPanel {

	private static final long serialVersionUID = 1L;

	public VehiculesView() {
		super(new GridLayout(1, 0));

		Object[][] data = { { "TestA", "TestB", "TestC", "TestD", "TestE" } };

		final JTable table = new JTable(data,
				new String[] { "Immatriculation", "Marque", "Modèle", "Propriétaire", "Emplacement" });
		
		table.setPreferredScrollableViewportSize(new Dimension(600, 30));
		table.setFillsViewportHeight(true);

		table.addMouseListener(new MouseAdapter() {
			public void mouseClicked(MouseEvent e) {

				int numRows = table.getRowCount();
				int numCols = table.getColumnCount();
				javax.swing.table.TableModel model = table.getModel();

				for (int i = 0; i < numRows; i++) {
					System.out.print("    row " + i + ":");
					for (int j = 0; j < numCols; j++) {
						System.out.print("  " + model.getValueAt(i, j));
					}
					System.out.println();
				}
				System.out.println("--------------------------");

			}
		});

		JScrollPane scrollPane = new JScrollPane(table);
		add(scrollPane);
	}
	
	static void afficher() {
		JFrame frame = new JFrame("Véhicules");

		frame.setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
		frame.addWindowListener(new WindowAdapter() {
			public void windowClosing(WindowEvent we) {
				String YesNo[] = {"Oui", "Non"};
				int PromptResult = JOptionPane.showOptionDialog(null,
						"Fermer le programme ?", "Marre des parkings ?",
						JOptionPane.DEFAULT_OPTION, JOptionPane.WARNING_MESSAGE,
						null, YesNo, YesNo[1]);
			  if(PromptResult==JOptionPane.YES_OPTION) System.exit(0);
			}
		});
		
		JMenuBar menuBar = new JMenuBar();
		frame.setJMenuBar(menuBar);
		JMenu printMenu = new JMenu("Imprimer");
        JMenu helpMenu = new JMenu("Aide");
        JMenu quitMenu = new JMenu("Quitter");
        menuBar.add(printMenu);
        menuBar.add(helpMenu);
        menuBar.add(quitMenu);
        JMenuItem ProgramHelp = new JMenuItem("Documentation");
        helpMenu.add(ProgramHelp);
        JMenuItem ProgramAbout = new JMenuItem("A propos...");
        helpMenu.add(ProgramAbout);
        
        /*ProgramAbout.addActionListener(new WindowAdapter() {
        JOptionPane.showMessageDialog(frame, "Eggs are not supposed to be green.");*/
		
		VehiculesView newContentPane = new VehiculesView();
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