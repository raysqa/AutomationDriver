package raysullivan.operation;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Properties;
import java.util.Set;

import org.openqa.selenium.By;
import org.openqa.selenium.ElementNotVisibleException;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.NoAlertPresentException;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.NoSuchFrameException;
import org.openqa.selenium.StaleElementReferenceException;
import org.openqa.selenium.TimeoutException;
import org.openqa.selenium.UnsupportedCommandException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebDriverException;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.interactions.MoveTargetOutOfBoundsException;

/**
 * UIOperation
 * 
 * @author rsullivan
 *
 */
public class UIOperation {
	private WebDriver driver;
	private WebDriverWait waitVar;
	private static final int millisec = 1000;
	private long starttime, endtime;
	private static AutomationDriverUtil util = new AutomationDriverUtil();
	private static Set<?> setOfOldHandles = null;

	/**
	 * UIOperations
	 * 
	 * @param driver
	 * @param waitVar
	 */
	public UIOperation(WebDriver driver, WebDriverWait waitVar) {
		this.driver = driver;
		this.waitVar = waitVar;
	}

	/**
	 * Perform a specific Selenium WebDriver action
	 * 
	 * @param p
	 *            object properties
	 * @param cell
	 *            array containing spreadsheet cell information
	 * @return returnMessage array containing elapsed time and status message
	 * @throws Exception
	 */
	public String[] perform(Properties p, String propertyName, String cell[],
			DriverVariable var, AutomationDriverUtil util) throws Exception {
		String operation = cell[1];
		String objectName = cell[2];
		String value = cell[3];
		String valueType = cell[4];
		String variable = cell[5];
		String returnMessage[] = new String[3];
		String getVariable[] = new String[2];
		String createVariable[] = new String[2];
		returnMessage[1] = util.getErrorString(); // actual result
		returnMessage[2] = util.getSuccessString(); // expected result
		WebElement checkbox = null;
		starttime = new Date().getTime();
		switch (operation.toUpperCase()) {
			case "SKIP" :
				// Ignore the step and return success
				returnMessage[1] = util.getSuccessString();
				break;
			case "CLICK" :
				// Perform click
				returnMessage = click(p, objectName, propertyName,
						returnMessage);
				break;
			case "CLICKON" :
				returnMessage = clickOn(p, objectName, propertyName, checkbox,
						returnMessage);
				break;
			case "CLICKOFF" :
				returnMessage = clickOff(p, objectName, propertyName, checkbox,
						returnMessage);
				break;
			case "CLICKNOASSERT" :
				returnMessage = clickNoAssert(p, objectName, propertyName,
						returnMessage);
				break;
			case "CLICKALERT" :
				returnMessage = clickAlert(returnMessage);
				break;
			case "PAUSE" :
				// sleep for the determined number of milliseconds
				returnMessage = pause(value, returnMessage);
				break;
			case "REFRESH" :
				returnMessage = refresh(returnMessage);
				break;
			case "SUBMIT" :
				// Perform submit
				returnMessage = submit(p, objectName, propertyName,
						returnMessage);
				break;
			case "SETTEXT" :
				// If the get variable and set variable are the same, error
				returnMessage = setText(p, objectName, propertyName, value,
						variable, operation, var, createVariable, returnMessage);
				break;
			case "SELECT" :
				returnMessage = select(p, objectName, propertyName, value,
						variable, operation, var, createVariable, returnMessage);
				break;
			case "DESELECT" :
				returnMessage = deselect(p, objectName, propertyName, value,
						variable, operation, var, createVariable, returnMessage);
				break;
			case "SELECTBYINDEX" :
				returnMessage = selectByIndex(p, objectName, propertyName,
						value, variable, operation, var, createVariable,
						returnMessage);
				break;
			case "DESELECTBYINDEX" :
				returnMessage = deselectByIndex(p, objectName, propertyName,
						value, variable, operation, var, createVariable,
						returnMessage);
				break;
			case "CLEAR" :
				returnMessage = clear(p, objectName, propertyName,
						returnMessage);
				break;
			case "GOTOURL" :
				// Get url of application
				returnMessage = gotoUrl(p, objectName, propertyName, returnMessage);
				break;
			case "GOTO" :
				returnMessage = gotoAddress(p, objectName, propertyName, value,
						variable, operation, var, createVariable, returnMessage);
				break;
			case "ASSERTURL" :
				returnMessage = assertUrl(objectName, value, variable,
						operation, var, createVariable, returnMessage);
				break;
			case "ASSERTTEXT" :
				returnMessage = assertText(p, objectName, propertyName, value,
						variable, operation, var, createVariable, returnMessage);
				break;
			case "ASSERTEQUAL" :
				returnMessage = assertEqual(p, objectName, propertyName, value,
						variable, operation, var, getVariable, returnMessage);
				break;
			case "ASSERTNOTEQUAL" :
				returnMessage = assertNotEqual(p, objectName, propertyName,
						value, variable, operation, var, getVariable,
						returnMessage);
				break;
			case "ASSERTCONTAINS" :
				returnMessage = assertContains(p, objectName, propertyName,
						value, variable, operation, var, getVariable,
						returnMessage);
				break;
			case "ASSERTNOTCONTAINS" :
				returnMessage = assertNotContains(p, objectName, propertyName,
						value, variable, operation, var, getVariable,
						returnMessage);
				break;
			case "CREATEVARIABLE" :
				returnMessage = createVar(value, variable, operation, var,
						createVariable, returnMessage);
				break;
			case "CONTAINSTEXT" :
				returnMessage = containsText(p, objectName, propertyName,
						value, variable, operation, var, createVariable,
						returnMessage);
				break;
			case "STORETEXT" :
				returnMessage = storeText(p, objectName, propertyName, value,
						variable, operation, var, createVariable, returnMessage);
				break;
			case "ASSERTATTRIBUTE" :
				returnMessage = assertAttribute(p, objectName, propertyName, value,
						variable, valueType, operation, var, createVariable, returnMessage);
				break;
			case "WAITVISIBLE" :
				returnMessage = waitVisible(p, objectName, propertyName,
						returnMessage);
				break;
			case "WAITINVISIBLE" :
				returnMessage = waitInvisible(p, objectName, propertyName,
						returnMessage);
				break;
			case "CLICKANDHOLD" :
				returnMessage = clickAndHold(p, objectName, propertyName,
						value, returnMessage);
				break;
			case "HOVER" :
				// rs hover action not supported by Selenium Webdriver 3.0.1 - see mozilla bug 
				// rs https://bugzilla.mozilla.org/show_bug.cgi?id=1292178
				returnMessage = hover(p, objectName, propertyName,
						returnMessage);  
				break;
			case "SWITCHFRAME" :
				returnMessage = switchFrame(p, objectName, propertyName,
						returnMessage);
				break;
			case "LISTWINDOWS" :
				returnMessage = listWindows(returnMessage);
				break;
			case "LISTFRAMES" :
				returnMessage = listFrames(returnMessage);
				break;
			case "SCROLLTO" :
				returnMessage = scrollTo(p, objectName, propertyName,
						returnMessage);
				break;
			case "NAVFORWARD" :
				driver.navigate().forward();
				returnMessage[1] = util.getSuccessString();
				break;
			case "NAVBACK" :
				driver.navigate().back();
				returnMessage[1] = util.getSuccessString();
				break;
			case "SCREENSHOT" :
				String baseScreenshot = util.getTestReportPath();
				SimpleDateFormat dateFormat = new SimpleDateFormat(
						"yyyy-MM-dd HH.mm.ss");
				String ssfile = baseScreenshot + "Screenshot_"
						+ util.getSpreadsheet() + "_" + util.getWorksheet()
						+ "_" + util.getTestCase() + "_"
						+ dateFormat.format(new Date()) + ".png";
				try {
					AutomationDriverUtil.takeSnapShot(driver, ssfile);
				} catch (Exception e) {
					returnMessage[1] = "Error:  Unable to create a SCREENSHOT";
					break;
				}
				returnMessage[1] = util.getSuccessString();
				break;
			default :
				// error
				throw new AutomationDriverException("Error: " + operation
						+ " is not a valid Keyword");
		}
		endtime = new Date().getTime();
		returnMessage[0] = Float.toString(util.calcEt(endtime, starttime,
				millisec));
		return returnMessage;
	}
	/**
	 * Find element BY using object type and value
	 * 
	 * @param p
	 * @param objectName
	 * @param objectType
	 * @return By
	 * @throws Exception
	 */
	public static By getObject(Properties p, String objectName,
			String propertyName) throws Exception {
		String objectType = "none";
		String newObjectName = null;
		String propDelimiter = util.getPropertyDelimiter();
		try {
			newObjectName = p
					.getProperty(objectName)
					.substring(0,
							p.getProperty(objectName).indexOf(propDelimiter))
					.trim();
			objectType = p
					.getProperty(objectName)
					.substring(
							p.getProperty(objectName).indexOf(propDelimiter) + 1)
					.trim();
		} catch (StringIndexOutOfBoundsException | NullPointerException e) {
			throw new AutomationDriverException("Error: object " + objectName
					+ " not found in property file " + propertyName);
		}

		// Find by id
		if (objectType.equalsIgnoreCase("ID")) {
			return By.id(newObjectName);
		}
		// find by class name
		else if (objectType.equalsIgnoreCase("CLASSNAME")) {
			return By.className(newObjectName);
		}
		// find by tag name
		else if (objectType.equalsIgnoreCase("TAGNAME")) {
			return By.tagName(newObjectName);
		}
		// find by name
		else if (objectType.equalsIgnoreCase("NAME")) {
			return By.name(newObjectName);
		}
		// find by link text
		else if (objectType.equalsIgnoreCase("LINK")) {
			return By.linkText(newObjectName);
		}
		// find by partial link text
		else if (objectType.equalsIgnoreCase("PARTIALLINK")) {
			return By.partialLinkText(newObjectName);
		}
		// Find by css
		else if (objectType.equalsIgnoreCase("CSS")) {
			return By.cssSelector(newObjectName);
		}
		// Find by xpath
		else if (objectType.equalsIgnoreCase("XPATH")) {
			return By.xpath(newObjectName);
		}
		// throw exception if invalid object type
		else {
			throw new AutomationDriverException("Error : " + objectType
					+ " not a valid object type for object name "
					+ newObjectName + ".");
		}
	}

	public static String[] validateVar(String value, String variable,
			String operation, DriverVariable var)
			throws AutomationDriverException {
		String validateVariable[] = new String[2];
		if (value == null || value == "") {
			throw new AutomationDriverException("Error in "
					+ operation.toUpperCase()
					+ ":  Value to assert cannot be blank or null.");
		}
		try {
			if (value.equals(variable)) {
				throw new AutomationDriverException("Error in "
						+ operation.toUpperCase()
						+ ":  Cannot set value variable " + value
						+ " to variable " + variable + ".");
			}
		} catch (NullPointerException e) {
			throw new AutomationDriverException("Error in "
					+ operation.toUpperCase() + ":  Value cannot be null.");
		}
		// If creating a new variable, validate
		try {
			if (variable.length() > 0) {
				if (createVar(variable, value, var).equals(
						util.getErrorString())) {
					throw new AutomationDriverException("Error in "
							+ operation.toUpperCase() + ":  Invalid variable "
							+ variable);
				}
			}
		} catch (NullPointerException e) {
			throw new AutomationDriverException("Error in "
					+ operation.toUpperCase() + ":  Variable cannot be null.");
		}
		// Validate that the passed value is a variable
		validateVariable = var.validateVariableName(value);
		return validateVariable;
	}

	public static String createVar(String variableName, String value,
			DriverVariable var) throws AutomationDriverException {
		String createVariable[] = var.validateVariableName(variableName);
		if (createVariable[0] == "true") {
			var.setVariableValue(createVariable[1], value);
		}
		return createVariable[0];
	}

	public static String[] validateAssertVariables(String variable1,
			String variable2, String operation, DriverVariable var)
			throws AutomationDriverException {
		String returnString[] = {null, null};
		// If the get variable and set variable are the same, error
		try {
			if (variable1.equals(variable2)) {
				throw new AutomationDriverException(
						"Error:  Cannot compare a variable to itself.");
			}
		} catch (NullPointerException npe) {
			throw new AutomationDriverException(
					"Error:  Variable name cannot be null.");
		}
		// Validate that the passed values are variables
		try {
			int varLength = variable1.length(), varLength1 = variable2.length();
			if (varLength == 0 || varLength1 == 0) {
				throw new AutomationDriverException(
						"Error:  "
								+ operation.toUpperCase()
								+ " requires a valid variable in the format \'${variable}\'.");
			}
		} catch (NullPointerException e) {
			throw new AutomationDriverException(
					"Error:  "
							+ operation.toUpperCase()
							+ " requires a valid variable in the format \'${variable}\'.");
		}
		String createVariable[] = var.validateVariableName(variable1);
		String getGetValVar[] = var.validateVariableName(variable2);
		// Stop if an error
		if (createVariable[0] != "true" || getGetValVar[0] != "true") {
			throw new AutomationDriverException(
					"Error:  "
							+ operation.toUpperCase()
							+ " field Value requires a valid variable in the format \'${variable}\'");
		}
		returnString[0] = createVariable[1];
		returnString[1] = getGetValVar[1];
		return returnString;
	}
	// Draws a yellow border around the found element. Does not set it back
	// anyhow.
	private WebElement findElement(By by) {
		WebElement elem = driver.findElement(by);
		// draw a border around the found element
		if (driver instanceof JavascriptExecutor) {
			((JavascriptExecutor) driver).executeScript(
					"arguments[0].style.border='3px solid yellow'", elem);
		}
		return elem;
	}
	private String[] click(Properties p, String objectName,
			String propertyName, String returnMessage[]) throws Exception {
		try {
			findElement(UIOperation.getObject(p, objectName, propertyName))
					.click();
			returnMessage[1] = util.getSuccessString();
		} catch (NoSuchElementException nsee) {
			returnMessage[1] = "Object " + objectName
					+ " could not be found due to a NoSuchElementException";
		} catch (StaleElementReferenceException sere) {
			returnMessage[1] = "Object "
					+ objectName
					+ " could not be found due to StaleElementReferenceException";
		} catch (ElementNotVisibleException enve) {
			returnMessage[1] = "Object " + objectName
					+ " could not be found due to ElementNotVisibleException";
		} catch (WebDriverException we) {
			returnMessage[1] = "Object "
					+ objectName
					+ " could not be clicked due to WebDriverException: Element is not clickable";
		}
		return returnMessage;
	}
	private String[] clickOn(Properties p, String objectName,
			String propertyName, WebElement checkbox, String returnMessage[])
			throws Exception {
		try {
			checkbox = findElement(UIOperation.getObject(p, objectName,
					propertyName));
			checkbox.click();
			if (checkbox.isSelected() == true) {
				returnMessage[1] = util.getSuccessString();
			}
		} catch (NoSuchElementException nsee) {
			returnMessage[1] = "Object " + objectName
					+ " could not be found due to NoSuchElementException";
		} catch (StaleElementReferenceException sere) {
			returnMessage[1] = "Object "
					+ objectName
					+ " could not be found due to StaleElementReferenceException";
		} catch (ElementNotVisibleException enve) {
			returnMessage[1] = "Object " + objectName
					+ " could not be found due to ElementNotVisibleException";
		} catch (WebDriverException we) {
			returnMessage[1] = "Object "
					+ objectName
					+ " could not be clicked on due to WebDriverException: Element is not clickable";
		}
		return returnMessage;
	}
	private String[] clickOff(Properties p, String objectName,
			String propertyName, WebElement checkbox, String returnMessage[])
			throws Exception {
		try {
			checkbox = findElement(UIOperation.getObject(p, objectName,
					propertyName));
			checkbox.click();
			if (checkbox.isSelected() == false) {
				returnMessage[1] = util.getSuccessString();
			}
		} catch (NoSuchElementException nsee) {
			returnMessage[1] = "Object " + objectName
					+ " could not be found due to NoSuchElementException";
		} catch (StaleElementReferenceException sere) {
			returnMessage[1] = "Object "
					+ objectName
					+ " could not be found due to StaleElementReferenceException";
		} catch (ElementNotVisibleException enve) {
			returnMessage[1] = "Object " + objectName
					+ " could not be found due to ElementNotVisibleException";
		} catch (WebDriverException we) {
			returnMessage[1] = "Object "
					+ objectName
					+ " could not be clicked on due to WebDriverException: Element is not clickable";
		}
		return returnMessage;
	}
	private String[] clickNoAssert(Properties p, String objectName,
			String propertyName, String returnMessage[]) throws Exception {
		returnMessage[1] = util.getSuccessString();
		try {
			findElement(UIOperation.getObject(p, objectName, propertyName))
					.click();
		} catch (Exception e) {
		}
		return returnMessage;
	}
	private String[] clickAlert(String returnMessage[]) throws Exception {
		try {
			driver.switchTo().alert().accept();
			returnMessage[1] = util.getSuccessString();
		} catch (NoAlertPresentException npe) {
			returnMessage[1] = "Error: No alert is present.";
		}
		return returnMessage;
	}
	private String[] pause(String value, String returnMessage[])
			throws Exception {
		try {
			int sleep = millisec * Integer.parseInt(value);
			if (sleep <= 0) {
				throw new AutomationDriverException(
						"Error:  PAUSE duration must be a positive integer value");
			}
			Thread.sleep(sleep);
			returnMessage[1] = util.getSuccessString();
		} catch (NumberFormatException nfe) {
			throw new AutomationDriverException(
					"Error:  PAUSE duration must be a positive integer value");
		}
		return returnMessage;
	}
	private String[] refresh(String returnMessage[])
			throws AutomationDriverException {
		try {
			((JavascriptExecutor) driver)
					.executeScript("document.location.reload()");
			Thread.sleep(2000);
			returnMessage[1] = util.getSuccessString();
		} catch (Exception er) {
			throw new AutomationDriverException(
					"Error: unable to refresh the current page.");
		}
		return returnMessage;
	}
	private String[] submit(Properties p, String objectName,
			String propertyName, String returnMessage[]) throws Exception {
		try {
			findElement(UIOperation.getObject(p, objectName, propertyName))
					.submit();
			returnMessage[1] = util.getSuccessString();
		} catch (NoSuchElementException nsee) {
			returnMessage[1] = "Object " + objectName
					+ " could not be found due to NoSuchElementException";
		} catch (StaleElementReferenceException sere) {
			returnMessage[1] = "Object "
					+ objectName
					+ " could not be found due to StaleElementReferenceException";
		}
		return returnMessage;
	}
	private String[] setText(Properties p, String objectName,
			String propertyName, String value, String variable,
			String operation, DriverVariable var, String createVariable[],
			String returnMessage[]) throws Exception {
		createVariable = validateVar(value, variable, operation, var);
		if (createVariable[0] == "true") {
			value = var.getVariableValue(createVariable[1]);
		}
		// Set text on control
		try {
			findElement(UIOperation.getObject(p, objectName, propertyName))
					.sendKeys(value);
			returnMessage[1] = util.getSuccessString();
		} catch (NoSuchElementException nsee) {
			returnMessage[1] = "Object " + objectName
					+ " could not be found due to NoSuchElementException";
		} catch (StaleElementReferenceException sere) {
			returnMessage[1] = "Object "
					+ objectName
					+ " could not be found due to StaleElementReferenceException";
		} catch (ElementNotVisibleException enve) {
			returnMessage[1] = "Object " + objectName
					+ " could not be found due to ElementNotVisibleException";
		}
		return returnMessage;
	}
	private String[] select(Properties p, String objectName,
			String propertyName, String value, String variable,
			String operation, DriverVariable var, String createVariable[],
			String returnMessage[]) throws Exception {
		createVariable = validateVar(value, variable, operation, var);
		if (createVariable[0] == "true") {
			value = var.getVariableValue(createVariable[1]);
		}
		// Set select on target dropdown box
		try {
			Select drpObject = new Select(findElement(UIOperation.getObject(p,
					objectName, propertyName)));
			drpObject.selectByVisibleText(value);
			returnMessage[1] = util.getSuccessString();
		} catch (NoSuchElementException nsee) {
			returnMessage[1] = "Object " + objectName
					+ " could not be found due to NoSuchElementException";
		} catch (StaleElementReferenceException sere) {
			returnMessage[1] = "Object "
					+ objectName
					+ " could not be found due to StaleElementReferenceException";
		} catch (ElementNotVisibleException enve) {
			returnMessage[1] = "Object " + objectName
					+ " could not be found due to ElementNotVisibleException";
		}
		return returnMessage;
	}
	private String[] deselect(Properties p, String objectName,
			String propertyName, String value, String variable,
			String operation, DriverVariable var, String createVariable[],
			String returnMessage[]) throws Exception {
		createVariable = validateVar(value, variable, operation, var);
		if (createVariable[0] == "true") {
			value = var.getVariableValue(createVariable[1]);
		}
		// Set select on target dropdown box
		try {
			Select drpObject = new Select(findElement(UIOperation.getObject(p,
					objectName, propertyName)));
			drpObject.deselectByVisibleText(value);
			returnMessage[1] = util.getSuccessString();
		} catch (NoSuchElementException nsee) {
			returnMessage[1] = "Object " + objectName
					+ " could not be found due to NoSuchElementException";
		} catch (StaleElementReferenceException sere) {
			returnMessage[1] = "Object "
					+ objectName
					+ " could not be found due to StaleElementReferenceException";
		} catch (ElementNotVisibleException enve) {
			returnMessage[1] = "Object " + objectName
					+ " could not be found due to ElementNotVisibleException";
		}
		return returnMessage;
	}
	private String[] selectByIndex(Properties p, String objectName,
			String propertyName, String value, String variable,
			String operation, DriverVariable var, String createVariable[],
			String returnMessage[]) throws Exception {
		// If the get variable and set variable are the same, error
		createVariable = validateVar(value, variable, operation, var);
		if (createVariable[0] == "true") {
			value = var.getVariableValue(createVariable[1]);
		}
		// Set select on target dropdown box
		try {
			Select drpObject = new Select(findElement(UIOperation.getObject(p,
					objectName, propertyName)));
			int selectIdx = Integer.parseInt(value);
			drpObject.selectByIndex(selectIdx);
			returnMessage[1] = util.getSuccessString();
		} catch (NoSuchElementException nsee) {
			returnMessage[1] = "Object " + objectName
					+ " could not be found due to NoSuchElementException";
		} catch (StaleElementReferenceException sere) {
			returnMessage[1] = "Object "
					+ objectName
					+ " could not be found due to StaleElementReferenceException";
		} catch (ElementNotVisibleException enve) {
			returnMessage[1] = "Object " + objectName
					+ " could not be found due to ElementNotVisibleException";
		}
		return returnMessage;
	}
	private String[] deselectByIndex(Properties p, String objectName,
			String propertyName, String value, String variable,
			String operation, DriverVariable var, String createVariable[],
			String returnMessage[]) throws Exception {
		// If the get variable and set variable are the same, error
		createVariable = validateVar(value, variable, operation, var);
		if (createVariable[0] == "true") {
			value = var.getVariableValue(createVariable[1]);
		}
		// Set select on target dropdown box
		try {
			Select drpObject = new Select(findElement(UIOperation.getObject(p,
					objectName, propertyName)));
			int selectIdx = Integer.parseInt(value);
			drpObject.deselectByIndex(selectIdx);
			returnMessage[1] = util.getSuccessString();
		} catch (NoSuchElementException nsee) {
			returnMessage[1] = "Object " + objectName
					+ " could not be found due to NoSuchElementException";
		} catch (StaleElementReferenceException sere) {
			returnMessage[1] = "Object "
					+ objectName
					+ " could not be found due to StaleElementReferenceException";
		} catch (ElementNotVisibleException enve) {
			returnMessage[1] = "Object " + objectName
					+ " could not be found due to ElementNotVisibleException";
		}
		return returnMessage;
	}
	private String[] clear(Properties p, String objectName,
			String propertyName, String returnMessage[]) throws Exception {
		try {
			findElement(UIOperation.getObject(p, objectName, propertyName))
					.clear();
			returnMessage[1] = util.getSuccessString();
		} catch (NoSuchElementException nsee) {
			returnMessage[1] = "Object " + objectName
					+ " could not be found due to NoSuchElementException";
		} catch (StaleElementReferenceException sere) {
			returnMessage[1] = "Object "
					+ objectName
					+ " could not be found due to StaleElementReferenceException";
		} catch (ElementNotVisibleException enve) {
			returnMessage[1] = "Object " + objectName
					+ " could not be found due to ElementNotVisibleException";
		}
		return returnMessage;
	}
	private String[] gotoUrl(Properties p, String value, String propertyName,
			String returnMessage[]) throws Exception {
		// Get url of application
		try {
			driver.get(p.getProperty(value));
		} catch (NullPointerException e) {
			throw new AutomationDriverException("Error: URL property " + value
					+ " not found in property file " + propertyName);
		}
		returnMessage[1] = util.getSuccessString();
		return returnMessage;
	}
	private String[] gotoAddress(Properties p, String objectName,
			String propertyName, String value, String variable,
			String operation, DriverVariable var, String createVariable[],
			String returnMessage[]) throws Exception {
		createVariable = validateVar(value, variable, operation, var);
		// if the passed parameter is a variable, get the value
		if (createVariable[0] == "true") {
			value = var.getVariableValue(createVariable[1]);
			// Get url of application
		}
		driver.get(value);
		returnMessage[1] = util.getSuccessString();
		return returnMessage;
	}
	private String[] assertUrl(String objectName, String value,
			String variable, String operation, DriverVariable var,
			String createVariable[], String returnMessage[]) throws Exception {
		// If creating a new variable, validate
		createVariable = validateVar(value, variable, operation, var);
		// Get the current page URL
		try {
			returnMessage[1] = driver.getCurrentUrl();
			returnMessage[2] = value;
			// validate against a variable
			if (createVariable[0] == "true") {
				returnMessage[2] = var.getVariableValue(createVariable[1]);
			}
		} catch (NoSuchElementException nsee) {
			returnMessage[1] = "Object " + objectName
					+ " could not be found due to NoSuchElementException";
		} catch (StaleElementReferenceException sere) {
			returnMessage[1] = "Object "
					+ objectName
					+ " could not be found due to StaleElementReferenceException";
		}
		return returnMessage;
	}
	private String[] assertText(Properties p, String objectName,
			String propertyName, String value, String variable,
			String operation, DriverVariable var, String createVariable[],
			String returnMessage[]) throws Exception {
		createVariable = validateVar(value, variable, operation, var);
		// Get text of an element
		try {
			returnMessage[1] = findElement(
					UIOperation.getObject(p, objectName, propertyName))
					.getText().replaceAll("(\\r|\\n|\\t)", "");
			returnMessage[2] = value;
			// validate against a variable
			if (createVariable[0] == "true") {
				returnMessage[2] = var.getVariableValue(createVariable[1]);
			}
		} catch (NoSuchElementException nsee) {
			returnMessage[1] = "Object " + objectName
					+ " could not be found due to NoSuchElementException";
		} catch (StaleElementReferenceException sere) {
			returnMessage[1] = "Object "
					+ objectName
					+ " could not be found due to StaleElementReferenceException";
		} catch (ElementNotVisibleException enve) {
			returnMessage[1] = "Object " + objectName
					+ " could not be found due to ElementNotVisibleException";
		}
		return returnMessage;
	}
	private String[] assertEqual(Properties p, String objectName,
			String propertyName, String value, String variable,
			String operation, DriverVariable var, String getVariable[],
			String returnMessage[]) throws Exception {
		// Validate parameters to ensure they are both valid variables
		getVariable = validateAssertVariables(value, variable,
				operation.toUpperCase(), var);
		// get the variable values
		returnMessage[2] = var.getVariableValue(getVariable[0]);
		returnMessage[1] = var.getVariableValue(getVariable[1]);
		return returnMessage;
	}
	private String[] assertNotEqual(Properties p, String objectName,
			String propertyName, String value, String variable,
			String operation, DriverVariable var, String getVariable[],
			String returnMessage[]) throws Exception {
		// Validate parameters to ensure they are both valid variables
		getVariable = validateAssertVariables(value, variable,
				operation.toUpperCase(), var);
		// get the variable values
		returnMessage[2] = var.getVariableValue(getVariable[0]);
		String assertValue = var.getVariableValue(getVariable[1]);
		if (!returnMessage[2].equals(assertValue)) {
			returnMessage[1] = util.getSuccessString();
			returnMessage[2] = util.getSuccessString();
		}
		return returnMessage;
	}
	private String[] assertContains(Properties p, String objectName,
			String propertyName, String value, String variable,
			String operation, DriverVariable var, String getVariable[],
			String returnMessage[]) throws Exception {
		// Validate parameters to ensure they are both valid variables
		getVariable = validateAssertVariables(value, variable,
				operation.toUpperCase(), var);
		// get the variable values
		returnMessage[2] = var.getVariableValue(getVariable[0]);
		String assertValue = var.getVariableValue(getVariable[1]);
		if (returnMessage[2].toLowerCase().contains(assertValue.toLowerCase())) {
			returnMessage[1] = util.getSuccessString();
			returnMessage[2] = util.getSuccessString();
		}
		return returnMessage;
	}
	private String[] assertNotContains(Properties p, String objectName,
			String propertyName, String value, String variable,
			String operation, DriverVariable var, String getVariable[],
			String returnMessage[]) throws Exception {
		// Validate parameters to ensure they are both valid variables
		getVariable = validateAssertVariables(value, variable,
				operation.toUpperCase(), var);
		// get the variable values
		returnMessage[2] = var.getVariableValue(getVariable[0]);
		String assertValue = var.getVariableValue(getVariable[1]);
		if (!returnMessage[2].toLowerCase().contains(assertValue.toLowerCase())) {
			returnMessage[1] = util.getSuccessString();
			returnMessage[2] = util.getSuccessString();
		}
		return returnMessage;
	}
	private String[] createVar(String value, String variable, String operation,
			DriverVariable var, String createVariable[], String returnMessage[])
			throws Exception {
		// Validate that the passed value is a variable
		// If the get variable and set variable are the same, error
		createVariable = validateVar(variable, value, operation, var);
		// Stop if an error or not a variable
		if (createVariable[0] != "true") {
			throw new AutomationDriverException(
					"Error:  "
							+ operation.toUpperCase()
							+ " requires a valid variable in the format \'${variable}\'");
		}
		// find the value from the passed element
		if (var.setVariableValue(createVariable[1], value).equals(value)) {
			returnMessage[1] = value;
		}
		returnMessage[2] = value;
		return returnMessage;
	}
	private String[] containsText(Properties p, String objectName,
			String propertyName, String value, String variable,
			String operation, DriverVariable var, String createVariable[],
			String returnMessage[]) throws Exception {
		// If the get variable and set variable are the same, error
		createVariable = validateVar(value, variable, operation, var);
		// Get text of an element
		try {
			returnMessage[1] = findElement(
					UIOperation.getObject(p, objectName, propertyName))
					.getText().toLowerCase().replaceAll("(\\r|\\n|\\t)", "");
			// validate against a variable
			if (createVariable[0] == "true") {
				value = var.getVariableValue(createVariable[1]);
			}
			if (returnMessage[1].contains(value.toLowerCase())) {
				returnMessage[1] = util.getSuccessString();
			}
		} catch (NoSuchElementException nsee) {
			returnMessage[1] = "Object " + objectName
					+ " could not be found due to NoSuchElementException";
		} catch (StaleElementReferenceException sere) {
			returnMessage[1] = "Object "
					+ objectName
					+ " could not be found due to StaleElementReferenceException";
		} catch (ElementNotVisibleException enve) {
			returnMessage[1] = "Object " + objectName
					+ " could not be found due to ElementNotVisibleException";
		}
		return returnMessage;
	}
	private String[] storeText(Properties p, String objectName,
			String propertyName, String value, String variable,
			String operation, DriverVariable var, String createVariable[],
			String returnMessage[]) throws Exception {
		// Validate that the passed value is a variable
		try {
			int varLength = variable.length();
			if (varLength == 0) {
				throw new AutomationDriverException(
						"Error:  "
								+ operation.toUpperCase()
								+ " requires a valid variable in the format \'${variable}\'");
			}
		} catch (NullPointerException e) {
			throw new AutomationDriverException(
					"Error:  "
							+ operation.toUpperCase()
							+ " requires a valid variable in the format \'${variable}\'");
		}
		createVariable = var.validateVariableName(variable);
		// Stop if an error or not a variable
		if (createVariable[0] != "true") {
			throw new AutomationDriverException("Error:  "
					+ operation.toUpperCase() + " variable name " + variable
					+ " is not valid.");
		}
		// find the value from the passed element
		try {
			String vvalue = findElement(
					UIOperation.getObject(p, objectName, propertyName))
					.getText().replaceAll("(\\r|\\n|\\t)", "");
			// store the value with the variable name
			if (var.setVariableValue(createVariable[1], vvalue).equals(vvalue)) {
				returnMessage[1] = vvalue;
			}
			returnMessage[2] = vvalue;
		} catch (NoSuchElementException nsee) {
			returnMessage[1] = "Object " + objectName
					+ " could not be found due to NoSuchElementException";
		} catch (StaleElementReferenceException sere) {
			returnMessage[1] = "Object "
					+ objectName
					+ " could not be found due to StaleElementReferenceException";
		} catch (ElementNotVisibleException enve) {
			returnMessage[1] = "Object " + objectName
					+ " could not be found due to ElementNotVisibleException";
		}
		return returnMessage;
	}
	private String[] assertAttribute(Properties p, String objectName,
			String propertyName, String value, String variable,
			String valueType, String operation, DriverVariable var,
			String createVariable[], String returnMessage[]) throws Exception {
		// If the get variable and set variable are the same, error
		createVariable = validateVar(value, variable, operation, var);
		// Set text on control
		try {
			returnMessage[1] = findElement(
					UIOperation.getObject(p, objectName, propertyName))
					.getAttribute(valueType);
			// If the attribute is null, return an error
			if (returnMessage[1] == null) {
				returnMessage[1] = "Object " + objectName
						+ " does not have an attribute of " + valueType;
			}
			returnMessage[2] = value;
			// validate against a variable
			if (createVariable[0] == "true") {
				returnMessage[2] = var.getVariableValue(createVariable[1]);
			}
		} catch (NoSuchElementException nsee) {
			returnMessage[1] = "Object " + objectName
					+ " could not be found due to NoSuchElementException";
		} catch (StaleElementReferenceException sere) {
			returnMessage[1] = "Object "
					+ objectName
					+ " could not be found due to StaleElementReferenceException";
		} catch (ElementNotVisibleException enve) {
			returnMessage[1] = "Object " + objectName
					+ " could not be found due to ElementNotVisibleException";
		}
		return returnMessage;
	}
	private String[] waitVisible(Properties p, String objectName,
			String propertyName, String returnMessage[]) throws Exception {
		// wait for element to display
		try {
			waitVar.until(ExpectedConditions
					.visibilityOfElementLocated(UIOperation.getObject(p,
							objectName, propertyName)));
			returnMessage[1] = util.getSuccessString();
		} catch (TimeoutException te) {
			returnMessage[1] = "Object " + objectName
					+ " did not become visible within the default timeout duration";

		} catch (NoSuchElementException nsee) {
			returnMessage[1] = "Object " + objectName
					+ " could not be found due to NoSuchElementException";
		} catch (StaleElementReferenceException sere) {
			returnMessage[1] = "Object "
					+ objectName
					+ " could not be found due to StaleElementReferenceException";
		}
		return returnMessage;
	}
	private String[] waitInvisible(Properties p, String objectName,
			String propertyName, String returnMessage[]) throws Exception {
		// wait for element to not display
		try {
			waitVar.until(ExpectedConditions
					.invisibilityOfElementLocated(UIOperation
							.getObject(p, objectName, propertyName)));
			returnMessage[1] = util.getSuccessString();
		} catch (TimeoutException te) {
			returnMessage[1] = "Object " + objectName
					+ " did not become invisible within the default timeout duration of " + waitVar + " seconds";
		} catch (NoSuchElementException nsee) {
			returnMessage[1] = "Object "
					+ objectName
					+ " could not be found due to NoSuchElementException";
		} catch (StaleElementReferenceException sere) {
			returnMessage[1] = "Object "
					+ objectName
					+ " could not be found due to StaleElementReferenceException";
		}
		return returnMessage;
	}
	private String[] scrollTo(Properties p, String objectName,
			String propertyName, String returnMessage[]) throws Exception {
		try {
			WebElement scroll = findElement(UIOperation.getObject(p,
					objectName, propertyName));
			((JavascriptExecutor) driver).executeScript(
					"arguments[0].scrollIntoView(true);", scroll);
			Thread.sleep(500);
			returnMessage[1] = util.getSuccessString();
			} catch (TimeoutException te) {
			returnMessage[1] = "Object " + objectName
					+ " failed to become invisible";
		} catch (NoSuchElementException nsee) {
			returnMessage[1] = "Object "
					+ objectName
					+ " could not be found due to NoSuchElementException";
		} catch (StaleElementReferenceException sere) {
			returnMessage[1] = "Object "
					+ objectName
					+ " could not be found due to StaleElementReferenceException";
		}
		return returnMessage;
	}
	private String[] clickAndHold(Properties p, String objectName,
			String propertyName, String value, String returnMessage[]) throws Exception {
		Actions ch = new Actions(driver);
		try {
			ch.clickAndHold(
					findElement(UIOperation.getObject(p, objectName,
							propertyName))).build().perform();
			int sleep = Integer.parseInt(value);
			if (sleep <= 0) {
				throw new AutomationDriverException(
						"Error:  CLICKANDHOLD duration must be a positive integer value");
			}
			Thread.sleep(sleep);
			ch.release(
					findElement(UIOperation.getObject(p, objectName,
							propertyName))).build().perform();
			returnMessage[1] = util.getSuccessString();
		} catch (NoSuchElementException nsee) {
			returnMessage[1] = "Object "
					+ objectName
					+ " could not be found due to NoSuchElementException";
		} catch (StaleElementReferenceException sere) {
			returnMessage[1] = "Object "
					+ objectName
					+ " could not be found due to StaleElementReferenceException";
		} catch (ElementNotVisibleException enve) {
			returnMessage[1] = "Object "
					+ objectName
					+ " could not be found due to ElementNotVisibleException";
		} catch (MoveTargetOutOfBoundsException mtoobe) {
			returnMessage[1] = "Object "
					+ objectName
					+ " could not be found due to MoveTargetOutOfBoundsException";
		}
		return returnMessage;
	}
	private String[] hover(Properties p, String objectName,
			String propertyName, String returnMessage[]) throws Exception {
		Actions a = new Actions(driver);
		try {
			a.moveToElement(
					findElement(UIOperation.getObject(p, objectName,
							propertyName))).build().perform();
//							propertyName))).perform();
			returnMessage[1] = util.getSuccessString();
		} catch (NoSuchElementException nsee) {
			returnMessage[1] = "Object "
					+ objectName
					+ " could not be found due to NoSuchElementException";
		} catch (StaleElementReferenceException sere) {
			returnMessage[1] = "Object "
					+ objectName
					+ " could not be found due to StaleElementReferenceException";
		} catch (ElementNotVisibleException enve) {
			returnMessage[1] = "Object "
					+ objectName
					+ " could not be found due to ElementNotVisibleException";
		} catch (MoveTargetOutOfBoundsException mtoobe) {
			returnMessage[1] = "Object "
					+ objectName
					+ " could not be found due to MoveTargetOutOfBoundsException";
		} catch (UnsupportedCommandException uce) {
			returnMessage[1] = "HOVER action on object "
					+ objectName
					+ " not supported on this version of Selenium WebDriver";
			// rs hover action not supported by Selenium Webdriver 3.0.1 - see mozilla bug 
			// rs https://bugzilla.mozilla.org/show_bug.cgi?id=1292178
		}
		return returnMessage;
	}
	private String[] switchFrame(Properties p, String objectName,
			String propertyName, String returnMessage[]) throws Exception {
		try {
			if (objectName.length() == 0) {
				driver.switchTo().defaultContent();
			} else {
				System.out.println("Attempting to switch to iFrame " + p.getProperty(objectName));
				driver.switchTo().frame(p.getProperty(objectName));
				System.out.println("Did not error out...hmmm");
			}
			returnMessage[1] = util.getSuccessString();
		} catch (NoSuchFrameException nsee) {
			final List<WebElement> iframes = driver.findElements(By
					.tagName("iframe"));
			if (iframes.isEmpty()) {
				returnMessage[1] = "iFrame "
						+ p.getProperty(objectName)
						+ " could not be found due to NoSuchFrameException";
			}
			for (WebElement iframe : iframes) {
				try {
					System.out.println("In the Catch loop - iframe index = " + iframe);
					driver.switchTo().frame(iframe);
					System.out.println("Switched to iframe index successfully");
					returnMessage[1] = util.getSuccessString();
				} catch (NoSuchFrameException nsee1) {
					returnMessage[1] = "iFrame "
							+ p.getProperty(objectName)
							+ " could not be found due to NoSuchFrameException";
				}
			}
		} catch (StaleElementReferenceException sere) {
			returnMessage[1] = "iFrame "
					+ objectName
					+ " could not be found due to StaleElementReferenceException";
		}
		return returnMessage;
	}
	private String[] listWindows(String returnMessage[]) throws Exception {
		if (setOfOldHandles != null) {
			setOfOldHandles.clear();
		}
		setOfOldHandles = driver.getWindowHandles(); // here we save
														// id's of
														// windows
		System.out.print("Number of windows open: "
				+ setOfOldHandles.size() + " value: ");
		if (!setOfOldHandles.isEmpty()) {
			String newWindowHandle = (String) setOfOldHandles
					.iterator().next(); // here IF we have new window it
										// will shift on it
			System.out.println(newWindowHandle);
		}
		System.out.println();
		returnMessage[1] = util.getSuccessString();
		return returnMessage;
	}
	private String[] listFrames(String returnMessage[]) throws Exception {
		final List<WebElement> iframes = driver.findElements(By
				.tagName("iframe"));
		System.out.println("Number of frames on this page: "
				+ iframes.size());
		for (WebElement iframe : iframes) {
			System.out.println("Page has iFrame " + iframe);
		}
		returnMessage[1] = util.getSuccessString();
		return returnMessage;
	}
}