package raysullivan.operation;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;

import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.BorderStyle;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.FillPatternType;
import org.apache.poi.ss.usermodel.Font;
import org.apache.poi.ss.usermodel.IndexedColors;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

/**
 * 
 * @author rsullivan
 *
 */
public class WriteExcelTestResults {
	// Get the utility class
	AutomationDriverUtil util = new AutomationDriverUtil();
	/**
	 * writeExcel Creates a datasheet, worksheet and inserts rows
	 * 
	 * @param filePath
	 * @param fileName
	 * @param sheetName
	 * @param dataToWrite
	 * @throws Exception 
	 */
	public void writeExcel(String filePath, String fileName, String sheetName,
			String[] dataToWrite) throws Exception {
		// Determine the file extension
		String fileExtensionName=null;
		try{
			fileExtensionName = fileName.substring(fileName.indexOf("."));
		}
		catch (StringIndexOutOfBoundsException e){
			fileExtensionName = util.getSpreadsheetExtension();
			fileName=fileName+fileExtensionName;
		}
		// Create an object of File class to open xlsx file
		File file = new File(filePath + "\\" + fileName);
		// Create an object of FileInputStream class to read the excel file
		Workbook workbook = null;
		// create the file if it doesn't exist
		if (!file.exists()) {
			// if the file extension is xlsx, then create the correct worksheet
			// object
			if (fileExtensionName.equalsIgnoreCase(util.getSpreadsheetExtension())) {
				workbook = new XSSFWorkbook();
				// or create the worksheet class if .xls
			} else if (fileExtensionName.equalsIgnoreCase(".xls")) {
				workbook = new HSSFWorkbook();
			}
			FileOutputStream outputStream = new FileOutputStream(file);
			workbook.write(outputStream);
			outputStream.close();
		}
		FileInputStream inputStream = new FileInputStream(file);
		// if the file extension is xlsx, then create the correct worksheet
		// object
		if (fileExtensionName.equalsIgnoreCase(".xlsx")) {
			workbook = new XSSFWorkbook(inputStream);
			// or create the worksheet class if .xls
		} else if (fileExtensionName.equalsIgnoreCase(".xls")) {
			workbook = new HSSFWorkbook(inputStream);
		}
		// if the sheetname does not exist, create it and write it to the file
		if (workbook.getSheet(sheetName) == null) {
			FileOutputStream outputStream = new FileOutputStream(file);
			workbook.createSheet(sheetName);
			workbook.write(outputStream);
			outputStream.close();
		}
		// Mask encrypted values
		String e = "encrypt";
		if(dataToWrite[8].equals(e)){
			util.setKeyString("automationDriver");
			dataToWrite[7] = AESencrp.encrypt(dataToWrite[7], util.getKeyString());
		}
		// Now get the default worksheet name using the getSheet method
		Sheet sheet = workbook.getSheet(sheetName);
		// define the error style - grey with red border, red font color italics
		CellStyle errStyle = workbook.createCellStyle();
		errStyle.setFillForegroundColor(IndexedColors.GREY_25_PERCENT.getIndex());
		errStyle.setFillPattern(FillPatternType.SOLID_FOREGROUND);
		errStyle.setBorderBottom(BorderStyle.THICK);
		errStyle.setBottomBorderColor(IndexedColors.RED.getIndex());
		errStyle.setBorderLeft(BorderStyle.THICK);
		errStyle.setLeftBorderColor(IndexedColors.RED.getIndex());
		errStyle.setBorderRight(BorderStyle.THICK);
		errStyle.setRightBorderColor(IndexedColors.RED.getIndex());
		errStyle.setBorderTop(BorderStyle.THICK);
		errStyle.setTopBorderColor(IndexedColors.RED.getIndex());
		Font errfont = workbook.createFont();
		errfont.setColor(Font.COLOR_RED);
		errfont.setItalic(true);
		errStyle.setFont(errfont);
		// get total number of rows
		int rowCount = sheet.getPhysicalNumberOfRows();
		// get the number of cells to write
		int cells = dataToWrite.length;
		// create a new row and append it at the end of the sheet
		Row newRow = sheet.createRow(rowCount);
		// Iterate through the passed parameters
		for (int j = 0; j < cells; j++) {
			// create a new cell and apply the value passed
			Cell cell = newRow.createCell(j);
			cell.setCellValue(dataToWrite[j]);
			// if an error result, apply the error style
			if (j == 13 && !dataToWrite[j].equals(dataToWrite[j - 1])
					&& dataToWrite[j] != "Actual") {
				cell.setCellStyle(errStyle);
			}
		}
		// close the input stream
		inputStream.close();
		// create an object of FileOutputStream class to write the data to Excel
		FileOutputStream outputStream = new FileOutputStream(file);
		// write it
		workbook.write(outputStream);
		// close it
		outputStream.close();
	}
	/**
	 * clearWorksheet clears an existing worksheet from within a spreadsheet
	 * 
	 * @param filePath
	 * @param fileName
	 * @param sheetName
	 * @throws IOException
	 */
	public void clearWorksheet(String filePath, String fileName,
			String sheetName) throws IOException {
		// Create an object of File class to open xlsx file			// Determine the file extension
		String fileExtensionName=null;
		try{
			fileExtensionName = fileName.substring(fileName.indexOf("."));
		}
		catch (StringIndexOutOfBoundsException e){
			fileExtensionName = util.getSpreadsheetExtension();
			fileName=fileName+fileExtensionName;
		}
		File file = new File(filePath + "\\" + fileName);
		if (file.exists()) {
			// Create workbook
			Workbook workbook = null;
			// Create input stream to read the file attributes
			FileInputStream inputStream = new FileInputStream(file);
			// if the file extension is xlsx, then create the correct worksheet
			// object
			if (fileExtensionName.equalsIgnoreCase(".xlsx")) {
				workbook = new XSSFWorkbook(inputStream);
				// or create the worksheet class if .xls
			} else if (fileExtensionName.equalsIgnoreCase(".xls")) {
				workbook = new HSSFWorkbook(inputStream);
			}
			// create the worksheet object
			Sheet sheet = workbook.getSheet(sheetName);
			// Execute only if the worksheet exists
			if (sheet != null) {
				// Create the output stream to write the updates
				FileOutputStream outputStream = new FileOutputStream(file);
				// for each row in the worksheet, delete the row
				for (int index = sheet.getLastRowNum(); index >= sheet
						.getFirstRowNum(); index--) {
					sheet.removeRow(sheet.getRow(index));
				}
				// write the change to the workbook
				workbook.write(outputStream);
				outputStream.close();
			}
			// close the input/output streams
			inputStream.close();
		}
	}
}