/*
 * Copyright (c) 2017. Phasmid Software
 */

package edu.neu.coe.info6205.randomwalk;

import java.util.Random;
import java.util.ArrayList;
import java.util.List;

import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartPanel;
import org.jfree.chart.JFreeChart;
import org.jfree.data.xy.XYSeries;
import org.jfree.data.xy.XYSeriesCollection;

import java.awt.*;

public class RandomWalk {

    private int x = 0;
    private int y = 0;

    private final Random random = new Random();

    /**
     * Private method to move the current position, that's to say the drunkard moves
     *
     * @param dx the distance he moves in the x direction
     * @param dy the distance he moves in the y direction
     */
    private void move(int dx, int dy) {
        this.x = x + dx;
        this.y = y + dy;
    }

    /**
     * Perform a random walk of m steps
     *
     * @param m the number of steps the drunkard takes
     */
    private void randomWalk(int m) {
        for(int i = 0; i < m; i++) {
        	this.randomMove();
        }
    }

    /**
     * Private method to generate a random move according to the rules of the situation.
     * That's to say, moves can be (+-1, 0) or (0, +-1).
     */
    private void randomMove() {
        boolean ns = random.nextBoolean();
        int step = random.nextBoolean() ? 1 : -1;
        move(ns ? step : 0, ns ? 0 : step);
    }

    /**
     * Method to compute the distance from the origin (the lamp-post where the drunkard starts) to his current position.
     *
     * @return the (Euclidean) distance from the origin to the current position.
     */
    public double distance() {
       
    	double distanceY = Math.abs(this.y - 0);
    	double distanceX = Math.abs(this.x - 0);
    	return Math.sqrt((distanceY)*(distanceY) + (distanceX)*(distanceX));
    			
    }

    /**
     * Perform multiple random walk experiments, returning the mean distance.
     *
     * @param m the number of steps for each experiment
     * @param n the number of experiments to run
     * @return the mean distance
     */
    public static double randomWalkMulti(int m, int n) {
        double totalDistance = 0;
        for (int i = 0; i < n; i++) {
            RandomWalk walk = new RandomWalk();
            walk.randomWalk(m);
            totalDistance = totalDistance + walk.distance();
        }
        return totalDistance / n;
    }
    
    /**
     * Divide the square root of steps from the average distance walked in those steps and add it to the list where we track the factor for all the steps.
     * Hence, we track the factor by which square root of steps differs from the average distance walked in those steps.
     * 
     * @param step the number of steps for the experiment
     * @param distance the average distance walked in those steps
     * @param factors the list keeping track of the step and distance relationship
     *
     */
    public static void findFactor(int step, double distance, List<Double> factors) {
    	factors.add(distance/Math.sqrt(step));
    }
    
    /**
     * Add the x and y value to the series
     * 
     * @param series to which the x value and y value should be added
     * @param x the x-value
     * @param y the y-value
     *
     */
    public static void addToSeries(XYSeries series, double x, double y) {
    	series.add(x, y);
    }

    public static void main(String[] args) {
    	
    	int n = 500;	//The number of times to repeat the experiment for each step
    	int m = 100;	//The number of steps 
    	
    	XYSeries meanDistanceSeries = new XYSeries("Mean Distance"); //Create a series used to plot the average distance for a random walk of m steps
    	XYSeries expectedSeries = new XYSeries("√Steps*0.88716"); //Create a series used to plot the derived expected distance for a random walk of m steps
    	XYSeries sqrtStepsSeries = new XYSeries("√Steps"); //Create a series used to plot the initial hypothesis for a random walk of m steps
        
    	List<Double> distanceStepsFactor = new ArrayList<>(); //List holding the coefficient of distance/square root of step m
        
    	//Run the experiment for 1 to m steps and find the distance averaged over each step run n number of times
    	for(int i = 0; i < m; i++) {
        	double meanDistance = randomWalkMulti(i+1, n);
        	double sqrtSteps = Math.sqrt((double) (i+1));
        	addToSeries(meanDistanceSeries, (i+1), meanDistance); 
        	addToSeries(sqrtStepsSeries, (i+1), sqrtSteps);
        	addToSeries(expectedSeries, (i+1), 0.88716*(sqrtSteps));
        	findFactor((i+1), meanDistance, distanceStepsFactor);
        	System.out.println(i+1 + " steps: " + meanDistance + " over " + n + " experiments");
        }
    	
    	//Aggregate sum of the coefficients 
    	double sumFactors = 0;
    	
    	//Traverse through the list of coefficients and find the average
    	for(double factor : distanceStepsFactor) {
    		sumFactors+=factor;
    	}
    	
    	//Find the average 
    	double avgFactor = sumFactors/m;
    	System.out.println("The average of the square root of the steps divided by the distance for: " + m + " steps is: " + avgFactor);
      
    	//Plot the results
    	RandomWalkPlotter plotter = new RandomWalkPlotter(meanDistanceSeries, sqrtStepsSeries, expectedSeries);
        plotter.setVisible(true);
   
    }
}
