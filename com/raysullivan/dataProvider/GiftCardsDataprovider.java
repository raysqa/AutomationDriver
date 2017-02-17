package raysullivan.dataProvider;

import org.testng.annotations.DataProvider;
/**
 * Data Provider template Copy/paste to new data provider and change as needed
 * 
 * @author rsullivan
 *
 */
public class GiftCardsDataprovider {

	@DataProvider
	/**
	 * Fusion Data Provider
	 * @return	Object[][] contains Test Driver test cases and parameters
	 */
	public static Object[][] GiftCardsDataProvider() {
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
				{"GiftCards", "VanillaVisa",
						"GiftCardsResults", "Firefox", "TestProfile", 30,
						"GiftCard.properties", 1, false, false},};
	}
}
