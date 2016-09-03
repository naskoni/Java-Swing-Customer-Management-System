package cms.business;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.swing.JOptionPane;

import cms.interfaces.InputValidator;
import cms.views.OptionDialogs;

public class InputValidatorImpl implements InputValidator {

	private static final String NAME_MUST_BE_FILLED = "Field \"name\" must be filled in!";
	private static final String INVALID_DATE = "Invalid date! Enter date in format dd.MM.yyyy!";
	private static final Logger logger = Logger.getLogger(InputValidatorImpl.class.getName());

	private final OptionDialogs optionDialogs = new OptionDialogs();

	@Override
	public boolean validate(String[] inputArgs) {
		String name = inputArgs[0];
		String date = inputArgs[1];

		return validateName(name) && validateDate(date);
	}

	private boolean validateName(String name) {
		if (name.isEmpty()) {
			optionDialogs.displayErrorMessage(NAME_MUST_BE_FILLED);
			return false;
		}

		return true;
	}

	private boolean validateDate(String date) {
		if (!date.isEmpty()) {
			try {
				DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd.MM.yyyy");
				LocalDate.parse(date, formatter);
			} catch (DateTimeParseException e) {
				logger.log(Level.WARNING, "Date cannot be parsed", e);
				JOptionPane.showMessageDialog(null, INVALID_DATE);
				return false;
			}
		}

		return true;
	}

}
