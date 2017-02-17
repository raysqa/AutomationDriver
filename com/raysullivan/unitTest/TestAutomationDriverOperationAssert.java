package raysullivan.unitTest;

import static org.fest.assertions.Assertions.assertThat;

import java.io.IOException;
import java.util.Properties;

import org.mockito.Mockito;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import raysullivan.operation.AutomationDriverException;
import raysullivan.operation.AutomationDriverUtil;
import raysullivan.operation.DriverVariable;
import raysullivan.operation.UIOperation;

public class TestAutomationDriverOperationAssert {

	/**
	 * Properties
	 */
	private Properties allObjects = Mockito.mock(Properties.class);
	/**
	 * ReadObject
	 */
	private WebDriver webdriver = Mockito.mock(WebDriver.class);
	private WebDriverWait waitVar = Mockito.mock(WebDriverWait.class);
	private AutomationDriverUtil util = new AutomationDriverUtil();
	/**
	 * DriverVariable
	 */
	private DriverVariable var = new DriverVariable();
	/**
	 * UIOperation
	 */
	private UIOperation operation = new UIOperation(webdriver, waitVar);

	String propertyName = "RedRobinCom.properties";

	@Test(description = "ASSERTEQUALTestValid", dataProvider = "ASSERTEQUALvalidCompare", enabled = true)
	public void AssertEqualTestValid(String vName1, String v1, String vValue1,
			String vName2, String v2, String vValue2, String result) throws Exception {
		String[] cell = {"", "ASSERTEQUAL", null, v1, null, v2};
		//System.out.println(cell[0] + ", " + cell[1] + ", " + cell[2] + ", " + cell[3] + ", " + cell[4] + ", " + cell[5]);
		var.setVariableValue(vName1, vValue1);
		var.setVariableValue(vName2, vValue2);
		String returnString[] = new String[3];
		returnString = operation.perform(allObjects, propertyName, cell, var, util);
		assertThat(returnString[1]).isEqualTo(result);
		assertThat(returnString[2]).isEqualTo(vValue1);
		assertThat(returnString[1]).isEqualTo(returnString[2]);
	}

	@Test(description = "ASSERTEQUALTestInvalid", expectedExceptions = AutomationDriverException.class, dataProvider = "ASSERTEQUALinvalidCompare", enabled = true)
	public void AssertEqualTestInvalid(String vName1, String v1, String vValue1,
			String vName2, String v2, String vValue2, String result) throws Exception,
			AutomationDriverException, IOException {
		String[] cell = {"", "ASSERTEQUAL", null, v1, null, v2};
		var.setVariableValue(vName1, vValue1);
		var.setVariableValue(vName2, vValue2);
		String returnString[] = new String[3];
		returnString = operation.perform(allObjects, propertyName, cell, var, util);
		assertThat(returnString[1]).isNotEqualTo(returnString[2]);
	}
	@Test(description = "ASSERTNOTEQUALTestValid", dataProvider = "ASSERTNOTEQUALvalidCompare", enabled = true)
	public void AssertNotEqualTestValid(String vName1, String v1, String vValue1,
			String vName2, String v2, String vValue2, String result) throws Exception {
		String[] cell = {"", "ASSERTNOTEQUAL", null, v1, null, v2};
		var.setVariableValue(vName1, vValue1);
		var.setVariableValue(vName2, vValue2);
		String returnString[] = new String[3];
		returnString = operation.perform(allObjects, propertyName, cell, var, util);
		assertThat(returnString[1]).isEqualTo(result);
		assertThat(returnString[2]).isEqualTo(result);
		assertThat(returnString[1]).isEqualTo(returnString[2]);
	}

	@Test(description = "ASSERTNOTEQUALTestInvalid", expectedExceptions = AutomationDriverException.class, dataProvider = "ASSERTNOTEQUALinvalidCompare", enabled=true)
	public void AssertNotEqualsTestInvalid(String vName1, String v1,
			String vValue1, String vName2, String  v2, String vValue2, String result)
			throws Exception, AutomationDriverException, IOException {
		var.setVariableValue(vName1, vValue1);
		var.setVariableValue(vName2, vValue2);
		String[] cell = {"", "ASSERTNOTEQUAL", null, v1, null, v2};
		String returnString[] = new String[3];
		returnString = operation.perform(allObjects, propertyName, cell, var, util);
		assertThat(returnString[1]).isEqualTo(returnString[2]);
	}
	@Test(description = "ASSERTNOTCONTAINSTestInvalid", expectedExceptions = AutomationDriverException.class, dataProvider = "ASSERTNOTCONTAINSinvalidCompare", enabled=true)
	public void AssertNotContainsTestInvalid(String vName1, String v1,
			String vValue1, String vName2, String  v2, String vValue2, String result)
			throws Exception, AutomationDriverException, IOException {
		var.setVariableValue(vName1, vValue1);
		var.setVariableValue(vName2, vValue2);
		String[] cell = {"", "ASSERTNOTCONTAINS", null, v1, null, v2};
		String returnString[] = new String[3];
		returnString = operation.perform(allObjects, propertyName, cell, var, util);
		assertThat(returnString[1]).isEqualTo(returnString[2]);
	}
	@Test(description = "ASSERTCONTAINSTestValid", dataProvider = "ASSERTCONTAINSvalidCompare", enabled = true)
	public void AssertContainsTestValid(String vName1, String v1, String vValue1,
			String vName2, String v2, String vValue2, String result) throws Exception {
		String[] cell = {"", "ASSERTCONTAINS", null, v1, null, v2};
		var.setVariableValue(vName1, vValue1);
		var.setVariableValue(vName2, vValue2);
		String returnString[] = new String[3];
		returnString = operation.perform(allObjects, propertyName, cell, var, util);
		assertThat(returnString[1]).isEqualTo(result);
		assertThat(returnString[2]).isEqualTo(result);
		assertThat(returnString[1]).isEqualTo(returnString[2]);
	}
	/* 
	String operation = cell[1];
	String objectName = cell[2];
	String value = cell[3];
	String valueType = cell[4];
	String variable = cell[5]; 
	*/
	@Test(description = "ASSERTATTRIBUTETestValid", dataProvider = "ASSERTATTRIBUTEvalidCompare", enabled = false)
	public void AssertAttributeTestValid(String objectName, String value, String valueType, String variableName, String result) throws Exception {
		String[] cell = {"","ASSERTATTRIBUTE", objectName, value, valueType, variableName};
		var.setVariableValue(variableName, value);
		String returnString[] = new String[3];
		returnString = operation.perform(allObjects, propertyName, cell, var, util);
		assertThat(returnString[1]).isEqualTo(result);
		assertThat(returnString[2]).isEqualTo(result);
		assertThat(returnString[1]).isEqualTo(returnString[2]);
			}
	@DataProvider
	public final Object[][] ASSERTATTRIBUTEvalidCompare() {
		return new String[][]{
				new String[]{"upperIconInstagram","http://instagram.com/redrobinburgers","href", 
						"${instagram}","Success"}};
	}
	/**
	 * ASSERTEQUALvalidCompare data provider
	 * 
	 * @return Object[][]
	 */
	@DataProvider
	public final Object[][] ASSERTEQUALvalidCompare() {
		return new String[][]{
				new String[]{"vname1", "${vname1}", "vValue1", "vname2", "${vname2}","vValue1",
						"vValue1"},
				new String[]{"vname2", "${vname2}", "vValue2", "vname1", "${vname1}", "vValue2",
						"vValue2"}};
	}

	/**
	 * ASSERTEQUALinvalidCompare data provider
	 * 
	 * @return Object[][]
	 */
	@DataProvider
	public final Object[][] ASSERTEQUALinvalidCompare() {
		return new String[][]{
				new String[]{null, null, null, null, null, null, ""},
				new String[]{"vname1", null, null, null, null, null, ""},
				new String[]{"vname1", "vname1", null, null, null, null, ""},
				new String[]{"vname1", "vname1", "Value1", null, null, null, ""},
				new String[]{"vname1", "vname1", "Value1", "vname2", null, null, ""},
				new String[]{"vname1", "vname1", "Value1", "vname2", "vname2", null, ""},
				new String[]{"vname1", "vname1", "Value1", "vname2", "vname2", "Value2", "Value1"},
				new String[]{"vname1", "${vname1}", "Value1", "vname2", "vname2", "Value2", "Value1"},
				new String[]{"", "", "", "", "", "", ""},
				new String[]{"vname1", "", "", "", "", "", ""},
				new String[]{"vname1", "vname1", "", "", "", "", ""},
				new String[]{"vname1", "vname1", "Value1", "", "", "", ""},
				new String[]{"vname1", "vname1", "Value1", "vname2", "", "", ""},
				new String[]{"vname1", "vname1", "Value1", "vname2", "vname2", "", ""},
				new String[]{"vname1", "vname1", "Value1", "vname2", "vname2", "Value2", "Value1"},
				new String[]{"vname1", "${vname1}", "Value1", "vname2", "vname2", "Value2", "Value1"},
																				};
	}
	/**
	 * ASSERTNOTEQUALvalidCompare data provider
	 * 
	 * @return Object[][]
	 */
	@DataProvider
	public final Object[][] ASSERTNOTEQUALvalidCompare() {
		return new String[][]{
				new String[]{"vname1", "${vname1}", "errValue1", "vname2", "${vname2}", "errValue2",
						"Success"},
				new String[]{"vname2", "${vname2}", "errValue2", "vname1", "${vname1}", "errValue1",
						"Success"}};
	}

	/**
	 * ASSERTNOTEQUALinvalidCompare data provider
	 * 
	 * @return Object[][]
	 */
	@DataProvider
	public final Object[][] ASSERTNOTEQUALinvalidCompare() {
		return new String[][]{
				new String[]{null, null, null, null, null, null, ""},
				new String[]{"vname1", null, null, null, null, null, ""},
				new String[]{"vname1", "vname1", null, null, null, null, ""},
				new String[]{"vname1", "vname1", "Value1", null, null, null, ""},
				new String[]{"vname1", "vname1", "Value1", "vname2", null, null, ""},
				new String[]{"vname1", "vname1", "Value1", "vname2", "vname2", null, ""},
				new String[]{"vname1", "vname1", "Value1", "vname2", "vname2", "Value2", "Value1"},
				new String[]{"vname1", "${vname1}", "Value1", "vname2", "vname2", "Value2", "Value1"},
				new String[]{"", "", "", "", "", "", ""},
				new String[]{"vname1", "", "", "", "", "", ""},
				new String[]{"vname1", "vname1", "", "", "", "", ""},
				new String[]{"vname1", "vname1", "Value1", "", "", "", ""},
				new String[]{"vname1", "vname1", "Value1", "vname2", "", "", ""},
				new String[]{"vname1", "vname1", "Value1", "vname2", "vname2", "", ""},
				new String[]{"vname1", "vname1", "Value1", "vname2", "vname2", "Value2", "Value1"},
				new String[]{"vname1", "${vname1}", "Value1", "vname2", "vname2", "Value2", "Value1"},
																};
	}
	/**
	 * ASSERTNOTCONTAINSinvalidCompare data provider
	 * 
	 * @return Object[][]
	 */
	@DataProvider
	public final Object[][] ASSERTNOTCONTAINSinvalidCompare() {
		return new String[][]{
				new String[]{null, null, null, null, null, null, ""},
				new String[]{"vname1", null, null, null, null, null, ""},
				new String[]{"vname1", "vname1", null, null, null, null, ""},
				new String[]{"vname1", "vname1", "Value1", null, null, null, ""},
				new String[]{"vname1", "vname1", "Value1", "vname2", null, null, ""},
				new String[]{"vname1", "vname1", "Value1", "vname2", "vname2", null, ""},
				new String[]{"vname1", "vname1", "Value1", "vname2", "vname2", "Value2", "Success"},
				new String[]{"vname1", "${vname1}", "Value1", "vname2", "vname2", "Value2", "Success"},
				new String[]{"", "", "", "", "", "", ""},
				new String[]{"vname1", "", "", "", "", "", ""},
				new String[]{"vname1", "vname1", "", "", "", "", ""},
				new String[]{"vname1", "vname1", "Value1", "", "", "", ""},
				new String[]{"vname1", "vname1", "Value1", "vname2", "", "", ""},
				new String[]{"vname1", "vname1", "Value1", "vname2", "vname2", "", ""},
				new String[]{"vname1", "vname1", "Value1", "vname2", "vname2", "Value2", "Success"},
				new String[]{"vname1", "${vname1}", "Value1", "vname2", "vname2", "Value2", "Success"},
																};
	}
	/**
	 * ASSERTEQUALvalidCompare data provider
	 * 
	 * @return Object[][]
	 */
	@DataProvider
	public final Object[][] ASSERTCONTAINSvalidCompare() {
		return new String[][]{
				new String[]{"vname1", "${vname1}", "vValue1Balloon", "vname2", "${vname2}","vValue1",
						"Success"},
				new String[]{"vname2", "${vname2}", "vValue2House", "vname1", "${vname1}", "2House",
						"Success"}};
	}
}

