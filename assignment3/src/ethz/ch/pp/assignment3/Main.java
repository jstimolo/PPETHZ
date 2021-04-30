package ethz.ch.pp.assignment3;

import java.util.ArrayList;
import java.util.List;

import ethz.ch.pp.assignment3.counters.AtomicCounter;
import ethz.ch.pp.assignment3.counters.Counter;
import ethz.ch.pp.assignment3.counters.SequentialCounter;
import ethz.ch.pp.assignment3.counters.SynchronizedCounter;
import ethz.ch.pp.assignment3.threads.ThreadCounterFactory;
import ethz.ch.pp.assignment3.threads.ThreadCounterFactory.ThreadCounterType;


public class Main {

	public static void count(final Counter counter, int numThreads, ThreadCounterType type, int numInterations) {
		List<Thread> threads = new ArrayList<Thread>();
		for (int i = 0; i < numThreads; i++) {
			threads.add(new Thread(ThreadCounterFactory.make(type, counter, i, numThreads, numInterations)));
		}

		for (int i = 0; i < numThreads; i++) {
			threads.get(i).start();
		}
		
		for (int i = 0; i < numThreads; i++) {
			try {
				threads.get(i).join();
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
	}
	
	public static void main(String[] args) {
		System.out.println("\nTask A sequential:\n");
		taskASequential();
		System.out.println("\nTask A parallel:\n");
		taskAParallel();
		System.out.println("\nTask B:\n");
		taskB();
		System.out.println("\nTask D:\n");
		taskD();
		System.out.println("\nTask E:\n");
		taskE();
	}

	/*
	 * To start with, implement a sequential version of the Counter in
	 * SequentialCounter class that does not use any synchronization. That is, the
	 * counter simply increments an integer value by one. We already provide code in
	 * taskASequential method that runs a single thread that increments the counter.
	 * Inspect the code and understand how it works. Verify that the
	 * SequentialCounter works properly when used with a single thread (the test
	 * testSequentialCounter should pass). Now run the code in taskAParallel which
	 * creates several threads that all try to increment the counter at the same
	 * time. Notice how the expected value of counter at the end of the execution is
	 * not what we would expect. Discuss why this is the case.
	 */
	public static void taskASequential(){
		System.out.println("Task A, Sequential:");
		Counter counter = new SequentialCounter();
		count(counter, 1, ThreadCounterType.NATIVE, 100000);
		System.out.println("Counter: " + counter.value());
		System.out.println();
	}

	public static void taskAParallel(){
		System.out.println("Task A, Synchronized:");
		Counter counter = new SequentialCounter();
		count(counter, 4, ThreadCounterType.NATIVE, 100000);
		System.out.println("Counter: " + counter.value());
		System.out.println();
	}

	
	
	
	/*
	 * To fix this issue, implement a different thread safe version of the Counter
	 * in SynchronizedCounter. In this version use the standard primitive type int
	 * but synchronize the access to the variable by inserting synchronized blocks.
	 * Run the code in taskB.
	 */
	/*
	 * Whenever the Counter is incremented, keep track which thread performed the
	 * increment (you can print out the thread-id to the console). Can you see a
	 * pattern in how the threads are scheduled? Discuss what might be the reason
	 * for this behaviour.
	 * 
	 * ANSWER:
	 * Since the method increment() in the object counter is synchronized, Each
	 * thread locks the function one after the other. This implied that increment()
	 * is a critical section in the code and is therefore executed sequencially by
	 * the threads.
	 */
	public static void taskB(){
		System.out.println("task B:");
		Counter counter = new SynchronizedCounter();
		count(counter, 4, ThreadCounterType.NATIVE, 100000);
		System.out.println("Counter: " + counter.value());
		System.out.println();
	}
	
	
	
	
	/*
	 * Implement a FairThreadCounter that ensures that different threads increment
	 * the Counter in an round-robin fashion. In round-robin scheduling the threads
	 * perform the increments in circular order. That is, two threads with ids 1 and
	 * 2 would increment the value in the following order 1, 2, 1, 2, 1, 2, etc. You
	 * should implement the scheduling using the wait and notify methods. Can you
	 * think of an implementation that does not use wait and notify methods?
	 * (Optional) Extend your implemenation to work with arbitrary number of
	 * threads (instead of only 2) that increment the counter in round-robin
	 * fashion.
	 */
	public static void taskD(){
		Counter counter = new SynchronizedCounter(); //which type of counter can we use here? => synchonized
		count(counter, 4, ThreadCounterType.FAIR, 100000);
		System.out.println("Counter: " + counter.value());
	}
	
	
	
	public static void taskE(){
		Counter counter = new AtomicCounter();
		count(counter, 4, ThreadCounterType.NATIVE, 100000);
		System.out.println("Counter: " + counter.value());
	}
	

}
