package cms.interfaces;

import javax.swing.table.TableModel;

import cms.views.CustomerPanel;

public interface Operations {

	void addNewCustomer(TableModel tableModel, CustomerPanel customerPanel);

	void editCustomer(TableModel tableModel, CustomerPanel customerPanel, int rowSelected);

	void deleteCustomer(TableModel tableModel, int rowSelected);
}