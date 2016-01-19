package cms.interfaces;

import javax.swing.table.TableModel;

public interface TableModelPersister {

	TableModel load();

	void save(TableModel tableModel);

}