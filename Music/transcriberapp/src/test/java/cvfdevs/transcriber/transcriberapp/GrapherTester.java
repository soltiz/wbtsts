package cvfdevs.transcriber.transcriberapp;

import java.awt.EventQueue;
import java.lang.Math;
import java.awt.GraphicsConfiguration;
import java.awt.GraphicsDevice;
import java.awt.GraphicsEnvironment;

import javax.swing.JFrame;

import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartPanel;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.plot.PlotOrientation;
import org.jfree.data.category.DefaultCategoryDataset;
import org.jfree.data.time.TimeSeries;
import org.jfree.data.time.TimeSeriesCollection;
import org.jfree.data.xy.XYSeries;
import org.jfree.data.xy.XYSeriesCollection;

import javax.swing.JPanel;

import java.awt.BorderLayout;

import javax.swing.JSlider;

import java.awt.event.InputMethodListener;
import java.awt.event.InputMethodEvent;

import javax.swing.JLabel;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;

public class GrapherTester {

	private JFrame frame;
	private FreqsGrapherPanel fgp;
	private JLabel lblNewLabel;

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
	private void refreshData(Integer xOffset) {
		fgp.updateData(xOffset);
	
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
		offsetSlider.addChangeListener(new ChangeListener() {
			
			@Override
			public void stateChanged(ChangeEvent arg0) {
				refreshData(offsetSlider.getValue());
				lblNewLabel.setText("Offset : "+offsetSlider.getValue());
				
			}
		});
		offsetSlider.addInputMethodListener(new InputMethodListener() {
			public void caretPositionChanged(InputMethodEvent arg0) {
				refreshData(offsetSlider.getValue());
				lblNewLabel.setText("Offset : "+offsetSlider.getValue());
			}
			public void inputMethodTextChanged(InputMethodEvent arg0) {
			}
		});
		
			frame.pack();

		frame.setVisible(true);
	}

}
