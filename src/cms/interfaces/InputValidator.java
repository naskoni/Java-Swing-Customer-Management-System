package cms.interfaces;

@FunctionalInterface
public interface InputValidator {

	boolean validate(String[] inputArgs);

}