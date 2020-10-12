package edu.neu.coe.info6205.union_find;

import java.util.Random;
import java.util.function.Supplier;

import org.jfree.data.xy.XYSeries;

import edu.neu.coe.info6205.util.Benchmark_Timer;

public class UnionFindAlternativesBenchmarking {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		
		//Instantiate Benchmark_Timer that invokes Timer class to perform Benchmark Test
    	Benchmark_Timer<Integer> benchmarkWQU = new Benchmark_Timer<>("Benchmark Test for Union Find Experiment", null, (a) -> countWQU(a), null);
    	Benchmark_Timer<Integer> benchmarkWQUPC = new Benchmark_Timer<>("Benchmark Test for Union Find Experiment", null, (a) -> countWQUPC(a), null);
    	Benchmark_Timer<Integer> benchmarkHWQU = new Benchmark_Timer<>("Benchmark Test for Union Find Experiment", null, (a) -> countHWQU(a), null);
    	Benchmark_Timer<Integer> benchmarkWQUPCR = new Benchmark_Timer<>("Benchmark Test for Union Find Experiment", null, (a) -> countWQUPCR(a), null);
    	
    	//Instantiate series to plot the benchmark results
    	XYSeries wquSeries = new XYSeries("weighted quick union");
    	XYSeries hwquSeries = new XYSeries("height weighted quick union");
    	XYSeries wqupcSeries = new XYSeries("weighted quick union with one pass path compression");
    	XYSeries wqupcrSeries = new XYSeries("weighted quick union with two pass path compression");
    	
    	int runs = 20;			//Number of times the experiment should run for each n
    	int start = 1000;		//Start value of n
    	int end = 1000000;		//Upper bound value of n
    	
    	/* 
    	 * FOR BENCHMARKING
    	 */
    	
    	//Start a loop from start to end, doubling at each value
    	for(int i = start; i < end; i = i*2) {
    		
    		int fI = i; 
    		
    		//Generate supplier to provide the value of n
    		Supplier<Integer> supplier = (() -> fI);
    		
    		//Weighted Quick Union
    		double wqu = benchmarkWQU.runFromSupplier(supplier, runs);
    		wquSeries.add(i, wqu);
    		System.out.println("For nodes: " + i + " and weighted quick union, time taken to connect all nodes is: " + wqu);

    		//Height Weighted Quick Union
    		double hwqu = benchmarkHWQU.runFromSupplier(supplier, runs);
    		hwquSeries.add(i, hwqu);
    		System.out.println("For nodes: " + i + " and height weighted quick union, time taken to connect all nodes is: " + hwqu);
    		
    		//Weighted Quick Union with One Pass Path Compression (Grandparent fix)
    		double wqupc = benchmarkWQUPC.runFromSupplier(supplier, runs);
    		wqupcSeries.add(i, wqupc);
    		System.out.println("For nodes: " + i + " and weighted quick union with one pass path compression, time taken to connect all nodes is: " + wqupc);
    		
    		//Weighted Quick Union with Two Pass Path Compression (Connect intermediate node to root)
    		double wqupcr = benchmarkWQUPCR.runFromSupplier(supplier, runs);
    		wqupcrSeries.add(i, wqupcr);
    		System.out.println("For nodes: " + i + " and weighted quick union with two pass path compression, time taken to connect all nodes is: " + wqupcr);
    		
    		System.out.println("\n" + "..............................................................................................................." + "\n");	
    	}

    	/* 
    	 * FOR GETTING DEPTH 
    	 */
    	
    	//Start a loop from start to end, doubling at each value
    	for(int i = start; i < end; i = i*2) {
    		
    		System.out.println("\n" + "..............................................................................................................." + "\n");
    		int wqu = 0;
    		int hwqu = 0;
    		int wqupc = 0;
    		int wqupcr = 0; 
    		
    		for(int j = 0; j < 1; j++) {
    			countWQU(i);
    			wqu += ufClient.getMaximumDepth();
    			countHWQU(i);
    			hwqu += ufClientHeight.getMaximumDepth();
    			countWQUPC(i);
    			wqupc += ufClient.getMaximumDepth();
    			countWQUPCR(i);
    			wqupcr += ufClient.getMaximumDepth();
    		}
    		
    		System.out.println("For nodes: " + i + " and weighted quick union, the average depth is: " + wqu);
    		System.out.println("For nodes: " + i + " and height weighted quick union, the average depth is: " + hwqu);
    		System.out.println("For nodes: " + i + " and weighted quick union with one pass path compression, the average depth is: " + wqupc);
    		System.out.println("For nodes: " + i + " and weighted quick union wuth two pass path compression, the average depth is: " + wqupcr);	
    	}
    	
    	//Plot the results
    	UFClientPlotter plotter = new UFClientPlotter(wquSeries, hwquSeries, wqupcSeries, wqupcrSeries);
        plotter.setVisible(true);

	}
	
	/**
	 * Method to conduct union find experiment using weighted quick union find and 
	 * 
	 * @param n Number of sites
	 */
	public static void countWQU(int n) {
		ufClient = new WeightedQuickUnion(n, false);
		Random random = new Random();
		while(ufClient.count()!=1) {
			int p = random.nextInt(n);
			int q = random.nextInt(n);
			if(!ufClient.connected(p, q)) {
				ufClient.union(p, q);
			}
		}	
	}
	
	/**
	 * Method to conduct union find experiment using weighted quick union find with
	 * one pass path compression 
	 * 
	 * @param n Number of sites
	 */
	public static void countWQUPC(int n) {
		ufClient = new WeightedQuickUnion(n, true, 2);
		Random random = new Random();
		while(ufClient.count()!=1) {
			int p = random.nextInt(n);
			int q = random.nextInt(n);
			if(!ufClient.connected(p, q)) {
				ufClient.union(p, q);
			}
		}
	}
	
	/**
	 * Method to conduct union find experiment using height weighted quick union find 
	 * 
	 * @param n Number of sites
	 */
	public static void countHWQU(int n) {
		ufClientHeight = new UF_HWQUPC(n, false);
		Random random = new Random();
		while(ufClientHeight.components()!=1) {
			int p = random.nextInt(n);
			int q = random.nextInt(n);
			if(!ufClientHeight.connected(p, q)) {
				ufClientHeight.union(p, q);
			}
		}
	}
	
	/**
	 * Method to conduct union find experiment using weighted quick union find with 
	 * two pass path compression 
	 * 
	 * @param n Number of sites
	 */
	public static void countWQUPCR(int n) {
		ufClient = new WeightedQuickUnion(n, true, 1);
		Random random = new Random();
		while(ufClient.count()!=1) {
			int p = random.nextInt(n);
			int q = random.nextInt(n);
			if(!ufClient.connected(p, q)) {
				ufClient.union(p, q);
			}
		}
	}
	
	static private WeightedQuickUnion ufClient;
	static private UF_HWQUPC ufClientHeight;	
}
