package raysullivan.operation;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

import raysullivan.operation.AutomationDriverUtil;
/**
 * ReadObject reads the application specific page object repository
 * 
 * @author rsullivan
 *
 */
public class ReadObject {
	private Properties p = new Properties();
	AutomationDriverUtil tutil = new raysullivan.operation.AutomationDriverUtil();
	/**
	 * getObjectRepository
	 * 
	 * @param propertyName
	 *            name of the application specific object repository file
	 * @return Properties object containing the application object repository
	 * @throws IOException
	 * @throws AutomationDriverException
	 */
	public Properties getObjectRepository(String propertyName)
			throws IOException, AutomationDriverException {
		// Read object repository file
		InputStream stream;
		try {
			stream = new FileInputStream(new File(tutil.getTestPropertyPath()
					+ propertyName));
		} catch (FileNotFoundException e) {
			throw new AutomationDriverException(
					"Error:  Cannot find test property file " + propertyName
							+ " at location " + tutil.getTestPropertyPath());
		}
		// load all objects
		p.load(stream);
		return p;
	}

}
