package com.interview.redmart;

import java.util.Optional;

/**
 * Utility class
 * 
 * @author bandi.kishore
 *
 */
public class Utils {

	public static void handleException(String exception, Exception ex) {
		// ex.printStackTrace();
		System.out.println(exception);
		System.exit(Constants.EXIT_CODE);
	}

	public static void printString(String value) {
		System.out.println(value);
	}

	public static Optional<Double> parseDouble(String value) {
		try {
			return Optional.of(Double.parseDouble(value));
		} catch (Exception e) {
			// Suppress exception
		}
		return Optional.empty();
	}

}
