package raysullivan.driver;

import org.testng.annotations.BeforeSuite;
import org.testng.annotations.Test;

import raysullivan.dataProvider.RedRobinRoyaltyDataprovider;
import raysullivan.operation.AutomationDriver;

@Test(dataProvider = "redRobinRoyaltyDataProvider", dataProviderClass = RedRobinRoyaltyDataprovider.class, description = "RedRobinRoyaltyDriver")
public class RedRobinRoyaltyDriver extends AutomationDriver {

	@BeforeSuite
	public void beforeSuite() {
		System.out.println("Beginning Test Suite RedRobinRoyaltyDriver");
	}
}
