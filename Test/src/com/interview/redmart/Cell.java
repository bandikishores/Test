package com.interview.redmart;

/**
 * POJO which wraps a cell & its associated properties
 * 
 * @author bandi.kishore
 *
 */
public class Cell {

	private Position position;

	private String originalData;

	private Double parsedData;

	private boolean resolved;

	public Cell(Position position, String data) {
		super();
		this.position = position;
		this.originalData = data;
	}

	public Double getParsedData() {
		return parsedData;
	}

	public void setParsedData(Double parsedData) {
		this.parsedData = parsedData;
		setResolved(true);
	}

	public boolean isResolved() {
		return resolved;
	}

	public void setResolved(boolean resolved) {
		this.resolved = resolved;
	}

	public String getOriginalData() {
		return originalData;
	}

	public void setOriginalData(String data) {
		this.originalData = data;
	}

	public Position getPosition() {
		return position;
	}

	public void setPosition(Position position) {
		this.position = position;
	}

}
