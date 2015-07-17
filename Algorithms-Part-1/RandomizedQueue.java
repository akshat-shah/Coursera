/*
*		AUTHOR:			AKSHAT SHAH
*		WRITTEN:		07/03/2015
*
*		COMPILATION:	javac RandomizedQueue.java
*		EXECUTION:		java RandomizedQueue
*
*		JAR Files:		http://algs4.cs.princeton.edu/code/index.php
*
*		DESCRIPTION:	Implementing a Randomized Queue using an Array 
*/
import java.util.Iterator;

public class RandomizedQueue<Item> implements Iterable<Item> 
{
	private Item[] q;						// queue elements
	private int N;
	private int first;						// index of first element of queue
	private int last;						// index of next available slot

	public RandomizedQueue()                 // construct an empty randomized queue
	{
		q = (Item[]) new Object[2];
		N = 0;
		first = 0;
		last = 0;
	}

	public boolean isEmpty()                 // is the queue empty?
	{
		return N == 0;
	}

	public int size()                        // return the number of items on the queue
	{
		return N;
	}

	public void enqueue(Item item)           // add the item
	{
		if (item == null) throw new java.lang.NullPointerException("Null item entered");

		if (N == q.length)
			resize(2*q.length);

		q[last++] = item;

		if (last == q.length)
			last = 0;						// wrap around
		N++;
	}

	public Item dequeue()                    // remove and return a random item
	{
		if (isEmpty()) throw new java.util.NoSuchElementException("Queue underflow");

		int index = (first + StdRandom.uniform(N)) % q.length;
		Item item = q[index];
		q[index] = q[first];
		q[first] = null;
		N--;
		first++;

		if (first == q.length)
			first = 0;

		if (N > 0 && N == q.length/4)
			resize(q.length/2);
		
		return item;

	}

	private void resize(int max)
	{
		Item[] temp = (Item[]) new Object[max];
		for (int i = 0; i < N; i++)
		{
			temp[i] = q[(first + i) % q.length];
		}
		q = temp;
		first = 0;
		last = N;
	}

	public Item sample()                     // return (but do not remove) a random item
	{
		if (isEmpty()) throw new java.util.NoSuchElementException("Queue underflow");

		int index = (first + StdRandom.uniform(N)) % q.length;
		return q[index];
	}

	public Iterator<Item> iterator()         // return an independent iterator over items in random order
	{
		return new ArrayIterator();
	}

	private class ArrayIterator implements Iterator<Item>
	{
		private int i = 0;
		private int arr[];

		private ArrayIterator()
		{
			arr = new int[N];
			for (int index = 0; index < N; index++)
			{
				arr[index] = (first + index) % q.length;
			}
			StdRandom.shuffle(arr);
		}

		public boolean hasNext()
		{
			return i < N;
		}

		public void remove()
		{
			throw new java.lang.UnsupportedOperationException();
		}

		public Item next()
		{
			if (!hasNext()) throw new java.util.NoSuchElementException();
			
			Item item = q[arr[i]];
			i++;
			return item;
		}
	}

	public static void main(String[] args)   // unit testing
	{
		RandomizedQueue<Integer> test = new RandomizedQueue<Integer>();

		test.enqueue(1);
		test.enqueue(2);
		test.enqueue(3);
		test.enqueue(4);
		test.enqueue(5);
		StdOut.println(test.dequeue());
	}
}