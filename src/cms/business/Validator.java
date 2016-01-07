package cms.business;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Set;

import javax.swing.JOptionPane;

import cms.view.OptionDialogs;

public class Validator {
	
	private static final String NAME_MUST_BE_FILLED = "Field \"name\" must be filled in!";
	private static final String NAME_IS_USED = "The name entered is already used!";
	private static final String INVALID_DATE = "Invalid date! Enter date in format dd.MM.yyyy!";
	
	private final OptionDialogs optionDialogs = new OptionDialogs();	
	
	public Validator() {		
	}

	public boolean validateNameAndDate(Set<String> usedNames, String customerName, String date) {		
		if (customerName.isEmpty()) {
			optionDialogs.displayErrorMessage(NAME_MUST_BE_FILLED);			
			return false;					
		} else if (usedNames.contains(customerName.toUpperCase())) {
			optionDialogs.displayErrorMessage(NAME_IS_USED);
			return false;				
		} else if (!date.isEmpty()) {
			String format = "dd.MM.yyyy";		
			SimpleDateFormat sdf = new SimpleDateFormat(format);
			sdf.setLenient(false);
			try {	
				@SuppressWarnings("unused")
				Date d = sdf.parse(date);							 			
			} catch (ParseException pe) {
				JOptionPane.showMessageDialog(null, INVALID_DATE);
				return false;
			}
		}	
		
		return true;
	}
}
