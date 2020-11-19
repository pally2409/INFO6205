package edu.neu.coe.info6205.sort.par;

import java.io.BufferedWriter;
import java.util.stream.*;

import org.jfree.data.xy.XYSeries;

import edu.neu.coe.info6205.sort.simple.MergeSortBasic;

import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.Random;
import java.util.concurrent.ForkJoinPool;
import java.util.Arrays;

/**
 * This code has been fleshed out by Ziyao Qiao. Thanks very much.
 * TODO tidy it up a bit.
 */
public class Main {

    public static void main(String[] args) {
        processArgs(args);
        
//        experimentWithFixedThreadSizeVaryingCutoffs(16, 50, 100000, 1000000, 10000, 2000000);
//        experimentWithVaryingThreadSizeFixedCutoffs(50, 1000, 200000, 5);
        
        System.out.println("Degree of parallelism: " + ForkJoinPool.getCommonPoolParallelism());
        Random random = new Random();
        int[] array = new int[2000000];
        
        //Lists to contain results for thread pool sizes 1 through 16
        ArrayList<Long> oneThreadList = new ArrayList<>();
        ArrayList<Long> twoThreadList = new ArrayList<>();
        ArrayList<Long> fourThreadList = new ArrayList<>();
        ArrayList<Long> eightThreadList = new ArrayList<>();
        ArrayList<Long> sixteenThreadList = new ArrayList<>();
        
        XYSeries timeSeries;	//Time series to hold the time and cutoff for plotting
        int runs = 50;			//Number of runs for the experiment to run for each cutoff 		
        ParSort.myPool  = new ForkJoinPool(1);
        Plotter plotter = new Plotter("Cutoff vs Time for Array Size = 2000000", "Cutoff", "Time");
        
        //Run the loop for thread sizes 1 through 16 increasing by a factor of 2 each time
        for(int k = 1; k < 17; k = k*2) {
        	ParSort.myPool  = new ForkJoinPool(k);
        	timeSeries = new XYSeries(k + "Threads");
        	int cumulativeTime = 0;
        	
        	//Variables to hold and maximum, minimum time and their respective cutoffs
        	long minimumTime = Integer.MAX_VALUE;
        	int minimumTimeCutoff = Integer.MAX_VALUE;
        	long maximumTime = Integer.MIN_VALUE;
        	int maximumTimeCutoff = Integer.MIN_VALUE;
        	
	        for (int j = 50; j < 100; j++) {
	            ParSort.cutoff = 10000 * (j + 1);
	            long time;
	            
	            //Start the timer
	            long startTime = System.currentTimeMillis();
	            for (int t = 0; t < runs; t++) {
	                for (int i = 0; i < array.length; i++) array[i] = random.nextInt(10000000);
	                ParSort.sort(array, 0, array.length);
	            }
	            
	            //End the timer
	            long endTime = System.currentTimeMillis();
	            
	            //Calculate time elapsed
	            time = (endTime - startTime);
	            
	            //Check whether the average time is the current maximum and minimum
	            if(time/runs > maximumTime) {
	            	maximumTime = time/runs;
	            	maximumTimeCutoff = ParSort.cutoff;
	            }
	            
	            if(time/runs < minimumTime) {
	            	minimumTime = time/runs;
	            	minimumTimeCutoff = ParSort.cutoff;
	            }
	            
	            //Add the results to respective lists 
	            if(k == 1) oneThreadList.add(time);
	            else if(k == 2) twoThreadList.add(time);
	            else if(k == 4) fourThreadList.add(time);
	            else if(k == 8) eightThreadList.add(time);
	            else if(k == 16) sixteenThreadList.add(time);
	            System.out.println("cutoff and threads = " + k + ": "  + (ParSort.cutoff) + "\t\t " + runs + " times Time:" + time + "ms");
	            timeSeries.add(10000 * (j + 1), time/runs);
	            cumulativeTime += time/runs;
	        }
	       
	        System.out.println("Average time taken over all cutoffs for thread count: " + k + " = " + cumulativeTime/50);
	        System.out.println("Minimum time taken over all cutoffs for thread count: " + k + " = " + minimumTime + " at cutoff: " + minimumTimeCutoff);
	        System.out.println("Maximum time taken over all cutoffs for thread count: " + k + " = " + maximumTime + " at cutoff: " + maximumTimeCutoff);
	        plotter.addSeries(timeSeries);
        }
        
        plotter.initUI();
        plotter.setVisible(true);
        
        //Store the results in a csv file
        try {
            FileOutputStream fis = new FileOutputStream("./src/result.csv");
            OutputStreamWriter isr = new OutputStreamWriter(fis);
            BufferedWriter bw = new BufferedWriter(isr);
            int j = 0;
            bw.write("Percentage of Parallel Programming" + "," + "1 thread" + "," + "2 Threads" + "," + "4 Threads" + "," + "8 Threads" + "," + "16 Threads"  + "\n");
            for (int i = 0; i < oneThreadList.size(); i++) {
                String content = (double) 10000 * (j + 1) / 2000000 + "," + (double) oneThreadList.get(i) / runs +  "," + (double) twoThreadList.get(i) / runs + "," + (double) fourThreadList.get(i) / runs + "," + (double) eightThreadList.get(i) / runs + "," + (double) sixteenThreadList.get(i) / runs + "\n";
                j++;
                bw.write(content);
                bw.flush();
            }
            bw.close();

        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    
    /**
     * Method to plot and provide results for parallel sorting with a fixed thread size and varying cutoffs 
     * 
     * @param threadSize	Thread pool size for parallel sorting
     * @param runs			Number of time the experiment should run 
     * @param startCutoff	Lower threshold for the cutoff
     * @param endCutoff		Upper threshold for the cutoff
     * @param increment		Increments by which the cutoff increases
     * @param arraySize		Array size for the experiment
     */
    private static void experimentWithFixedThreadSizeVaryingCutoffs(int threadSize, int runs, int startCutoff, int endCutoff, int increment, int arraySize) {
    	Random random = new Random();
    	int[] array = new int[arraySize];
    	XYSeries timeSeries = new XYSeries("Thread Count = 16");
    	Plotter plotter = new Plotter("Cutoff vs Time for Array Size = 1000000", "Cutoff", "Time");
    	ParSort.myPool = new ForkJoinPool(threadSize);
    	for(int i = startCutoff; i < endCutoff; i = i+increment) {
    		ParSort.cutoff = i;
    		long startTime = System.currentTimeMillis();
            for (int t = 0; t < runs; t++) {
                for (int j = 0; j < array.length; j++) array[j] = random.nextInt(arraySize*2);
                ParSort.sort(array, 0, array.length);
            }
            long endTime = System.currentTimeMillis();
            
            long time = (endTime - startTime);
            timeSeries.add(i, time/runs);
            System.out.println("For thread count: " + threadSize + " and cutoff: " + i + " the time taken is: " + time/runs);
    	}
    	
    	plotter.addSeries(timeSeries);
    	plotter.initUI();
    	plotter.setVisible(true);
    }
    
    /**
     * Method to plot and provide results for parallel sorting algorithm for varying thread pool sizes, fixed cutoff and varying array sizes
     * 
     * @param runs				Number of time the experiment should run 
     * @param cutoff			Cutoff for the parallel sorting algorithm after which it switches to sequential system sort
     * @param arraySizeStart	Lower threshold for the array size
     * @param doubleTimes		Number of times the array size to double
     */
    private static void experimentWithVaryingThreadSizeFixedCutoffs(int runs, int cutoff, int arraySizeStart, int doubleTimes) {
    	Random random = new Random();
    	ParSort.cutoff = cutoff;
    	Plotter plotter = new Plotter("Array Size vs Time for Cutoff = 600,000", "Size", "Time");
    	XYSeries threadOne = new XYSeries("Thread Count 1");
    	XYSeries threadTwo = new XYSeries("Thread Count 2");
    	XYSeries threadFour = new XYSeries("Thread Count 4");
    	XYSeries threadEight = new XYSeries("Thread Count 8");
    	XYSeries threadSixteen = new XYSeries("Thread Count 16");
    	XYSeries systemSort = new XYSeries("System Sort");
    	int size = arraySizeStart;
    	for(int i = 0; i < doubleTimes; i++) {
    		int[] array = new int[size];
	    	for(int k = 1; k < 17; k = k*2) {
	    		ParSort.myPool = new ForkJoinPool(k);
	    		long startTime = System.currentTimeMillis();
	            for (int t = 0; t < runs; t++) {
	                for (int j = 0; j < array.length; j++) array[j] = random.nextInt(size*2);
	                ParSort.sort(array, 0, array.length);
	            }
	            long endTime = System.currentTimeMillis();
	            
	            long time = (endTime - startTime);
	            if(k == 1) threadOne.add(size, time/runs);
	            else if(k == 2) threadTwo.add(size, time/runs);
	            else if(k == 4) threadFour.add(size, time/runs);
	            else if(k == 8) threadEight.add(size, time/runs);
	            else if(k == 16) threadSixteen.add(size, time/runs);

	            System.out.println("For thread count: " + k + " and size: " + size + " the time taken is: " + time/runs);
	            
	    	}	
	    	

	    	long startTime = System.currentTimeMillis();
	        for (int t = 0; t < runs; t++) {
	            for (int j = 0; j < array.length; j++) array[j] = random.nextInt(size*2);
	            Arrays.sort(array, 0, array.length);
	            
	        }
	        long endTime = System.currentTimeMillis();
	        
	        long time = (endTime - startTime);
	        System.out.println("Time taken by system sort is: " + time/runs);
	        systemSort.add(size, time/runs);
	        size = size*2;
    	}
    	plotter.addSeries(threadOne);
    	plotter.addSeries(threadTwo);
    	plotter.addSeries(threadFour);
    	plotter.addSeries(threadEight);
    	plotter.addSeries(threadSixteen);
    	plotter.addSeries(systemSort);
    	plotter.initUI();
    	plotter.setVisible(true);
    	
    }

    private static void processArgs(String[] args) {
        String[] xs = args;
        while (xs.length > 0)
            if (xs[0].startsWith("-")) xs = processArg(xs);
    }

    private static String[] processArg(String[] xs) {
        String[] result = new String[0];
        System.arraycopy(xs, 2, result, 0, xs.length - 2);
        processCommand(xs[0], xs[1]);
        return result;
    }

    private static void processCommand(String x, String y) {
        if (x.equalsIgnoreCase("N")) setConfig(x, Integer.parseInt(y));
        else
            // TODO sort this out
            if (x.equalsIgnoreCase("P")) //noinspection ResultOfMethodCallIgnored
                ForkJoinPool.getCommonPoolParallelism();
    }

    private static void setConfig(String x, int i) {
        configuration.put(x, i);
    }

    @SuppressWarnings("MismatchedQueryAndUpdateOfCollection")
    private static final Map<String, Integer> configuration = new HashMap<>();
}
