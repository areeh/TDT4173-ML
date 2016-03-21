package project1;

import java.util.ArrayList;
import java.util.List;

import org.jfree.ui.RefineryUtilities;

public class GradientDescent {
	int N;
	double[] W = {0, 0};
	double b;
	double alpha;
	double[] vals;
	List<Double> errors;
	List<double[]> train;
	List<double[]> test;

	public GradientDescent() {
		this.N = 1000;
		this.W[0] = 1;
		this.W[1] = 1;
		this.b = 1;
		this.alpha = 0.95;
		this.train = ReadCSV.readTraining();
		this.test = ReadCSV.readTest();
		errors = new ArrayList<Double>();
	}
	
	public void runGrDsc() {
		iterLoop();

		System.out.println("Final result");
		printLoss(train);
		printLoss(test);
		
		plotResult(errors);
	}
	
	public void iterLoop() {
		double currentError = 100;
		double small = 1E-10;
		int iter = 0;
		boolean conv = false;
		while (iter < N && !conv) {
			double[] temp = lw(this.train);
			W[0] = W[0] - alpha * temp[0];
			W[1] = W[1] - alpha * temp[1];
			b = b - alpha * lb(this.train);
			iter ++;
			
			errors.add(errorRate(test));
			if (currentError - small < errorRate(test)) {
				conv = true;
				System.out.println("Convergence");
			} else {
				currentError = errorRate(test);
			}
			
			
			
			if (iter == 5 || iter == 10) {
				if (iter == 5) {System.out.println("5th iteration");}
				else {System.out.println("10th iteration");}
				System.out.println("Training set");
				printLoss(train);
				System.out.println("Test set");
				printLoss(test);
			}
		}	
	}
	
	public double[] lw(List<double[]> vals) {
		double[] res = {0, 0};
		double temp = 0;
		
		for (double[] ex : vals) {
			temp = (linReg(ex[0], ex[1]) - ex[2]);
			res[0] += temp * ex[0];
			res[1] += temp * ex[1];
		}
		res[0] = (res[0]*2)/vals.size();
		res[1] = (res[1]*2)/vals.size();
		
		return res;
	}
	
	public double lb(List<double[]> vals) {
		double res = 0;
		for (double [] ex : vals) {
			res += (linReg(ex[0], ex[1]) - ex[2]);			
		}
		res = (res*2)/vals.size();
		
		return res;
	}
	
	public double errorRate(List<double[]> vals) {
		double res = 0;
		for (double[] ex: vals) {
			res += Math.pow((linReg(ex[0], ex[1]) - ex[2]), 2);
		}
		return res/vals.size();
	}
	
	public void printLoss(List<double[]> vals) {
		System.out.println("Variables: ");
		System.out.println("W0: " + W[0]);
		System.out.println("W1: " + W[1]);
		System.out.println("b: " + b);
		System.out.println("Error rate: " + errorRate(vals));
	}
	
	public double linReg(double x1, double x2) {
		return (b + W[0]*x1 + W[1]*x2);		
	}
	
	public void plotResult(List<Double> errors) {
		   final PlotGraph demo = new PlotGraph("XY Series Demo");
		   demo.plotDoubles(errors);
		   demo.pack();
		   RefineryUtilities.centerFrameOnScreen(demo);
		   demo.setVisible(true);
	}
	
    public static void main(String[] args) {
		GradientDescent gr = new GradientDescent();
		gr.runGrDsc();
	}
	
	

}
