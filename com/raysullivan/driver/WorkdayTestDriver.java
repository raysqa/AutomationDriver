package raysullivan.driver;

import org.testng.annotations.BeforeSuite;
import org.testng.annotations.Test;

import raysullivan.dataProvider.WorkdayTestDataprovider;
import raysullivan.operation.AutomationDriver;

@Test(dataProvider = "workdayTestDataProvider", dataProviderClass = WorkdayTestDataprovider.class, description = "WorkdayTestDriver")
public class WorkdayTestDriver extends AutomationDriver{
	@BeforeSuite
	public void beforeSuite() {
		System.out.println("Beginning Test Suite WorkdayTestDriver");
	}
}
