package cvfdevs.transcriber.transcriberapp;

import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;

import javax.sound.sampled.AudioFormat;
import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.DataLine;
import javax.sound.sampled.SourceDataLine;

public class SoundFilesHelper {
	final static String soundfilesExtension = ConfigurationHolder
			.getProperty("soundfiles.extension");
	final static String soundfilesDefaultDir = ConfigurationHolder
			.getProperty("soundfiles.defaultDirectory");

	static File[] getSoundFiles() {
		File folder = new File(soundfilesDefaultDir);
		return folder.listFiles(new java.io.FileFilter() {

			public boolean accept(File pathname) {
				return (pathname.getName().endsWith(soundfilesExtension));
			}
		});
	}

	public static void playSoundFile(File soundFile) {
		try {
			// Get everything set up for playback.
			// Get the previously-saved data into a byte
			// array object.
			InputStream inputStream = new FileInputStream(soundFile);

			Long dataLength = soundFile.length();

			System.out.println("Replay Array size = " + dataLength);
			// Get an input stream on the byte array
			// containing the data
			AudioFormat audioFormat = getPreferredAudioFormat();
			final AudioInputStream audioInputStream = new AudioInputStream(
					inputStream, audioFormat, dataLength
							/ audioFormat.getFrameSize());
			DataLine.Info dataLineInfo = new DataLine.Info(
					SourceDataLine.class, audioFormat);
			final SourceDataLine sourceDataLine = (SourceDataLine) AudioSystem
					.getLine(dataLineInfo);
			sourceDataLine.open(audioFormat);
			sourceDataLine.start();

			// Create a thread to play back the data and
			// start it running. It will run until
			// all the data has been played back.

			class PlayThread extends Thread {
				byte tempBuffer[] = new byte[10000];

				public void run() {
					try {
						int cnt;
						// Keep looping until the input read method
						// returns -1 for empty stream.
						while ((cnt = audioInputStream.read(tempBuffer, 0,
								tempBuffer.length)) != -1) {
							if (cnt > 0) {
								System.out.println("Read " + cnt + " bytes");
								// Write data to the internal buffer of
								// the data line where it will be
								// delivered to the speaker.
								sourceDataLine.write(tempBuffer, 0, cnt);
							}// end if
						}// end while
							// Block and wait for internal buffer of the
							// data line to empty.
						sourceDataLine.drain();
						sourceDataLine.close();
					} catch (Exception e) {
						System.out.println(e);
						e.printStackTrace(System.out);
						System.exit(0);
					}// end catch
				}// end run
			}// end inner class PlayThread

			Thread playThread = new PlayThread();
			playThread.start();
		} catch (Exception e) {
			System.out.println(e);
			e.printStackTrace(System.out);

			System.exit(0);
		}// end catch
	}// end playAudio

	static public AudioFormat getPreferredAudioFormat() {

		float sampleRate = 16000.0F;
		// 8000,11025,16000,22050,44100
		int sampleSizeInBits = 16;
		// 8,16
		int channels = 1;
		// 1,2
		boolean signed = true;
		// true,false
		boolean bigEndian = false;
		// true,false

		// Vector<AudioFormat> formats =
		// getSupportedFormats(SourceDataLine.class);

		return new AudioFormat(sampleRate, sampleSizeInBits, channels, signed,
				bigEndian);
	}// end getAudioFormat
}
