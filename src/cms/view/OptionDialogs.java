package cms.view;

import javax.swing.JOptionPane;

public class OptionDialogs {
	
	private static final String[] OPTIONS = { "Save", "Cancel" };
	private static final String[] CONFIRM_BUTTON_NAME = { "OK" };
	private static final String ERROR = "Error";
	private static final String INFO = "Information";
	
	public OptionDialogs() {		
	}
	
	public int displayClientPanel(String message, CustomerPanel panel) {
		return JOptionPane.showOptionDialog(null,
				panel, message, JOptionPane.NO_OPTION, 
				JOptionPane.DEFAULT_OPTION,	null, OPTIONS , null);
	}

	public void displayErrorMessage(String message) {
		JOptionPane.showOptionDialog(null, message, ERROR,
				JOptionPane.NO_OPTION, JOptionPane.ERROR_MESSAGE, null,
				CONFIRM_BUTTON_NAME , CONFIRM_BUTTON_NAME[0]);
	}	

	public void displayInfoMessage(String message) {
		JOptionPane.showOptionDialog(null, message, INFO,
				JOptionPane.NO_OPTION, JOptionPane.INFORMATION_MESSAGE, null,
				CONFIRM_BUTTON_NAME , CONFIRM_BUTTON_NAME[0]);		
	}

}
