package com.interview.redmart;

/**
 * Class which wraps a Sheet in SpreadSheet File.
 * 
 * @author bandi.kishore
 *
 */
public class Sheet {

	private int width;

	private int height;

	private Cell[][] cells;

	public int getWidth() {
		return width;
	}

	public void setWidth(int width) {
		this.width = width;
	}

	public int getHeight() {
		return height;
	}

	public void setHeight(int height) {
		this.height = height;
	}

	public Cell[][] getCells() {
		return cells;
	}

	public void setCells(Cell[][] cells) {
		this.cells = cells;
	}

}
