package raysullivan.operation;

import java.io.File;
import java.io.FileWriter;

import com.opencsv.*;

/**
 * 
 * @author rsullivan
 *
 */
public class WriteCsvTestResults {
	/**
	 * writeExcel Creates a datasheet, worksheet and inserts rows
	 * 
	 * @param filePath
	 * @param fileName
	 * @param sheetName
	 * @param dataToWrite
	 * @throws Exception 
	 */
	public void writeCsv(String filePath, String fileName, String sheetName,
			String[] dataToWrite) throws Exception {
		// Get the utility class
		AutomationDriverUtil util = new AutomationDriverUtil();
		// Create an object of File class to open .csv file
		String csvFile = filePath + "\\" + fileName + "_" + sheetName + ".csv";
		File file = new File(csvFile);
		CSVWriter writer = null;;
		// create the file if it doesn't exist
		if (!file.exists()) {
			writer = new CSVWriter(new FileWriter(csvFile));
		}	else {
			writer = new CSVWriter(new FileWriter(csvFile, true));
		}

		// Mask encrypted values
		String e = "encrypt";
		if(dataToWrite[9].equals(e)){
			util.setKeyString("automationDriver");
			dataToWrite[8] = AESencrp.encrypt(dataToWrite[8], util.getKeyString());
		}
		// get total number of rows
		writer.writeNext(dataToWrite);
		writer.close();
	}
}