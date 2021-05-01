package ethz.ch.pp.searchAndCount;

import ethz.ch.pp.util.Workload;

public class SumThread extends Thread {
	int lo; // arguments
	int hi;
	int[] arr;
	int numThreads;
	Workload.Type wt;

	int result = 0;

	
	
	SumThread(int[] a, int l, int h, int numThreads, Workload.Type wt) {
		lo = l;
		hi = h;
		arr = a;
		this.numThreads = numThreads;
		this.wt = wt;
	}

	
	
	public void run() {
		for (int i = lo; i < hi; i++) {
			if (Workload.doWork(arr[i], wt)) {
				result++;
			}
		}
	}
}
