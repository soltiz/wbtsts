package cvfdevs.transcriber.transcriberapp;

import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JToolBar;
import java.awt.BorderLayout;
import javax.swing.JComboBox;
import javax.swing.JButton;
import javax.swing.ImageIcon;
import javax.swing.JPanel;

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
		frame.setBounds(100, 100, 450, 300);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		
		JPanel mainPanel = new JPanel();
		frame.getContentPane().add(mainPanel, BorderLayout.SOUTH);
		
		JToolBar toolBar = new JToolBar();
		mainPanel.add(toolBar);
		
		JComboBox<String> soundFile = new JComboBox<String>();
		//TODO : renseiner la liste des fichiers sons
		toolBar.add(soundFile);
		
		JButton play = new JButton("");
		play.setIcon(new ImageIcon(TranscriberApp.class.getResource("/icons/arrow-right-3.png")));
		toolBar.add(play);
		
		JPanel timeSpectrumPanel = new TimeFrequencyPanel();
		mainPanel.add(timeSpectrumPanel);
	}

}
