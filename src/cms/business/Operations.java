package cms.business;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

import javax.swing.table.DefaultTableModel;

import cms.persistance.TableModelPersister;
import cms.view.CustomerPanel;
import cms.view.OptionDialogs;

public class Operations {

	private static final int COLUMNS_COUNT = 6;	
	private static final String ADD_CUSTOMER = "Add new customer: ";
	private static final String CUSTOMER_ADDED = "Added new customer.";
	private static final String CHOOSE_ROW_TO_EDIT = "Please, select a row for editing!";
	private static final String CHOOSE_ROW_TO_DELETE = "Please, select a row for deleting!";	
	private static final String EDIT_CUSTOMER = "Edit customer information: ";
	private static final String INFO_EDITED = "Information is edited.";
	private static final String INFO_NOT_EDITED = "No changes have been made.";	
	private static final String CUSTOMER_DELETED = "Information for selected customer is deleted.";	
	
	private static Validator validator = new Validator();
	private static OptionDialogs optionDialogs = new OptionDialogs();
	private static TableModelPersister tableModelPersister = new TableModelPersister();
	private static Set<String> usedNames = new HashSet<>();
	
	public Operations() {		
	}

	public void addNewCustomer(DefaultTableModel tableModel, CustomerPanel customerPanel) {
		String customerName = "";
		String date;
		int input = 1;
		do {
			input = optionDialogs.displayCustomerPanel(ADD_CUSTOMER, customerPanel);
			customerName = customerPanel.getCustomerNameTextField().getText().trim();
			date = customerPanel.getContractDateTextField().getText();
		} while (input == 0 && !validator.validateNameAndDate(usedNames, customerName, date));		
		
		if (input == 0) {
			String[] newCustomerDetails = new String[COLUMNS_COUNT];
			newCustomerDetails[0] = customerName;	
			newCustomerDetails[1] = customerPanel.getLocationTown().getSelectedItem().toString();
			newCustomerDetails[2] = customerPanel.getNotesTextArea().getText().trim();			
			newCustomerDetails[3] = date;
			newCustomerDetails[4] = customerPanel.getContractFilePath();
			newCustomerDetails[5] = customerPanel.getLogoFilePath();

			tableModel.addRow(newCustomerDetails);
			usedNames.add(customerName.toUpperCase());
			tableModelPersister.save(tableModel);
        	optionDialogs.displayInfoMessage(CUSTOMER_ADDED);
		}  
	}
	
	public void editCustomer(DefaultTableModel tableModel, 
			CustomerPanel customerPanel, int rowSelected) {
		if (rowSelected == -1) {
			optionDialogs.displayErrorMessage(CHOOSE_ROW_TO_EDIT);
		} else {
			String[] oldCustomerDetails = readRow(tableModel, rowSelected);			

			customerPanel.getCustomerNameTextField().setText(oldCustomerDetails[0]);
			customerPanel.getLocationTown().setEditable(true);
			customerPanel.getLocationTown().getEditor().setItem(oldCustomerDetails[1]);
			customerPanel.getNotesTextArea().setText(oldCustomerDetails[2]);
			customerPanel.getContractDateTextField().setText(oldCustomerDetails[3]);
			customerPanel.setContractFilePath(oldCustomerDetails[4]);
			customerPanel.setLogoFilePath(oldCustomerDetails[5]);
			
			usedNames.remove(oldCustomerDetails[0].toUpperCase());
			
			String customerName = "";
			String date;
			int input = 1;			
			do {
				input = optionDialogs.displayCustomerPanel(EDIT_CUSTOMER, customerPanel);
				customerName = customerPanel.getCustomerNameTextField().getText().trim();
				date = customerPanel.getContractDateTextField().getText();
			} while (input == 0 && !validator.validateNameAndDate(usedNames, customerName, date));			
			
			if (input == 0) {
				String[] newCustomerDetails = new String[COLUMNS_COUNT];
				newCustomerDetails[0] = customerName;	
				newCustomerDetails[1] = customerPanel.getLocationTown().getEditor().getItem().toString();
				newCustomerDetails[2] = customerPanel.getNotesTextArea().getText().trim();
				newCustomerDetails[3] = customerPanel.getContractDateTextField().getText();
				newCustomerDetails[4] = customerPanel.getContractFilePath();
				newCustomerDetails[5] = customerPanel.getLogoFilePath();
				
				if (Arrays.equals(oldCustomerDetails, newCustomerDetails)) {
					usedNames.add(oldCustomerDetails[0].toUpperCase());
					optionDialogs.displayInfoMessage(INFO_NOT_EDITED);
				} else {
					tableModel.insertRow(rowSelected, newCustomerDetails);
					tableModel.removeRow(rowSelected + 1);
					tableModelPersister.save(tableModel);
					usedNames.add(customerName.toUpperCase());
					optionDialogs.displayInfoMessage(INFO_EDITED);
				}	
			} else {
				usedNames.add(oldCustomerDetails[0].toUpperCase());
			}	
		}
	}

	public void deleteCustomer(DefaultTableModel tableModel, int rowSelected) {
		if (rowSelected == -1) {
			optionDialogs.displayErrorMessage(CHOOSE_ROW_TO_DELETE);			
		} else {
			String customerName = tableModel.getValueAt(rowSelected, 0).toString();
			usedNames.remove(customerName.toUpperCase());
			tableModel.removeRow(rowSelected);
			tableModelPersister.save(tableModel);
			optionDialogs.displayInfoMessage(CUSTOMER_DELETED);
		}
	}

	public String[] readRow(DefaultTableModel tableModel, int rowSelected) {
		String[] result = new String[COLUMNS_COUNT];

		for (int i = 0; i < COLUMNS_COUNT; i++) {
			result[i] = (String) tableModel.getValueAt(rowSelected, i);
		}

		return result;
	}
}