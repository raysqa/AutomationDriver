package raysullivan.driver;

import org.testng.annotations.BeforeSuite;
import org.testng.annotations.Test;

import raysullivan.dataProvider.FusionSPDataprovider;
import raysullivan.operation.AutomationDriver;

@Test(dataProvider = "fusionSPDataProvider", dataProviderClass = FusionSPDataprovider.class, description = "FusionSPDriver")
public class FusionSPDriver extends AutomationDriver{
	@BeforeSuite
	public void beforeSuite() {
		System.out.println("Beginning Test Suite FusionSPDriver");
	}
}
