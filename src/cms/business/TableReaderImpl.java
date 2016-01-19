package cms.business;

import javax.swing.table.TableModel;

import cms.interfaces.TableReader;

public class TableReaderImpl implements TableReader {

	@Override
	public String[] readRow(TableModel tableModel, int rowSelected) {
		int columnsCount = tableModel.getColumnCount();		
		String[] result = new String[columnsCount];

		for (int i = 0; i < columnsCount; i++) {
			result[i] = (String) tableModel.getValueAt(rowSelected, i);
		}

		return result;
	}

}
