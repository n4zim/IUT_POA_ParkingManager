package parking.gui;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

public class VehiculesView extends JPanel {

	private static final long serialVersionUID = 1L;

	public VehiculesView() {
		super(new GridLayout(1, 0));

		String[] colonnes = { "Immatriculation", "Marque", "Modèle",
				"Propriétaire", "Emplacement" };

		Object[][] data = { { "TestA", "TestB", "TestC", new Integer(5),
				"TestC" } };

		final JTable table = new JTable(data, colonnes);
		table.setPreferredScrollableViewportSize(new Dimension(500, 500));
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
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

		VehiculesView newContentPane = new VehiculesView();
		newContentPane.setOpaque(true);
		frame.setContentPane(newContentPane);
		frame.pack();
		frame.setLocation(
				(Toolkit.getDefaultToolkit().getScreenSize().width-500)/2,
				(Toolkit.getDefaultToolkit().getScreenSize().height-500)/2
				);
		frame.setVisible(true);
	}

}