package parking.gui;

import java.util.Vector;

import javax.swing.table.AbstractTableModel;

public class VehiculesViewModel extends AbstractTableModel {

	private static final long serialVersionUID = 1L;

	public static final int IMMA = 0;
	public static final int MARQ = 1;
	public static final int MODE = 2;
	public static final int PROP = 3;
	public static final int EMPL = 4;
	public static final int ENDC = 5;

	protected String[] colNames;
	@SuppressWarnings("rawtypes")
	protected Vector data;

	@SuppressWarnings("rawtypes")
	public VehiculesViewModel(String[] colNames) {
		this.colNames = colNames;
		data = new Vector();
	}

	public String getColumnName(int column) {
		return colNames[column];
	}

	public boolean isCellEditable(int row, int column) {
		if (column == ENDC)
			return false;
		else
			return true;
	}

	@SuppressWarnings({ "rawtypes", "unchecked" })
	public Class getColumnClass(int column) {
		switch (column) {
		case IMMA:
		case MARQ:
		case MODE:
		case PROP:
		case EMPL:
			return String.class;
		default:
			return Object.class;
		}
	}

	public Object getValueAt(int row, int column) {
		VehiculesViewRecords record = (VehiculesViewRecords) data.get(row);
		switch (column) {
		case IMMA:
			return record.getImmatriculation();
		case MARQ:
			return record.getMarque();
		case MODE:
			return record.getModele();
		case PROP:
			return record.getProprietaire();
		case EMPL:
			return record.getEmplacement();
		default:
			return new Object();
		}
	}

	public void setValueAt(Object value, int row, int column) {
		VehiculesViewRecords record = (VehiculesViewRecords) data.get(row);
		switch (column) {
		case IMMA:
			record.setImmatriculation((String) value);
			break;
		case MARQ:
			record.setMarque((String) value);
			break;
		case MODE:
			record.setModele((String) value);
			break;
		case PROP:
			record.setProprietaire((String) value);
			break;
		case EMPL:
			record.setEmplacement((String) value);
			break;
		default:
			System.out.println("Erreur de colonne !");
		}
		fireTableCellUpdated(row, column);
	}

	public int getRowCount() {
		return data.size();
	}

	public int getColumnCount() {
		return colNames.length;
	}

	public boolean hasEmptyRow() {
		if (data.size() == 0)
			return false;
		VehiculesViewRecords audioRecord = (VehiculesViewRecords) data.get(data
				.size() - 1);

		if (audioRecord.getImmatriculation().trim().equals("")
				&& audioRecord.getMarque().trim().equals("")
				&& audioRecord.getModele().trim().equals("")
				&& audioRecord.getProprietaire().trim().equals("")
				&& audioRecord.getEmplacement().trim().equals("")) {
			return true;
		} else
			return false;
	}

	@SuppressWarnings("unchecked")
	public void addEmptyRow() {
		data.add(new VehiculesViewRecords());
		fireTableRowsInserted(data.size() - 1, data.size() - 1);
	}
}