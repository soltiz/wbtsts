package cvfdevs.transcriber.transcriberapp;

import java.io.File;

public class SoundFilesHelper {
	final static String soundfilesExtension=ConfigurationHolder.getProperty("soundfiles.extension");
	final static String soundfilesDefaultDir=ConfigurationHolder.getProperty("soundfiles.defaultDirectory");
	static File[] getSoundFiles() {
		File folder = new File(soundfilesDefaultDir);
		return folder.listFiles(new java.io.FileFilter() {
			
			public boolean accept(File pathname) {
				return (pathname.getName().endsWith(soundfilesExtension));
			}
		});
	}
}
