/*
Kevin Baron
4/13/13
CSE 143 Assignment #4
Sieve of Eratosthenes
*/

public class Sieve {
	
	private Queue<Integer> q;//initial queue for for holding all numbers up to the given max
	private Queue<Integer> primes;//stores prime numbers up to the given max
	private int lastMax;//records the most recent max value provided by client
	
	//post: both queues have been constructed
	public Sieve() {
		q = new LinkedQueue<Integer>();
		primes = new LinkedQueue<Integer>();
	}//eo Sieve constructor
	
	//pre : given an integer greater than or equal to 2. throws IllegalArgumentException if not.
	//      q is empty. primes may be filled.
	//post: q is empty and primes is filled with all prime numbers up to n. lastMax has been recorded as n.
	public void computeTo(int n) {
		if (n < 2)
			throw new IllegalArgumentException();
		lastMax = n;//record this n value
		//fill q with all integers 2 through n
		for (int i = 2; i <= n; i++)
			q.enqueue(i);
		//make sure that primes is empty before using it
		while (!primes.isEmpty())
			primes.dequeue();
		//keep adding the first number of q to primes until...
		while (true) {
			int p = q.dequeue();
			primes.enqueue(p);
			//...the first number from q is greater than the square root of n.
			//at that point, the rest of the numbers in q are guaranteed to be prime,
			//so add them all to primes and break the while loop
			if (p * p > n) {
				while (!q.isEmpty())
					primes.enqueue(q.dequeue());
				break;
			}//eo if
			//when the first number from q is less than or equal to the square root of n,
			//keep grabbing from the front of q until...
			int starter = 0;
			while (true) {
				int test = q.dequeue();
				//...you reach the first number that has already been seen.
				//in that case, put this number at the back and recycle all the numbers
				//until increasing order has been restored.
				//then break the while loop
				if (test == starter){
					q.enqueue(test);
					for (int i = 1; i < q.size(); i++)
						q.enqueue(q.dequeue());
					break;
				}//eo if
				//for any new number, re-add it to the back of q if they are not divisible by prime number p;
				//if it is divisible by p, it will be disposed of.
				else
					if (test % p != 0) {
						q.enqueue(test);
						//record the first re-added number as starter
						if (starter == 0)
							starter = test;
					}//eo if
			}//eo while
		}//eo while
	}//eo compute to
	
	//pre : computeTo has been called validly at least once. throws IllegalStateException if not.
	//post: all prime numbers cumputed by the most recent computeTo call are reported, 12 numbers per line.
	//      primes is unchanged
	public void reportResults() {
		if (primes.isEmpty())
			throw new IllegalStateException();
		//make enough lines for all the prime numbers
		for (int i = 0; i <= primes.size() / 12; i++) {
			//on this line, print either 12 numbers or as many as possible before you run out, whichever comes first
			for (int j = 0; j < 12 && j + i * 12 < primes.size(); j++) {
				int p = primes.dequeue();
				System.out.print(p + " ");
				primes.enqueue(p);
			}//eo for
			System.out.println();
		}//eo for
	}//eo reportResults
	
	//pre : computeTo has been called validly at least once. throws IllegalStateException if not.
	//post: the most recent argument passed to computeTo has been returned
	public int getMax() {
		if (primes.isEmpty())
			throw new IllegalStateException();
		return lastMax;
	}//eo getMax
	
	//pre : computeTo has been called validly at least once. throws IllegalStateException if not.
	//post: the number of prime numbers in Queue primes has been returned
	public int getCount() {
		if (primes.isEmpty())
			throw new IllegalStateException();
		return primes.size();
	}//eo getCount
	
}//eo Sieve class