package cvfdevs.transcriber.transcriberapp;

import java.awt.Dimension;
import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JToolBar;

import java.awt.BorderLayout;
import java.io.File;

import javax.swing.ComboBoxModel;
import javax.swing.JComboBox;
import javax.swing.JButton;
import javax.swing.ImageIcon;
import javax.swing.JPanel;
import javax.swing.event.ListDataListener;
import javax.swing.BoxLayout;

public class TranscriberApp {

	private JFrame frame;

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
		
		JPanel mainPanel = new JPanel();
		frame.getContentPane().add(mainPanel);
		mainPanel.setLayout(new BoxLayout(mainPanel, BoxLayout.Y_AXIS));
		
		JToolBar toolBar = new JToolBar();
		mainPanel.add(toolBar);
		
		File[] soundFiles = SoundFilesHelper.getSoundFiles();
		JComboBox<File> soundFileSelector = new JComboBox<File>();
		for (File soundfile:soundFiles) {
			soundFileSelector.addItem(soundfile);
		}
	
		toolBar.add(soundFileSelector);
		
		JButton play = new JButton("");
		play.setIcon(new ImageIcon(TranscriberApp.class.getResource("/icons/arrow-right-3.png")));
		toolBar.add(play);
		
		JPanel timeSpectrumPanel = new TimeFrequencyPanel();
		mainPanel.add(timeSpectrumPanel);
	}

}
