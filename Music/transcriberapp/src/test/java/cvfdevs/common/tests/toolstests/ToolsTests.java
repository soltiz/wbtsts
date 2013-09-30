package cvfdevs.common.tests.toolstests;

import static org.junit.Assert.*;

import org.junit.Test;

import cvfdevs.transcriber.transcriberapp.ConfigurationHolder;

public class ToolsTests {
	@Test
	public void configurationTest() {
		String value=ConfigurationHolder.getProperty("soundfiles.extension");
		assertEquals(".snd",value);
	}
	@Test(expected=NullPointerException.class)
	public void missingConfigurationTest() {
		ConfigurationHolder.getProperty("blurp");
	}
}
