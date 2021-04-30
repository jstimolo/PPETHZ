package ethz.ch.pp.assignment3.counters;

//TODO: implement
public class SynchronizedCounter implements Counter {
	private int value;
	
	@Override
	public synchronized void increment() {
		value++;
	}

	@Override
	public int value() {
		return value;		
	}

}