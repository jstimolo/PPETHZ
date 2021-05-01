package ethz.ch.pp.searchAndCount;

import ethz.ch.pp.util.Workload;

public class SearchAndCountThreadDivideAndConquer extends Thread {
	public static int[] list;
	public static Workload.Type wlt;
	public static int cutoff,hi,lo,result;
	
	
	SearchAndCountThreadDivideAndConquer(int lo, int hi){
		this.lo = lo;
		this.hi = hi;
	}
	
	public static int countNoAppearances(int[] input, Workload.Type wt, int cutOff, int numThreads) {
		list = input;
		wlt = wt;
		cutoff = cutOff;
		hi = input.length-1;
		lo = 0;
		Thread t = new Thread();
		t.start();
		return result;
	}
	
	
	public void run(){
		System.out.println("hello");
		boolean heavy = (Workload.doWork(list[lo], wlt));
		int size = hi-lo;
		
		if (size < cutoff) {
			if(!heavy) {
				result = 0;
				return;
			} else {
				result = 1;
				return;
			}
		} 
		
		int mid = size/2;
		SearchAndCountThreadDivideAndConquer t1 = new SearchAndCountThreadDivideAndConquer(lo,mid);
		SearchAndCountThreadDivideAndConquer t2 = new SearchAndCountThreadDivideAndConquer(mid+1,hi);
		
		t1.start();
		t2.start();
		
		try {
			t1.join();
			t2.join();
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		result = t1.result + t2.result;
		return;
	}

}