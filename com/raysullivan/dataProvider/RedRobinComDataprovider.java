package raysullivan.dataProvider;

import org.testng.annotations.DataProvider;
/**
 * 
 * @author rsullivan
 *
 */
public class RedRobinComDataprovider {

	@DataProvider
	/**
	 * RedRobinCom Data Provider
	 * @return	Object[][] contains Test Driver test cases and parameters
	 */
	public static Object[][] redRobinComDataProvider() {
		/*
		 * P1 - spreadsheet name in resource folder 
		 * P2 - worksheet name in the spreadsheet 
		 * P3 - Test result output spreadsheet
		 * P4 - Browser (Firefox, IE, Firefox, Safari Opera); the first browser
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
				{"RedRobinComTestCases", "LandingPage", "RedRobinComResults",
						"Firefox", "TestProfile", 20, "RedRobinCom_V2.properties",
						1, false, false},
				{"RedRobinComTestCases", "ChangeLocation",
						"RedRobinComResults", "Firefox", "TestProfile", 20,
						"RedRobinComLocation.properties", 0, false, false},
				{"RedRobinComTestCases", "GuestSupport", "RedRobinComResults",
						"Firefox", "TestProfile", 20,
						"RedRobinComGuestSupport.properties", 0, false, false},
				{"RedRobinComTestCases", "Kids", "RedRobinComResults",
						"Firefox", "TestProfile", 20,
						"RedRobinComKids.properties", 0, false, false},
				{"RedRobinComTestCases", "eClub", "RedRobinComResults",
						"Firefox", "TestProfile", 20,
						"RedRobinComeClub.properties",0, false, false},
				{"RedRobinComTestCases", "Newsroom", "RedRobinComResults",
						"Firefox", "TestProfile", 20,
						"RedRobinComNewsroom.properties", 0, false, false},
				{"RedRobinComTestCases", "OurFood", "RedRobinComResults",
						"Firefox", "TestProfile", 20,
						"RedRobinComMenu.properties", 0, false, false},
				{"RedRobinComTestCases", "Customizer", "RedRobinComResults",
						"Firefox", "TestProfile", 20,
						"RedRobinComCustomizer.properties", 0, false, false},
				{"RedRobinComTestCases", "Grocery", "RedRobinComResults",
						"Firefox", "TestProfile", 20,
						"RedRobinComGrocery.properties", 0, false, false},
				{"RedRobinComTestCases", "About", "RedRobinComResults",
						"Firefox", "TestProfile", 20,
						"RedRobinComAbout.properties", 0, false, false},};
	}
}
