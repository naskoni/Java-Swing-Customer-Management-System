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
	private static final String ADD_CLIENT = "Add new client: ";
	private static final String CLIENT_ADDED = "Added new client.";
	private static final String CHOOSE_ROW_TO_EDIT = "Please, select a row for editing!";
	private static final String CHOOSE_ROW_TO_DELETE = "Please, select a row for deleting!";	
	private static final String EDIT_CLIENT = "Edit client information: ";
	private static final String INFO_EDITED = "Information is edited.";
	private static final String INFO_NOT_EDITED = "No changes have been made.";	
	private static final String CLIENT_DELETED = "Information for selected client is deleted.";	
	
	private static Validator validator = new Validator();
	private static OptionDialogs optionDialogs = new OptionDialogs();
	private static TableModelPersister tableModelPersister = new TableModelPersister();
	private static Set<String> usedNames = new HashSet<>();
	
	public Operations() {		
	}

	public void addNewClient(DefaultTableModel tableModel, CustomerPanel panel) {
		String custName = "";
		String date;
		int input = 1;
		do {
			input = optionDialogs.displayClientPanel(ADD_CLIENT, panel);
			custName = panel.getCustNameTextField().getText().trim();
			date = panel.getContractDateTextField().getText();
		} while (input == 0 && !validator.validateNameAndDate(usedNames, custName, date));		
		
		if (input == 0) {
			String[] newClientDetails = new String[COLUMNS_COUNT];
			newClientDetails[0] = custName;	
			newClientDetails[1] = panel.getLocationTown().getSelectedItem().toString();
			newClientDetails[2] = panel.getNotesTextArea().getText().trim();
			newClientDetails[3] = date;
			newClientDetails[4] = panel.getContractFilePath();
			newClientDetails[5] = panel.getLogoFilePath();

			tableModel.addRow(newClientDetails);
			usedNames.add(custName.toUpperCase());
			tableModelPersister.save(tableModel);
        	optionDialogs.displayInfoMessage(CLIENT_ADDED);
		}  
	}
	
	public void editClient(DefaultTableModel tableModel, 
			CustomerPanel panel, int rowSelected) {
		if (rowSelected == -1) {
			optionDialogs.displayErrorMessage(CHOOSE_ROW_TO_EDIT);
		} else {
			String[] oldClientDetails = readRow(tableModel, rowSelected);			

			panel.getCustNameTextField().setText(oldClientDetails[0]);
			panel.getLocationTown().setEditable(true);
			panel.getLocationTown().getEditor().setItem(oldClientDetails[1]);
			panel.getNotesTextArea().setText(oldClientDetails[2]);
			panel.getContractDateTextField().setText(oldClientDetails[3]);
			panel.setContractFilePath(oldClientDetails[4]);
			panel.setLogoFilePath(oldClientDetails[5]);
			
			usedNames.remove(oldClientDetails[0].toUpperCase());
			
			String custName = "";
			String date;
			int input = 1;			
			do {
				input = optionDialogs.displayClientPanel(EDIT_CLIENT, panel);
				custName = panel.getCustNameTextField().getText().trim();
				date = panel.getContractDateTextField().getText();
			} while (input == 0 && !validator.validateNameAndDate(usedNames, custName, date));			
			
			if (input == 0) {
				String[] newClientDetails = new String[COLUMNS_COUNT];
				newClientDetails[0] = custName;	
				newClientDetails[1] = panel.getLocationTown().getEditor().getItem().toString();
				newClientDetails[2] = panel.getNotesTextArea().getText().trim();
				newClientDetails[3] = panel.getContractDateTextField().getText();
				newClientDetails[4] = panel.getContractFilePath();
				newClientDetails[5] = panel.getLogoFilePath();
				
				if (Arrays.equals(oldClientDetails, newClientDetails)) {
					usedNames.add(oldClientDetails[0].toUpperCase());
					optionDialogs.displayInfoMessage(INFO_NOT_EDITED);
				} else {
					tableModel.insertRow(rowSelected, newClientDetails);
					tableModel.removeRow(rowSelected + 1);
					tableModelPersister.save(tableModel);
					usedNames.add(custName.toUpperCase());
					optionDialogs.displayInfoMessage(INFO_EDITED);
				}	
			} else {
				usedNames.add(oldClientDetails[0].toUpperCase());
			}	
		}
	}

	public void deleteClient(DefaultTableModel tableModel, int rowSelected) {
		if (rowSelected == -1) {
			optionDialogs.displayErrorMessage(CHOOSE_ROW_TO_DELETE);			
		} else {
			String custName = tableModel.getValueAt(rowSelected, 0).toString();
			usedNames.remove(custName.toUpperCase());
			tableModel.removeRow(rowSelected);
			tableModelPersister.save(tableModel);
			optionDialogs.displayInfoMessage(CLIENT_DELETED);
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