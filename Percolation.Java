/*
*		AUTHOR:			AKSHAT SHAH
*		WRITTEN:		06/28/2015
*
*		COMPILATION:	javac Percolation.java
*		EXECUTION:		java Percolation sample_input_file.txt
*
*		JAR Files:		http://algs4.cs.princeton.edu/code/index.php
*
*		DESCRIPTION:	An application of the dynamic connectivity problem sovled using Quick Union (Union Find) Algorithm
*/
public class Percolation
{
	private int N, Nsq;			// variable to store Grid size
	private boolean[][] site;
	private WeightedQuickUnionUF uf;		//Weighted Quick Union algorithm using Algs4.jar
	private WeightedQuickUnionUF uf_backwash;;
	private boolean percolated = false;

	public Percolation(int N)	// create N-by-N grid, with all sites blocked
	{
		if (N <= 0)
		{
			throw new java.lang.IllegalArgumentException("N must be larger than 0");
		}

		this.N = N;
		Nsq = N*N;
		site = new boolean[N][N];
		uf = new WeightedQuickUnionUF(Nsq+2);
		uf_backwash = new WeightedQuickUnionUF(Nsq+1);
	}

	private int getPosition(int i, int j)		// converts 2D-grid position to 1D-array index
	{
		int pos = i*N + j;
		return pos;
	}

	private void validateInput(int i, int j) 	// validates grid indices
	{
		if (i < 0 || i >= N)
		{
			throw new IndexOutOfBoundsException("Index i is out of bounds");
		}
		if (j < 0 || j >= N)
		{
			throw new IndexOutOfBoundsException("Index j is out of bounds");
		}
	}

	public void open(int i, int j)        	  // open site (row i, column j) if it is not open already
	{
		i--;
		j--;

		validateInput(i, j);

		if (!site[i][j])
		{
			int pos = getPosition(i, j);
			site[i][j] = true;
			int pos2;

			if (i > 0 && isOpen(i, j+1))		// Union with upper site if open
			{
				pos2 = getPosition(i-1, j);
				uf.union(pos, pos2);
				uf_backwash.union(pos, pos2);
			}

			if (i < N-1 && isOpen(i+2, j+1))	// Union with lower site if open
			{
				pos2 = getPosition(i+1, j);
				uf.union(pos, pos2);
				uf_backwash.union(pos, pos2);
			}

			if (j > 0 && isOpen(i+1, j))		// Union with left-hand site if open
			{
				pos2 = getPosition(i, j-1);
				uf.union(pos, pos2);
				uf_backwash.union(pos, pos2);
			}

			if (j < N-1 && isOpen(i+1, j+2))	// Union with right-hand site if open
			{
				pos2 = getPosition(i, j+1);
				uf.union(pos, pos2);
				uf_backwash.union(pos, pos2);
			}

			if (i == 0)							// Union with Virtual Top node
			{
				uf.union(Nsq, pos);
				uf_backwash.union(Nsq, pos);
			}

			if (i == N-1)						// Union with Virtual Bottom node
			{
				uf.union((Nsq+1), pos);
			}
		}
	}

	public boolean isOpen(int i, int j)     // is site (row i, column j) open?
	{
		i--;
		j--;

		validateInput(i, j);

		return site[i][j];
	}


	public boolean isFull(int i, int j)     // is site (row i, column j) full?
	{
		
		i--;
		j--;

		validateInput(i, j);

		if (!site[i][j])
		{
			return false;
		}

		int pos = getPosition(i, j);
		return uf_backwash.connected(pos, Nsq);
	}

	public boolean percolates()             // does the system percolate?
	{
		if (!percolated)
		{
			percolated = uf.connected(Nsq, Nsq+1);
		}

		return percolated;
	}

	public static void main(String[] args)   // test client (optional)
	{
		In in = new In(args[0]);			// Accessing the input file
		int N = in.readInt();				
		Percolation perc = new Percolation(N);	// N-by-N percolation system

		while (!in.isEmpty())
		{
			int i = in.readInt();
			int j = in.readInt();
			perc.open(i, j);
		}
	}
}
