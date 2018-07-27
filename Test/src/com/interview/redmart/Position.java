package com.interview.redmart;

/**
 * Class which wraps Position and its related functions
 * 
 * @author bandi.kishore
 *
 */
public class Position {

	private int rowPos;

	private int colPos;

	public Position(int rowPos, int colPos) {
		super();
		this.rowPos = rowPos;
		this.colPos = colPos;
	}

	public int getRowPos() {
		return rowPos;
	}

	public void setRowPos(int rowPos) {
		this.rowPos = rowPos;
	}

	public int getColPos() {
		return colPos;
	}

	public void setColPos(int colPos) {
		this.colPos = colPos;
	}

	@Override
	public String toString() {
		return extractSheetRow();
	}

	/**
	 * - This method extracts the position from a String such as A2 to (0,1) C9 to
	 * (2,8)
	 * 
	 * It does this by converting the character to ASCII & then subtracting "A"
	 * ascii 65 with to get the row's position. It assumes Rows to be maximum 26
	 * Characters.
	 * 
	 * @param value
	 * @return
	 */
	public static Position extractPositionFromValue(String value) {
		try {
			int rowPosition = (int) value.charAt(0) - 65;
			if (rowPosition >= 26 || rowPosition < 0) {
				Errors.INVALID_ROW_POSITION.handleError(value);
			}
			value.charAt(1);
			return new Position(rowPosition, Integer.parseInt(value.substring(1, value.length())) - 1);
		} catch (Exception e) {
			Utils.handleException("Exception occured while extracting Position", e);
		}
		return null;
	}

	/**
	 * Converts a Position to Excel style position
	 * 
	 * @param rowPos2
	 * @param colPos2
	 * @return
	 */
	private String extractSheetRow() {
		return ((char) (rowPos + 65)) + "" + (colPos + 1);
	}

	@Override
	public boolean equals(Object obj) {
		Position position = (Position) obj;
		return getRowPos() == position.getRowPos() && getColPos() == position.getColPos();
	}

	@Override
	public int hashCode() {
		return (getRowPos() * 26) + getColPos();
	}

}
