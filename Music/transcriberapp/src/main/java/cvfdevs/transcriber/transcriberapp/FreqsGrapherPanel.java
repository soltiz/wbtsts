package cvfdevs.transcriber.transcriberapp;

import javax.swing.JPanel;

import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartPanel;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.plot.PlotOrientation;
import org.jfree.data.xy.XYSeries;
import org.jfree.data.xy.XYSeriesCollection;

public class FreqsGrapherPanel extends JPanel {
	/**
	 * 
	 */
	private static final long serialVersionUID = 3262273213097555412L;
	private JFreeChart chart; 
	private ChartPanel chartPanel ;
	final XYSeries dataset1 = new XYSeries("cos");
	final XYSeries dataset2 = new XYSeries("sin");

	public FreqsGrapherPanel() {
		
	
		for (int i = 0; i <= 2000; i++) {
			dataset1.add(i,Math.cos(Math.PI*i/100)*800);
			dataset2.add(i,Math.sin(Math.PI*i/100)*800);
		}
		
		

        XYSeriesCollection dataset = new XYSeriesCollection();
        dataset.addSeries(dataset1);
        dataset.addSeries(dataset2);

        chart= ChartFactory.createXYLineChart(
                "Spectre",
                "frequence",
                "intensite",
                dataset, 
                PlotOrientation.VERTICAL,
                true,
                true,
                false
                );
        
        chartPanel= new ChartPanel(chart);
        
        this.add(chartPanel);
        

	}

	public void updateData(Integer xOffset,Integer xGrain) {
		
		dataset1.clear();
		dataset2.clear();
		

		for (int i = 0; i <= 2000; i+=xGrain) {
			dataset1.add(i,Math.cos(Math.PI*(i-xOffset)/100)*800);
			dataset2.add(i,Math.sin(Math.PI*(i-xOffset)/100)*800);
		}
	
           chartPanel.firePropertyChange("a",true,true);
           chartPanel.repaint();
           
		
	}
}
