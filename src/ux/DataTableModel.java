package ux;

import javax.swing.table.DefaultTableModel;

public class DataTableModel extends DefaultTableModel {

	private static final long serialVersionUID = 1L;

	public DataTableModel(Object[][] data, Object[] colNames) {
		super(data, colNames);
	}
	
	public DataTableModel() {
		super();
	}

	public boolean isCellEditable(int row, int column) {
		return false;
    }
}
