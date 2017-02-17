package raysullivan.dataProvider;

import org.testng.annotations.DataProvider;
/**
 * 
 * @author rsullivan
 *
 */
public class RedRobinRoyaltyDataprovider {

	@DataProvider
	/**
	 * RedRobinCom Data Provider
	 * @return	Object[][] contains Test Driver test cases and parameters
	 */
	public static Object[][] redRobinRoyaltyDataProvider() {
		/*
		 * P1 - spreadsheet name in resource folder 
		 * P2 - worksheet name in the spreadsheet 
		 * P3 - Test result output spreadsheet
		 * P4 - Browser (Firefox, IE, Chrome, Opera, Safari, Marionette); the first browser
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
				{"RedRobinRoyaltyTestCases", "LandingPageUpper",
						"RedRobinRoyaltyResults", "Firefox", "TestProfile", 20,
						"RedRobinRoyalty.properties", 1, false, false},
				{"RedRobinRoyaltyTestCases", "LandingPageSocial",
						"RedRobinRoyaltyResults", "Firefox", "TestProfile", 20,
						"RedRobinRoyalty.properties", 1, false, false},
				{"RedRobinRoyaltyTestCases", "LandingPageMain",
						"RedRobinRoyaltyResults", "Firefox", "TestProfile", 20,
						"RedRobinRoyalty.properties", 1, false, false},
				{"RedRobinRoyaltyTestCases", "LandingPageBottom",
						"RedRobinRoyaltyResults", "Firefox", "TestProfile", 20,
						"RedRobinRoyalty.properties", 1, false, false},
				{"RedRobinRoyaltyTestCases", "LandingPageRegister",
						"RedRobinRoyaltyResults", "Firefox", "TestProfile", 20,
						"RedRobinRoyalty.properties", 1, false, false},
				{"RedRobinRoyaltyTestCases", "LandingPageSignin",
						"RedRobinRoyaltyResults", "Firefox", "TestProfile", 20,
						"RedRobinRoyalty.properties", 1, false, false},
				{"RedRobinRoyaltyTestCases", "LandingPageAlternative",
						"RedRobinRoyaltyResults", "Firefox", "TestProfile", 20,
						"RedRobinRoyalty.properties", 1, false, false},
				{"RedRobinRoyaltyTestCases", "LandingPageWidgets",
						"RedRobinRoyaltyResults", "Firefox", "TestProfile", 20,
						"RedRobinRoyalty.properties", 1, false, false},
				{"RedRobinRoyaltyTestCases", "Locations",
						"RedRobinRoyaltyResults", "Firefox", "TestProfile", 20,
						"RedRobinRoyalty.properties", 1, false, false},
				{"RedRobinRoyaltyTestCases", "Terms", "RedRobinRoyaltyResults",
						"Firefox", "TestProfile", 20,
						"RedRobinRoyalty.properties", 1, false, false},
				{"RedRobinRoyaltyTestCases", "SignIn",
						"RedRobinRoyaltyResults", "Firefox", "TestProfile", 20,
						"RedRobinRoyaltySignIn.properties", 1, false, false}};
	}
}
