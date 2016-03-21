package project4;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Arrays;
import java.util.List;

import org.jfree.ui.RefineryUtilities;

import project1.PlotGraph;
public class ExpectationMaximization {
	
	int iterations;
	double[] vals;
	double[] expectedVals;
	double[] mean;
	double std;
	double maxIters;
	double logl;
	boolean convergence;

	public ExpectationMaximization() {
		mean = new double[2];
		convergence = false;
		maxIters = 200;
		vals = readData();
		std = 1;
		logl = 0;
		mean[0] = 3;
		mean[1] = -0.5;
	}
	
	public double[] readData() {
		List<String> temp;
		Path file = Paths.get("sample-data.txt");
		double[] out;
		
		try {
			temp = Files.readAllLines(file);
			out = new double[temp.size()];
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			temp = null;
			out = null;
		}
		
		for (int i = 0; i<temp.size(); i++) {
			out[i] = Double.parseDouble(temp.get(i));
		}
		return out;
	}
	
	private double pdfN(double x, double mean) {
		double exponent = -(Math.pow(x-mean, 2)/(2*Math.pow(std, 2)));
		double numerator = Math.pow(Math.E, exponent);
		double denominator = std*Math.sqrt(2*Math.PI);
		
		if (denominator == 0) {
			System.err.println("Denominator 0 in pdfN");
		}
		
		return numerator/denominator;
		
		
	}
	
	private double ezij(int i, int j) {
		double numerator = pdfN(vals[i], mean[j]);
		double denominator = pdfN(vals[i], mean[0]) + pdfN(vals[i], mean[1]);
		
		
		if (denominator == 0) {
			System.err.println("Denominator 0 in ezij");
		}
		
		return numerator/denominator;
		
	}
	
	private void Estep() {
		double[] res = new double[vals.length*2];
		for (int i=0; i<vals.length; i++) {
			res[i] = ezij(i, 0);
			res[i+vals.length] = ezij(i, 1);
			
		}		
		
		expectedVals = res;
	}
	
	private void Mstep() {
		double[] resMean = new double[mean.length];
		double numerator1 = 0;
		double denominator1 = 0;
		double numerator2 = 0;
		double denominator2 = 0;
		for (int i=0; i<vals.length; i++) {
			numerator1 += expectedVals[i]*vals[i];
			denominator1 += expectedVals[i];
			numerator2 += expectedVals[i+vals.length]*vals[i];
			denominator2 += expectedVals[i+vals.length];
			
			if (denominator1 == 0 || denominator2 == 0) {
				System.err.println("Denominator 0 in Mstep");
			}
		}
		resMean[0] = numerator1/denominator1;
		resMean[1] = numerator2/denominator2;
		
		double newLogl = logLikelihood();
		
		if (Math.abs(newLogl-logl) < 0.0001 ) {
			convergence = true;
		} else {
			logl = newLogl;
		}
		
		mean = resMean;

	}
	
	private double logLikelihood() {
		double res = 0;
		for (int i=0; i<vals.length; i++) {
			res+=Math.log(pdfN(vals[i], mean[0])+pdfN(vals[i], mean[1]));
		}
		return res;
	}
	
	public void plotResult() {
		   final Plot demo = new Plot("", mean[0], mean[1], vals);
		   demo.pack();
		   RefineryUtilities.centerFrameOnScreen(demo);
		   demo.setVisible(true);
	}
	
	private void run() {
		int n = 0;
		
		while (n++ < maxIters && !convergence) {
			if (n==5) {
				dispResults(n);
			}
			Estep();
			Mstep();
		}
		dispResults(n);
	}
	
	private void dispResults(int n) {
		plotResult();
		
		System.out.println("Means: ");
		System.out.println(mean[0]);
		System.out.println(mean[1]);
		
		System.out.println("Runs: ");
		System.out.println(n);
	}
	
	
	public static void main(String[] args) {
		ExpectationMaximization test = new ExpectationMaximization();
		test.run();
	}

}
