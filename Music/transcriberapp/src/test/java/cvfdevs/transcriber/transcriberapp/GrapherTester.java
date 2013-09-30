package cvfdevs.transcriber.transcriberapp;

import java.awt.BorderLayout;
import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JSlider;
import javax.swing.JSpinner;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;

public class GrapherTester {

	private JFrame frame;
	private FreqsGrapherPanel fgp;
	private JLabel lblNewLabel;
	private JSpinner spinner;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					GrapherTester window = new GrapherTester();
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
	public GrapherTester() {
		initialize();
	}

	/**
	 * Initialize the contents of the frame.
	 */
	private void refreshData(Integer xOffset, Integer xGrain) {
		fgp.updateData(xOffset,xGrain);
	
	}

	private void initialize() {
		frame = new JFrame();
		frame.setBounds(100, 100, 450, 300);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		
		fgp=new FreqsGrapherPanel();
		frame.getContentPane().add(fgp);
		
		JPanel panel = new JPanel();
		frame.getContentPane().add(panel, BorderLayout.NORTH);
		
		final JSlider offsetSlider = new JSlider();
		offsetSlider.setMinimum(0);
		offsetSlider.setMaximum(100);

		panel.add(offsetSlider);
		
		lblNewLabel = new JLabel("Offset : ");
		panel.add(lblNewLabel);
		
		spinner = new JSpinner();
		spinner.setValue(10);
	
		panel.add(spinner);
		offsetSlider.addChangeListener(new ChangeListener() {
			
			public void stateChanged(ChangeEvent arg0) {
				refreshData(offsetSlider.getValue(), (Integer) spinner.getValue());
				lblNewLabel.setText("Offset : "+offsetSlider.getValue());
				
			}
		});
		
		
			frame.pack();

		frame.setVisible(true);
	}

}
