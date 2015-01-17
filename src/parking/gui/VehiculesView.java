package parking.gui;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import java.awt.Dimension;
import java.awt.GridLayout;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

public class VehiculesView extends JPanel {

	public VehiculesView() {
		super(new GridLayout(1, 0));

		String[] columnNames = { "Immatriculation", "Marque", "Modèle", "Propriétaire", "Emplacement" };

		Object[][] data = {
				{ "TestA", "TestB", "TestC", new Integer(5), "TestC" }
			};

		final JTable table = new JTable(data, columnNames);
		table.setPreferredScrollableViewportSize(new Dimension(500, 70));
		table.setFillsViewportHeight(true);

			table.addMouseListener(new MouseAdapter() {
				public void mouseClicked(MouseEvent e) {
					
					int numRows = table.getRowCount();
					int numCols = table.getColumnCount();
					javax.swing.table.TableModel model = table.getModel();

					System.out.println("Value of data: ");
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

	private static void afficher() {

		JFrame frame = new JFrame("Véhicules");
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

		VehiculesView newContentPane = new VehiculesView();
		newContentPane.setOpaque(true);
		frame.setContentPane(newContentPane);

		frame.setSize(700, 700);
		frame.pack();
		frame.setVisible(true);
	}

	public static void main(String[] args) {
		javax.swing.SwingUtilities.invokeLater(new Runnable() {
			public void run() {
				afficher();
			}
		});
	}
}