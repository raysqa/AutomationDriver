package raysullivan.operation;

import java.io.File;

import org.apache.commons.io.FileUtils;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.firefox.FirefoxProfile;
import org.openqa.selenium.ie.InternetExplorerDriver;
import org.openqa.selenium.opera.OperaDriver;
import org.openqa.selenium.safari.SafariDriver;

import raysullivan.operation.AESencrp;

/**
 * TestDriverUtil miscellaneous utility methods for the automation driver
 * 
 * @author rsullivan
 *
 */
public class AutomationDriverUtil {
	private static WebDriver driver;
	private String spreadsheet, testcase, worksheet;
	private String decimalPlaces, keyString, testReportName;
	private String webprofile, propertyname, resultspreadsheet;
	private int sheetiterations, defaultTimeout;
	private boolean capCsv, capVideo;
	private static String browser;
	private static final String SUCCESS = "Success";
	private static final String ERROR = "Error";
	private static final String RESOURCEPATH = System.getProperty("user.dir")
			+ "\\resource\\";
	private static final String SPREADSHEET = ".xlsx";
	private static final String TESTREPORTPATH = "Test Report\\";
	private static final String TESTCASEPATH = "Test Cases\\";
	private static final String TESTPROPERTYPATH = "Test Properties\\";
	private static final String PROPERTYDELIMITER = "|";
	private static final String REPORTNAME = "AutomationTestReport";

	/**
	 * getDriver Creates a new web driver instance
	 * 
	 * @param profile
	 *            optional FirefoxProfile name
	 * 
	 * @return WebDriver
	 * @throws AutomationDriverException
	 */
	public static WebDriver getDriver(final FirefoxProfile profile)
			throws AutomationDriverException {
		// First session of WebDriver
		 if (driver == null) { // rs 20160603
			 switch (getBrowser().toLowerCase()) {
			case "ie" :
				System.setProperty("webdriver.ie.driver", RESOURCEPATH
						+ "IEDriverServer.exe");
				driver = new InternetExplorerDriver();
				break;
			case "chrome" :
				System.setProperty("webdriver.chrome.driver", RESOURCEPATH
						+ "chromedriver.exe");
				driver = new ChromeDriver();
				break;
			case "opera" :
				driver = new OperaDriver();
				break;
			case "safari" :
				driver = new SafariDriver();
				break;
			case "firefox" :
				System.setProperty("webdriver.gecko.driver", RESOURCEPATH
						+ "geckodriver.exe");
				if (profile == null) {
					driver = new FirefoxDriver();
				} else {
					driver = new FirefoxDriver(profile);
				}
				break;
			default :
				throw new AutomationDriverException("Browser " + getBrowser()
				+ " not supported");
			}
			try {
				driver.manage().window().maximize();
			} catch (UnsupportedOperationException e) {
			}
		} // rs 20160603
		return driver;
	}


	/**
	 * takeSnapshot takes a screenshot
	 * 
	 * @param webdriver		Selenium webdrier instance
	 * @param fileWithPath	file name and path to store the screenshot
	 * 
	 * @throws Exception
	 */
	public static void takeSnapShot(final WebDriver webdriver, final String fileWithPath)
			throws Exception {

		// Convert web driver object to TakeScreenshot
		final TakesScreenshot scrShot = ((TakesScreenshot) webdriver);
		// Call getScreenshotAs method to create image file
		final File srcFile = scrShot.getScreenshotAs(OutputType.FILE);
		// Move image file to new destination
		final File destFile = new File(fileWithPath);
		// Copy file at destination
		FileUtils.copyFile(srcFile, destFile);
		// Cleanup
		FileUtils.deleteQuietly(srcFile);
	}
	/**
	 * getSuccessString getter
	 * 
	 * @return SUCCESS
	 */
	public String getSuccessString() {
		return SUCCESS;
	}
	/**
	 * getErrorString getter
	 * 
	 * @return ERROR
	 */
	public String getErrorString() {
		return ERROR;
	}
	/**
	 * getCapCsv getter
	 * 
	 * @return capCsv
	 */
	public boolean isCapCsv() {
		return capCsv;
	}
	/**
	 * getCapVideo getter
	 * 
	 * @return capVideo
	 */
	public boolean isCapVideo() {
		return capVideo;
	}

	/**
	 * getTestCase getter
	 * 
	 * @return TestCase
	 */
	public String getTestCase() {
		return testcase;
	}

	/**
	 * setTestCase setter
	 * 
	 */
	public void setTestCase(final String testCase) {
		this.testcase = testCase;
	}
	/**
	 * getWorksheet getter
	 * 
	 * @return worksheet
	 */
	public String getWorksheet() {
		return worksheet;
	}
	/**
	 * getResultSpreadsheet getter
	 * 
	 * @return resultspreadsheet
	 */
	public String getResultSpreadsheet() {
		return resultspreadsheet;
	}
	/**
	 * getSpreadsheet getter
	 * 
	 * @return spreadsheet
	 */
	public String getSpreadsheet() {
		return spreadsheet;
	}
	/**
	 * getBrowser getter
	 * 
	 * @return browser
	 */
	public static String getBrowser() {
		return browser;
	}
	/**
	 * getWebProfile getter
	 * 
	 * @return webprofile
	 */
	public String getWebProfile() {
		return webprofile;
	}
	/**
	 * getPropertyName getter
	 * 
	 * @return propertyname
	 */
	public String getPropertyName() {
		return propertyname;
	}
	/**
	 * setTimeout setter
	 */
	public void setTimeout(final int defaultTimeout) {
		this.defaultTimeout = defaultTimeout;
	}
	/**
	 * getTimeout getter
	 * 
	 * @return defaulTtimeout
	 */
	public int getTimeout() {
		return defaultTimeout;
	}
	/**
	 * getSheetIterations getter
	 * 
	 * @return sheetiterations
	 */
	public int getSheetIterations() {
		return sheetiterations;
	}
	/**
	 * setTestProfile setter
	 * 
	 */
	public void setTestProfile(final String spreadsheet, final String worksheet,
			final String resultspreadsheet, final String browser, final String webprofile,
			final int defaultTimeout, final String propertyname, final int sheetiterations, final boolean capCsv2,
			final boolean capVideo2) {
		this.spreadsheet = spreadsheet;
		this.worksheet = worksheet;
		this.resultspreadsheet = resultspreadsheet;
		AutomationDriverUtil.browser = browser;
		this.webprofile = webprofile;
		this.defaultTimeout = defaultTimeout;
		this.propertyname = propertyname;
		this.sheetiterations = sheetiterations;
		this.capCsv = capCsv2;
		this.capVideo = capVideo2;
	}
	/**
	 * setTestReportName setter
	 * 
	 * @return RESOURCEPATH
	 */
	public void setTestReportName(final String timestamp) {
		this.testReportName = REPORTNAME + "_" + timestamp + ".pdf";
	}
	/**
	 * getTestReportName getter
	 * 
	 * @return testReportName
	 */
	public String getTestReportName() {
		return testReportName;
	}
	/**
	 * getResourcePath getter
	 * 
	 * @return RESOURCEPATH
	 */
	public String getResourcePath() {
		return RESOURCEPATH;
	}
	/**
	 * getResourcePath getter
	 * 
	 * @return RESOURCEPATH
	 */
	public String getSpreadsheetExtension() {
		return SPREADSHEET;
	}
	/**
	 * getTestReportPath getter
	 * 
	 * @return getResourcePath()+TESTREPORTPATH;
	 */
	public String getTestReportPath() {
		return getResourcePath() + TESTREPORTPATH;
	}
	/**
	 * getTestCasePath getter
	 * 
	 * @return getResourcePath()+TESTCASEPATH;
	 */
	public String getTestCasePath() {
		return getResourcePath() + TESTCASEPATH;
	}
	/**
	 * getTestPropertyPath getter
	 * 
	 * @return getResourcePath()+TESTPROPERTYPATH;
	 */
	public String getTestPropertyPath() {
		return getResourcePath() + TESTPROPERTYPATH;
	}
	/**
	 * calcEt Calculates elapsed time in seconds between two events
	 * 
	 * @param end
	 *            end of the timed event
	 * 
	 * @param start
	 *            start of the timed event
	 * 
	 * @param millisec
	 * 
	 * @return elapsedTime
	 */
	public final float calcEt(final long end, final long start, final int millisec) {
		float duration = 0;
		if (end > start) {
			duration = (float) (end - start) / millisec;
		} else {
			duration = (float) (start - end) / millisec;
		}
		return duration;
	}
	/**
	 * calcAvg Returns an average value
	 * 
	 * @param total
	 *            total amount
	 * 
	 * @param divideBy
	 *            number to divide by
	 * 
	 * @param roundup
	 *            value to determine rounding
	 * 
	 * @return average
	 */
	public final float calcAvg(final float total, final float divideBy, final int roundup) {
		if (divideBy == 0) {
			return divideBy;
		} else {

			final float average = (float) Math.round((total / divideBy) * roundup)
					/ roundup;
			return average;
		}
	}
	/**
	 * cellFormat correct numeric or text format of a number
	 * 
	 * @param cell
	 * @return cell[3]
	 * @throws Exception
	 */
	public String valueCellFormat(final String[] cell) throws Exception {
		/*
		 * If a value is TEXT or if the action is PAUSE/CLICKANDHOLD, strip of trailing .0
		 * from numeric values passed from the spreadsheet
		 * 
		 * Otherwise if it is Numeric, make sure the number has two decimal
		 * places
		 */
		String value = cell[3];
		final String valueType = cell[4];
		final String keyword = cell[1];
		final String decimalConstant = ".0";
		setKeyString("automationDriver");
		boolean error = false;
		try {
			decimalPlaces = value.substring(value.indexOf("."));
		} catch (StringIndexOutOfBoundsException s) {
			decimalPlaces = ".0";
			error = true;
		}
		if (valueType.equalsIgnoreCase("TEXT")
				|| keyword.equalsIgnoreCase("PAUSE")
				|| keyword.equalsIgnoreCase("CLICKANDHOLD")
				|| keyword.equalsIgnoreCase("SELECTBYINDEX")
				|| keyword.equalsIgnoreCase("DESELECTBYINDEX")) {
			value = value.replace(decimalPlaces, "");
		} else if (valueType.equalsIgnoreCase("DECIMAL")
				&& decimalPlaces.equals(decimalConstant)) {
			if (error) {
				value = value + decimalPlaces;
			}
			value = value.replace(decimalPlaces, ".00");
		} else if (valueType.equalsIgnoreCase("DECIMAL")
				&& decimalPlaces.length() == 2){
			value = value + "0";
		}
		if (valueType.equalsIgnoreCase("ENCRYPT")) {
			value = AESencrp.decrypt(value, getKeyString());
		}
		return value;
	}
	/**
	 * cellFormat correct numeric or text format of a number
	 * 
	 * @param cell
	 * @param returnString
	 * @return returnString[1]
	 */
	public String resultCellFormat(final String[] cell, String[] returnString) {
		/*
		 * If the return value is a NUMBER, strip out commas from the result
		 */
		final String valueType = cell[4];
		if (valueType.equalsIgnoreCase("DECIMAL")) {
			returnString[1] = returnString[1].replace(",", "");
		}
		return returnString[1];
	}
	/**
	 * deleteFile Delete a file system file
	 * 
	 * @param filepath
	 * @param filename
	 */
	public void deleteFile(final String filepath, final String filename) {
		final File file = new File(filepath + "\\" + filename);
		if (file.exists()) {
			FileUtils.deleteQuietly(file);
		}
	}
	/**
	 * getKeyString getter
	 * 
	 * @return keyString
	 */
	public String getKeyString() {
		return keyString;
	}
	/**
	 * setKeyString setter
	 * 
	 */
	public void setKeyString(final String keyString) {
		this.keyString = keyString;
	}
	/**
	 * getPropertyDelimiter getter
	 * 
	 * @return PROPERTYDELIMITER
	 */
	public String getPropertyDelimiter() {
		return PROPERTYDELIMITER;
	}
	/**
	 * testOutput sends output to spreadsheet and .csv file
	 * 
	 * @param rows
	 * @param wfile
	 * @param cfile
	 * @throws Exception
	 */
	public final void testOutput(final String rows[], final WriteExcelTestResults wfile,
			final WriteCsvTestResults cfile) throws Exception {
		/*
		 * Send the test results to the log
		 */
		wfile.writeExcel(getTestReportPath(), getResultSpreadsheet(),
				getWorksheet(), rows);
		if (isCapCsv() == true) {
			cfile.writeCsv(getTestReportPath(), getResultSpreadsheet(),
					getWorksheet(), rows);
		}
	}
}
