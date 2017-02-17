package raysullivan.operation;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;

import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
/**
 * ReadKDTestFile
 * 
 * @author rsullivan
 *
 */
public class ReadKDTestFile {
	// function to read the Keyword Driven excel file with file path, file name
	// and worksheet name
	/**
	 * 
	 * @param filePath	file path to the spreadsheet
	 * @param fileName	name of the excel spreadsheet
	 * @param sheetName	name of the excel worksheet within the spreadsheet
	 * @return Sheet returns an instance of the worksheet
	 * @throws IOException
	 * @throws AutomationDriverException
	 */
	public Sheet readKDSheet(final String filePath, final String fileName, final String sheetName)
			throws IOException, AutomationDriverException {
		// Create an object of File class to open xlsx file
		final File file = new File(filePath + "\\" + fileName);
		// Create an object of FileInputStream class to read the excel file
		FileInputStream inputStream;
		try {
			inputStream = new FileInputStream(file);
		} catch (Exception e) {
			throw new AutomationDriverException(
					"Error:  Test Spreadsheet " + fileName
							+ " at location " + filePath + " not found.");
		}
		Workbook workbook = null;
		// Determine the file extension
		final String fileExtensionName = fileName.substring(fileName.indexOf("."));
		// instantiate the correct workbook based on extension
		if (fileExtensionName.equalsIgnoreCase(".xlsx")) {
			workbook = new XSSFWorkbook(inputStream);
			// or create the worksheet class if .xls
		} else if (fileExtensionName.equalsIgnoreCase(".xls")) {
			workbook = new HSSFWorkbook(inputStream);
		}
		// Now get the default worksheet name using the getSheet method
		final Sheet sheet = workbook.getSheet(sheetName);
		return sheet;
	}
}
