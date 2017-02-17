package raysullivan.operation;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Properties;

import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.junit.Assert;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.firefox.FirefoxProfile;
import org.openqa.selenium.firefox.internal.ProfilesIni;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.annotations.AfterSuite;
import org.testng.annotations.AfterTest;
import org.testng.annotations.Test;

import raysullivan.dataProvider.*;

/**
 * 
 * @author rsullivan
 *
 */
/*
 * Implement this superclass and override the @Test annotation at the class
 * level
 */
@Test(dataProvider = "DefaultDataProvider", dataProviderClass = Default.class)
public class AutomationDriver {
	/**
	 * Tutil
	 */
	private AutomationDriverUtil util = new AutomationDriverUtil();
	/**
	 * WebDriver
	 */
	private WebDriver webdriver = null;
	/**
	 * Sheet
	 */
	private Sheet sheet = null;
	/**
	 * UIOperation
	 */
	private UIOperation operation = null;
	/**
	 * Properties
	 */
	private Properties allObjects = null;
	/**
	 * WebDriverWait
	 */
	private WebDriverWait waitVar = null;
	/**
	 * ReadKDTestFile
	 */
	private ReadKDTestFile rfile = null;
	/**
	 * ReadObject
	 */
	private ReadObject object = null;
	/**
	 * WriteExcelTestResults
	 */
	private WriteExcelTestResults wfile = null;
	/**
	 * WriteCsvTestResults
	 */
	private WriteCsvTestResults cfile = null;
	/**
	 * ProfilesIni
	 */
	private ProfilesIni allProfiles = null;
	/**
	 * FirefoxProfile
	 */
	private FirefoxProfile profile = null;
	/**
	 * rowCount
	 */
	private int rowCount;
	/**
	 * DriverVariableList
	 */
	private DriverVariable var = null;

	/**
	 * afterTest
	 * 
	 * @throws Exception
	 */
	@AfterTest
	public void afterTest() throws Exception {
		Thread.sleep(1000);
		try {
			AutomationDriverVideoRecorder.stopRecording();
		} catch (NullPointerException npe) {
		}
	}

	@AfterSuite
	public void afterSuite() throws Exception {
		webdriver.close();
		try {
			Thread.sleep(1000);
			// webdriver.close();
			webdriver.quit();
		} catch (Exception e) {
		}
	}

	/**
	 * testDriver Main Test Driver
	 * 
	 * @param spreadsheet
	 * @param worksheet
	 * @param resultSpreadsheet
	 * @param browser
	 * @param webProfile
	 * @param timeout
	 * @param propertyName
	 * @param sheetIterations
	 * @throws Exception
	 */
	public final void testDriver(final String spreadsheet,
			final String worksheet, String resultSpreadsheet,
			final String browser, final String webProfile, final int timeout,
			final String propertyName, final int sheetIterations,
			final boolean capCsv, final boolean capVideo) throws Exception {
		util.setTestProfile(spreadsheet, worksheet, resultSpreadsheet, browser,
				webProfile, timeout, propertyName, sheetIterations, capCsv,
				capVideo);
		/*
		 * Create the Selenium test driver; use a profile if provided
		 */
		if (webProfile == "none") {
			webdriver = AutomationDriverUtil.getDriver(null);
		} else {
			allProfiles = new ProfilesIni();
			profile = allProfiles.getProfile(webProfile);
			try {
				profile.setPreference("foo.bar", 23);
			} catch (NullPointerException npe) {
				throw new AutomationDriverException("Error: FireFox Profile "
						+ webProfile + " is invalid or does not exist.");
			}
			webdriver = AutomationDriverUtil.getDriver(profile);
		}
		/*
		 * Set default wait time for screen elements
		 */
		if (timeout < 5) {
			throw new AutomationDriverException(
					"Error:  Default WebDriverWait cannot be less than 5 seconds.");
		}
		util.setTimeout(timeout);
		waitVar = new WebDriverWait(webdriver, timeout);
		/*
		 * Define the object containing custom variables
		 */
		var = new DriverVariable();
		/*
		 * Define the input file
		 */
		rfile = new ReadKDTestFile();
		/*
		 * Define the output file
		 */
		if (resultSpreadsheet.length() == 0) {
			throw new AutomationDriverException("Error:  Result Spreadsheet cannot be blank or null.");
		}
		wfile = new WriteExcelTestResults();
		cfile = new WriteCsvTestResults();
		String excelFile = null, csvFile = null;
		/*
		 * Load the object repository
		 */
		object = new ReadObject();
		allObjects = object.getObjectRepository(propertyName);
		/*
		 * create a new the UI operation
		 */
		operation = new UIOperation(webdriver, waitVar);
		/*
		 * Open the spreadsheet and worksheet
		 */
		sheet = initFiles(spreadsheet, excelFile, worksheet, resultSpreadsheet,
				csvFile, sheetIterations, capCsv);
		/*
		 * Turn on the screen recording for this test
		 */
		startVidCap(capVideo);
		/*
		 * Execute the sheet sheetIterations times
		 */
		for (int si = 1; si <= sheetIterations; si++) {
			/*
			 * Send test information to console
			 */
			System.out.println("Test " + worksheet + " iteration " + si
					+ " of " + sheetIterations + " started.");
			/*
			 * Count the number of test steps and set up to iterate through all
			 * test steps
			 */
			try {
				rowCount = sheet.getLastRowNum() - sheet.getFirstRowNum();
			} catch (NullPointerException e1) {
				throw new AutomationDriverException("Error:  Worksheet "
						+ worksheet + " does not exist or is invalid");
			}
			// Create a loop over all the rows of excel file to read it
			for (int i = 1; i < rowCount + 1; i++) {
				// Loop over all the rows
				Row row = sheet.getRow(i);
				/*
				 * Excel is zero based for rows; add 1 to the current row for
				 * the actual row number
				 */
				int r = i + 1;
				/*
				 * Move the spreadsheet row just read to an array
				 */
				String[] cell = new String[7];
				try {
					cell[0] = row.getCell(0).toString();
					cell[1] = row.getCell(1).toString();
					cell[2] = row.getCell(2).toString();
					cell[3] = row.getCell(3).toString()
							.replaceAll("(\\r|\\n|\\t)", "");
					cell[4] = row.getCell(4).toString();
					cell[5] = row.getCell(5).toString();
					cell[6] = row.getCell(6).toString();
				} catch (NullPointerException e) {
					throw new AutomationDriverException(
							"Error:  Unable to process row " + r
									+ " from spreadsheet: " + spreadsheet
									+ ", worksheet :" + worksheet);
				}
				/*
				 * Check if the first cell contain a value, if yes, That means
				 * it is the new testcase name; if the second cell has content,
				 * process as a keyword instruction
				 */
				if (cell[0].length() == 0 && cell[1].length() > 0) {
					/*
					 * Format the value cell to ensure correct text or decimal
					 */
					cell[3] = util.valueCellFormat(cell);
					/*
					 * Timestamp the call to execute the test step
					 */
					String timeStamp = new SimpleDateFormat(
							"MM/dd/yyyy HH:mm:ss").format(Calendar
							.getInstance().getTime());
					/*
					 * Call the method to perform the action on the UI returning
					 * a value of the elapsed time and result message
					 */
					String returnString[] = new String[3];
					returnString = operation.perform(allObjects, propertyName,
							cell, var, util);
					/*
					 * Validate the format of the return string
					 */
					returnString[1] = util.resultCellFormat(cell, returnString);
					/*
					 * Initialize log variables and assertion error message
					 */
					String[] rows = {timeStamp, worksheet, util.getTestCase(),
							si + " of " + sheetIterations, Integer.toString(r),
							cell[1], cell[2], cell[3], cell[4], cell[5],
							cell[6], returnString[0], returnString[2],
							returnString[1]};
					String errorMsg = "Error in operation "
							+ util.getSpreadsheet() + "\\"
							+ util.getWorksheet() + "\\" + util.getTestCase()
							+ " Row " + r + " " + cell[1] + " " + cell[2] + " "
							+ cell[3];
					/*
					 * Assertions: 1. Passed if the UI action returned a success
					 * string 2. Failed if the UI action returned an error
					 * string 3. Failed if the UI action returned an unexpected
					 * result
					 */
					/*
					 * Success Returned
					 */
					if (returnString[1].equals(returnString[2])) {
						rows[1] = worksheet;
						/*
						 * Send the test results to the log
						 */
						util.testOutput(rows, wfile, cfile);
						Assert.assertTrue(true);
					}
					/*
					 * Generic Error Returned
					 */
					else if (returnString[1].equals(util.getErrorString())) {
						util.testOutput(rows, wfile, cfile);
						Assert.assertTrue(errorMsg + "\t" + cell[3], false);
					}
					/*
					 * Specific Error Returned
					 */
					else {
						/*
						 * Set actual result for excel output
						 */
						rows[12] = returnString[2];
						if (returnString[1].equals(returnString[2])) {
							// Assertion will pass if this condition exists
							rows[1] = worksheet;
						}
						util.testOutput(rows, wfile, cfile);
						Assert.assertTrue(errorMsg + " Expecting: \""
								+ returnString[2] + "\" Actual: \""
								+ returnString[1] + "\"",
								returnString[1].equals(returnString[2]));
					}
				} else {
					if (cell[0].length() > 0) {
						// Set the new testcase name when it started
						String testCase = cell[0];
						util.setTestCase(testCase);
						System.out.println("\tTest Case: " + testCase + "...");
					}
				}
			}
			System.out.println("Test " + worksheet + " iteration " + si
					+ " of " + sheetIterations + " completed successfully.");
		}
		stopVidCap(capVideo);
		if (sheetIterations > 0) {
			System.out.println("Test " + worksheet + " complete.");
		}
	}

	private boolean startVidCap(boolean capVideo) throws Exception {
		if (capVideo == true) {
			System.out.println("\tStarting Video Recording");
			AutomationDriverVideoRecorder.startRecording(
					util.getSpreadsheet() + "_" + util.getWorksheet(),
					util.getTestReportPath());
		}
		return capVideo;
	}
	private boolean stopVidCap(boolean capVideo) throws Exception {
		if (capVideo == true) {
			Thread.sleep(1000);
			System.out.println("\tStop Video Recording");
			AutomationDriverVideoRecorder.stopRecording();
		}
		return capVideo;
	}
	private Sheet initFiles(String spreadsheet, String excelFile,
			String worksheet, String resultSpreadsheet, String csvFile,
			int sheetIterations, boolean capCsv) throws Exception {
		try {
			spreadsheet.substring(spreadsheet.indexOf("."));
			excelFile = spreadsheet;
		} catch (StringIndexOutOfBoundsException e) {
			excelFile = spreadsheet + util.getSpreadsheetExtension();
		}
		sheet = rfile.readKDSheet(util.getTestCasePath(), excelFile, worksheet);
		if (sheetIterations > 0) {
			/*
			 * Set the worksheet header information
			 */
			String[] row0 = {"Date/Time", "Worksheet", "TestCase", "Iteration",
					"Row", "Action", "Object", "Value", "Value Type",
					"Variable", "Comment", "Elapsed Time", "Expected", "Actual"};
			/*
			 * If executing, clear the result worksheet and .csv output files
			 */
			try {
				resultSpreadsheet.substring(resultSpreadsheet.indexOf("."));
				excelFile = resultSpreadsheet;
			} catch (StringIndexOutOfBoundsException e) {
				excelFile = resultSpreadsheet + util.getSpreadsheetExtension();
			}
			wfile.clearWorksheet(util.getTestReportPath(), excelFile, worksheet);
			wfile.writeExcel(util.getTestReportPath(), excelFile, worksheet,
					row0);
			if (capCsv == true) {
				csvFile = resultSpreadsheet + "_" + worksheet + ".csv";
				util.deleteFile(util.getTestReportPath(), csvFile);
				cfile.writeCsv(util.getTestReportPath(), resultSpreadsheet,
						worksheet, row0);
			}
		}
		return sheet;
	}
}