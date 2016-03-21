package project1;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import com.opencsv.CSVReader;


public class ReadCSV {
	
	public static List<double[]> readTraining() {
		List<String[]> strs = null;
		try {
			CSVReader reader = new CSVReader(new FileReader("data-train.csv"));
			strs = reader.readAll();
			reader.close();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		int l = strs.size();
		List<double[]> out = new ArrayList<double[]>();
		for (int i = 0; i < l; i++) {
			out.add(strToDbl(strs.get(i)));
		}
		return out;
		

	}
	
	public static List<double[]> readTest() {
		CSVReader reader;
		List<String[]> strs = null;
		try {
			reader = new CSVReader(new FileReader("data-test.csv"));
			strs = reader.readAll();
			reader.close();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		int l = strs.size();
		List<double[]> out = new ArrayList<double[]>();
		for (int i = 0; i < l; i++) {
			out.add(strToDbl(strs.get(i)));
		}
		return out;
		
	}
	
	//helpers
	
	private static double[] strToDbl(String[] in) {
		int l = in.length;
		double [] out = new double[l];
		for (int i = 0; i < l; i++) {
			out[i] = Double.parseDouble(in[i]);
		}
		
		return out;
	}

}
