package raysullivan.driver;

import org.testng.annotations.BeforeSuite;
import org.testng.annotations.Test;

import raysullivan.dataProvider.GiftCardsDataprovider;
import raysullivan.operation.AutomationDriver;

@Test(dataProvider = "GiftCardsDataProvider", dataProviderClass = GiftCardsDataprovider.class, description = "GiftCardsDriver")
public class GiftCardsDriver extends AutomationDriver{
	@BeforeSuite
	public void beforeSuite() {
		System.out.println("Beginning Test Suite GiftCardsDriver");
	}
}
