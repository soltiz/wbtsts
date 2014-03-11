package cvfdevs.transcriber.transcriberapp;

import java.awt.Dimension;
import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JToolBar;

import java.awt.BorderLayout;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.RandomAccessFile;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Arrays;
import java.util.Collection;

import javax.swing.ComboBoxModel;
import javax.swing.JComboBox;
import javax.swing.JButton;
import javax.swing.ImageIcon;
import javax.swing.JPanel;
import javax.swing.event.ListDataListener;
import javax.swing.BoxLayout;

import org.apache.commons.math3.complex.Complex;
import org.jfree.util.Log;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.sun.xml.internal.ws.util.ByteArrayBuffer;

import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;

public class TranscriberApp {

	private JFrame frame;
	private static final Logger log = LoggerFactory.getLogger(TranscriberApp.class);

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					TranscriberApp window = new TranscriberApp();
					window.frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the application.
	 */
	public TranscriberApp() {
		initialize();
	}

	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize() {
		frame = new JFrame();
		frame.setBounds(100, 100, 850, 500);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.getContentPane().setLayout(new BoxLayout(frame.getContentPane(), BoxLayout.PAGE_AXIS));
		final JComboBox<File> soundFileSelector = new JComboBox<File>();
		
		
		File[] soundFiles = SoundFilesHelper.getSoundFiles();
		for (File soundfile:soundFiles) {
			soundFileSelector.addItem(soundfile);
		}
			
			JToolBar toolBar = new JToolBar();
			frame.getContentPane().add(toolBar);
			
			
				toolBar.add(soundFileSelector);
				
				JButton play = new JButton("");
				play.addActionListener(new ActionListener() {
					public void actionPerformed(ActionEvent arg0) {
						SoundFilesHelper.playSoundFile((File)soundFileSelector.getSelectedItem());
					}
				});
				play.setIcon(new ImageIcon(TranscriberApp.class.getResource("/icons/arrow-right-3.png")));
				toolBar.add(play);
				
				JButton analyze = new JButton("");
				analyze.addActionListener(new ActionListener() {
					public void actionPerformed(ActionEvent arg0) {
						File soundFile=(File)soundFileSelector.getSelectedItem();
						log.debug("Starting analysis of sound file '{}'",soundFile);
						RandomAccessFile f;
						try {
							f = new RandomAccessFile(soundFile, "r");
						byte[] b;
							b = new byte[(int)f.length()];
							f.read(b);
							final int ANALYSIS_STEP=10000;
							for (int i=0; i<f.length()-ANALYSIS_STEP;i+=ANALYSIS_STEP){
								byte[] x = Arrays.copyOfRange(b, i, i+ANALYSIS_STEP-1);
								Complex[] fft = SoundAnalysis.bufferFFT(x, ANALYSIS_STEP/2);
								final double minIntensity=50.0;
								final int windowSize=100;
								Collection<FrequencyData> peaks = SoundAnalysis.peakFrequencies(fft, minIntensity, 3, windowSize);
								log.debug("Slice starting at {}...", i);
								for (FrequencyData peak : peaks){
									log.debug("Peak freq = {}",peak.getFrequency());
								}
							}
						} catch (FileNotFoundException e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						
						} catch (IOException e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						}
						
						
					}
				});
				analyze.setIcon(new ImageIcon(TranscriberApp.class.getResource("/icons/audio-x-generic.png")));
				toolBar.add(analyze);
				
				JPanel timeSpectrumPanel = new TimeFrequencyPanel();
				frame.getContentPane().add(timeSpectrumPanel);
	}

}
