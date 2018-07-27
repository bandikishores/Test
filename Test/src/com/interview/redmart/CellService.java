package com.interview.redmart;

import java.util.Optional;
import java.util.Set;
import java.util.Stack;
import java.util.stream.Collectors;

/**
 * Class which processes & controls the contents of a Cell
 * 
 * @author bandi.kishore
 *
 */
public class CellService {

	private final OperationService operationService = new OperationService();

	/**
	 * Process the expression in the cell (if any) and returns the value. Handles
	 * Cyclic depedency as well
	 * 
	 * @param position
	 * @param cells
	 * @param visitedPositions
	 * @return
	 */
	public Optional<Double> computeCellValue(Position position, Cell[][] cells, Set<Position> visitedPositions) {
		Cell cell = cells[position.getRowPos()][position.getColPos()];
		if (cell.isResolved()) {
			return Optional.ofNullable(cell.getParsedData());
		}
		if (visitedPositions.contains(position)) {
			Errors.CYCLIC_DEPENDENCY
					.handleError(visitedPositions.stream().map(Position::toString).collect(Collectors.joining("->")));
		}
		visitedPositions.add(position);

		Optional<Double> optionalValue = Optional
				.ofNullable(parseOriginalData(cell.getOriginalData(), cells, visitedPositions));
		if (optionalValue.isPresent()) {
			cell.setParsedData(optionalValue.get());
		} else {
			Errors.COULD_NOT_COMPUTE_VALUE.handleError(cell.getPosition());
		}

		visitedPositions.remove(position);

		return optionalValue;
	}

	/**
	 * Parses the original data passed and evaluvates the expression
	 * 
	 * @param originalData
	 * @param cells
	 * @param visitedPositions
	 * @return
	 */
	private Double parseOriginalData(String originalData, Cell[][] cells, Set<Position> visitedPositions) {
		try {
			String[] postfixExpression = originalData.split(" ");
			return evaluvatePostfixExpression(postfixExpression, cells, visitedPositions);
		} catch (Exception e) {
			Utils.handleException("Exception occured while parsing Data", e);
		}
		return null;
	}

	/**
	 * - Process the Postfix Expression and returns the result. Understands 1)
	 * Increment/Decrement Operators. 2) Understands Arithmetric Operations.
	 * 
	 * @param postfixExpression
	 * @param visitedPositions
	 * @param cells
	 * @return
	 */
	private Double evaluvatePostfixExpression(String[] postfixExpression, Cell[][] cells,
			Set<Position> visitedPositions) {
		Stack<Double> stack = new Stack<>();

		for (int i = 0; i < postfixExpression.length; i++) {
			String value = postfixExpression[i].trim();
			Optional<Double> optDouble = Utils.parseDouble(value);

			if (optDouble.isPresent()) {
				stack.push(optDouble.get());
			} else if (operationService.isIncDecOperator(value)) {
				ProcessIncDec(value, stack);
			} else if (operationService.isArithmeticOperator(value)) {
				processArithmeticOperators(stack, value);
			} else {
				processCellReference(cells, visitedPositions, stack, value);
			}
		}

		if (stack.isEmpty()) {
			Errors.DATA_EXHAUSTED.handleError();
		}
		if (stack.size() > 1) {
			Errors.TOO_MANY_VALUES.handleError();
		}

		return stack.pop();
	}

	/**
	 * Extracts the cell which is being referenced and computes its value.
	 * 
	 * @param cells
	 * @param visitedPositions
	 * @param stack
	 * @param value
	 */
	private void processCellReference(Cell[][] cells, Set<Position> visitedPositions, Stack<Double> stack,
			String value) {
		Position position = Position.extractPositionFromValue(value);
		Optional<Double> optValue = computeCellValue(position, cells, visitedPositions);
		if (optValue.isPresent()) {
			stack.push(optValue.get());
		} else {
			Errors.COULD_NOT_COMPUTE_VALUE.handleError(position);
		}
	}

	private void processArithmeticOperators(Stack<Double> stack, String value) {
		if (stack.size() < 2) {
			Errors.DATA_EXHAUSTED.handleError();
		}
		Double val1 = stack.pop();
		Double val2 = stack.pop();
		stack.push(operationService.calculate(value, val2, val1));
	}

	private void ProcessIncDec(String value, Stack<Double> stack) {
		if (stack.isEmpty()) {
			Errors.DATA_EXHAUSTED.handleError();
		}
		stack.push(operationService.calculate(value, stack.pop()));
	}

	public void printCellValue(Double parsedData) {
		System.out.println(String.format("%.5f", parsedData));
	}

}
