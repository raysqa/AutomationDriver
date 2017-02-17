package raysullivan.dataProvider;

import org.testng.annotations.DataProvider;
/**
 * Data Provider template Copy/paste to new data provider and change as needed
 * 
 * @author rsullivan
 *
 */
public class FusionSPDataprovider {

	@DataProvider
	/**
	 * Fusion Data Provider
	 * @return	Object[][] contains Test Driver test cases and parameters
	 */
	public static Object[][] fusionSPDataProvider() {
		/*
		 * P1 - spreadsheet name in resource folder 
		 * P2 - worksheet name in the spreadsheet 
		 * P3 - Test result output spreadsheet
		 * P4 - Browser (Firefox, IE, Chrome); the first browser
		 * 		in any data test case will be used for all
		 * P5 - Firefox Profile name (must exist on machine
		 * 		executing the test); default = default 
		 * P6 - default waitFor in seconds
		 * P7 - Application specific object.properties file
		 * P8 - Number of times to execute sheet; 0=skip
		 * P9 - Output to .csv (false=no, true=yes)
		 * P10 - Output to video (false=no, true=yes)
		 */
		return new Object[][]{
				{"FusionSupplierPortalTestCases", "SignInSignOut",
						"FusionSPTestResult", "Firefox", "TestProfile", 30,
						"FusionSupplierPortal.properties", 0, false, false},
				{"FusionSupplierPortalTestCases", "ValidateInvoices",
						"FusionSPTestResult", "Firefox", "TestProfile", 30,
						"FusionSupplierPortal.properties", 0, false, false},
				{"FusionSupplierPortalTestCases", "AmIGettingPaid",
						"FusionSPTestResult", "Firefox", "TestProfile", 30,
						"FusionSupplierPortal.properties", 1, false, false},
				{"FusionSupplierPortalTestCases", "AnyHope",
						"FusionSPTestResult", "Firefox", "TestProfile", 30,
						"FusionSupplierPortal.properties", 1, false, false},
				{"FusionSupplierPortalTestCases", "ChangePassword",
						"FusionSPTestResult", "Firefox", "TestProfile", 30,
						"FusionPasswordChange.properties", 1, false, false},};
	}
}
