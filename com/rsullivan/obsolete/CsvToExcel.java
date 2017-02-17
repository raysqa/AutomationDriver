package rsullivan.obsolete;

import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.IOException;

import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;

import com.opencsv.CSVReader;

public class CsvToExcel {
	public static void convertInput(String filename, String outputFilename)
			throws IOException {
		CSVReader reader = new CSVReader(new FileReader(filename));
		String newLine[] = null;
		int rowCount = 0;
		XSSFWorkbook hwb = new XSSFWorkbook();
		Sheet sheet = hwb.createSheet("Sheet1");
		int columns = 0;
		while ((newLine = reader.readNext()) != null) {
			columns = newLine.length;
			Row row = sheet.createRow(rowCount);
			for (int j = 0; j < columns; j++) {
				Cell cell = row.createCell(j);
				cell.setCellValue(newLine[j]);
			}
			rowCount++;
		}
		reader.close();
		FileOutputStream fileOut = new FileOutputStream(outputFilename);
		hwb.write(fileOut);
		fileOut.close();
		System.out.println(outputFilename + " has been generated");
		hwb.close();
	}
}
