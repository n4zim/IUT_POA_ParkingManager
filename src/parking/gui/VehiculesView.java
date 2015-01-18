package parking.gui;

import java.awt.BorderLayout;
import java.awt.Component;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

import javax.swing.event.TableModelEvent;
import javax.swing.event.TableModelListener;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.TableColumn;
import javax.swing.*;

public class VehiculesView extends JPanel {

	private static final long serialVersionUID = 1L;

	public static final String[] columns = { "Immatriculation", "Marque", "Modèle", "Propriétaire", "Emplacement", "" };

	protected JTable table;
	protected JScrollPane scroll;
	protected VehiculesViewModel model;

	
	
	public static void main(String[] args) {
		JFrame frame = new JFrame("ICI CA MARCHE P***** DE M****");
		frame.addWindowListener(new WindowAdapter() {
			public void windowClosing(WindowEvent evt) {
				System.exit(0);
			}
		});
		frame.getContentPane().add(new VehiculesView());
		frame.pack();
		frame.setVisible(true);
	}
	
	
	
	public VehiculesView() {
		initComponent();
	}

	public void initComponent() {
		model = new VehiculesViewModel(columns);
		model.addTableModelListener(new VehiculesView.InteractiveTableModelListener());
		table = new JTable();
		table.setModel(model);
		table.setSurrendersFocusOnKeystroke(true);
		if (!model.hasEmptyRow()) model.addEmptyRow();

		scroll = new javax.swing.JScrollPane(table);
		table.setPreferredScrollableViewportSize(new java.awt.Dimension(500, 300));
		TableColumn hidden = table.getColumnModel().getColumn(VehiculesViewModel.ENDC);
		hidden.setMinWidth(2);
		hidden.setPreferredWidth(2);
		hidden.setMaxWidth(2);
		hidden.setCellRenderer(new InteractiveRenderer(VehiculesViewModel.ENDC));

		setLayout(new BorderLayout());
		add(scroll, BorderLayout.CENTER);
	}

	public void highlightLastRow(int row) {
		int lastrow = model.getRowCount();
		if (row == lastrow - 1) table.setRowSelectionInterval(lastrow - 1, lastrow - 1);
		else table.setRowSelectionInterval(row + 1, row + 1);

		table.setColumnSelectionInterval(0, 0);
	}

	class InteractiveRenderer extends DefaultTableCellRenderer {

		private static final long serialVersionUID = 1L;

		protected int interactiveColumn;

		public InteractiveRenderer(int interactiveColumn) {
			this.interactiveColumn = interactiveColumn;
		}

		public Component getTableCellRendererComponent(JTable table,
				Object value, boolean isSelected, boolean hasFocus, int row,
				int column) {
			Component c = super.getTableCellRendererComponent(table, value, isSelected, hasFocus, row, column);
			if (column == interactiveColumn && hasFocus) {
				if ((VehiculesView.this.model.getRowCount() - 1) == row
						&& !VehiculesView.this.model.hasEmptyRow()) {
					VehiculesView.this.model.addEmptyRow();
				}

				highlightLastRow(row);
			}

			return c;
		}
	}

	public class InteractiveTableModelListener implements TableModelListener {
		public void tableChanged(TableModelEvent evt) {
			if (evt.getType() == TableModelEvent.UPDATE) {
				int column = evt.getColumn();
				int row = evt.getFirstRow();
				System.out.println("row: " + row + " column: " + column);
				table.setColumnSelectionInterval(column + 1, column + 1);
				table.setRowSelectionInterval(row, row);
			}
		}
	}

}