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

public class GrapherTester {

	private JFrame frame;

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
	private void initialize() {
		frame = new JFrame();
		frame.setBounds(100, 100, 450, 300);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		
		
			final XYSeries dataset1 = new XYSeries("cos");
			final XYSeries dataset2 = new XYSeries("sin");

			for (int i = 0; i <= 2000; i++) {
				dataset1.add(i,Math.cos(Math.PI*i/100)*800);
				dataset2.add(i,Math.sin(Math.PI*i/100)*800);
			}
			
			

	        XYSeriesCollection dataset = new XYSeriesCollection();
	        dataset.addSeries(dataset1);
	        dataset.addSeries(dataset2);

	        JFreeChart chart = ChartFactory.createXYLineChart(
	                "Spectre",
	                "frequence",
	                "intensite",
	                dataset, 
	                PlotOrientation.VERTICAL,
	                true,
	                true,
	                false
	                );
	        ChartPanel chartPanel = new ChartPanel(chart);


	        
			
			
			frame.getContentPane().add(chartPanel);


			frame.pack();

		frame.setVisible(true);
	}

}
