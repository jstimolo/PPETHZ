package ethz.ch.pp.assignment2;

import java.util.Random;

public class Main{

	public static void main(String[] args) throws InterruptedException {
 		
		//taskA();
		
		int[] input1 = generateRandomInput(1000);
		int[] input2 = generateRandomInput(10000);
		int[] input3 = generateRandomInput(100000);
		int[] input4 = generateRandomInput(1000000);
		
		// Sequential version
//		taskB(input1);
//		taskB(input2);
//		taskB(input3);
//		taskB(input4);
		
		// Parallel version
//		taskE(input1, 4);
//		taskE(input2, 4);
//		taskE(input3, 4);
//		taskE(input4, 4);
		
		multiThreading(input2, 1);
		
		
		long threadOverhead = taskC();
		System.out.format("Thread overhead on current system is: %d nano-seconds\n", threadOverhead);		
	}
	
	private final static Random rnd = new Random(42);

	public static int[] generateRandomInput() {
		return generateRandomInput(rnd.nextInt(10000) + 1);
	}
	
	public static int[] generateRandomInput(int length) {	
		int[] values = new int[length];		
		for (int i = 0; i < values.length; i++) {
			values[i] = rnd.nextInt(99999) + 1;				
		}		
		return values;
	}
	
	public static int[] computePrimeFactors(int[] values) {		
		int[] factors = new int[values.length];	
		for (int i = 0; i < values.length; i++) {
			factors[i] = numPrimeFactors(values[i]);
		}		
		return factors;
	}
	
	public static int numPrimeFactors(int number) {
		int primeFactors = 0;
		int n = number;		
		for (int i = 2; i <= n; i++) {
			while (n % i == 0) {
				primeFactors++;
				n /= i;
			}
		}
		return primeFactors;
	}
	
	public static class ArraySplit {
		public final int startIndex;
		public final int length;
		
		ArraySplit(int startIndex, int length) {
			this.startIndex = startIndex;
			this.length = length;
		}
	}
	
	// TaskD
	public static ArraySplit[] PartitionData(int length, int numPartitions) {
		/*
		 * Before you parallelize the loop in task E), design how the work should be
		 * split between the threads by implementing method PartitionData. Each thread
		 * should process roughly equal amount of elements. Briefly describe your
		 * solution and discuss alternative ways to split the work.
		 */
		ArraySplit[] subArrays = new ArraySplit[numPartitions];
		
		
		// If the length of the array mod numPartitions is zero => Simply divide the
		// array in num of parts: 
		if (length % numPartitions == 0) {
			int subLength = length/numPartitions;
			int subIndex = 0;
			for(int i=0; i<numPartitions; i++) {
				subArrays[i] = new ArraySplit(subIndex, subLength);
				subIndex += subLength;
			}
		}else {
			//If there is a rest => Subtract the rest and devide the array:
			int rest = length % numPartitions;
			int subLength = (length-rest)/numPartitions;
			int subIndex = 0;
			//Add the rest to the last subArray;
			for(int i=0; i<numPartitions-1; i++) {
				subArrays[i] = new ArraySplit(subIndex, subLength);
				subIndex += subLength;
			}
			subArrays[numPartitions-1] = new ArraySplit(subIndex, subLength+rest);
		}
		
		return subArrays;
	}
	
	public static void taskA() throws InterruptedException {
		/*
		 * To start with, print to the console "Hello Thread!" from a new thread. How do
		 * you check that the statement was indeed printed from a thread that is
		 * different to the main tread of your application? Furthermore, ensure that you
		 * program (i.e., the execution of main thread) finishes only after the thread
		 * execution finishes.
		 */
		Thread t = new Thread();
		
		//If the thread is the main thread, the Name would be "main" and not thread-*:
		System.out.println("\"Hello Thread\" from Thread: "+t.getId());
		t.start();
		t.join(1000); //main-thread waits until t finished. (Throws exception), delay of 1 sec
	}

	
	
	public static int[] taskB(final int[] values) throws InterruptedException {
		/*
		 * Run the method computePrimeFactors in a single thread other than the main
		 * thread. Measure the execution time of sequential execution (on the main
		 * thread) and execution using a single thread. Is there any noticeable
		 * difference?
		 */
		
		
		System.out.println("--------------------");
		System.out.print("main-thread: ");
		long timerSeq_start = System.nanoTime();
		for(int i=0; i<values.length; i++) {
			numPrimeFactors(values[i]); //with main Thread
		}
		long timerSeq_end = System.nanoTime();
		System.out.println((double)(timerSeq_end-timerSeq_start)/1.0e9+" s");
		
		
		System.out.print("thr1-thread: ");
		long timerPar_start = System.nanoTime();
		for(int i=0; i<values.length; i++) {
			final int value = values[i];
			Thread t1 = new Thread(new Runnable() {
			    @Override
			    public void run() {
			    	numPrimeFactors(value);
			    }
			});  
			t1.start();
		}
		long timePar_end = System.nanoTime();
		System.out.println((double)(timerSeq_end-timerSeq_start)/1.0e9+" s");
		
		System.out.println((timerSeq_end-timerSeq_start)-(timerSeq_end-timerSeq_start)+" ns difference");
		return values;
	}
	
	
	
	// Returns overhead of creating thread in nano-seconds
	public static long taskC() {
		/*
		 * Design and run an experiment that would measure the overhead of creating and
		 * executing a thread. That is, the time it takes to create a thread object with
		 * empty runnable, calling the start method and waiting for the thread to finish
		 * execution.
		 */
		
		long start = System.nanoTime();
        Thread t = new Thread() {
            public void run() {
                //Empty runnable
            }
        };
        t.start();
        try {
            t.join();
        } catch (Exception e) {System.out.println(e);}
        long end = System.nanoTime();
        
        long delta = end - start;
        return delta;
    }

	
	public static int[] taskE(final int[] values, final int numThreads) {
		/*
		 * Parallelize the loop execution in computePrimeFactors using a configurable
		 * amount of threads.
		 */
		ArraySplit[] split = PartitionData(values.length, numThreads);
        Thread[] threads = new Thread[numThreads];
        int[] factors = new int[values.length];
        
        for (int i = 0; i < numThreads; ++i) {
            int index = i;
            threads[i] = new Thread() {
                public void run() {
                    for (int j = 0; j < split[index].length; ++j) {
                        int index2 = split[index].startIndex + j;
                        factors[index2] = numPrimeFactors(values[index2]);
                    }
                }
            };
            threads[i].start();
        }
        
        for (int i = 0; i < numThreads; ++i) {
            try {
                threads[i].join();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
        
        return factors;
	}
	
	public static void multiThreading(final int[] values, final int numThreads) throws InterruptedException {
		ArraySplit[] split = PartitionData(values.length, numThreads);
        Thread[] threads = new Thread[numThreads];
        int[] factors = new int[values.length];
        
        if(numThreads > 1000) {
        	System.exit(-1);
        }
        
        
        System.out.println("--------------------");
        System.out.println("Number of threads: "+numThreads);
		System.out.print("main-thread: ");
		long timerSeq_start = System.nanoTime();
		for(int i=0; i<values.length; i++) {
			numPrimeFactors(values[i]); //with main Thread
		}
		long timerSeq_end = System.nanoTime();
		System.out.println((double)(timerSeq_end-timerSeq_start)/1.0e9+" s");
        
        
        
        
		long timerPar_start = System.nanoTime();
        for (int i = 0; i < numThreads; ++i) {
            int index = i;
            threads[i] = new Thread() {
                public void run() {
                    for (int j = 0; j < split[index].length; ++j) {
                        int index2 = split[index].startIndex + j;
                        factors[index2] = numPrimeFactors(values[index2]);
                    }
                }
            };
            threads[i].start();
        }
        
        for (int i = 0; i < numThreads; ++i) {
            try {
                threads[i].join();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
        long timerPar_stop = System.nanoTime();
        System.out.println("Thrd-Thread: "+(timerPar_stop-timerPar_start)/1.0e9+" s");
        System.out.println("--------------------");
        Thread.sleep(2000);
        multiThreading(values, numThreads*2);
	}
}