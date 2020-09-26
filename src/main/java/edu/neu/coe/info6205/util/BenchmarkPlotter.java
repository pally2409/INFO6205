package edu.neu.coe.info6205.util;

import java.awt.Color;

import javax.swing.BorderFactory;
import javax.swing.JFrame;

import org.jfree.chart.ChartPanel;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.axis.LogarithmicAxis;
import org.jfree.chart.axis.NumberAxis;
import org.jfree.data.xy.XYSeries;
import org.jfree.data.xy.XYSeriesCollection;
import java.awt.BasicStroke;
import java.util.ArrayList;
import java.util.List;
import java.awt.Color;
import java.awt.Font;

import javax.swing.BorderFactory;
import javax.swing.JFrame;

import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartPanel;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.block.BlockBorder;
import org.jfree.chart.plot.PlotOrientation;
import org.jfree.chart.plot.XYPlot;
import org.jfree.chart.renderer.xy.XYLineAndShapeRenderer;
import org.jfree.chart.title.TextTitle;
import org.jfree.data.xy.XYDataset;
import org.jfree.data.xy.XYSeries;
import org.jfree.data.xy.XYSeriesCollection;

/**
 * 
 * @author pallaksingh
 * 
 * This class is used to plot the n (size) vs T (time) graph for the values produced during the benchmark test conducted 
 * in Benchmark_Timer.java
 *
 */
public class BenchmarkPlotter extends JFrame {
	
	/**
	 * Constructor to initialize a single series plot
	 * 
	 * @param series1 the series to be added to the dataset for plotting
	 */
	public BenchmarkPlotter(XYSeries series1) {
		initUI(series1);
	}
	
	/**
	 * Constructor to initialize a two series plot
	 * 
	 * @param series1 the first series to be added to the dataset for plotting
	 * @param series2 the second series to be added to the dataset for plotting
	 */
	public BenchmarkPlotter(XYSeries series1, XYSeries series2) {
		initUI(series1, series2);
	}
	
	/**
	 * Constructor to initialize a four series plot
	 * 
	 * @param series1 the first series to be added to the dataset for plotting
	 * @param series2 the second series to be added to the dataset for plotting
	 * @param series3 the third series to be added to the dataset for plotting
	 * @param series4 the fourth series to be added to the dataset for plotting
	 */
	public BenchmarkPlotter(XYSeries series1, XYSeries series2, XYSeries series3, XYSeries series4) {
		initUI(series1, series2, series3, series4);
	}
	
	/**
	 * Constructor to initialize a three series plot
	 * 
	 * @param series1 the first series to be added to the dataset for plotting
	 * @param series2 the second series to be added to the dataset for plotting
	 * @param series3 the third series to be added to the dataset for plotting
	 */
	public BenchmarkPlotter(XYSeries series1, XYSeries series2, XYSeries series3) {
		initUI(series1, series2, series3);
	}
	
	 /** Method to graph the plot of a single XYSeries
	 * 
	 * @param series1 the series to be added to the dataset and create a chart
	 */
	private void initUI(XYSeries series1) {
		
		XYSeriesCollection dataset = new XYSeriesCollection();
		dataset.addSeries(series1);
		
		JFreeChart chart = createChart(dataset, 1);
		
		ChartPanel chartPanel = new ChartPanel(chart);
		chartPanel.setBorder(BorderFactory.createEmptyBorder(15, 15, 15, 15));
		
		chartPanel.setBackground(Color.white);
		add(chartPanel);
		
		pack();
		setTitle("Benchmark Test");
		setLocationRelativeTo(null);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		
	}
	
	/**
	 * Method to graph the plot of a two XYSeries
	 * 
	 * @param series1 the series to be added to the dataset and create a chart
	 * @param series2 the series to be added to the dataset and create a chart
	 */
	private void initUI(XYSeries series1, XYSeries series2) {
		
		XYSeriesCollection dataset = new XYSeriesCollection();
		dataset.addSeries(series1);
		dataset.addSeries(series2);
		JFreeChart chart = createChart(dataset, 2);
		
		ChartPanel chartPanel = new ChartPanel(chart);
		chartPanel.setBorder(BorderFactory.createEmptyBorder(15, 15, 15, 15));
		
		chartPanel.setBackground(Color.white);
		add(chartPanel);
		
		pack();
		setTitle("Benchmark Test");
		setLocationRelativeTo(null);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		
	}
	
	/**
	 * Constructor to graph the plot of a three XYSeries
	 * 
	 * @param series1 the series to be added to the dataset and create a chart
	 * @param series2 the series to be added to the dataset and create a chart
	 * @param series3 the series to be added to the dataset and create a chart
	 */
	private void initUI(XYSeries series1, XYSeries series2, XYSeries series3) {
		
		XYSeriesCollection dataset = new XYSeriesCollection();
		dataset.addSeries(series1);
		dataset.addSeries(series2);
		dataset.addSeries(series3);
		JFreeChart chart = createChart(dataset, 3);
		
		ChartPanel chartPanel = new ChartPanel(chart);
		chartPanel.setBorder(BorderFactory.createEmptyBorder(15, 15, 15, 15));
		
		chartPanel.setBackground(Color.white);
		add(chartPanel);
		
		pack();
		setTitle("Benchmark Test");
		setLocationRelativeTo(null);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		
	}
	
	/**
	 * Constructor to graph the plot of a three XYSeries
	 * 
	 * @param series1 the series to be added to the dataset and create a chart
	 * @param series2 the series to be added to the dataset and create a chart
	 * @param series3 the series to be added to the dataset and create a chart
	 * @param series4 the series to be added to the dataset and create a chart
	 */
	private void initUI(XYSeries series1, XYSeries series2, XYSeries series3,  XYSeries series4) {
		
		XYSeriesCollection dataset = new XYSeriesCollection();
		dataset.addSeries(series1);
		dataset.addSeries(series2);
		dataset.addSeries(series3);
		dataset.addSeries(series4);
		JFreeChart chart = createChart(dataset, 4);
		
		ChartPanel chartPanel = new ChartPanel(chart);
		chartPanel.setBorder(BorderFactory.createEmptyBorder(15, 15, 15, 15));
		
		chartPanel.setBackground(Color.white);
		add(chartPanel);
		
		pack();
		setTitle("Benchmark Test");
		setLocationRelativeTo(null);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		
	}
	
	/**
	 * Private method to plot the graph from the dataset 
	 * 
	 * @param dataset The dataset consisting of series which are to plotted
	 * @param length The number of series in the dataset
	 * @return the chart GUI from the plotted dataset
	 */
	private JFreeChart createChart(XYDataset dataset, int length) {

        JFreeChart chart = ChartFactory.createXYLineChart(
                "Size vs Time",
                "n",
                "T",
                dataset,
                PlotOrientation.VERTICAL,
                true,
                true,
                false
        );
        
        XYPlot plot = chart.getXYPlot();

        XYLineAndShapeRenderer renderer = new XYLineAndShapeRenderer();
        
        List<Color> colors = new ArrayList<>();
        colors.add(Color.RED);
        colors.add(Color.BLUE);
        colors.add(new Color(0, 210, 0));
        colors.add(Color.ORANGE);
        for(int i = 0; i < length; i++) {
        	 renderer.setSeriesPaint(i, colors.get(i));
             renderer.setSeriesStroke(i, new BasicStroke(2.0f));
        }
      
        plot.setRenderer(renderer);
        plot.setBackgroundPaint(Color.white);

        plot.setRangeGridlinesVisible(true);
        plot.setRangeGridlinePaint(Color.BLACK);

        plot.setDomainGridlinesVisible(true);
        plot.setDomainGridlinePaint(Color.BLACK);

        chart.getLegend().setFrame(BlockBorder.NONE);

        chart.setTitle(new TextTitle("Size vs Time",
                        new Font("Serif", java.awt.Font.BOLD, 18)
                )
        );

        return chart;
    }
}
