package cvfdevs.transcriber.transcriberapp;

import static org.junit.Assert.*;

import java.io.File;

import org.junit.Test;

import cvfdevs.common.tests.toolstests.CvfTests;

public class SoundHelperTests extends CvfTests{
	
	@Test
	public void listSoundfilesTest() {
		File [] soundfiles=SoundFilesHelper.getSoundFiles();
		assertTrue(soundfiles.length>0);
		log.debug("Sound files : {}",soundfiles);
	}
}
