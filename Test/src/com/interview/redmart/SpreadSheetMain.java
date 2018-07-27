package com.interview.redmart;

/**
 * Main application which starts the spread sheet processing.
 * 
 * @author bandi.kishore
 *
 */
public class SpreadSheetMain {

	public static void main(String[] args) {
		try {
			Sheet sheet = new Sheet();
			SpreadSheetService spreadSheetService = new SpreadSheetService();
			spreadSheetService.fillSheet(sheet);
			spreadSheetService.processSheet(sheet);
			spreadSheetService.printSheet(sheet);
		} catch (Exception e) {
			Utils.handleException("Error occurred while processing", e);
		}
	}
}
