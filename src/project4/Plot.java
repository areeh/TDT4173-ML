package project4;

import java.util.List;

import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartPanel;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.axis.CategoryAxis;
import org.jfree.chart.axis.CategoryLabelPositions;
import org.jfree.chart.axis.NumberAxis;
import org.jfree.chart.axis.ValueAxis;
import org.jfree.chart.plot.CategoryPlot;
import org.jfree.chart.plot.DatasetRenderingOrder;
import org.jfree.chart.plot.PlotOrientation;
import org.jfree.chart.plot.XYPlot;
import org.jfree.chart.renderer.category.BarRenderer;
import org.jfree.chart.renderer.category.CategoryItemRenderer;
import org.jfree.chart.renderer.category.LineAndShapeRenderer;
import org.jfree.chart.renderer.xy.XYBarRenderer;
import org.jfree.chart.renderer.xy.XYItemRenderer;
import org.jfree.chart.renderer.xy.XYLineAndShapeRenderer;
import org.jfree.data.category.DefaultCategoryDataset;
import org.jfree.data.function.Function2D;
import org.jfree.data.function.NormalDistributionFunction2D;
import org.jfree.data.general.DatasetUtilities;
import org.jfree.data.xy.DefaultXYDataset;
import org.jfree.data.xy.XYDataset;
import org.jfree.data.xy.XYSeries;
import org.jfree.data.xy.XYSeriesCollection;
import org.jfree.ui.ApplicationFrame;
import org.jfree.ui.RefineryUtilities;


public class Plot extends ApplicationFrame {

    /**
     * Default constructor.
     *
     * @param title  the frame title.
     */
    public Plot(final String title, double mean1, double mean2, double[] vals) {

        super(title);
        
        double[][] groupedVals = groupVals(vals);

        // create the first dataset...
        XYSeriesCollection histogramData = new XYSeriesCollection();
        XYSeries xy = new XYSeries("Temp");
        for (int i=0; i<groupedVals.length; i++) {
        	xy.add((groupedVals[i][0]+0.25), groupedVals[i][1]);
        }
        histogramData.addSeries(xy);
        
        
        final XYItemRenderer renderer1 = new XYBarRenderer();

        
        final XYPlot plot = new XYPlot();
        plot.setDataset(0, (XYDataset) histogramData);
        plot.setRenderer(0, renderer1);
        
        plot.setDomainAxis(0, new NumberAxis("Value"));
        plot.setRangeAxis(0, new NumberAxis("Frequency"));

        plot.setOrientation(PlotOrientation.VERTICAL);
        plot.setRangeGridlinesVisible(true);
        plot.setDomainGridlinesVisible(true);
        
        // now create the second dataset and renderer...
        Function2D normal1 = new NormalDistributionFunction2D(mean1, 1);
        XYDataset dataset2 = DatasetUtilities.sampleFunction2D(normal1, mean1-4, mean1+4, 100, "Normal");

        final XYItemRenderer renderer2 = new XYLineAndShapeRenderer();
        plot.setDataset(1, dataset2);
        plot.setRenderer(1, renderer2);

        // create the third dataset and renderer...
        final ValueAxis rangeAxis2 = new NumberAxis("Axis 2");
        plot.setRangeAxis(1, rangeAxis2);
        
        Function2D normal2 = new NormalDistributionFunction2D(mean2, 1);
        XYDataset dataset3 = DatasetUtilities.sampleFunction2D(normal2, mean2-4,mean2+4, 100, "Normal2");

        final XYItemRenderer renderer3 = new XYLineAndShapeRenderer();
        plot.setDataset(2, dataset3);
        plot.setRenderer(2, renderer3);
        
        plot.mapDatasetToRangeAxis(2, 1);

        // change the rendering order so the primary dataset appears "behind" the x
        // other datasets...
        plot.setDatasetRenderingOrder(DatasetRenderingOrder.FORWARD);
        
        //plot.getDomainAxis().se(CategoryLabelPositions.UP_45);
        final JFreeChart chart = new JFreeChart(plot);
        chart.setTitle("Overlaid Bar Chart");
      //  chart.setLegend(new StandardLegend());

        // add the chart to a panel...
        final ChartPanel chartPanel = new ChartPanel(chart);
        chartPanel.setPreferredSize(new java.awt.Dimension(500, 270));
        setContentPane(chartPanel);

    }
    
    private double[][] groupVals(double[] vals) {
    	double min;
    	double max;
    	if (vals.length > 0) {
    		min = vals[0];
    		max = vals[0];	
    	} else {
    		return new double[0][0];
    	}
    	for (double val : vals) {
    		if (val < min) {
    			min = val;
    		}
    		if (val > max) {
    			max = val;
    		}
    	}
    	
    	double current = min;
    	double[] ranges = new double[(int)Math.ceil((Math.abs(max-min)/0.5)+1)];
    	
    	for (int i=0; i<ranges.length; i++) {
    		ranges[i] = current;
    		current+=0.5;
    	}
    	
    	double[][] out = new double[ranges.length][2];
    	
    	for (double val: vals) {
    		for (int i=0; i<ranges.length-1; i++) {
    			if (val >= ranges[i] && val < ranges[i+1]) {
    				out[i][0] = ranges[i];
    				out[i][1] +=(1.0/vals.length);
    			}
    		}
    	}
    	
    	return out;
    	
    	
    }

//****************************************************************************
//* JFREECHART DEVELOPER GUIDE                                               *
//* The JFreeChart Developer Guide, written by David Gilbert, is available   *
//* to purchase from Object Refinery Limited:                                *
//*                                                                          *
//* http://www.object-refinery.com/jfreechart/guide.html                     *
//*                                                                          *
//* Sales are used to provide funding for the JFreeChart project - please    * 
//* support us so that we can continue developing free software.             *
//****************************************************************************

/**
* Starting point for the demonstration application.
*
* @param args  ignored.
*/
public static void main(final String[] args) {
	ExpectationMaximization em = new ExpectationMaximization();
	double[] vals = new double[4];
	vals[0] = 0.5;
	vals[1] = 0.5;
	vals[2] = 0.5;
	vals[3] = 0.7;
	
	final Plot plot = new Plot("", 1.2, 2, vals);
	

	
	
	
	
	/*
   final Plot demo = new Plot("XY Series Demo");
	 */
   plot.pack();
   RefineryUtilities.centerFrameOnScreen(plot);
   plot.setVisible(true);
}

}