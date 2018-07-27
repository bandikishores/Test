package com.interview.redmart;

/**
 * Class responsible for handling Arithmetic & inc/dec operations
 * 
 * @author bandi.kishore
 *
 */
public class OperationService {

	public Double calculate(String operator, Double... value) {
		switch (operator) {
		case "++":
			return value[0] + 1;
		case "--":
			return value[0] - 1;
		case "+":
			return value[0] + value[1];
		case "-":
			return value[0] - value[1];
		case "*":
			return value[0] * value[1];
		case "/":
			return value[0] / value[1];
		}
		return null;
	}

	public boolean isIncDecOperator(String data) {
		switch (data) {
		case "++":
		case "--":
			return true;
		}
		return false;
	}

	public boolean isArithmeticOperator(String value) {
		switch (value) {
		case "+":
		case "-":
		case "*":
		case "/":
			return true;
		}
		return false;
	}

}
