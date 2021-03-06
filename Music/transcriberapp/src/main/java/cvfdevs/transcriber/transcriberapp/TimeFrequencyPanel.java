package cvfdevs.transcriber.transcriberapp;

import javax.swing.BoxLayout;
import javax.swing.JPanel;

import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartPanel;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.plot.PlotOrientation;
import org.jfree.data.xy.DefaultXYZDataset;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class TimeFrequencyPanel extends JPanel {
	/**
	 * 
	 */
	private static final long serialVersionUID = 3262273213097555412L;
	private JFreeChart chart; 
	private ChartPanel chartPanel ;
	private static final Logger log = LoggerFactory.getLogger(TimeFrequencyPanel.class);

	public TimeFrequencyPanel() {
		
	
		
		
		
		DefaultXYZDataset dataset = new DefaultXYZDataset();
		
		double [][] data = new double [3][3];
		for (int i=0; i<3;i++) {
			for (int j=0;j<3;j++) {
				data[i][j]=i+j+1;
			}
		}
        dataset.addSeries("ts", data);
        
        chart= ChartFactory.createBubbleChart(
                "frequencies",
                "time",
                "frequency",
                dataset, 
                PlotOrientation.VERTICAL,
                false,
                true,
                false
                );
        
        chartPanel= new ChartPanel(chart);
        chartPanel.setLayout(new BoxLayout(chartPanel, BoxLayout.Y_AXIS));
       
        this.add(chartPanel);
       // this.setSize(50,50);

	}

	public void updateData(Integer xOffset,Integer xGrain) {
		
		throw new RuntimeException("Not implemented yet");
		
	}
}
