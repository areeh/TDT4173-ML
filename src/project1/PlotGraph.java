package project1;

import java.util.List;

import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartPanel;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.plot.PlotOrientation;
import org.jfree.data.xy.XYSeries;
import org.jfree.data.xy.XYSeriesCollection;
import org.jfree.ui.ApplicationFrame;
import org.jfree.ui.RefineryUtilities;


public class PlotGraph extends ApplicationFrame {

/**
* A demonstration application showing an XY series containing a null value.
*
* @param title  the frame title.
*/
public PlotGraph(final String title) {
   super(title);

}

public void plotDoubles(List<Double> errors) {
	int iter = 0;
	final XYSeries series = new XYSeries("Data in doubles");
	
	
	for (double val : errors) {
		series.add(iter, val);
		iter ++;
	}
	
	final XYSeriesCollection data = new XYSeriesCollection(series);
	final JFreeChart chart = ChartFactory.createXYLineChart(
			"Error Rate Progression",
			"Iteration",
			"Error Rate",
			data,
			PlotOrientation.VERTICAL,
			true,
			true,
			false
	);
	
	final ChartPanel chartPanel = new ChartPanel(chart);
	chartPanel.setPreferredSize(new java.awt.Dimension(800, 470));
	setContentPane(chartPanel);
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
   final PlotGraph demo = new PlotGraph("XY Series Demo");
   demo.pack();
   RefineryUtilities.centerFrameOnScreen(demo);
   demo.setVisible(true);
}

}