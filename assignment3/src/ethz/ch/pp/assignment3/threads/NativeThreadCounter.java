package ethz.ch.pp.assignment3.threads;

import ethz.ch.pp.assignment3.counters.Counter;

public class NativeThreadCounter extends ThreadCounter {
		
	public NativeThreadCounter(Counter counter, int id, int numThreads, int numIterations) {
		super(counter, id, numThreads, numIterations);
	}

	@Override
	public void run() {
		System.out.println("Thread-id: "+super.id+" locked the function.");
		for (int i = 0; i < numIterations; i++) {
			counter.increment();
		}
	}	
}
