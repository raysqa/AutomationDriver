package raysullivan.unitTest;

import java.io.IOException;
import java.nio.file.FileVisitResult;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.SimpleFileVisitor;
import java.nio.file.attribute.*;

import rsullivan.obsolete.CsvToExcel;

public class CSVToExcelTest {
	private static final String DATAPATH = System.getProperty("user.home")
			//+ "\\Box Sync\\TNT - Temp Storage\\Catch Up Transactions\\Production Files\\";
			//+ "\\Documents\\Temp Merge\\Create XLSX\\";
			+ "\\OneDrive - Webroot1\\Downloads\\";
	public static void main(String args[]) throws IOException {
		String FILEPATH = DATAPATH
				//+ "November 28\\Final .txt fixes for November 28";
				+ "export.csv";
		Files.walkFileTree(Paths.get(FILEPATH), new SimpleFileVisitor<Path>() {
			@Override
			public FileVisitResult visitFile(Path file,
					BasicFileAttributes attrs) throws IOException {
				String outputfile = file + ".xlsx";
				String inputfile = file + "";
				CsvToExcel.convertInput(inputfile, outputfile);
				return FileVisitResult.CONTINUE;
			}
		});
	}

}
