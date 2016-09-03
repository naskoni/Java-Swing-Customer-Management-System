package cms.interfaces;

import javax.swing.table.TableModel;

@FunctionalInterface
public interface TableReader {

	String[] readRow(TableModel tableModel, int rowSelected);
}
