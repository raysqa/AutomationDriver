package raysullivan.unitTest;

import static org.fest.assertions.Assertions.assertThat;

import java.util.Properties;

import org.openqa.selenium.By;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import raysullivan.operation.AutomationDriverException;
import raysullivan.operation.DriverVariable;
import raysullivan.operation.ReadObject;
import raysullivan.operation.UIOperation;

public class TestUIOperation {

	/**
	 * DriverVariable
	 */
	private DriverVariable var = new DriverVariable();

	@Test(description = "TestValidateAssertVariables", dataProvider = "AssertVariablesValid", enabled = true)
	public void TestValidateAssertVariables(String vName1, String vName2,
			String operation, String result1, String result2) throws AutomationDriverException {
		String getVariable[] = new String[2];
		getVariable = UIOperation.validateAssertVariables(vName1, vName2,
				operation, var);
		assertThat(getVariable[0]).isEqualTo(result1);
		assertThat(getVariable[1]).isEqualTo(result2);
	}

	@Test(description = "TestValidateAssertVariablesInvalid", expectedExceptions = AutomationDriverException.class, dataProvider = "AssertVariablesValidInvalid", enabled = true)
	public void TestValidateAssertVariablesInvalid(String vName1, String vName2,
			String operation, String result) throws AutomationDriverException {
		UIOperation.validateAssertVariables(vName1, vName2,
				operation, var);
	}

	@Test(description = "TestCreateVar", dataProvider = "CreateVarValid", enabled = true)
	public void TestCreateVar(String vName1, String vValue, String result1)
			throws AutomationDriverException {
		String getVariable = null;
		getVariable = UIOperation.createVar(vName1, vValue, var);
		assertThat(getVariable).isEqualTo(result1);
	}

	@Test(description = "TestCreateVarInvalid", expectedExceptions = AutomationDriverException.class, dataProvider = "CreateVarInvalid", enabled = true)
	public void TestCreateVarInvalid(String vName1, String vValue, String result1)
			throws AutomationDriverException {
		UIOperation.createVar(vName1, vValue, var);
	}
	
	@Test(description = "TestValidateVar", dataProvider = "ValidateVarValid", enabled = true)
	public void TestValidateVar(String vName1, String vValue, String operation, String result1, String result2)
			throws AutomationDriverException {
		String getVariable[] = new String[2];
		getVariable = UIOperation.validateVar(vName1, vValue, operation, var);
		assertThat(getVariable[0]).isEqualTo(result1);
		assertThat(getVariable[1]).isEqualTo(result2);
	}
	
	@Test(description = "TestValidateVarInvalid", dataProvider = "ValidateVarInvalid", expectedExceptions = AutomationDriverException.class, enabled = true)
	public void TestValidateVarInvalid(String vName1, String vValue, String operation, String result1, String result2)
			throws AutomationDriverException {
		UIOperation.validateVar(vName1, vValue, operation, var);
	}
	
	@Test(description = "TestGetObject", dataProvider = "GetObjectValid", enabled = true)
	public void TestGetObject(String objectName, String result)
			throws Exception {
		ReadObject object = new ReadObject();
		Properties p = new Properties();
		String propertyName="Test.properties";
		p = object.getObjectRepository(propertyName);
		By getVariable = null;
		getVariable = UIOperation.getObject(p, objectName, propertyName);
		assertThat(getVariable.toString()).isEqualTo(result);
	}
	
		@Test(description = "TestGetObjectInvalid", dataProvider = "GetObjectInvalid", expectedExceptions = AutomationDriverException.class, enabled = true)
	public void TestGetObjectInvalid(String objectName, String result)
			throws Exception {
		ReadObject object = new ReadObject();
		Properties p = new Properties();
		String propertyName="Test.properties";
		p = object.getObjectRepository(propertyName);
		UIOperation.getObject(p, objectName, propertyName);
	}
	/**
	 * AssertVariablesValid data provider
	 * 
	 * @return Object[][]
	 */
	@DataProvider
	public final Object[][] AssertVariablesValid() {
		return new String[][]{
				new String[]{"${vname1}", "${vname2}", "ASSERTEQUALS", "vname1", "vname2"},
				new String[]{"${vname2}", "${vname1}", "ASSERTNOTEQUALS",
						"vname2", "vname1"},};
	}

	/**
	 * AssertVariablesValidInvalid data provider
	 * 
	 * @return Object[][]
	 */
	@DataProvider
	public final Object[][] AssertVariablesValidInvalid() {
		return new String[][]{new String[]{"", "", "ASSERTEQUALS", "true"},
				new String[]{"vname1", "", "ASSERTEQUALS", "true"},
				new String[]{"vname1", "vname2", "ASSERTEQUALS", "true"},
				new String[]{"${vname1}", "vname2", "ASSERTEQUALS", "true"},
				new String[]{"${vname1}", "${vname1}", "ASSERTEQUALS", "true"},
				new String[]{null, null, "ASSERTEQUALS", "true"},
				new String[]{"${vname1}", null, "ASSERTEQUALS", "true"},
				new String[]{"${}", "${vname2}", "ASSERTEQUALS", "true"},
				new String[]{"${vname1}", "${}", "ASSERTEQUALS", "true"},
				new String[]{"${}", "${}", "ASSERTEQUALS", "true"},
				new String[]{"${vname1", "${vname2", "ASSERTEQUALS", "true"},
				new String[]{"${vname1}", "${vname2", "ASSERTEQUALS", "true"},
				new String[]{"{vname}", "{vname2}", "ASSERTEQUALS", "true"},
				new String[]{"${vname1}", "{vname2}", "ASSERTEQUALS", "true"},
				};
	}
	/**
	 * CreateVarValid data provider
	 * 
	 * @return Object[][]
	 */
	@DataProvider
	public final Object[][] CreateVarValid() {
		return new String[][]{
				new String[]{"${vname1}", "value", "true"},
				new String[]{"vname1", "value", "false"},
				new String[]{"", "value", "false"},
						};
	}
	/**
	 * CreateVarinvalid data provider
	 * 
	 * @return Object[][]
	 */
	@DataProvider
	public final Object[][] CreateVarInvalid() {
		return new String[][]{
				new String[]{"${vname1}", "", "true"},
				new String[]{"${vname1}", null, "true"},
						};
	}

	/**
	 * ValidateVarValid data provider
	 * 
	 * @return Object[][]
	 */
	@DataProvider
	public final Object[][] ValidateVarValid() {
		return new String[][]{// value, variable, operation, result1, result2
				new String[]{"NEWSROOM", "${compare1}", "ASSERTTEXT", "false", "NEWSROOM"},
				new String[]{"11a-11p", "", "ASSERTTEXT", "false", "11a-11p"},
				new String[]{"${holiday}", "Holiday", "CREATEVARIABLE", "true", "holiday"},
						};
	}

	/**
	 * ValidateVarinvalid data provider
	 * 
	 * @return Object[][]
	 */
	@DataProvider
	public final Object[][] ValidateVarInvalid() {
		return new String[][]{// value, variable, operation, result1, result2
				new String[]{null, "Holiday", "CREATEVARIABLE", "true", "holiday"},
				new String[]{"${holiday}",null, "CREATEVARIABLE", "true", "holiday"},
				new String[]{"11a-11p", null, "ASSERTTEXT", "false", "11a-11p"},
						};
	}

	/**
	 * GetObjectValid data provider
	 * 
	 * @return Object[][]
	 */
	@DataProvider
	public final Object[][] GetObjectValid() {
		return new String[][]{// 
				new String[]{"mainLogo", "By.cssSelector: img[alt=\"Red Robin Logo\"]"},
				new String[]{"changeLocation", "By.linkText: Change Location"},
				new String[]{"menuLanding", "By.id: pageTitle"},
				new String[]{"locSearchBox", "By.className: changeLocationInput"},
				new String[]{"locFindButton", "By.tagName: findBtn"},
				new String[]{"locationMoreInfo", "By.name: toggleMoreLocationInfo"},
				new String[]{"locationDistance", "By.partialLinkText: div.distance"},
				new String[]{"upperIconInstagram", "By.xpath: //div[3]/div[1]/div[2]/div[2]/ul/li[1]/a"},
						};
	}

	/**
	 * GetObjectInvalid data provider
	 * 
	 * @return Object[][]
	 */
	@DataProvider
	public final Object[][] GetObjectInvalid() {
		return new String[][]{// 
				new String[]{"failedNoBy", ""},
				new String[]{"failedIncorrectType", "By.dork: incorrectType"},
						};
	}
}
