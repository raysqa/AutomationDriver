package raysullivan.driver;

import org.testng.annotations.BeforeSuite;
import org.testng.annotations.Test;

import raysullivan.dataProvider.FusionSuiteDataprovider;
import raysullivan.operation.AutomationDriver;

@Test(dataProvider = "FusionApDataProvider", dataProviderClass = FusionSuiteDataprovider.class, description = "FusionAPDriver")
public class FusionAPDriver extends AutomationDriver{
	@BeforeSuite
	public void beforeSuite() {
		System.out.println("Beginning Test Suite FusionAPDriver");
	}
}
