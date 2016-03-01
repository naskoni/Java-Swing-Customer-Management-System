package cms.business;

import java.util.Arrays;

import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableModel;

import cms.interfaces.InputValidator;
import cms.interfaces.Operations;
import cms.interfaces.TableModelPersister;
import cms.interfaces.TableReader;
import cms.persistance.TableModelPersisterImpl;
import cms.views.CustomerPanel;
import cms.views.OptionDialogs;

public class OperationsImpl implements Operations {

	private static final int COLUMNS_COUNT = 6;
	private static final String ADD_CUSTOMER = "Add new customer: ";
	private static final String CUSTOMER_ADDED = "Added new customer.";
	private static final String CHOOSE_ROW_TO_EDIT = "Please, select a row for editing!";
	private static final String CHOOSE_ROW_TO_DELETE = "Please, select a row for deleting!";
	private static final String EDIT_CUSTOMER = "Edit customer information: ";
	private static final String INFO_EDITED = "Information is edited.";
	private static final String INFO_NOT_EDITED = "No changes have been made.";
	private static final String CUSTOMER_DELETED = "Information for selected customer is deleted.";

	private final OptionDialogs optionDialogs = new OptionDialogs();
	private final TableModelPersister persister = new TableModelPersisterImpl();
	private final TableReader tableReader = new TableReaderImpl();
	private final InputValidator validator = new InputValidatorImpl();

	@Override
	public void addNewCustomer(TableModel tableModel, CustomerPanel customerPanel) {
		String customerName;
		String date;
		String[] unputArgs = new String[2];
		int input;
		do {
			input = optionDialogs.displayCustomerPanel(ADD_CUSTOMER, customerPanel);
			customerName = customerPanel.getCustomerNameTextField().getText().trim();
			date = customerPanel.getContractDateTextField().getText();
			unputArgs[0] = customerName;
			unputArgs[1] = date;
		} while (input == 0 && !validator.validate(unputArgs));

		if (input == 0) {
			String[] newCustomerDetails = new String[COLUMNS_COUNT];
			newCustomerDetails[0] = customerName;
			newCustomerDetails[1] = customerPanel.getLocationTown().getSelectedItem().toString();
			newCustomerDetails[2] = customerPanel.getNotesTextArea().getText().trim();
			newCustomerDetails[3] = date;
			newCustomerDetails[4] = customerPanel.getContractFilePath();
			newCustomerDetails[5] = customerPanel.getLogoFilePath();

			((DefaultTableModel) tableModel).addRow(newCustomerDetails);
			persister.save(tableModel);
			optionDialogs.displayInfoMessage(CUSTOMER_ADDED);
		}
	}

	@Override
	public void editCustomer(TableModel tableModel, CustomerPanel customerPanel, int rowSelected) {
		if (rowSelected == -1) {
			optionDialogs.displayErrorMessage(CHOOSE_ROW_TO_EDIT);
		} else {
			String[] oldCustomerDetails = tableReader.readRow(tableModel, rowSelected);

			customerPanel.getCustomerNameTextField().setText(oldCustomerDetails[0]);
			customerPanel.getLocationTown().setEditable(true);
			customerPanel.getLocationTown().getEditor().setItem(oldCustomerDetails[1]);
			customerPanel.getNotesTextArea().setText(oldCustomerDetails[2]);
			customerPanel.getContractDateTextField().setText(oldCustomerDetails[3]);
			customerPanel.setContractFilePath(oldCustomerDetails[4]);
			customerPanel.setLogoFilePath(oldCustomerDetails[5]);

			String customerName;
			String date;
			String[] unputArgs = new String[2];
			int input;
			do {
				input = optionDialogs.displayCustomerPanel(EDIT_CUSTOMER, customerPanel);
				customerName = customerPanel.getCustomerNameTextField().getText().trim();
				date = customerPanel.getContractDateTextField().getText();
				unputArgs[0] = customerName;
				unputArgs[1] = date;
			} while (input == 0 && !validator.validate(unputArgs));

			if (input == 0) {
				String[] newCustomerDetails = new String[COLUMNS_COUNT];
				newCustomerDetails[0] = customerName;
				newCustomerDetails[1] = customerPanel.getLocationTown().getEditor().getItem().toString();
				newCustomerDetails[2] = customerPanel.getNotesTextArea().getText().trim();
				newCustomerDetails[3] = customerPanel.getContractDateTextField().getText();
				newCustomerDetails[4] = customerPanel.getContractFilePath();
				newCustomerDetails[5] = customerPanel.getLogoFilePath();

				if (Arrays.equals(oldCustomerDetails, newCustomerDetails)) {
					optionDialogs.displayInfoMessage(INFO_NOT_EDITED);
				} else {
					((DefaultTableModel) tableModel).insertRow(rowSelected, newCustomerDetails);
					((DefaultTableModel) tableModel).removeRow(rowSelected + 1);
					persister.save(tableModel);
					optionDialogs.displayInfoMessage(INFO_EDITED);
				}
			}
		}
	}

	@Override
	public void deleteCustomer(TableModel tableModel, int rowSelected) {
		if (rowSelected == -1) {
			optionDialogs.displayErrorMessage(CHOOSE_ROW_TO_DELETE);
		} else {
			((DefaultTableModel) tableModel).removeRow(rowSelected);
			persister.save(tableModel);
			optionDialogs.displayInfoMessage(CUSTOMER_DELETED);
		}
	}
}