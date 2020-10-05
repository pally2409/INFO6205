package edu.neu.coe.info6205.union_find;
import java.util.Random;
import java.util.*;
import java.util.stream.*;

import org.jfree.data.xy.XYSeries;

import edu.neu.coe.info6205.randomwalk.RandomWalkPlotter;

/** 
 * @author pallaksingh
 * 
 * Union Find client for conducting Height Weighted Quick Union with Path Compression Experiment
 */
public class UFClient {
	
	public static void main(String[] args) {
		// TODO Auto-generated method stub

		int runs = 200; 											//Number of times the count() should be called for a value of n
		double coefficient = 0;										//Variable for averaging the value of m/(log(n)*n
		int count = 0;												//Number of experiments conducted
		XYSeries mn = new XYSeries("n vs m");						//Series to hold x,y values for n and m
		XYSeries mnexpected = new XYSeries("n * log(n) * 0.53");    //Series to hold x,y values for n and n*log(n)*0.53
		
		//Run a loop for n = 100 to n = 40000 incrementing by 500 after each step
		for(int i = 100; i < 10000; i= i+500) {
			
			int sum = 0;			//Variable for holding the cumulative sum of generated pairs from count(n) function
			for(int j = 0; j < runs; j++) {
				int m = count(i);
				sum+=m;
			}
			
			count++;
			int avg = sum/runs;		//Average value of pairs generated (m) over the number of runs
			mn.add(i, avg);
			double logFactor = Math.log(i) * i;	
			coefficient += avg/logFactor;
			mnexpected.add(i, logFactor*0.53);
			System.out.println("For n: " + i + " the number of generated pairs are: " + avg + " for " + runs + " runs");
			System.out.println("Coefficient for n: " + i + " and m = " + avg + " is: " + (avg/logFactor) + "\n");
		}
		
		System.out.println("Average value of the coefficient (m/n*log(n)) is: " + (coefficient/count));

		//Plot the results
    	UFClientPlotter plotter = new UFClientPlotter(mn, mnexpected);
        plotter.setVisible(true);
	}
	
	/**
	 * Method to conduct union find experiment using height weighted quick union find and 
	 * return the number of connections generated
	 * 
	 * @param n Number of sites
	 * @return Number of connections generated to go from n components to 1
	 */
	public static int count(int n) {
		UF_HWQUPC ufClient = new UF_HWQUPC(n, false);
		int connections = 0;
		Random random = new Random();
		while(ufClient.components()!=1) {
			int p = random.nextInt(n);
			int q = random.nextInt(n);
			connections++;
			if(!ufClient.connected(p, q)) {
				ufClient.union(p, q);
			}
		}
		return connections;
	}
}
