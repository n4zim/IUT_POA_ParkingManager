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
		JFrame frame = new JFrame("Véhicule");
		
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