package ethz.ch.pp.assignment3.threads;



import ethz.ch.pp.assignment3.counters.Counter;

public class FairThreadCounter extends ThreadCounter {

	public FairThreadCounter(Counter counter, int id, int numThreads, int numIterations) {
		super(counter, id, numThreads, numIterations);
	}

	

	@Override
	public void run() {
		int counter = 0;
		
		for (int i = 0; i < numIterations; i++) {
			if (this.id == i % numThreads) {
				this.counter.increment();
				counter++;
			}
		}
		System.out.println("Thread: "+this.id+" made "+counter+" operations.");
	}
}
