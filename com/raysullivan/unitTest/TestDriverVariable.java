package raysullivan.unitTest;

import static org.fest.assertions.Assertions.assertThat;

import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import raysullivan.operation.AutomationDriverException;
import raysullivan.operation.AutomationDriverUtil;
import raysullivan.operation.DriverVariable;

public class TestDriverVariable {
	AutomationDriverUtil util = new AutomationDriverUtil();
	String returnString;
	DriverVariable var = new DriverVariable();
	@Test
	public void setVariableAndOverwrite() throws AutomationDriverException {
		String returnString = var.setVariableValue("user.name", "Heidi Mc Natt");
		assertThat(returnString).isEqualTo("Heidi Mc Natt");
		assertThat("Heidi Mc Natt").isEqualTo(var.getVariableValue("user.name"));
		returnString = var.setVariableValue("user.name", "Curious George");
		assertThat(returnString).isEqualTo("Curious George");
		assertThat("Curious George").isEqualTo(var.getVariableValue("user.name"));
	}
	@Test(expectedExceptions = AutomationDriverException.class)
	public void setBlankVariableName() throws AutomationDriverException {
		String returnString = var.setVariableValue("Invalid", "");
		assertThat(returnString).isEqualTo(util.getErrorString());
	}
	@Test(expectedExceptions = AutomationDriverException.class)
	public void setNullVariableName() throws AutomationDriverException {
		String returnString = var.setVariableValue("Invalid", null);
		assertThat(returnString).isEqualTo(util.getErrorString());;
	}

	@Test(expectedExceptions = AutomationDriverException.class)
	public void setBlankVariableValue() throws AutomationDriverException {
		String returnString = var.setVariableValue("", "Invalid");
		assertThat(returnString).isEqualTo(util.getErrorString());
	}
	@Test(expectedExceptions = AutomationDriverException.class)
	public void SetNullVariableValue() throws AutomationDriverException {
		String returnString = var.setVariableValue(null, "Invalid");
		assertThat(returnString).isEqualTo(util.getErrorString());;
	}
	@Test(expectedExceptions = AutomationDriverException.class)
	public void setVariableNameAndValueBlank() throws AutomationDriverException {
		String returnString = var.setVariableValue("", "");
		assertThat(returnString).isEqualTo(util.getErrorString());;
	}
	@Test(expectedExceptions = AutomationDriverException.class)
	public void setVariableNameAndValueNull() throws AutomationDriverException {
		String returnString = var.setVariableValue(null, null);
		assertThat(returnString).isEqualTo(util.getErrorString());;
	}
	@Test
	public void getPreviouslySetVariableValue() throws AutomationDriverException {
		assertThat("Curious George").isEqualTo(var.getVariableValue("user.name"));
	}
	/**
	 * createVariable
	 * 
	 * @param string
	 * @param newString
	 * @param result
	 * @throws AutomationDriverException
	 */
	@Test(description = "createVariable", dataProvider = "characterStrings")
	public final void createVariable(String string, String newString,
			String result) throws AutomationDriverException {
		String[] returnString = var.validateVariableName(string);
		assertThat(returnString[0]).isEqualTo(result);
		assertThat(returnString[1]).isEqualTo(newString);
	}
	/**
	 * createVariableInvalid
	 * 
	 * @param string
	 * @param newString
	 * @param result
	 * @throws AutomationDriverException
	 */
	@Test(description = "createVariableInvalid", dataProvider = "characterStringsInvalid", expectedExceptions = AutomationDriverException.class)
	public final void createVariableInvalid(String string, String newString,
			String result) throws AutomationDriverException {
		var.validateVariableName(string);
	}
	/**
	 * characterStrings data provider
	 * 
	 * @return Object[][]
	 */
	@DataProvider
	public final Object[][] characterStrings() {
		return new String[][]{
				new String[]{"${ValidVariableName}", "ValidVariableName",
						"true"},
				new String[]{"Not a Variable", "Not a Variable", "false"},
				new String[]{"{NotaVariable}", "{NotaVariable}", "false"},
				new String[]{"{NotaVariable", "{NotaVariable", "false"},
				new String[]{"NotaVariable}", "NotaVariable}", "false"},
				new String[]{null, null, "false"},
				new String[]{"a", "a", "false"},
				new String[]{"ab", "ab", "false"},
				new String[]{"abc", "abc", "false"},
				new String[]{"", "", "false"},
				new String[]{"${Valid.Variable}", "Valid.Variable", "true"}};
	}
	/**
	 * characterStrings data provider
	 * 
	 * @return Object[][]
	 */
	@DataProvider
	public final Object[][] characterStringsInvalid() {
		return new String[][]{
				new String[]{"${Invalid VariableName}",
						"${Invalid VariableName}", "Error"},
				new String[]{"${}", "${}", "Error"},
				new String[]{"${InalidVariableName", "${InalidVariableName",
						"Error"}};
	}
}
