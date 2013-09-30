package cvfdevs.transcriber.transcriberapp;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class ConfigurationHolder {
	private final static String CONFIGURATION_FILE ="transcriber.properties";
	private static final Logger log = LoggerFactory.getLogger(ConfigurationHolder.class);
	private static Properties properties;
	private static Properties getProperties() {
		if (properties==null) {
			Properties tempProp = new Properties();
			InputStream propsFile;
			try {
				propsFile = ClassLoader.getSystemResourceAsStream(CONFIGURATION_FILE);
				//propsFile = new FileInputStream(CONFIGURATION_FILE);
				tempProp.load(propsFile);
				properties=tempProp;
			} catch (FileNotFoundException e) {
				log.error("Configuration file '{}' not found. Exception was :{}",CONFIGURATION_FILE,e);
				e.printStackTrace();
			} catch (IOException e) {
				log.error("Configuration file '{}' not decoded. Exception was :{}",CONFIGURATION_FILE,e);
				e.printStackTrace();
			}

		}
	    return properties;
	}
	
	public static String getProperty(final String key, final String defaultValue){
		final String value=getProperties().getProperty(key, defaultValue);
		log.debug("Property '{}'='{}'.",key,value);
		return value;
	}
	
	public static String getProperty(final String key){
		final String value=getProperties().getProperty(key);
		if (value==null) {
			log.error("Property '{}' has no provided value in file '{}'.",key,CONFIGURATION_FILE);
			throw new NullPointerException("Property not found.");
		}
		log.debug("Read property '{}'='{}'.",key,value);
		return value;
	}
}
