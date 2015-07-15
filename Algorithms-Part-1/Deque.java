/*
*		AUTHOR:			AKSHAT SHAH
*		WRITTEN:		07/02/2015
*
*		COMPILATION:		javac Deque.java
*		EXECUTION:		java Deque
*
*		JAR Files:		http://algs4.cs.princeton.edu/code/index.php
*
*		DESCRIPTION:		Creating a Double-ended Queue (Deque) using Linked-lists, and making basic operations on it.
*/
import java.util.Iterator;

public class Deque<Item> implements Iterable<Item> 
{

	private int N;
	private Node first;
	private Node last;

	private class Node
	{
		private Item item;
		private Node next;
		private Node prev;
	}

	public Deque()                           // construct an empty deque
	{
		first = null;
		last = null;
		N = 0;
	}

	public boolean isEmpty()                 // is the deque empty?
	{
		return first == null;
	}

	public int size()                        // return the number of items on the deque
	{
		return N;
	}

	public void addFirst(Item item)          // add the item to the front
	{
		if (item == null) throw new java.lang.NullPointerException("Null item entered");

		if (first == null)
		{
			first = new Node();
			first.item = item;
			N++;
			last = first;

			return;
		}

		Node oldfirst = first;			// A    B
		first = new Node();
		first.item = item;
		first.next = oldfirst;			// First ---> Oldfirst
		oldfirst.prev = first;			// First <==> Oldfirst

		N++;
	}

	public void addLast(Item item)           // add the item to the end
	{
		if (item == null) throw new java.lang.NullPointerException("Null item entered");

		if (first == null)
		{
			first = new Node();
			first.item = item;
			N++;
			last = first;

			return;
		}

		Node oldlast = last;
		last = new Node();
		last.item = item;					
		last.prev = oldlast;			// Oldlast <--- Last 
		oldlast.next = last;			// Oldlast <==> Last

		N++;
	}

	public Item removeFirst()                // remove and return the item from the front
	{
		if (isEmpty()) throw new java.util.NoSuchElementException("Empty Deque");

		Item item = first.item;

		if (N == 1)
		{
			first = null;
			last = null;
			N--;
			return item;
		}

		first = first.next;
		first.prev = null;
		N--;

		return item;
	}

	public Item removeLast()                 // remove and return the item from the end
	{
		if (isEmpty()) throw new java.util.NoSuchElementException("Empty Deque");

		Item item = last.item;

		if (N == 1)
		{
			first = null;
			last = null;
			N--;
			return item;
		}

		last = last.prev;
		last.next = null;
		N--;

		return item;
	}

	public Iterator<Item> iterator()         // return an iterator over items in order from front to end
	{
		return new ListIterator();
	}

	private class ListIterator implements Iterator<Item>
	{
		private Node current = first;
		
		public boolean hasNext()
		{
			return current != null;
		}

		public void remove()
		{
			throw new java.lang.UnsupportedOperationException();
		}

		public Item next()
		{
			if (!hasNext()) throw new java.util.NoSuchElementException();
			
			Item item = current.item;
			current = current.next; 
			return item;
		}
	}

	public static void main(String[] args)   // unit testing
	{
		Deque<Integer> test = new Deque<Integer>();
		test.addFirst(10);
		test.addLast(20);

		while(test.iterator().hasNext())
			StdOut.println(test.iterator().next());
	}
}
