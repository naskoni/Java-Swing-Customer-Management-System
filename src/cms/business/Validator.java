package cms.business;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
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
			try {
				DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd.MM.yyyy");				
		    	@SuppressWarnings("unused")
				LocalDate d = LocalDate.parse(date, formatter);							 			
			} catch (DateTimeParseException pe) {
				JOptionPane.showMessageDialog(null, INVALID_DATE);
				return false;
			}
		}	
		
		return true;
	}
}
