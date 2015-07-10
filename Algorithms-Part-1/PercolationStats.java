/*
*		AUTHOR:			AKSHAT SHAH
*		WRITTEN:		06/29/2015
*
*		COMPILATION:		javac PercolationStats.java
*		EXECUTION:		java PercolationStats
*
*		JAR Files:		http://algs4.cs.princeton.edu/code/index.php
*
*		DESCRIPTION:		Generating standard statistics using the Percolation class
*/
public class PercolationStats
{
	private int T;
	private double[] result;

	public PercolationStats(int N, int T)     // perform T independent experiments on an N-by-N grid
	{
		if (N <= 0)
		{
			throw new java.lang.IllegalArgumentException("N must be larger than 0");
		}
		if (T <= 0)
		{
			throw new java.lang.IllegalArgumentException("T must be larger than 0");
		}

		this.T = T;
		result = new double[T];

		for (int i = 0; i < T; i++)
		{
			double count = monteCarloSimulation(N);
			result[i] = count/(N*N);
		}
	}

	private double monteCarloSimulation(int N)
	{
		Percolation perc = new Percolation(N);
		double count = 0;
		while (!perc.percolates())
		{
			int i = 1 + StdRandom.uniform(N);
			int j = 1 + StdRandom.uniform(N);

			if (!perc.isOpen(i, j))
			{
				perc.open(i, j);
				count++;
			}
		}
		return count;
	}

	public double mean()                      // sample mean of percolation threshold
	{
		return StdStats.mean(result);
	}

	public double stddev()                    // sample standard deviation of percolation threshold
	{
		return StdStats.stddev(result);
	}

	public double confidenceLo()              // low  endpoint of 95% confidence interval
	{
		double mean = mean();
		double stddev = stddev();
		double loConfidence = mean - 1.96 * stddev / Math.sqrt(T);

		return loConfidence;
	}

	public double confidenceHi()              // high endpoint of 95% confidence interval
	{
		double mean = mean();
		double stddev = stddev();
		double highConfidence = mean + 1.96 * stddev / Math.sqrt(T);

		return highConfidence;
	}

	public static void main(String[] args)    // test client (described below)
	{
		int N = StdIn.readInt();
		int T = StdIn.readInt();

		PercolationStats test = new PercolationStats(N, T);

		double mean = test.mean();
		double sd = test.stddev();
		double loConfidence = test.confidenceLo();
		double highConfidence = test.confidenceHi();

		StdOut.println("Mean : " + mean);
		StdOut.println("Standard Deviation : " + sd);
		StdOut.println("95% Confidence Interval : " + loConfidence + ", " + highConfidence);
	}
}
