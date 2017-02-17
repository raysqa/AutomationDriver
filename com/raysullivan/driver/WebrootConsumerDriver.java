package raysullivan.driver;

import org.testng.annotations.BeforeSuite;
import org.testng.annotations.Test;

import raysullivan.dataProvider.WebrootConsumerDataprovider;
import raysullivan.operation.AutomationDriver;

@Test(dataProvider = "webrootConsumerDataProvider", dataProviderClass = WebrootConsumerDataprovider.class, description = "WebrootConsumerDriver")
public class WebrootConsumerDriver extends AutomationDriver {

	@BeforeSuite
	public void beforeSuite() {
		System.out.println("Beginning Test Suite WebrootConsumerDriver");
	}
}
