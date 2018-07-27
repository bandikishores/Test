package com.interview.redmart;

import java.text.MessageFormat;

/**
 * All Errors within the application are wrapped inside this.
 * 
 * @author bandi.kishore
 *
 */
public enum Errors {

	CYCLIC_DEPENDENCY("Cyclic Dependency has been detected for Positions {0}"), 
	DATA_EXHAUSTED("Numbers have been drained but operator was still found to be present"), 
	TOO_MANY_VALUES("There were more than 2 numbers remaning even after processing expression!! Too few operators"), 
	COULD_NOT_COMPUTE_VALUE("No value could be computed for position {0}"), 
	INVALID_ROW_POSITION("Invalid Row Number found {0}"), 
	INVALID_SHEET_SIZE("Sheet Size provided is Invalid");

	private final String message;

	private Errors(String message) {
		this.message = message;
	}

	public String getMessage() {
		return message;
	}

	public void handleError(Object... arguments) {
		System.out.println(MessageFormat.format(getMessage(), arguments));
		System.exit(Constants.EXIT_CODE);
	}
}
