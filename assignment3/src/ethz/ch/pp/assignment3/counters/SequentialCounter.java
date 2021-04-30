package ethz.ch.pp.assignment3.counters;

public class SequentialCounter implements Counter {
	private int value;
	
	@Override
	public synchronized void increment() {
		value++;		
	}

	@Override
	public int value() {
		return this.value;		
	}
}