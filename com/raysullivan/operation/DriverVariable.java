package raysullivan.operation;

import java.util.ArrayList;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
/**
 * DriverVariable
 * 
 * @author rsullivan
 *
 */
public class DriverVariable {
	private ArrayList<String> vNameList = new ArrayList<String>();
	private ArrayList<String> vValueList = new ArrayList<String>();
	private AutomationDriverUtil util = new AutomationDriverUtil();
	private int index;

	public DriverVariable() {
	}
	/**
	 * validateVariableName validates and returns the usable variable name
	 * 
	 * @param string
	 * @return returnString[]
	 */
	public String[] validateVariableName(String string)
			throws AutomationDriverException {
		// Set up matching pattern
		String vDs = "${", vDe = "}";
		// Initialize the return string to appear as a normal string
		String[] returnString = {"false", string};
		String variableString = null;
		try {
			// determine if the passed string is a variable name or a normal
			// string
			variableString = string.substring(0, 2);
			// catch null or string less than length of two value passed
		} catch (NullPointerException | StringIndexOutOfBoundsException e) {
			return returnString;
		}
		// set matching pattern to validate whitespace
		Pattern pattern = Pattern.compile("\\s");
		// If this is a variable name
		if (variableString.equals(vDs)) {
			// set the return type to Variable
			returnString[0] = "true";
			// Try will only work if the complete matching pattern is present
			try {
				// create the start of the index substring
				int sx = string.indexOf(vDs) + 2;
				// create the end of the substring
				int ex = string.indexOf(vDe);
				// create the new variable name
				String newString = string.substring(sx, ex);
				// check to see if there is whitespace in the variable name
				CharSequence s = newString;
				Matcher matcher = pattern.matcher(s);
				boolean found = matcher.find();
				// if whitespace or the value is blank, error
				if (found || newString.length() <= 1) {
					throw new AutomationDriverException("Variable " + string
							+ " cannot contain whitespace");
				} else {
					// return the new variable
					returnString[1] = newString;
				}
				// catch will cover missing end delimiters from vDe
			} catch (StringIndexOutOfBoundsException s) {
				throw new AutomationDriverException("Variable " + string
						+ " does not have a valid ending delimiter");
			}
		}
		return returnString;
	}
	/**
	 * setVariableValue
	 * 
	 * @param vName
	 * @param vValue
	 * @return returnString
	 * @throws AutomationDriverException
	 */
	public String setVariableValue(String vName, String vValue)
			throws AutomationDriverException {
		//System.out.println("\t\tSetting variable " + vName + " to value "
		//		+ vValue);
		String returnString = util.getErrorString();
		int valueLen, variableLen;
		try {
			valueLen = vValue.length();
		} catch (NullPointerException e) {
			throw new AutomationDriverException("Error: variable ${" + vName
					+ "} cannot be null");
		}
		try {
			variableLen = vName.length();
		} catch (NullPointerException e) {
			throw new AutomationDriverException(
					"Error: variable name cannot be null");
		}
		if (valueLen == 0) {
			throw new AutomationDriverException("Error: variable ${" + vName
					+ "} cannot contain a value of \"" + vValue + "\".");
		} else if (variableLen == 0) {
			throw new AutomationDriverException("Error: variable ${" + vName
					+ "} not a valid variable name.");
		} else
			returnString = vValue;
		if (vNameList.contains(vName)) {
			index = vNameList.indexOf(vName);
			vValueList.set(index, vValue);
		} else {
			vNameList.add(vName);
			vValueList.add(vValue);
		}
		return returnString;
	}
	/**
	 * 
	 * @param vName
	 * @return vValue
	 * @throws AutomationDriverException
	 */
	public String getVariableValue(String vName)
			throws AutomationDriverException {
		String vValue = util.getErrorString();
		int index;
		if (vNameList.contains(vName)) {
			index = vNameList.indexOf(vName);
			vValue = vValueList.get(index);
			//System.out.println("\t\tGetting variable " + vName + " value "
			//		+ vValue);
		} else {
			throw new AutomationDriverException("Error: variable ${" + vName
					+ "} not declared.");
		}
		return vValue;
	}
}
