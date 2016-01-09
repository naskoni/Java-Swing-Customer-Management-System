package cms.interfaces;

import javax.swing.table.TableModel;

public interface Persister {

	TableModel load();

	void save(TableModel tableModel);

}