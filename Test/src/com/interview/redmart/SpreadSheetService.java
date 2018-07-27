package com.interview.redmart;

import java.util.HashSet;
import java.util.Optional;
import java.util.Scanner;

/**
 * Class which processes the contents of a Sheet
 * 
 * @author bandi.kishore
 *
 */
public class SpreadSheetService {

	private CellService cellService = new CellService();

	// ExecutorService executorService = Executors.newFixedThreadPool(10);

	/**
	 * Accepts input from user and updates the sheet being worked upon.
	 * 
	 * @param sheet
	 */
	public void fillSheet(Sheet sheet) {
		try (Scanner sc = new Scanner(System.in)) {
			sheet.setWidth(sc.nextInt());
			sheet.setHeight(sc.nextInt());
			sc.nextLine(); // Skip this current line

			if (sheet.getWidth() < 1 || sheet.getHeight() < 1) {
				Errors.INVALID_SHEET_SIZE.handleError();
			}

			Cell[][] cells = new Cell[sheet.getHeight()][];
			sheet.setCells(cells);

			for (int rowPos = 0; rowPos < sheet.getHeight(); rowPos++) {
				cells[rowPos] = new Cell[sheet.getWidth()];
				for (int colPos = 0; colPos < sheet.getWidth(); colPos++) {
					String data = sc.nextLine().toUpperCase();
					Cell cell = new Cell(new Position(rowPos, colPos), data);
					Optional<Double> optionalDouble = Utils.parseDouble(data);

					if (optionalDouble.isPresent()) {
						cell.setParsedData(optionalDouble.get());
					} else {
						cell.setResolved(false);
					}

					cells[rowPos][colPos] = cell;
				}
			}
		} catch (Exception ex) {
			Utils.handleException("Exception occured while Accepting input", ex);
		}
	}

	/**
	 * Processes a Sheet by going through each cell and evaluvating the cell.
	 * 
	 * @param sheet
	 */
	public void processSheet(Sheet sheet) {
		Cell[][] cells = sheet.getCells();
		for (int rowPos = 0; rowPos < sheet.getHeight(); rowPos++) {
			for (int colPos = 0; colPos < sheet.getWidth(); colPos++) {
				Cell cell = cells[rowPos][colPos];
				if (!cell.isResolved()) {
					cellService.computeCellValue(cell.getPosition(), cells, new HashSet<>());
				}
			}
		}
	}

	public void printSheet(Sheet sheet) {
		Cell[][] cells = sheet.getCells();
		Utils.printString(sheet.getWidth() + " " + sheet.getHeight());
		for (int rowPos = 0; rowPos < sheet.getHeight(); rowPos++) {
			for (int colPos = 0; colPos < sheet.getWidth(); colPos++) {
				cellService.printCellValue(cells[rowPos][colPos].getParsedData());
			}
		}
	}

}
